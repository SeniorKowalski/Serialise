package com.kowalski;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.kowalski.Basket.loadFromTxtFile;

public class Main {

    public static void main(String[] args) throws IOException {

        String[] products = {"Молоко", "Хлеб", "Гречка", "Масло", "Творог", "Макароны", "Шоколад"};
        int[] prices = {50, 25, 80, 60, 55, 42, 30};
        Scanner scanner = new Scanner(System.in);
        Settings loadSettings = new Settings("load");
        Settings saveSettings = new Settings("save");
        Settings logSettings = new Settings("log");
        Basket basket = new Basket(products, prices);
        ClientLog logger = new ClientLog();
        File file = new File(loadSettings.getFileName() + loadSettings.getFormat());
        File log = new File(logSettings.getFileName());

        try {
            if (file.exists()) {
                if (loadSettings.getEnabled()) {
                    if (loadSettings.getFormat().equals(".txt")) {
                        loadFromTxtFile(file);
                    } else {
                        basket = Basket.loadAsJSON(file);
                    }
                    System.out.println("File " + file.getName() + " loaded!");
                }
            } else {
                file.createNewFile();
                System.out.println("File " + file.getName() + " created!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!log.exists()) {
            log.createNewFile();
            System.out.println("Log file created!");
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
                if (logSettings.getEnabled()) {
                    logger.exportAsCSV(log);
                }
                break;
            } else if (input.equals("price")) {
                whatPrice(products, prices);
            } else {
                String[] parts = input.split(" ");
                try {
                    if (parts.length == 2) {
                        int inputProd = Integer.parseInt(input.substring(0, input.indexOf(" ")));
                        int prodQuantity = Integer.parseInt(parts[1]);
                        if (inputProd > 0 && inputProd < (products.length + 1)) {
                            basket.addToCart(inputProd, prodQuantity);
                            isSaveSettingsEnabled(saveSettings, basket, file);
                            logger.log(inputProd, prodQuantity);
                        } else {
                            System.out.println("Не корректное число!");
                        }
                    } else {
                        System.out.println("Не корректное число!!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Не корректное число!!!");
                }
            }
        }
    }

    private static void isSaveSettingsEnabled(Settings saveSettings, Basket basket, File file) {
        if (saveSettings.getEnabled()) {
            if (saveSettings.getFormat().equals(".txt")) {
                basket.saveTxt(file);
            } else if (saveSettings.getFormat().equals(".json")) {
                basket.saveAsJSON(file);
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