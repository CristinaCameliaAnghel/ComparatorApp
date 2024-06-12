package etti.comparator.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="service_details")
public class ServiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String serviceProvider;

    private String estimatedDuration;
    private double price;
    private String onlineBookingAvailability;
    private String requiredEquipment;
    private String guarantee;
    private String approvedCertifiedLicensed;
    private String serviceOfferName;
    private String geographicCoverage;

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

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(String estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOnlineBookingAvailability() {
        return onlineBookingAvailability;
    }

    public void setOnlineBookingAvailability(String onlineBookingAvailability) {
        this.onlineBookingAvailability = onlineBookingAvailability;
    }

    public String getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(String requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getApprovedCertifiedLicensed() {
        return approvedCertifiedLicensed;
    }

    public void setApprovedCertifiedLicensed(String approvedCertifiedLicensed) {
        this.approvedCertifiedLicensed = approvedCertifiedLicensed;
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
