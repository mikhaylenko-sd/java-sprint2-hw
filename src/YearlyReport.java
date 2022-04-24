import java.util.ArrayList;

public class YearlyReport {
    private final int yearNum;
    private final ArrayList<YearReportItem> yearReportItems = new ArrayList<>();

    public YearlyReport(int yearNum, String fileContents) {
        this.yearNum = yearNum;
        parseContent(fileContents);
    }

    void parseContent(String fileContents) {
        if (fileContents != null) {
            String[] lines = fileContents.split(System.lineSeparator());
            for (int k = 1; k < lines.length; k++) {
                String[] line = lines[k].split(",");
                YearReportItem monthInfo = new YearReportItem(Integer.parseInt(line[0]), Integer.parseInt(line[1]),
                        Boolean.parseBoolean(line[2]));
                yearReportItems.add(monthInfo);
            }
        }
    }

    public YearReportItem getByMonthExpense(int monthNum, boolean isExpense) {
        for (YearReportItem yearReportItem : yearReportItems) {
            if (yearReportItem.getMonth() == monthNum && yearReportItem.isExpense() == isExpense) {
                return yearReportItem;
            }
        }
        return null;
    }

    public void printAveragesByYear() {
        int sumExpensesByYear = 0;
        int sumNotExpensesByYear = 0;
        for (YearReportItem yearReportItem : yearReportItems) {
            if (yearReportItem.isExpense()) {
                sumExpensesByYear += yearReportItem.getAmount();
            } else {
                sumNotExpensesByYear += yearReportItem.getAmount();
            }
        }
        int averageExpensesByYear = sumExpensesByYear / yearReportItems.size();
        int averageNotExpensesByYear = sumNotExpensesByYear / yearReportItems.size();
        System.out.println("Средний расход за все месяцы в году: " + averageExpensesByYear);
        System.out.println("Средний доход за все месяцы в году: " + averageNotExpensesByYear);
    }

    public int getProfitByMonth(int month) {
        int amountOfExpense = 0;
        int amountOfNotExpense = 0;
        for (YearReportItem yearReportItem : yearReportItems) {
            if (yearReportItem.getMonth() == month) {
                if (yearReportItem.isExpense()) {
                    amountOfExpense = yearReportItem.getAmount();
                } else {
                    amountOfNotExpense = yearReportItem.getAmount();
                }
            }
        }
        return amountOfNotExpense - amountOfExpense;
    }

    public int getYearNum() {
        return yearNum;
    }

}

