package fxDeals.validator.Impl;


import fxDeals.dto.TransactionDTO;
import fxDeals.model.Violation;
import fxDeals.validator.TransactionValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public abstract class DefaultTransactionValidator implements TransactionValidator {


    @Override
    public List<Violation> validateTransactionFile(TransactionDTO dto) {
        List<Violation> violations = new ArrayList<>();
        validateDealUniqueId(dto.getDealUniqueId(), violations);
        validateFromCurrencyIsoCode(dto.getFromCurrencyIsoCode(), violations);
        validateToCurrencyIsoCode(dto.getToCurrencyIsoCode(), violations);
        validateDealTimestamp(dto.getDealTimestamp(), violations);
        validateDealAmount(dto.getDealAmount(), violations);
        return violations;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void validateDealUniqueId(String dealUniqueId, List<Violation> violations) {
        if (isNullOrEmpty(dealUniqueId)) {
            violations.add(new Violation("dealUniqueId", "a null dealUniqueId field"));
        }
    }

    private void validateFromCurrencyIsoCode(String fromCurrencyIsoCode, List<Violation> violations) {
        if (isNullOrEmpty(fromCurrencyIsoCode)) {
            violations.add(new Violation("fromCurrencyIsoCode", "a null fromCurrencyIsoCode field"));
        } else try {
            if (!Currency.getAvailableCurrencies().contains(Currency.getInstance(fromCurrencyIsoCode))) {

            }

        } catch (Exception e) {
            e.getMessage();
            violations.add(new Violation("fromCurrencyIsoCode", "an invalid fromCurrencyIsoCode!"));
        }

    }

    private void validateToCurrencyIsoCode(String toCurrencyIsoCode, List<Violation> violations) {
        if (isNullOrEmpty(toCurrencyIsoCode)) {
            violations.add(new Violation("Currency", "a null toCurrencyIsoCode field"));
        } else
            try {
                if (!Currency.getAvailableCurrencies().contains(Currency.getInstance(toCurrencyIsoCode))) {

                }

            } catch (Exception e) {
                e.getMessage();
                violations.add(new Violation("toCurrencyIsoCode", "an invalid toCurrencyIsoCode!"));
            }

    }

    private void validateDealAmount(String dealAmount, List<Violation> violations) {
        if (isNullOrEmpty(dealAmount))
            violations.add(new Violation("dealAmount", "a null deal amount Field"));
        else if (!dealAmount.matches("^[1-9]\\d*(\\.\\d+)?$"))
            violations.add(new Violation("dealAmount", "an invalid deal amount"));
    }

    private void validateDealTimestamp(String dealTimestamp, List<Violation> violations) {
        if (isNullOrEmpty(dealTimestamp))
            violations.add(new Violation("dealTimestamp", "a null deal TimeStamp Field"));
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
            try {
                LocalDateTime.parse(dealTimestamp, formatter);
            } catch (DateTimeParseException e) {
                e.getMessage();
                violations.add(new Violation("dealTimestamp", "an invalid deal TimeStamp!"));
            }
        }

    }
}