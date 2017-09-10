package tiemy.android.br.com.beersplitapp.api;

import tiemy.android.br.com.beersplitapp.model.Expense;

public interface OnExpenseClickListener {

    void onItemClick(Expense expense);

    void onLongClick(Expense expense);
}
