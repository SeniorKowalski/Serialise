package com.kowalski;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.kowalski.Basket.loadFromBinFile;

public class Main {

    public static void main(String[] args) {

        String[] products = {"������", "����", "������", "�����", "������", "��������", "�������"};
        int[] prices = {50, 25, 80, 60, 55, 42, 30};
        Scanner scanner = new Scanner(System.in);
        Basket basket = new Basket(products, prices);
        File file = new File("basket.bin");
        try {
            if (file.exists()) {
                basket = loadFromBinFile(file);
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
            System.out.println("������� ����� ������ � ���������� \n" +
                    "\"price\" -  ������ ���� \n" +
                    "\"end\" - �����");
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
                            basket.saveBin(file);
                        } else {
                            System.out.println("�� ���������� �����!");
                        }
                    } else {
                        System.out.println("�� ���������� �����!!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("�� ���������� �����!!!");
                }
            }
        }
    }

    public static void whatPrice(String[] products, int[] prices) {
        System.out.println("������ ���������: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(new StringBuilder().append(i + 1).append(" - ")
                    .append(products[i]).append(" ")
                    .append(prices[i]).append(" ������ �� �����.").toString());
        }
    }
}