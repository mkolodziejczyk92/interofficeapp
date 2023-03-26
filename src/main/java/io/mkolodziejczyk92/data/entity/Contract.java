package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Contract extends AbstractEntity {

    private String number;
    private String comodityType;
    private String amount;
    private String client;
    private LocalDate signatureDate;
    private LocalDate implementationDate;
    private boolean completed;

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getComodityType() {
        return comodityType;
    }
    public void setComodityType(String comodityType) {
        this.comodityType = comodityType;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public LocalDate getSignatureDate() {
        return signatureDate;
    }
    public void setSignatureDate(LocalDate signatureDate) {
        this.signatureDate = signatureDate;
    }
    public LocalDate getImplementationDate() {
        return implementationDate;
    }
    public void setImplementationDate(LocalDate implementationDate) {
        this.implementationDate = implementationDate;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
