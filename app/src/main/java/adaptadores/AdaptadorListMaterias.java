package adaptadores;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ghtdeveloper.my_uasd_bani.R;
import com.ghtdeveloper.my_uasd_bani.modelo.Materias;
import adaptadores_vistas.AdaptadorVistaListItem;

/**
 * Creado por Edison Martinez on 09,Monday,2020
 **/
public class AdaptadorListMaterias extends FirestoreRecyclerAdapter<Materias, AdaptadorVistaListItem>
{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options = query de Firebase
     */
    public AdaptadorListMaterias(@NonNull FirestoreRecyclerOptions<Materias> options)
    {
        super(options);
    }//Fin del constructor de la clase

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorVistaListItem holder, int position,
                                    @NonNull Materias model)
    {
        holder.txtMateria.setText(model.getMateria());
        holder.txtProfesor.setText(model.getProfesor());
        holder.txtSeccion.setText(model.getSeccion());
        holder.txtLugar.setText(model.getLugar());
        holder.txtAula.setText(model.getAula());
        //Listener
        holder.btnVerGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("Click","Accediendo al gps");
            }
        });
    }//Fin del metodo onBindViewHolder

    @NonNull
    @Override
    public AdaptadorVistaListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info_clases,
                parent,false);
        return new AdaptadorVistaListItem(view);
    }//Fin del metodo  AdaptadorVistaListItem

}//Fin de la clase AdaptadorListMaterias
