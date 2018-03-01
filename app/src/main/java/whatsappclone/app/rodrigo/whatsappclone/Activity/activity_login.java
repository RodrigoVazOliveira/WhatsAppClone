package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Permissao;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

import static android.widget.Toast.*;

public class activity_login extends AppCompatActivity {

    private EditText editCodigoPais;
    private EditText editCodigoArea;
    private EditText editTelefone;
    private EditText editNome;
    private Button btnCadatrar;
    private String[] permissaonecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editCodigoPais = findViewById(R.id.editTextCodigoPais);
        editCodigoArea = findViewById(R.id.editTextCodigoArea);
        editTelefone   = findViewById(R.id.editTextNumeroCelular);
        btnCadatrar    = findViewById(R.id.buttonCadastrar);
        editNome       = findViewById(R.id.editTextNome);


        Permissao.validaPermissoes(1, this, permissaonecessarias);

        SimpleMaskFormatter SimplasMaskCodigoPais   = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter SimplasMaskCodigoArea   = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter SimplasMaskTelefone     = new SimpleMaskFormatter("N NNNN-NNNN");

        MaskTextWatcher MaskEditWatcherPais                 = new MaskTextWatcher(editCodigoPais, SimplasMaskCodigoPais);
        MaskTextWatcher MaskEditWatcherArea                 = new MaskTextWatcher(editCodigoArea, SimplasMaskCodigoArea);
        MaskTextWatcher MaskEditWatcherTelefone             = new MaskTextWatcher(editTelefone, SimplasMaskTelefone);

        editCodigoPais.addTextChangedListener(MaskEditWatcherPais);
        editCodigoArea.addTextChangedListener(MaskEditWatcherArea);
        editTelefone.addTextChangedListener(MaskEditWatcherTelefone);


        btnCadatrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String codigoAreaPais = editCodigoPais.getText().toString();
                String codigoArea     = editCodigoArea.getText().toString();
                String numeroTelefone = editTelefone.getText().toString();

                // limpando mascaras

                if (!codigoAreaPais.isEmpty() && !codigoArea.isEmpty() && !numeroTelefone.isEmpty()){

                    String numerocompleto = codigoAreaPais.replace("+","").toString() + codigoArea.toString() + numeroTelefone.toString();
                    numerocompleto        = numerocompleto.replace(" ","");
                    numerocompleto        = numerocompleto.replace("-","");

                    Random randomico         = new Random();
                    int tokenNumber         = randomico.nextInt( 8999 ) + 1000;
                    String token            = String.valueOf(tokenNumber);
                    String mensagemEnvio    = "WhatsApp código de confirmação é: " + token;

                    // Salvar dados

                    Preferencias preferencias = new Preferencias(activity_login.this);
                    preferencias.salvarUsuarioPreferencias(editNome.getText().toString(), numerocompleto.toString(), token);


                    // envio do SMS
                    String emulador = "5554";
                    boolean envioTest = enviaSMS("+"+emulador, mensagemEnvio);

                    if (envioTest){
                        Intent intent = new Intent(activity_login.this, ValidadorActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(activity_login.this, "Problemas ao enviar o SMS, tente novamente!",Toast.LENGTH_LONG).show();
                    }


                }else if (codigoAreaPais.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor digite o código do pais", LENGTH_SHORT).show();
                }else if (codigoArea.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor digite o código do área", LENGTH_SHORT).show();
                }else if (numeroTelefone.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Por favor digite o número de telefone", LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     *
     * Envio de TOKEN VIA SMS
     * @param telefone
     * @param mensagem
     * @return boolean
     */
    private boolean enviaSMS(String telefone, String mensagem){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null, mensagem, null, null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResult){

        super.onRequestPermissionsResult( requestCode,  permissions,   grantResult);

        for (int resultado : grantResult){

            if (resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }

        }

    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Permissões negadas");
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
