package fxDeals.parser;

import fxDeals.entity.Deals;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TransactionParser {

    List<Deals> parse(MultipartFile transactionsFile) ;
}
