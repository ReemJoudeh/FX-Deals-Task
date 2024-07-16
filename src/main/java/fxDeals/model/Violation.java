package fxDeals.model;

public class Violation {

    private String fieldName;
    private String errorMessage;

    public Violation(String fieldName, String errorMessage) {
        this.errorMessage = errorMessage;
        this.fieldName = fieldName;
    }
    public Violation(String errorMessage){
        this.errorMessage=errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    @Override
    public String toString() {
        return "Errors : " +
                "fieldName='" + fieldName + '\'' +
                ", error='" + errorMessage + '\'';
    }

}
