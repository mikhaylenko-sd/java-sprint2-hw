public class MonthReportItem {
    private final String itemName;
    private final int quantity;
    private final boolean isExpense;
    private final int sumOfOne;

    public MonthReportItem(String itemName, int quantity, boolean isExpense, int sumOfOne) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.isExpense = isExpense;
        this.sumOfOne = sumOfOne;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public int getSumOfOne() {
        return sumOfOne;
    }
}
