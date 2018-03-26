package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsappclone.app.rodrigo.whatsappclone.Adapter.TabAdapter;
import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Contato;
import whatsappclone.app.rodrigo.whatsappclone.Model.Usuario;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Base64Custom;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;
import whatsappclone.app.rodrigo.whatsappclone.helper.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticar;
    private Toolbar toolbar;
    private SlidingTabLayout slidingtablayout;
    private ViewPager viewPager;
    private String identifcadorContato;
    private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticar  = ConfiguracaoFireBase.getFirebaseAutetnicacao();

        toolbar = findViewById(R.id.toolbarMenuWhatsApp);
        toolbar.setTitle("WhatsApp Clone");
        setSupportActionBar( toolbar);

        slidingtablayout = findViewById(R.id.stl_tab);
        viewPager        = findViewById(R.id.vp_pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        slidingtablayout.setViewPager(viewPager);
        slidingtablayout.setDistributeEvenly(true);
        slidingtablayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccente));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater infeite = getMenuInflater();
        infeite.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                AbrirCadastroContato();

                return true;
            case R.id.iten_configuracoes:
                return true;
            case R.id.item_pesquisa:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void AbrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo usuário");
        alertDialog.setMessage("E-Mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailNovoContato = editText.getText().toString();

                    if (emailNovoContato.equals("")){
                        Toast.makeText(MainActivity.this, "Preencher o e-mail", Toast.LENGTH_SHORT).show();
                    } else {

                        // verificar s eo usuário está cadastro
                        identifcadorContato = Base64Custom.codificarBase64(emailNovoContato);

                        // recupera instancia firebase para verificar se já existe esse contato
                        referenciaFirebase  = ConfiguracaoFireBase.getFirebase().child("usuarios").child(identifcadorContato);

                        referenciaFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.getValue() != null){

                                    Preferencias preferencias = new Preferencias(MainActivity.this);
                                     String identificadorUsuarioLogado = preferencias.getIdentificador();

                                    referenciaFirebase = ConfiguracaoFireBase.getFirebase();
                                    referenciaFirebase = referenciaFirebase.child("contatos")
                                                                            .child(identificadorUsuarioLogado)
                                                                            .child(identifcadorContato);

                                   // recuperar dados do contato para serem adicionados

                                    Usuario usuarioContato  = dataSnapshot.getValue(Usuario.class);

                                    Contato contato = new Contato();
                                    contato.setIdentificador(identifcadorContato);
                                    contato.setNome(usuarioContato.getNomeCompletoUsuario());
                                    contato.setEmail(usuarioContato.getEmailUsuario());



                                    referenciaFirebase.setValue(contato);


                                }else {
                                    Toast.makeText(MainActivity.this, "Usuário não tem cadastro", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }



            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    /**
     * sair do app
     */
    private void deslogarUsuario(){
        autenticar.signOut();
        Intent intent = new Intent(MainActivity.this, activity_login.class);
        startActivity(intent);
        finish();
    }
}
