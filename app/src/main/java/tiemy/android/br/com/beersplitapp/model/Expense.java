package tiemy.android.br.com.beersplitapp.model;

public class Expense {

    private int id_round;
    private String nameExpense;
    private double price;
    private int quantity;

    public int getId_round() {
        return id_round;
    }

    public void setId_round(int id_round) {
        this.id_round = id_round;
    }

    public String getNameExpense() {
        return nameExpense;
    }

    public void setNameExpense(String nameExpense) {
        this.nameExpense = nameExpense;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
