package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;
import whatsappclone.app.rodrigo.whatsappclone.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticar  = ConfiguracaoFireBase.getFirebaseAutetnicacao();

        toolbar = findViewById(R.id.toolbarMenuWhatsApp);
        toolbar.setTitle("WhatsApp Clone");
        setSupportActionBar( toolbar);

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
                return true;
            case R.id.iten_configuracoes:
                return true;
            case R.id.item_pesquisa:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     * sair do app
     */
    public void deslogarUsuario(){
        autenticar.signOut();
        Intent intent = new Intent(MainActivity.this, activity_login.class);
        startActivity(intent);
        finish();
    }
}
