import java.util.ArrayList;

public class MonthlyReport {

    private final int monthNum;
    private final ArrayList<MonthReportItem> monthReportItems = new ArrayList<>();

    public MonthlyReport(int monthNum, String fileContents) {
        this.monthNum = monthNum;
        parseContent(fileContents);
    }

    void parseContent(String fileContents) {
        if (fileContents != null) {
            String[] lines = fileContents.split(System.lineSeparator());
            for (int k = 1; k < lines.length; k++) {
                String[] line = lines[k].split(",");
                MonthReportItem item = new MonthReportItem(line[0], Integer.parseInt(line[2]),
                        Boolean.parseBoolean(line[1]), Integer.parseInt(line[3]));
                monthReportItems.add(item);
            }
        }
    }

    public MonthReportItem getMaxExpenseOrNotExpense(boolean isExpense) {
        int max = 0;
        MonthReportItem maxMonthReportItem = null;
        for (MonthReportItem item : monthReportItems) {
            if (item.isExpense() == isExpense) {
                if (item.getQuantity() * item.getSumOfOne() > max) {
                    max = item.getQuantity() * item.getSumOfOne();
                    maxMonthReportItem = item;
                }
            }
        }
        return maxMonthReportItem;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public ArrayList<MonthReportItem> getMonthReportItems() {
        return monthReportItems;
    }
}

