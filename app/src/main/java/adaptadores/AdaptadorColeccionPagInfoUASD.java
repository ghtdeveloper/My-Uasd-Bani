package adaptadores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import vistas.login.menu_principal.ui.informacion.InfoUasd.FragmentoInfoUasd1;
import vistas.login.menu_principal.ui.informacion.InfoUasd.FragmentoInfoUasd2;


public class AdaptadorColeccionPagInfoUASD extends FragmentStatePagerAdapter
{

    public AdaptadorColeccionPagInfoUASD(@NonNull FragmentManager fm)
    {
        super(fm);
    }//Fin del constructor de la clase AdaptadorColeccionPagInfoUASD


    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new FragmentoInfoUasd1();

            case 1:
                return  new FragmentoInfoUasd2();

        }
        return new FragmentoInfoUasd1();
    }//Fin del metodo getItem

    @Override
    public int getCount()
    {
        return 2;
    }//Fin del metodo getCount

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

}//Fin de la class AdaptadorColeccionPagInfoUASD
