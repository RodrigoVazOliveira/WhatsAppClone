package whatsappclone.app.rodrigo.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by rodrigovzo on 27/02/2018.
 */

public class Permissao {

    /**
     * Verificar versão do android e pedir permissão ao usuário
     * @param activity
     * @param permissoes
     * @return
     */
    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){



        if (Build.VERSION.SDK_INT >= 23){

            ArrayList<String> listaPermissao = new ArrayList<String>();

            for (String permissao : permissoes ){

                boolean validaPermisao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_DENIED;

                if (validaPermisao) listaPermissao.add(permissao);

            }

            if (listaPermissao.isEmpty()) return true;

            String[] novaPermissao = new String[listaPermissao.size()];
            listaPermissao.toArray(novaPermissao);

            ActivityCompat.requestPermissions(activity, novaPermissao, requestCode);
        }

        return true;
    }

}
