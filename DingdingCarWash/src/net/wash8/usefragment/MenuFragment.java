package net.wash8.usefragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.wash8.R;

/**
 * Created by ncb-user on 2014/12/12.
 */
public class MenuFragment extends Fragment {
    private View left_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (left_menu==null){
        initFragment(inflater,container);
        }
        return left_menu;
    }

    private void initFragment(LayoutInflater inflater, ViewGroup container) {
        left_menu=inflater.inflate(R.layout.left_menu,container);
    }
}