package whatsappclone.app.rodrigo.whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsappclone.app.rodrigo.whatsappclone.Activity.ConversaActivity;
import whatsappclone.app.rodrigo.whatsappclone.Adapter.ContatoAdapter;
import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Contato;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

import static whatsappclone.app.rodrigo.whatsappclone.R.color.colorPrimaryDark;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {


    private ListView listViewContato;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener ValueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(ValueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(ValueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        listViewContato = view.findViewById(R.id.listViewContatos);

        contatos = new ArrayList<Contato>();
        adapter = new ContatoAdapter(getActivity(), contatos);
        listViewContato.setAdapter(adapter);

        // recuperar dados no firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificador      = preferencias.getIdentificador();
        firebase = ConfiguracaoFireBase.getFirebase()
                    .child("contatos")
                    .child(identificador);

        ValueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contatos.clear();

                for (DataSnapshot dados: dataSnapshot.getChildren()){

                    Contato contato = dados.getValue( Contato.class );
                    contatos.add( contato );

                }

                adapter. notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listViewContato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(i);

                intent.putExtra("nome",contato.getNome());
                intent.putExtra("email",contato.getEmail());

                startActivity(intent);


            }
        });

        return view;

    }

}
