package tiemy.android.br.com.beersplitapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tiemy.android.br.com.beersplitapp.R;
import tiemy.android.br.com.beersplitapp.api.OnExpenseClickListener;
import tiemy.android.br.com.beersplitapp.model.Expense;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.LinhaViewHolder>{

    private List<Expense> expenses;
    private OnExpenseClickListener listener;

    public ExpenseAdapter(List<Expense> expenses, OnExpenseClickListener listener) {
        this.expenses = expenses;
        this.listener = listener;
    }


    @Override
    public ExpenseAdapter.LinhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.row_expense, parent, false);

        return new ExpenseAdapter.LinhaViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(LinhaViewHolder holder, final int position) {

        holder.tvExpense.setText(expenses.get(position).getNameExpense());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(expenses.get(position));
            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(expenses.get(position));
                return true;
            }
        });

        Picasso.with(holder.itemView.getContext())
                .load(R.drawable.ic_greater)
                .placeholder(R.drawable.ic_greater)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivLogo);


    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class LinhaViewHolder extends RecyclerView.ViewHolder{

        public TextView tvExpense;
        public ImageView ivLogo;


        public LinhaViewHolder(View itemView) {
            super(itemView);
            tvExpense    = (TextView) itemView.findViewById(R.id.tvExpense);
            ivLogo      = (ImageView) itemView.findViewById(R.id.ivSeta);
        }
    }

    public void update(List<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }

}
