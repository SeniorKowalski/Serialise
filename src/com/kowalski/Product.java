package com.kowalski;

public class Product {

    private String productName;
    private int productPrice;

    public Product(String productName, int productPrice){

        this.productName = productName;
        this.productPrice = productPrice;

    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }
}
