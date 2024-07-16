package fxDeals.Exceptions;


import fxDeals.entity.Deals;
import fxDeals.model.Violation;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class TransactionsFileException extends RuntimeException {

    private Map<Integer, List<Violation>> violations;
    private List<Deals> deals;


    public TransactionsFileException(String message) {
        super(message);
    }

    public TransactionsFileException(Map<Integer, List<Violation>> fileViolations) {
        this.violations = new HashMap<>(fileViolations);


    }

    public TransactionsFileException(Map<Integer, List<Violation>> fileViolations, List<Deals> deals) {
        this.violations = new HashMap<>(fileViolations);
        this.deals = deals;
    }

    public TransactionsFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<Integer, List<Violation>> getViolations() {
        return violations;
    }

    public String violationsAsMessage() {
        if (violations == null)
            return "";
        StringBuilder errorMessage = new StringBuilder();
        for (Map.Entry<Integer, List<Violation>> entry : this.violations.entrySet()) {
            if (entry.getKey().equals(0))
                errorMessage.append(entry.getValue()).append("\n");
            else

                errorMessage.append("in ").append(entry.getKey()).append(" -->").append(entry.getValue() + "\n");
        }
        return errorMessage.toString();
    }


}