package tiemy.android.br.com.beersplitapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tiemy.android.br.com.beersplitapp.R;
import tiemy.android.br.com.beersplitapp.api.OnRoundClickListener;
import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.LinhaViewHolder>{

    private List<RoundRegister> rounds;
    private OnRoundClickListener listener;

    public RoundAdapter(List<RoundRegister> rounds, OnRoundClickListener listener) {
        this.rounds = rounds;
        this.listener = listener;
    }

    public RoundAdapter(List<RoundRegister> rounds){
        this.rounds = rounds;
    }

    @Override
    public LinhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //false eh apenas para inflar, mas ainda nao tem nada para renderizar/aparecer
        View meuLayout = inflater.inflate(R.layout.row_round_history, parent, false);

        return new RoundAdapter.LinhaViewHolder(meuLayout);
    }

    //associacao do valor com o item da tela
    @Override
    public void onBindViewHolder(RoundAdapter.LinhaViewHolder holder, final int position) {

        holder.tvMenu.setText(rounds.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return rounds.size();
    }



    public class LinhaViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMenu;

        public LinhaViewHolder(View itemView) {
            super(itemView);
            tvMenu    = (TextView) itemView.findViewById(R.id.tvMenu);
        }
    }

    public void update(List<RoundRegister> rounds){
        this.rounds = rounds;
        notifyDataSetChanged();
    }

}
