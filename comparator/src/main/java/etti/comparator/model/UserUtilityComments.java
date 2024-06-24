package etti.comparator.model;

import jakarta.persistence.*;
@Entity
@Table(name = "user_utility_comments")
public class UserUtilityComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "utility_id")
    private UtilityDetails utilityDetails;

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

    public UtilityDetails getUtilityDetails() {
        return utilityDetails;
    }

    public void setUtilityDetails(UtilityDetails utilityDetails) {
        this.utilityDetails = utilityDetails;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
            this.comment= comment;
    }}
