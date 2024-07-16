package fxDeals.parser.Impl;


import fxDeals.Exceptions.DuplicateDealFileException;
import fxDeals.Exceptions.EmptyTransactionFileException;
import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.dto.TransactionDTO;
import fxDeals.entity.Deals;
import fxDeals.model.Violation;
import fxDeals.parser.TransactionParser;
import fxDeals.repository.DealRepository;
import fxDeals.repository.ViolationRepository;
import fxDeals.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CsvTransactionParser implements TransactionParser {

    private static final Logger logger = Logger.getLogger(CsvTransactionParser.class.getName());
    private final TransactionValidator transactionValidator;
    private DealRepository dealRepository;

    private ViolationRepository violationRepository;

    @Autowired
    public CsvTransactionParser(TransactionValidator transactionValidator, DealRepository dealRepository, ViolationRepository violationRepository) {
        this.transactionValidator = transactionValidator;
        this.dealRepository = dealRepository;
        this.violationRepository = violationRepository;
    }

    @Override
    public List<Deals> parse(MultipartFile transactionsFile) {
        List<Deals> transactions = new ArrayList<>();
        Map<Integer, List<Violation>> fileViolations = new HashMap<>();
        transactionValidator.validateFile(transactionsFile);

        if (violationRepository.findByFileName(transactionsFile.getOriginalFilename()).isPresent()) {
            logger.severe("Duplicate Deals file request with name: " + transactionsFile.getOriginalFilename());
            throw new DuplicateDealFileException("Duplicate Deals file request with name: " + transactionsFile.getOriginalFilename());
        }
        doParse(transactionsFile, transactions, fileViolations);
        if (!fileViolations.isEmpty()) {

            throw new TransactionsFileException(fileViolations, transactions);
        }
        return transactions;
    }

    private void doParse(MultipartFile transactionsFile, List<Deals> deals, Map<Integer, List<Violation>> fileViolations) {
        List<String> lines = readAllLines(transactionsFile);
        List<Violation> duplicateViolations = new ArrayList<>();

        if (lines.size() == 0)
            throw new EmptyTransactionFileException("there's no data in the file, the file is empty!");

        for (int i = 0; i < lines.size(); i++) {
            if (i == 0)
                continue;
            String[] transactionsArray = lines.get(i).split(",");
            if (isDealsRecordsNull(transactionsArray))
                continue;

            //check if the record is duplicated

            if (dealRepository.findByDealUniqueId(transactionsArray[0]).isPresent()) {
                logger.info("duplicate record --> " + transactionsArray[0]);

                duplicateViolations.add(new Violation("duplicate", i + " ->a duplicate record with id ->" + transactionsArray[0]));

                continue;

            }
            validateLineLength(fileViolations, i, transactionsArray.length);

            TransactionDTO dto = getTransactionDTO(transactionsArray);
            List<Violation> violations = transactionValidator.validateTransactionFile(dto);
            if (violations.isEmpty()) {

                deals.add(createNewTransaction(dto));
            } else {
                if (!duplicateViolations.isEmpty()) {
                    violations.addAll(duplicateViolations);
                    duplicateViolations=new ArrayList<>();
                }
                fileViolations.put(i, violations);
            }
        }
        if (!duplicateViolations.isEmpty())
            fileViolations.put(0, duplicateViolations);

        if(deals.isEmpty() && fileViolations.isEmpty())
            throw new EmptyTransactionFileException("there's no data in the file, the file is empty!");
    }

    public boolean isDealsRecordsNull(String[] deals) {
        if (deals == null || deals.length == 0) {
            return true; // Array is null or empty
        }

        for (String str : deals) {
            if (str != null && !str.trim().isEmpty()) {
                return false; // Found a non-empty string
            }
        }
        return true; // All strings are empty
    }

    private List<String> readAllLines(MultipartFile file) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new TransactionsFileException("cannot read transactions file due to " + e.getMessage(), e);
        }
    }

    private void validateLineLength(Map<Integer, List<Violation>> fileViolations, int lineNumber, int length) {
        if (length != 5) {
            fileViolations.put(lineNumber, Collections.singletonList(new Violation("fields count", "invalid fields count")));
            throw new TransactionsFileException(fileViolations);
        }
    }

    private TransactionDTO getTransactionDTO(String[] transactionsArray) {
        TransactionDTO dto = new TransactionDTO();
        dto.setDealUniqueId(transactionsArray[0]);
        dto.setFromCurrencyIsoCode(transactionsArray[1]);
        dto.setToCurrencyIsoCode(transactionsArray[2]);
        dto.setDealTimestamp(transactionsArray[3]);
        dto.setDealAmount(transactionsArray[4]);
        return dto;
    }

    private Deals createNewTransaction(TransactionDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
        Deals deal = new Deals();
        deal.setToCurrencyIsoCode(Currency.getInstance(dto.getToCurrencyIsoCode()));
        deal.setFromCurrencyIsoCode(Currency.getInstance(dto.getFromCurrencyIsoCode()));
        deal.setDealAmount(BigDecimal.valueOf(Double.valueOf(dto.getDealAmount())));
        deal.setDealUniqueId(dto.getDealUniqueId());
        deal.setDealTimestamp(LocalDateTime.parse(dto.getDealTimestamp(), formatter));
        return deal;
    }

}