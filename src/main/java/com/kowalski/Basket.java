package com.kowalski;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class Basket {

    private static String[] products;
    private static int[] prices;
    private int[] productSum;
    private int[] quantity;
    private String[] result;

    public Basket(String[] products, int[] prices) {
        Basket.products = products;
        Basket.prices = prices;
        this.productSum = new int[products.length];
        this.result = new String[products.length];
        this.quantity = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        quantity[productNum - 1] += amount;
        productSum[productNum - 1] = prices[productNum - 1] * quantity[productNum - 1];
        result[productNum - 1] = products[productNum - 1] + ", " + quantity[productNum - 1] + ", " + productSum[productNum - 1];
        System.out.println("Вы добавили в корзину: " + products[productNum - 1] + " " + quantity[productNum - 1] + " шт. на " + productSum[productNum - 1] + " руб.");
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
        System.out.println("Итого: " + sum);
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String e : result) {
                if (e != null)
                    out.println(e + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket(products, prices);
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String res;
            while ((res = reader.readLine()) != null) {
                String[] in = res.split(" ");
                for (int i = 0; i < products.length; i++) {
                    if (products[i].equals(in[0])) {
                        basket.addToCart(i + 1, Integer.parseInt(in[1]));
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return basket;
    }

    public void saveAsJSON(File file) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(gson.toJson(this));
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadAsJSON(File file) {
        Basket basket;
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            basket = gson.fromJson(bufferedReader, Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}