package br.com.meuprontuario.meuprontuario;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.meuprontuario.meuprontuario.PacoteReceita.ReceitasActivity;

/**
 * Created by betoj on 13/04/2017.
 */

public class HomeFragment extends Fragment {

    ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInsatanceState){

            View view = inflater.inflate(R.layout.fragment_home,container,false);
            return  view;


        }
    }
