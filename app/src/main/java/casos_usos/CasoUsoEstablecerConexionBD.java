package casos_usos;

import android.content.Context;
import com.google.firebase.firestore.FirebaseFirestore;


public class CasoUsoEstablecerConexionBD
{
    //Objeto Firebase
    FirebaseFirestore db;
    Context context;

    /**
     * Constructor de la clase
     * @param context = context App
     */
    public CasoUsoEstablecerConexionBD (Context context)
    {
        this.context = context;
    }//Fin del constructor de la clase


    /**
     * Se define el metodo para realizar la conexion a la BD
     **/

    public FirebaseFirestore conexion()
    {
        assert  db != null;
        return  db = FirebaseFirestore.getInstance();
    }//Fin del metodo conexion


}//Fin de la class CasoUsoEstablecerConexionBD
