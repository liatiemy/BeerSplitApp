package tiemy.android.br.com.beersplitapp.model;


import java.math.BigDecimal;
import java.util.List;

public class RoundRegister {

    private int id_round;
    private String name;
    private BigDecimal people;
    private List<Expense> expenses;
    private BigDecimal total;
    private BigDecimal totalTip;
    private BigDecimal totalPerPerson;
    private BigDecimal totalPerPersonTips;

    public BigDecimal getTotalTip() {
        return totalTip;
    }

    public void setTotalTip(BigDecimal totalTip) {
        this.totalTip = totalTip;
    }

    public BigDecimal getTotalPerPersonTips() {
        return totalPerPersonTips;
    }

    public void setTotalPerPersonTips(BigDecimal totalPerPersonTips) {
        this.totalPerPersonTips = totalPerPersonTips;
    }

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

    public BigDecimal getPeople() {
        return people;
    }

    public void setPeople(BigDecimal people) {
        this.people = people;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalPerPerson() {
        return totalPerPerson;
    }

    public void setTotalPerPerson(BigDecimal totalPerPerson) {
        this.totalPerPerson = totalPerPerson;
    }

}
