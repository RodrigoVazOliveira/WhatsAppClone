package whatsappclone.app.rodrigo.whatsappclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;
import java.util.logging.SimpleFormatter;

public class activity_login extends AppCompatActivity {

    private EditText editCodigoPais;
    private EditText editCodigoArea;
    private EditText editTelefone;
    private Button btnCadatrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editCodigoPais = findViewById(R.id.editTextCodigoPais);
        editCodigoArea = findViewById(R.id.editTextCodigoArea);
        editTelefone   = findViewById(R.id.editTextNumeroCelular);
        btnCadatrar    = findViewById(R.id.buttonCadastrar);

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



                }else if (codigoAreaPais.isEmpty()){
                    Toast msg = new Toast.makeText(activity_login.this, "Por favor digite o código do pais", Toast.LENGTH_SHORT).show();
                }else if (codigoArea.isEmpty()){
                    Toast msg = new Toast.makeText(activity_login.this, "Por favor digite o código do área", Toast.LENGTH_SHORT).show();
                }else if (numeroTelefone.isEmpty()){
                    Toast msg = new Toast.makeText(activity_login.this, "Por favor digite o número de telefone", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
