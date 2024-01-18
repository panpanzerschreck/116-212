// Пакети
package com.investments;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Базовий клас для вкладу
class Deposit {
    private String bankName;
    private double interestRate;
    private int termMonths;

    public Deposit(String bankName, double interestRate, int termMonths) {
        this.bankName = bankName;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
    }

    public String getBankName() {
        return bankName;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public double calculateProfit(double principal) {
        return principal * (interestRate / 100) * (termMonths / 12);
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "bankName='" + bankName + '\'' +
                ", interestRate=" + interestRate +
                ", termMonths=" + termMonths +
                '}';
    }
}

// Клас для вкладів з можливістю дострокового зняття
class FlexibleDeposit extends Deposit {
    private boolean earlyWithdrawalAllowed;

    public FlexibleDeposit(String bankName, double interestRate, int termMonths, boolean earlyWithdrawalAllowed) {
        super(bankName, interestRate, termMonths);
        this.earlyWithdrawalAllowed = earlyWithdrawalAllowed;
    }

    public boolean isEarlyWithdrawalAllowed() {
        return earlyWithdrawalAllowed;
    }

    @Override
    public String toString() {
        return "FlexibleDeposit{" +
                "bankName='" + getBankName() + '\'' +
                ", interestRate=" + getInterestRate() +
                ", termMonths=" + getTermMonths() +
                ", earlyWithdrawalAllowed=" + earlyWithdrawalAllowed +
                '}';
    }
}

// Клас для управління вкладами
class DepositManager {
    private List<Deposit> deposits;

    public DepositManager() {
        this.deposits = new ArrayList<>();
    }

    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public List<Deposit> searchDeposits(double minInterestRate, int minTermMonths) {
        List<Deposit> searchResult = new ArrayList<>();
        for (Deposit deposit : deposits) {
            if (deposit.getInterestRate() >= minInterestRate && deposit.getTermMonths() >= minTermMonths) {
                searchResult.add(deposit);
            }
        }
        return searchResult;
    }

    public void sortByInterestRate() {
        Collections.sort(deposits, (d1, d2) -> Double.compare(d2.getInterestRate(), d1.getInterestRate()));
    }
}

// Клас консольного інтерфейсу
public class ConsoleApp {
    private static final String FILE_NAME = "deposits.txt";

    public static void main(String[] args) {
        DepositManager depositManager = new DepositManager();
        loadDepositsFromFile(depositManager);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Додати вклад");
            System.out.println("2. Переглянути вклади");
            System.out.println("3. Пошук вкладів");
            System.out.println("4. Сортування за відсотковою ставкою");
            System.out.println("5. Зберегти та вийти");

            System.out.print("Оберіть опцію: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addDeposit(scanner, depositManager);
                    break;
                case 2:
                    displayDeposits(depositManager);
                    break;
                case 3:
                    searchDeposits(scanner, depositManager);
                    break;
                case 4:
                    depositManager.sortByInterestRate();
                    System.out.println("Вклади відсортовано за відсотковою ставкою.");
                    break;
            }
        } while (choice != 5);

        saveDepositsToFile(depositManager);
    }

    private static void addDeposit(Scanner scanner, DepositManager depositManager) {
        System.out.print("Введіть назву банку: ");
        String bankName = scanner.next();

        System.out.print("Введіть відсоткову ставку: ");
        double interestRate = scanner.nextDouble();

        System.out.print("Введіть термін вкладу у місяцях: ");
        int termMonths = scanner.nextInt();

        System.out.print("Чи дозволяється дострокове зняття (true/false): ");
        boolean earlyWithdrawalAllowed = scanner.nextBoolean();

        Deposit deposit;
        if (earlyWithdrawalAllowed) {
            deposit = new FlexibleDeposit(bankName, interestRate, termMonths, true);
        } else {
            deposit = new Deposit(bankName, interestRate, termMonths);
        }

        depositManager.addDeposit(deposit);
        System.out.println("Вклад додано успішно.");
    }

    private static void displayDeposits(DepositManager depositManager) {
        List<Deposit> deposits = depositManager.getDeposits();
        for (Deposit deposit : deposits) {
            System.out.println(deposit);
        }
    }

    private static void searchDeposits(Scanner scanner, DepositManager depositManager) {
        System.out.print("Введіть мінімальну відсоткову ставку: ");
        double minInterestRate = scanner.nextDouble();

        System.out.print("Введіть мінімальний термін вкладу у місяцях: ");
        int minTermMonths = scanner.nextInt();

        List<Deposit> searchResult = depositManager.searchDeposits(minInterestRate, minTermMonths);

        if (searchResult.isEmpty()) {
            System.out.println("Відповідних вкладів не знайдено.");
        } else {
            System.out.println("Результати пошуку:");
            for (Deposit deposit : searchResult) {
                System.out.println(deposit);
            }
        }
    }

    private static void saveDepositsToFile(DepositManager depositManager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(depositManager.getDeposits());
            System.out.println("Вклади збережено в файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadDepositsFromFile(DepositManager depositManager) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Deposit> deposits = (List<Deposit>) ois.readObject();
            depositManager.getDeposits().addAll(deposits);
            System.out.println("Вклади завантажено з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Файл не знайдено або виникла помилка під час завантаження вкладів з файлу.");
        }
    }
}
