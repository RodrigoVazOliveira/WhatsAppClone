package whatsappclone.app.rodrigo.whatsappclone.helper;

import android.util.Base64;

/**
 * Created by rodrigovzo on 07/03/2018.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decoficarBase64(String texto){
        return Base64.decode(texto, Base64.DEFAULT).toString();
    }

}
