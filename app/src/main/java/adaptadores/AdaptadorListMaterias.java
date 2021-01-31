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


public class AdaptadorListMaterias extends FirestoreRecyclerAdapter<Materias, AdaptadorVistaListItem>
{
    //Interfaz
    private onItemClickCoordenadas onItemClickCoordenadas;
    //Variables
    private String idMateria;

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


    /*
        Se define la interfaz para el evento OnCLick
     */
    public interface  onItemClickCoordenadas
    {
        void onItemClick(int posicion);
    }//Fin de la interfaz onItemClickCoordenadas



    @Override
    protected void onBindViewHolder(@NonNull AdaptadorVistaListItem holder, final int position,
                                    @NonNull final Materias model)
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
                setIdMateria(model.getId());
               onItemClickCoordenadas.onItemClick(position);
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

    //Gettes & Setters
    public void setOnItemClickCoordenadas(AdaptadorListMaterias.onItemClickCoordenadas
                                                  onItemClickCoordenadas)
    {
        this.onItemClickCoordenadas = onItemClickCoordenadas;
    }//Fin del metodo setOnItemClickCoordenadas

    public String getIdMateria()
    {
        return idMateria;
    }//Fin del metodo getIdMateria

    public void setIdMateria(String idMateria)
    {
        this.idMateria = idMateria;
    }//Fin del metodo setIdMateria


}//Fin de la clase AdaptadorListMaterias
