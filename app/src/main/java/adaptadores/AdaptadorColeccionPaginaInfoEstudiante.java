package adaptadores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vistas.login.menu_principal.ui.informacion.infoEstudianteUasd.FragmentoInfoEstudiante1;
import vistas.login.menu_principal.ui.informacion.infoEstudianteUasd.FragmentoInfoEstudiante2;
import vistas.login.menu_principal.ui.informacion.infoEstudianteUasd.FragmentoInfoEstudiante3;


public class AdaptadorColeccionPaginaInfoEstudiante  extends FragmentStatePagerAdapter
{

    public AdaptadorColeccionPaginaInfoEstudiante(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return  new FragmentoInfoEstudiante1();

            case 1:
                return  new FragmentoInfoEstudiante2();

            case 2:
                return new FragmentoInfoEstudiante3();

        }//Fin del switch
        return  new FragmentoInfoEstudiante1();

    }//Fin del metodo getItem

    @Override
    public int getCount()
    {
        return 3;
    }//Fin del metodo getCount

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { return super.getPageTitle(position); }
}//Fin de la class AdaptadorColeccionPaginaInfoEstudiante
