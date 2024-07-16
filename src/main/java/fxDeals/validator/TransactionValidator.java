package fxDeals.validator;

import fxDeals.dto.TransactionDTO;
import fxDeals.model.Violation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransactionValidator {

     List<Violation> validateTransactionFile(TransactionDTO dto);

     void validateFile(MultipartFile file);
}