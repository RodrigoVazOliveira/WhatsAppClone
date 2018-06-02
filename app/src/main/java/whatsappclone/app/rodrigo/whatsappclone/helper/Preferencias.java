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
    private final String CHAVE_NOME     = "nomeUsuarioLogado";
    private final String CHAVE_IDENTIFICADOR  = "IdentificadorUsuarioLogado";

    public Preferencias(Context contextoParametro ){

            this.contexto       = contextoParametro;
            this.preferences    = this.contexto.getSharedPreferences(this.NOME_ARQUIVO, this.MODE);
            this.editor         = this.preferences.edit();
    }

    public void salvarDadoss(String identificadorUsuario, String nomeUsuario){

            this.editor.clear();
            this.editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
            this.editor.putString(CHAVE_NOME, nomeUsuario);
            this.editor.commit();

    }


    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNomeUsuario(){
        return preferences.getString(CHAVE_NOME, null);
    }
}
