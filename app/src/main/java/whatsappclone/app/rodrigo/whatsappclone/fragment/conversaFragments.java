package whatsappclone.app.rodrigo.whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsappclone.app.rodrigo.whatsappclone.Activity.ConversaActivity;
import whatsappclone.app.rodrigo.whatsappclone.Adapter.AdapterConversas;
import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Conversas;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Base64Custom;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

/**
 * A simple {@link Fragment} subclass.
 */
public class conversaFragments extends Fragment {

    private ListView                listViewConversas;
    private ArrayAdapter<Conversas> adapter;
    private ArrayList<Conversas>    conversa;
    private DatabaseReference       bancoDados;
    private ValueEventListener      valueEventListenerConversas;
    private String                  identificadorUsuario;

    public conversaFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_conversa_fragments, container, false);

        listViewConversas   = view.findViewById(R.id.listViewConversas);
        conversa            = new ArrayList<Conversas>();
        adapter             = new AdapterConversas(getActivity(), conversa);
        listViewConversas.setAdapter(adapter);

        Preferencias preferencias           = new Preferencias(getActivity());
        identificadorUsuario                = preferencias.getIdentificador();



        bancoDados                            = ConfiguracaoFireBase.getFirebase().child("conversas").child(identificadorUsuario);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                conversa.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Conversas dadosConveras = dados.getValue(Conversas.class);

                    conversa.add(dadosConveras);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listViewConversas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Conversas conversaIntent = conversa.get(position);
                String email             = Base64Custom.decoficarBase64(conversaIntent.getIdUsuario());

                intent.putExtra("nome", conversaIntent.getNome());
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bancoDados.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        bancoDados.removeEventListener(valueEventListenerConversas);
    }
}
