package whatsappclone.app.rodrigo.whatsappclone.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import whatsappclone.app.rodrigo.whatsappclone.fragment.ContatosFragment;
import whatsappclone.app.rodrigo.whatsappclone.fragment.conversaFragments;

/**
 * Created by rodrigovzo on 05/03/2018.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONVERSAS", "CONTATOS"};


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new conversaFragments();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    public CharSequence getPageTitle(int position){
        return tituloAbas[position];
    }
}
