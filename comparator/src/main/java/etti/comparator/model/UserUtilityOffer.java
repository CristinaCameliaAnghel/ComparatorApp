package etti.comparator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_utility_offers")
public class UserUtilityOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "utility_id", nullable = false)
    private UtilityDetails utilityDetails;

    @Column(nullable = false)
    private String status = "in asteptare";

    @Column(columnDefinition = "TEXT")
    private String description;

    private String email;
    private String phone;

    @Column(columnDefinition = "TEXT")
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
