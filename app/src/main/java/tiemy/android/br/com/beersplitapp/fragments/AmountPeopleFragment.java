package tiemy.android.br.com.beersplitapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tiemy.android.br.com.beersplitapp.R;

public class AmountPeopleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Toast.makeText(getActivity(), "Entrei no Fragment", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_amount_people, container, false);
    }
}
