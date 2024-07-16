package fxDeals.validator.Impl;


import fxDeals.Exceptions.TransactionsFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class CsvValidator extends DefaultTransactionValidator {


    @Override
    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Transactions File cannot be null");
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new TransactionsFileException("its not a csv file");
        }
    }
}
