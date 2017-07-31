package tiemy.android.br.com.beersplitapp.model;


import java.util.List;

public class RoundRegister {

    private int id_round;
    private String name;
    private int people;
    private List<Expense> expenses;
    private double total;
    private double totalPerPerson;
    private String tip;

    public int getId_round() {
        return id_round;
    }

    public void setId_round(int id_round) {
        this.id_round = id_round;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalPerPerson() {
        return totalPerPerson;
    }

    public void setTotalPerPerson(double totalPerPerson) {
        this.totalPerPerson = totalPerPerson;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
