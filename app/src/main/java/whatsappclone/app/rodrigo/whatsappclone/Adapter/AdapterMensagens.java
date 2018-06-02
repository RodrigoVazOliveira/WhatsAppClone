package whatsappclone.app.rodrigo.whatsappclone.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import whatsappclone.app.rodrigo.whatsappclone.Model.Mensagem;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

public class AdapterMensagens extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;



    public AdapterMensagens(@NonNull Context context,@NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (mensagens != null){

            // recuperar dados do usuário remetente
            Preferencias preferencias = new Preferencias(this.context);
            String idUsuarioRemetente = preferencias.getIdentificador();

            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            Mensagem messages = mensagens.get(position);

            if (idUsuarioRemetente.equals(messages.getIdUsuario())){
                view                    = inflater.inflate(R.layout.item_conversa_direita, parent, false);
            }else {
                view                    = inflater.inflate(R.layout.item_conversa_esquerdo, parent, false);
            }


            TextView mensagemRecebe = view.findViewById(R.id.tv_mensagem);
            mensagemRecebe.setText( messages.getMensagem() );

        }
        return view;
    }
}
