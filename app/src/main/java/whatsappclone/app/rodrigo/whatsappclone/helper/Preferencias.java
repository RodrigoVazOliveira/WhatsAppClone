package whatsappclone.app.rodrigo.whatsappclone.helper;

import android.content.SharedPreferences;
import android.content.Context;

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
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN    = "token";

    public Preferencias(Context contextoParametro ){

            this.contexto       = contextoParametro;
            this.preferences    = this.contexto.getSharedPreferences(this.NOME_ARQUIVO, this.MODE);
            this.editor         = this.preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){

            this.editor.putString(this.CHAVE_NOME, nome);
            this.editor.putString(this.CHAVE_TELEFONE, telefone);
            this.editor.putString(this.CHAVE_TOKEN, token);
            this.editor.commit();

    }

}
