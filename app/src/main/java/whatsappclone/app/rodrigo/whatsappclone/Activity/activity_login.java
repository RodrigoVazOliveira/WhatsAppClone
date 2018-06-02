package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.Manifest.permission;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Usuario;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Base64Custom;
import whatsappclone.app.rodrigo.whatsappclone.helper.Permissao;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

public class activity_login extends AppCompatActivity {


    private EditText usuarioLogin;
    private EditText senhaLogin;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticar;
    private String[] permissions = new String[]{
        permission.INTERNET,
            permission.SEND_SMS
    };

    private ValueEventListener valueEventListenerUsuario;
    private DatabaseReference firebase;
    private String identificadorUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // verifica permissões
        Permissao.validaPermissoes(1, this, permissions);


        usuarioLogin = findViewById(R.id.editTextEmail);
        senhaLogin   = findViewById(R.id.editTextSenha);
        botaoLogar   = findViewById(R.id.buttonEntrar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmailUsuario( usuarioLogin.getText().toString() );
                usuario.setSenhaUsuario( senhaLogin.getText().toString() );
                validarAutenticacao();
            }
        });

    }


    private void validarAutenticacao(){
        autenticar = ConfiguracaoFireBase.getFirebaseAutetnicacao();
        autenticar.signInWithEmailAndPassword(
                usuario.getEmailUsuario(),
                usuario.getSenhaUsuario()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmailUsuario());

                    firebase = ConfiguracaoFireBase.getFirebase().child("usuarios")
                            .child(identificadorUsuarioLogado);

                    valueEventListenerUsuario = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // salvar os dados de login

                            Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);
                            String id                 = Base64Custom.codificarBase64(usuario.getEmailUsuario());
                            Preferencias preferencias = new Preferencias(activity_login.this);
                            preferencias.salvarDadoss(id, usuarioRecuperado.getNomeCompletoUsuario());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    firebase.addListenerForSingleValueEvent(valueEventListenerUsuario);
                    Toast.makeText(activity_login.this, "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    abrirTelaPrincipal();
                }else{
                    Toast.makeText(activity_login.this, "Erro ao efetuar o login!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        autenticar          = ConfiguracaoFireBase.getFirebaseAutetnicacao();
        FirebaseUser   user = autenticar.getCurrentUser();
        if (user != null){
            abrirTelaPrincipal();
        }
    }


    public void abrirTelaPrincipal(){
        Intent intent = new Intent(activity_login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado : grantResults){

            if (resultado == PackageManager.PERMISSION_DENIED){

                alertaValidacaoPermissao();

            }
        }
    }

    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent(activity_login.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }



    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para usar esse App, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
