package com.kowalski;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.kowalski.Basket.loadFromTxtFile;

public class Main {

    public static void main(String[] args) {

        String[] products = {"Молоко", "Хлеб", "Гречка", "Масло", "Творог", "Макароны", "Шоколад"};
        int[] prices = {50, 25, 80, 60, 55, 42, 30};
        Scanner scanner = new Scanner(System.in);
        Basket basket = new Basket(products, prices);
        File file = new File("basket.txt");
        try {
            if (file.exists()) {
                loadFromTxtFile(file);
                System.out.println("File " + file.getName() + " loaded!");
            } else {
                file.createNewFile();
                System.out.println("File " + file.getName() + " created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        whatPrice(products, prices);

        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Введите номер товара и количество \n" +
                    "\"price\" -  узнать цены \n" +
                    "\"end\" - выход");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                scanner.close();
                basket.printCart();
                break;
            } else if (input.equals("price")) {
                whatPrice(products, prices);
            } else {
                String[] parts = input.split(" ");
                try {
                    if (parts.length == 2) {
                        int inputProd = Integer.parseInt(input.substring(0, input.indexOf(" ")));
                        if (inputProd > 0 && inputProd < (products.length + 1)) {
                            int prodQuantity = Integer.parseInt(parts[1]);
                            basket.addToCart(inputProd, prodQuantity);
                            basket.saveTxt(file);
                        } else {
                            System.out.println("Не корректное число!");
                        }
                    } else {
                        System.out.println("Не корректное число!!");
                    }
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Не корректное число!!!");
                }
            }
        }
    }

    public static void whatPrice(String[] products, int[] prices) {
        System.out.println("Список продуктов: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(new StringBuilder().append(i + 1).append(" - ")
                    .append(products[i]).append(" ")
                    .append(prices[i]).append(" рублей за штуку.").toString());
        }
    }
}