package org.example;

import java.util.Scanner;

public class Main {
    private static void printMenu() {
        System.out.println("Введите команду: " +
                "\n1 - новый посетитель входит в музей," +
                "\n2 - посетитель выходит из музея," +
                "\n3 - закрыть музей," +
                "\n4 - открыть музей," +
                "\n5 - вывести количество посетителей в музее," +
                "\n6 - завершить" +
                "\nЕсли хотите снова увидеть пользовательское меню, введите любое другое значение или строку");
    }

    public static void main(String[] args) {
        Museum storage = new Museum();
        East thread1 = new East(storage);
        West thread2 = new West(storage);
        Control thread3 = new Control(storage);
        Director thread4 = new Director(storage);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        Scanner scanner = new Scanner(System.in);
        printMenu();
        while (true) {
            try {
                int command = scanner.nextInt();
                synchronized (storage) {          // не дает другим потокам зайти в storage
                    storage.setCommand(command);
                    storage.notifyAll();
                }
                if (command == 5) {
                    System.out.println("Количество посетителей в музее: " + storage.getAmount());
                }
                if (command == 6) {
                    scanner.close();
                    System.exit(0);
                }
                if (command < 1 || command > 6) {
                    System.out.println("Введите целое число от 0 до 6:");
                    printMenu();
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Введите целое число от 0 до 6:");
                scanner.nextLine(); // Очистка буфера ввода
                printMenu();
            }
        }
    }
}
