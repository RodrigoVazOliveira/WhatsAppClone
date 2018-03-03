package whatsappclone.app.rodrigo.whatsappclone.Config;

/**
 * Created by rodrigovzo on 01/03/2018.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFireBase {

    private static DatabaseReference preferenciasFirebaseAppConfig;
    private static FirebaseAuth auteticacaoFirebaseApp;

    public static DatabaseReference getFirebase(){

        if (preferenciasFirebaseAppConfig == null)
            preferenciasFirebaseAppConfig = FirebaseDatabase.getInstance().getReference();

        return preferenciasFirebaseAppConfig;
    }

    public static FirebaseAuth getFirebaseAutetnicacao(){

        if (auteticacaoFirebaseApp == null)
            auteticacaoFirebaseApp = FirebaseAuth.getInstance();

        return auteticacaoFirebaseApp;

    }

}
