package etti.comparator.dto;

public class UserServiceCommentDto {
    private int offerId;
    private String comment;
    private Integer evaluationScore;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }
}
