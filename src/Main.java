import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final int NUM_OF_REPORT_MONTH = 3;
    private static final int NUM_OF_YEAR = 2021;


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fileContents;
        ArrayList<MonthlyReport> monthlyReports = new ArrayList<>();
        YearlyReport yearlyReport = null;

        while (true) {
            printMenu();
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                if (monthlyReports.isEmpty()) {
                    for (int i = 0; i < NUM_OF_REPORT_MONTH; i++) {
                        fileContents = readFileContentsOrNull("resources\\m." + NUM_OF_YEAR + "0" + (i + 1) + ".csv");
                        MonthlyReport monthlyReport = new MonthlyReport(i + 1, fileContents);
                        monthlyReports.add(monthlyReport);
                    }
                } else {
                    System.out.println("Месячные отчеты были считаны ранее.");
                }

            } else if (choice.equals("2")) {
                if (yearlyReport == null) {
                    fileContents = readFileContentsOrNull("resources\\y." + NUM_OF_YEAR + ".csv");
                    yearlyReport = new YearlyReport(NUM_OF_YEAR, fileContents);
                } else {
                    System.out.println("Годовой отчет был считан ранее.");
                }
            } else if (choice.equals("3")) {
                boolean isCorrect = true;
                if (monthlyReports.size() != 0 && yearlyReport != null) {
                    for (MonthlyReport monthlyReport : monthlyReports) {
                        int sumExpense = 0;
                        int sumNotExpense = 0;
                        for (MonthReportItem item : monthlyReport.getMonthReportItems()) {
                            if (item.isExpense()) {
                                sumExpense += item.getQuantity() * item.getSumOfOne();
                            } else {
                                sumNotExpense += item.getQuantity() * item.getSumOfOne();
                            }
                        }
                        YearReportItem yearlyReportByMonthExpense = yearlyReport.getByMonthExpense(monthlyReport.getMonthNum(), true);
                        YearReportItem yearlyReportByMonthNotExpense = yearlyReport.getByMonthExpense(monthlyReport.getMonthNum(), false);
                        if (yearlyReportByMonthExpense.getAmount() != sumExpense) {
                            isCorrect = false;
                            System.out.println("При сверке расходов обнаружено несоответсвие в месяце " + yearlyReportByMonthExpense.getMonth());
                        }
                        if (yearlyReportByMonthNotExpense.getAmount() != sumNotExpense) {
                            isCorrect = false;
                            System.out.println("При сверке доходов обнаружено несоответсвие в месяце " + yearlyReportByMonthExpense.getMonth());
                        }
                    }
                    if (isCorrect) {
                        System.out.println("Сверка прошла успешно");
                    }
                } else {
                    System.out.println("Вы не выполнили команды - 1 и/или 2. Считайте все отчеты и повторите попытку.");
                }
            } else if (choice.equals("4")) {
                if (monthlyReports.size() != 0) {
                    for (MonthlyReport monthlyReport : monthlyReports) {
                        System.out.println(getMonthName(monthlyReport.getMonthNum()));
                        MonthReportItem maxExpenseItem = monthlyReport.getMaxExpenseOrNotExpense(true);
                        MonthReportItem maxNotExpenseItem = monthlyReport.getMaxExpenseOrNotExpense(false);
                        System.out.println("Самая большая трата: " + maxExpenseItem.getItemName() + " - "
                                + maxExpenseItem.getQuantity() * maxExpenseItem.getSumOfOne());
                        System.out.println("Самый прибыльный товар: " + maxNotExpenseItem.getItemName() + " - "
                                + maxNotExpenseItem.getQuantity() * maxNotExpenseItem.getSumOfOne());
                    }
                } else {
                    System.out.println("Вы не выполнили команду - 1. Считайте месячные отчеты и повторите попытку.");
                }
            } else if (choice.equals("5")) {
                if (yearlyReport != null) {
                    System.out.println(yearlyReport.getYearNum() + " год");
                    for (int i = 0; i < NUM_OF_REPORT_MONTH; i++) {
                        System.out.println("Прибыль за " + getMonthName(i + 1) + " составила: " + yearlyReport.getProfitByMonth(i + 1));
                    }
                    yearlyReport.printAveragesByYear();
                } else {
                    System.out.println("Вы не выполнили команду - 2. Считайте годовой отчет и повторите попытку.");
                }
            } else if (choice.equals("exit")) {
                System.out.println("Выход из приложения.");
                break;
            } else {
                System.out.println("Извините, данная команда пока что отсутствует.");
            }
        }

    }

    public static void printMenu() {
        System.out.println("Какое действие Вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчёты.");
        System.out.println("2 - Считать годовой отчёт.");
        System.out.println("3 - Сверить отчёты.");
        System.out.println("4 - Вывести информацию о всех месячных отчётах.");
        System.out.println("5 - Вывести информацию о годовом отчёте.");
        System.out.println("exit - Выйти из приложения");
    }

    public static String getMonthName(int monthNum) {
        if (monthNum == 1) {
            return "Январь";
        } else if (monthNum == 2) {
            return "Февраль";
        } else if (monthNum == 3) {
            return "Март";
        }
        return "";
    }

    private static String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл: " + path + ". Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}

