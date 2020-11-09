package vistas.login.menu_principal.ui.informacion.InfoUasd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghtdeveloper.my_uasd_bani.R;


public class FragmentoInfoUasd2 extends Fragment {


    //View
    private View root;


    //Constructor de la clase
    public FragmentoInfoUasd2() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragmento_info_uasd2, container, false);
        return root;
    }//Fin del metodo onCreateView


}//Fin de la classFragmentoInfoUasd2