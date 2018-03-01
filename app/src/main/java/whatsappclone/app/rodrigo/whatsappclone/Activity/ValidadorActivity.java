package whatsappclone.app.rodrigo.whatsappclone.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;

import java.util.HashMap;

import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {


    private EditText editCodigoValidacao;
    private Button bottonValidacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);


        editCodigoValidacao = findViewById(R.id.editTextCodigoValidacao);
        bottonValidacao     = findViewById(R.id.buttonValidar);

        // mascarando o codigo
        SimpleMaskFormatter smfMask             = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher     editCodigoMask      = new MaskTextWatcher(editCodigoValidacao, smfMask);
        editCodigoValidacao.addTextChangedListener(editCodigoMask);


        bottonValidacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recuperar dados

                Preferencias dadosPreferncias = new Preferencias(ValidadorActivity.this );

                HashMap<String, String> usuario = dadosPreferncias.getDadosUsuuario();

                String tokenGerado      = usuario.get("token");
                String tokenDigitado    = editCodigoValidacao.getText().toString();

                    if (tokenDigitado.equals(tokenGerado)){
                        Toast.makeText(ValidadorActivity.this, "Token validado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ValidadorActivity.this, "Token inválido!", Toast.LENGTH_SHORT).show();
                    }


            }
        });

    }
}
