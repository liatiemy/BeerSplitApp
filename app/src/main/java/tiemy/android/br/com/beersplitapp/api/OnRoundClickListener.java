package tiemy.android.br.com.beersplitapp.api;

import tiemy.android.br.com.beersplitapp.model.RoundRegister;

public interface OnRoundClickListener {

    void onItemClick(RoundRegister roundRegister);

    void onLongClick(RoundRegister roundRegister);

}
