package fxDeals.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TransactionDTO {


    private String dealUniqueId;


    private String fromCurrencyIsoCode;


    private String toCurrencyIsoCode;


    private String dealTimestamp;


    private String dealAmount;


}
