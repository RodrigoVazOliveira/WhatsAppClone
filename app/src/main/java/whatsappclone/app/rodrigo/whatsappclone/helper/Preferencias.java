package whatsappclone.app.rodrigo.whatsappclone.helper;

import android.content.SharedPreferences;
import android.content.Context;

import java.util.HashMap;

/**
 * Created by rodrigovzo on 25/02/2018.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO   = "whatsapp.preferencias";
    private final int MODE              = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_NOME     = "nome";

/**    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN    = "token"; **/

    private final String CHAVE_IDENTIFICADOR  = "IdentificadorUsuarioLogado";


    public Preferencias(Context contextoParametro ){

            this.contexto       = contextoParametro;
            this.preferences    = this.contexto.getSharedPreferences(this.NOME_ARQUIVO, this.MODE);
            this.editor         = this.preferences.edit();
    }

    public void salvarDadoss(String identificadorUsuario){

            this.editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        this.editor.commit();
            /***
            this.editor.putString(this.CHAVE_NOME, nome);
            this.editor.putString(this.CHAVE_TELEFONE, telefone);
            this.editor.putString(this.CHAVE_TOKEN, token);
            this.editor.commit();

             **/



    }


    /**
    public HashMap<String, String> getDadosUsuuario(){

        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, preferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, preferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
    **/

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }
}
