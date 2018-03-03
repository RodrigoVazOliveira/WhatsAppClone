package whatsappclone.app.rodrigo.whatsappclone.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import whatsappclone.app.rodrigo.whatsappclone.Config.ConfiguracaoFireBase;

/**
 * Created by rodrigovzo on 01/03/2018.
 */

public class Usuario {

    private String idUsario;
    private String nomeCompletoUsuario;
    private String emailUsuario;
    private String senhaUsuario;

    public String getIdUsario() {
        return idUsario;
    }

    public void setIdUsario(String idUsario) {
        this.idUsario = idUsario;
    }

    public String getNomeCompletoUsuario() {
        return nomeCompletoUsuario;
    }

    public void setNomeCompletoUsuario(String nomeCompletoUsuario) {
        this.nomeCompletoUsuario = nomeCompletoUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }


    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFireBase.getFirebase();
        referenciaFirebase.child("usuarios").child(getIdUsario()).setValue(this);
    }

    public Usuario(){

    }




}
