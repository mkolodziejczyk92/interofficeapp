package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Orders extends AbstractEntity {

    private String comodityType;
    private String amount;
    private String client;
    private String contractNumber;
    private String status;
    private String supplier;
    private String supplierOrderNumber;
    private String comment;

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
    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }
    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
