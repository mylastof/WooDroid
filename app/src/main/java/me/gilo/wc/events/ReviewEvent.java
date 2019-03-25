package me.gilo.wc.events;

import me.gilo.woodroid.models.Product;
import me.gilo.woodroid.models.ProductReview;

public class ReviewEvent {

    ProductReview review;

    public ReviewEvent(ProductReview review) {
        this.review = review;
    }

    public ProductReview getReview() {
        return review;
    }

    public void setReview(ProductReview review) {
        this.review = review;
    }
}
