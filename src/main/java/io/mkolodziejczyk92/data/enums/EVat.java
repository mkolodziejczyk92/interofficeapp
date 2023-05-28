package io.mkolodziejczyk92.data.enums;

public enum EVat {
    VAT_8(8,"8%"),
    VAT_23(23, "23%");

    private final Integer vatValue;
    private final String wordForm;

    EVat(Integer vatValue, String wordForm) {
        this.vatValue = vatValue;
        this.wordForm = wordForm;
    }
    public String getVatValue(){
        return String.valueOf(vatValue);
    }
    public String getWordForm(){
        return wordForm;
    }
}
