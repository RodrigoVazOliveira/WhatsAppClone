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

import whatsappclone.app.rodrigo.whatsappclone.Model.Conversas;
import whatsappclone.app.rodrigo.whatsappclone.R;
import whatsappclone.app.rodrigo.whatsappclone.helper.Preferencias;

public class AdapterConversas extends ArrayAdapter<Conversas> {

    private Context contetxt;
    private ArrayList<Conversas> conversas;

    public AdapterConversas(@NonNull Context context,@NonNull ArrayList<Conversas> objects) {
        super(context, 0, objects);
        this.contetxt = context;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        if (this.conversas != null){
            LayoutInflater inflater     = (LayoutInflater) this.contetxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view                        = inflater.inflate(R.layout.lista_conversa,parent,  false);
            Conversas conversa          = this.conversas.get(position);
            TextView nome       = view.findViewById(R.id.list_conversa_nome);
            TextView mensagem   = view.findViewById(R.id.list_conversa_mensagem);
            nome.setText(conversa.getNome());
            mensagem.setText(conversa.getMensagem());
        }
        return view;
    }
}
