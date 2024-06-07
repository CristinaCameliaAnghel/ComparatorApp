package etti.comparator.model;
import jakarta.persistence.*;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_service_comments")
public class UserServiceComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceDetails serviceDetails;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Integer evaluationScore;

    public Integer getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceDetails getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(ServiceDetails serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}