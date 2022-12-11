package com.kowalski;

import java.io.*;

public class Basket implements Serializable {

    private String[] products;
    private int[] prices;
    private int[] productSum;
    private int[] quantity;
    private String[] result;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.productSum = new int[products.length];
        this.result = new String[products.length];
        this.quantity = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        quantity[productNum - 1] += amount;
        productSum[productNum - 1] = prices[productNum - 1] * quantity[productNum - 1];
        result[productNum - 1] = products[productNum - 1] + " " + quantity[productNum - 1] + " " + productSum[productNum - 1];
        System.out.println("Вы добавили в корзину: " + products[productNum - 1] + " " +
                quantity[productNum - 1] + " шт. на " + productSum[productNum - 1] + " руб.");
    }

    public void printCart() {
        int sum = 0;
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (productSum[i] != 0) {
                sum += productSum[i];
            }
            if (result[i] != null) {
                System.out.println(result[i]);
            }
        }
        System.out.println("Сумма: " + sum);
    }

    public void saveBin(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return basket;
    }
}