package vistas.login.menu_principal.ui.informacion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import adaptadores.AdaptadorColeccionPagInfoUASD;
import adaptadores.AdaptadorColeccionPaginaInfoEstudiante;
import contratos.Contratos;
import herramientas.TranformadorViewPager;

public class FragmentInformacion extends Fragment implements  Contratos.VistaFragmentoInformacion
{
    //Vista
    private  View root;
    //Objetos
    private static AdaptadorColeccionPagInfoUASD objAdaptadorInfoUASD;
    private static AdaptadorColeccionPaginaInfoEstudiante objAdaptadorInfoEst;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Se asigna el tema para este fragmento
        requireContext()
                .getTheme().applyStyle(R.style.materialDesignbyLogin, false);
        root = inflater.inflate(R.layout.fragmento_informacion, container, false);
        //Se inicializan los objetos
        objAdaptadorInfoUASD = new AdaptadorColeccionPagInfoUASD(getChildFragmentManager());
        objAdaptadorInfoEst = new AdaptadorColeccionPaginaInfoEstudiante(getChildFragmentManager());
        //Se cargan la vista
        init();
        return root;
    }//Fin del metodo onCreateView

    @Override
    public void init()
    {
        final Chip chipInfoNoticiasUASD = root.findViewById(R.id.chipInfoNoticiasUASD);
        final Chip chipInfoEstudiateUASD = root.findViewById(R.id.chipInfoEstudianteUASd);
        final ViewPager viewPager  = root.findViewById(R.id.viewPagerInfo);
        final TabLayout tabLayout = root.findViewById(R.id.tabls_layout_ViewPager);

        //Listeners
        chipInfoNoticiasUASD.setOnCheckedChangeListener(new CompoundButton.
                OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chipInfoEstudiateUASD.setChecked(false);
                    viewPager.setAdapter(objAdaptadorInfoUASD);
                    viewPager.setPageTransformer(true,
                            new TranformadorViewPager());
                    tabLayout.setupWithViewPager(viewPager,true);
                }
            }
        });
        chipInfoEstudiateUASD.setOnCheckedChangeListener(new CompoundButton.
                OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chipInfoNoticiasUASD.setChecked(false);
                    viewPager.setAdapter(objAdaptadorInfoEst);
                    viewPager.setPageTransformer(true
                            ,new TranformadorViewPager());
                    tabLayout.setupWithViewPager(viewPager,true);
                }
            }
        });
    }//Fin del metodo init


    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
        objAdaptadorInfoUASD = null;
        objAdaptadorInfoEst = null;
    }
}//Fin de la clase FragmentInformacion