package com.sayone.obr.model.request;

import com.sayone.obr.dto.UserReviewDto;

public class ReviewRequestModel extends UserReviewDto {

    private Long bookId;
    private Float rating;
    private String description;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public Float getRating() {
        return rating;
    }

    @Override
    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
