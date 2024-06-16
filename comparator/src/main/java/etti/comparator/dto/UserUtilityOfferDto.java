package etti.comparator.dto;

import etti.comparator.model.ServiceDetails;
import etti.comparator.model.User;
import etti.comparator.model.UtilityDetails;

public class UserUtilityOfferDto {

    private Long id;
    private User user;
    private UtilityDetails utilityDetails;
    private String status;
    private String description;
    private String email;
    private String phone;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UtilityDetails getUtilityDetails() {
        return utilityDetails;
    }

    public void setUtilityDetails(UtilityDetails utilityDetails) {
        this.utilityDetails = utilityDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
