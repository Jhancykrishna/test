package com.sayone.obr.exception;

public enum WishlistErrors {
    WISH_PRODUCT_EXISTS("Wish product exists"),
    INTERNAL_SERVER_ERROR("internal error.Please debug"),
    NO_RECORD_FOUND("no record found."),
    WISH_BID_NOTFOUND("Wishlist bookId not found");


    private String errorMessage;

    WishlistErrors(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
