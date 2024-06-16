package etti.comparator.model;

import jakarta.persistence.*;
@Entity
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
}
