package tiemy.android.br.com.beersplitapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tiemy.android.br.com.beersplitapp.R;
import tiemy.android.br.com.beersplitapp.api.OnItemClickListenter;
import tiemy.android.br.com.beersplitapp.model.Expense;

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.LinhaViewHolder>{

    private List<Expense> expenses;
    private OnItemClickListenter listener;

    public TotalAdapter(List<Expense> expenses, OnItemClickListenter listener) {
        this.expenses = expenses;
        this.listener = listener;
    }

    public TotalAdapter(List<Expense> expenses){
        this.expenses = expenses;
    }

    @Override
    public LinhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.row_total, parent, false);

        return new TotalAdapter.LinhaViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(LinhaViewHolder holder, final int position) {

        holder.tvExpense.setText(expenses.get(position).getNameExpense());
        holder.tvQuantity.setText(String.valueOf(expenses.get(position).getQuantity()));
        holder.tvSoma.setText(String.valueOf(expenses.get(position).getPrice()*expenses.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }



    public class LinhaViewHolder extends RecyclerView.ViewHolder{

        public TextView tvExpense;
        public TextView tvQuantity;
        public TextView tvSoma;

        public LinhaViewHolder(View itemView) {
            super(itemView);
            tvExpense    = (TextView) itemView.findViewById(R.id.tvExpense);
            tvQuantity    = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvSoma    = (TextView) itemView.findViewById(R.id.tvSoma);
        }
    }

    public void update(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }
}