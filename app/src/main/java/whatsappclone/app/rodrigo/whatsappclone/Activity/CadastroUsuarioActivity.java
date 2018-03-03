package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.Model.Usuario;
import whatsappclone.app.rodrigo.whatsappclone.R;

public class CadastroUsuarioActivity extends AppCompatActivity {


    private EditText nomeUsuario;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private Button botaoCadastrar;
    private Usuario usuario;


    private FirebaseAuth autenticacao;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nomeUsuario = findViewById(R.id.editTextNomeUsuario);
        emailUsuario    = findViewById(R.id.editTextEmailUsuairo);
        senhaUsuario    = findViewById(R.id.editTextSenhaUsuario);
        botaoCadastrar  = findViewById(R.id.buttonCadastrarUsuario);

        autenticacao  = ConfiguracaoFireBase.getFirebaseAutetnicacao();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out
                }
                // ...
            }
        };

         botaoCadastrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 usuario  = new Usuario();
                 usuario.setNomeCompletoUsuario(nomeUsuario.getText().toString());
                 usuario.setEmailUsuario(emailUsuario.getText().toString());
                 usuario.setSenhaUsuario(senhaUsuario.getText().toString());

                 cadastrarUsuario();
             }
         });



    }

    @Override
    public void onStart() {
        super.onStart();
        autenticacao.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            autenticacao.removeAuthStateListener(mAuthListener);
        }
    }


    public void cadastrarUsuario(){

        // sempre atualizar o google play services


        autenticacao  = ConfiguracaoFireBase.getFirebaseAutetnicacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmailUsuario(),
                usuario.getSenhaUsuario()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this,"Cadastro realizado com sucesso!",Toast.LENGTH_SHORT).show();

                    usuario.setIdUsario(task.getResult().getUser().getUid());
                    usuario.salvar();

                    // encerrar a sessão e voltar a tela de login
                    autenticacao.signOut();
                    finish();

                }else {

                    String excecao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte. Contendo mais caracteres e letras e números!";
                        e.printStackTrace();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um e-mail válido!";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esse e-mail já está em uso.";
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    Toast.makeText(CadastroUsuarioActivity.this,"Ocorreu um erro: " + excecao.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
