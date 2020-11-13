package adaptadores_vistas;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ghtdeveloper.my_uasd_bani.R;

/**
 * Creado por Edison Martinez on 09,Monday,2020
 **/
public class AdaptadorVistaListItem  extends RecyclerView.ViewHolder
{
    //Vistas
    public TextView txtMateria;
    public TextView txtProfesor;
    public TextView txtSeccion;
    public TextView txtLugar;
    public TextView txtAula;
    public Button   btnVerGps;

    public AdaptadorVistaListItem(@NonNull View itemView)
    {
        super(itemView);
        txtMateria = itemView.findViewById(R.id.txtMateria);
        txtProfesor = itemView.findViewById(R.id.txtProfesor);
        txtSeccion = itemView.findViewById(R.id.txtSeccion);
        txtLugar = itemView.findViewById(R.id.txtLugar);
        txtAula = itemView.findViewById(R.id.txtAula);
        btnVerGps = itemView.findViewById(R.id.btnVerGPS);
    }//Fin del constructor

}//Fin de la class AdaptadorVistaListItem
