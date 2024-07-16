package fxDeals.factory;


import fxDeals.Exceptions.TransactionsFileException;
import fxDeals.parser.Impl.CsvTransactionParser;
import fxDeals.parser.TransactionParser;
import fxDeals.repository.DealRepository;
import fxDeals.repository.ViolationRepository;
import fxDeals.validator.Impl.CsvValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionParserFactory {

    @Autowired
    private DealRepository dealRepository;
    @Autowired
    ViolationRepository violationRepository;

    public TransactionParser createParser(String file) {
        if (file == null) {
            throw new TransactionsFileException("the file is null");
        }
        if (file.endsWith(".csv")) {
            return new CsvTransactionParser(new CsvValidator(),dealRepository,violationRepository);

            //in case we have many extensions, so we can use the suitable parser among this factory
        } else if (file.endsWith(".xml")) {
            //return new XmlTransactionParser(new XmlValidator());
        }
        throw new TransactionsFileException("Invalid Transaction File");
    }

}
