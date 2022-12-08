package com.kowalski;
import java.io.*;
public class Basket implements Serializable{

    private static String[] products;
    private static int[] prices;
    static int[] productSum;
    static int[] quantity;
    static String[] result;

    public Basket(String[] products, int[] prices) {
        Basket.products = products;
        Basket.prices = prices;
        productSum = new int[products.length];
        result = new String[products.length];
        quantity = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        quantity[productNum - 1] += amount;
        productSum[productNum - 1] = prices[productNum - 1] * quantity[productNum - 1];
        result[productNum - 1] = products[productNum - 1] + " " + quantity[productNum - 1] + " " + productSum[productNum - 1];
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
        System.out.println("Сумма: " + sum);
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String e : result) {
                if (e != null)
                    out.println(e + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static Basket loadFromTxtFile(File textFile) {
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
