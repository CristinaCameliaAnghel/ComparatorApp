package etti.comparator.dto;
import jakarta.persistence.Column;

import java.util.Date;
public class UtilityDetailsDto {
    private int id;


    private String name; //category
    private String utilityProvider;
    private double price;
    private String unitOfMeasure;
    private String utilityOfferName;
    private String geographicCoverage;
    private String contractDuration;

    private String customerType;
    private String paymentFrequency;

    @Column(columnDefinition = "TEXT")
    private String description;
    private Date registeredAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUtilityProvider() {
        return utilityProvider;
    }

    public void setUtilityProvider(String utilityProvider) {
        this.utilityProvider = utilityProvider;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUtilityOfferName() {
        return utilityOfferName;
    }

    public void setUtilityOfferName(String utilityOfferName) {
        this.utilityOfferName = utilityOfferName;
    }

    public String getGeographicCoverage() {
        return geographicCoverage;
    }

    public void setGeographicCoverage(String geographicCoverage) {
        this.geographicCoverage = geographicCoverage;
    }

    public String getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(String contractDuration) {
        this.contractDuration = contractDuration;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }
}
