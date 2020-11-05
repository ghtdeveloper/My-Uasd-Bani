package vistas.login.menu_principal.ui.mapa_uasd_bani;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ghtdeveloper.my_uasd_bani.R;


public class FragmentMapa extends Fragment {

    //Vista
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_mapa, container, false);
        return root;
    }//Fin del metodo onCreateView


}//Fin de la class FragmentMapa