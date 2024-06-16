package etti.comparator.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UtilityDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; //category
    private String utilityProvider;
    private double price;
    private String unitOfMeasure;
    private String serviceOfferName;
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

    public String getUtilityProvider() {
        return utilityProvider;
    }

    public void setUtilityProvider(String utilityProvider) {
        this.utilityProvider = utilityProvider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getServiceOfferName() {
        return serviceOfferName;
    }

    public void setServiceOfferName(String serviceOfferName) {
        this.serviceOfferName = serviceOfferName;
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
