package vistas.login.menu_principal.ui.listado_materias_horarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ghtdeveloper.my_uasd_bani.R;


public class FragmentoListadoMaterias extends Fragment
{
    //Vista
    private  View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        root = inflater.inflate(R.layout.fragment_listado_materias,
                container, false);

        return root;
    }//Fin del metodo onCreateView

}//Fin de la clase FragmentoListadoMaterias