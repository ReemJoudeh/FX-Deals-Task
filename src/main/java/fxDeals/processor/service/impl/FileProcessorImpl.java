package fxDeals.processor.service.impl;

import fxDeals.Exceptions.EmptyTransactionFileException;
import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.entity.Deals;
import fxDeals.entity.Violation;
import fxDeals.factory.TransactionParserFactory;
import fxDeals.parser.TransactionParser;
import fxDeals.processor.service.FileProcessorService;
import fxDeals.repository.DealRepository;
import fxDeals.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FileProcessorImpl implements FileProcessorService {
    private static final Logger logger = Logger.getLogger(FileProcessorImpl.class.getName());
    private final TransactionParserFactory transactionParserFactory;
    private final DealRepository dealRepository;
    private final ViolationRepository violationRepository;

    @Autowired
    public FileProcessorImpl(TransactionParserFactory transactionParserFactory, DealRepository dealRepository, ViolationRepository violationRepository) {
        this.transactionParserFactory = transactionParserFactory;
        this.dealRepository = dealRepository;
        this.violationRepository = violationRepository;
    }

    @Override
    public void processFiles(MultipartFile inputFile) {
        logger.info("Start processing the file ..");

        parse(inputFile);


    }


    private void parse(MultipartFile fileEntry) {
        logger.info("Parsing the file ..");
        try {
            TransactionParser parser = transactionParserFactory.createParser(fileEntry.getOriginalFilename());
            List<Deals> acceptedDeals = parser.parse(fileEntry);
            if (!acceptedDeals.isEmpty())
                dealRepository.saveAll(acceptedDeals);
            else {
                throw new EmptyTransactionFileException("There's no deals to insert, the file is empty ot there's a duplicated records.");
            }
        } catch (TransactionsFileException e) {
            if (!e.getDeals().isEmpty() ) {
                logger.info("inserting accepted deals ..");
                dealRepository.saveAll(e.getDeals());
            }

            Violation violation = new Violation();
            violation.setFileName(fileEntry.getOriginalFilename());
            violation.setReason(e.violationsAsMessage());

            logger.info("inserting Violation into rejected deals ..");
            violationRepository.save(violation);

            throw new TransactionsFileException("There's a violations in the file");


        }
    }

}
