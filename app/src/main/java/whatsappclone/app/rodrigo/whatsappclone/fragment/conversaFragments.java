package whatsappclone.app.rodrigo.whatsappclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import whatsappclone.app.rodrigo.whatsappclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class conversaFragments extends Fragment {


    public conversaFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversa_fragments, container, false);
    }

}
