package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsappclone.app.rodrigo.whatsappclone.Adapter.AdapterMensagens;
import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Conversas;
import whatsappclone.app.rodrigo.whatsappclone.Model.Mensagem;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Base64Custom;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String nomeUsuarioDestinatario;
    private EditText editTextMensagem;
    private ImageButton btnEnviar;
    private ListView listViewMensagens;
    private ArrayList<Mensagem> mensagensArray;
    private ArrayAdapter adapter;
    private ValueEventListener valueEventListenerMensagens;
    private DatabaseReference firebase;
    private String identificadorUsuario;
    private String idRementente;
    private String idDestinatario;
    private String nomeUsuarioRemetente;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);


        // recuperar identificador do usuario
        Preferencias preferencias = new Preferencias(this);
        identificadorUsuario      = preferencias.getIdentificador();
        idRementente              = identificadorUsuario;
        nomeUsuarioRemetente   = preferencias.getNomeUsuario();

        toolbar =  findViewById(R.id.tb_conversa);
        Bundle extra =  getIntent().getExtras();
        editTextMensagem = findViewById(R.id.editText_mensagem);

        btnEnviar        = findViewById(R.id.botao_enviar);

        listViewMensagens = findViewById(R.id.lv_conversa);

        if (extra != null){
            nomeUsuarioDestinatario = extra.getString("nome");

            String emailDestinatario = extra.getString("email");
            Log.d("destino",emailDestinatario);
            idDestinatario = Base64Custom.codificarBase64(emailDestinatario);
        }

        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // montar list ade adapter
        mensagensArray = new ArrayList<>();
        adapter = new AdapterMensagens(ConversaActivity.this, mensagensArray);
        listViewMensagens.setAdapter(adapter);

        this.firebase = ConfiguracaoFireBase.getFirebase()
                            .child("mensagens")
                            .child(idRementente)
                            .child(idDestinatario);

            valueEventListenerMensagens = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensagensArray.clear();

                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Mensagem mensagens =  dados.getValue(Mensagem.class);
                    mensagensArray.add(mensagens);

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        this.firebase.addValueEventListener(valueEventListenerMensagens);

            btnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mensagem = editTextMensagem.getText().toString();

                    if (mensagem.isEmpty()){
                        Toast.makeText(ConversaActivity.this, "Digite uma mensagem paa ser enviada", Toast.LENGTH_LONG).show();
                    }else
                    {
                        Mensagem mensagems = new Mensagem();
                        mensagems.setIdUsuario(identificadorUsuario);
                        mensagems.setMensagem(mensagem);

                        Boolean retornoMensagemRementente = salvarMensagem(idRementente, idDestinatario, mensagems);

                            if (!retornoMensagemRementente){
                                Toast.makeText(
                                        ConversaActivity.this,
                                        "Ocorreu um problema ao enviar a mensagem",
                                        Toast.LENGTH_LONG
                                ).show();
                            }else {

                                // salvando mensagem par ao destinatario
                                Boolean retornoMensagemDestinatario = salvarMensagem( idDestinatario,idRementente , mensagems);

                                if (!retornoMensagemDestinatario){

                                    Toast.makeText(
                                            ConversaActivity.this,
                                            "O destinátario não recebeu a mensagem. Tente novamente!",
                                            Toast.LENGTH_LONG
                                    ).show();

                                }

                            }

                            // salvar conversa para o remetente
                            Conversas conversa = new Conversas();
                            conversa.setIdUsuario(idDestinatario);
                            conversa.setNome(nomeUsuarioDestinatario);
                            conversa.setMensagem(editTextMensagem.getText().toString());


                           Boolean retornoConversaDestinatario = salvarConversa(idRementente, idDestinatario, conversa);
                            if (!retornoConversaDestinatario){
                                Toast.makeText(
                                        ConversaActivity.this,
                                        "Problema ao salvar a conversa. Tente novamente!",
                                        Toast.LENGTH_LONG
                                ).show();
                            }else {
                                // salvar conversa para o destinatário
                                conversa = new Conversas();
                                conversa.setIdUsuario(idRementente);
                                conversa.setNome(nomeUsuarioRemetente);
                                conversa.setMensagem(editTextMensagem.getText().toString());
                                salvarConversa( idDestinatario, idRementente, conversa);
                            }

                        editTextMensagem.setText("");
                    }

                }
            });

    }

    private Boolean salvarConversa(String idUsuarioRemetente, String idUsuarioDestinatario, Conversas conversa){

        try{
            firebase = ConfiguracaoFireBase.getFirebase().child("conversas");
            firebase.child(idUsuarioRemetente)
                    .child(idUsuarioDestinatario)
                    .setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagens){
        try{
            this.firebase = ConfiguracaoFireBase.getFirebase().child("mensagens");

            this.firebase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
            .setValue(mensagens);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.firebase.removeEventListener(valueEventListenerMensagens);
    }
}
