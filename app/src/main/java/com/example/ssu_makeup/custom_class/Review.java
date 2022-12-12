package com.example.ssu_makeup.custom_class;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Review implements Serializable {
    private final int reviewScore;
    private final String reviewer;
    private final String review;

    public Review(int reviewScore, String reviewer, String review){
        this.reviewScore=reviewScore;
        this.reviewer=reviewer;
        this.review=review;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getReview() {
        return review;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("reviewScore", reviewScore);
        result.put("reviewer", reviewer);
        result.put("review", review);

        return result;
    }
}
