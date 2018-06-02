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
import whatsappclone.app.rodrigo.whatsappclone.Model.Contato;
import static whatsappclone.app.rodrigo.whatsappclone.R.*;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context  = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (this.contatos != null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout.list_contato, parent, false);

            Contato contato = contatos.get(position);

            TextView nomeContato = view.findViewById(id.list_contato_nome);
            TextView emailContato = view.findViewById(id.list_contato_email);

            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());

        }

        return view;


    }
}