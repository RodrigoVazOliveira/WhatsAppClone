package whatsappclone.app.rodrigo.whatsappclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import whatsappclone.app.rodrigo.whatsappclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {


    private ListView listViewContato;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listViewContato = view.findViewById(R.id.listViewContatos);

        contatos = new ArrayList<String>();
        contatos.add("joão");
        contatos.add("joana");
        contatos.add("renata");


        adapter = new ArrayAdapter(
                getActivity(),
                R.layout.list_contato,
                contatos
        );

        listViewContato.setAdapter(adapter);


        return view;

    }

}
