package casos_usos;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

/**
 * Creado por Edison Martinez on 03,Tuesday,2020
 **/
public class CasoUsoConsultas
{
    /**Variable TAG INFO**/
    private final String TAG="Consultas";
    /**Objetos Firebases**/
    private  CasoUsoEstablecerConexionBD objConexion;


    /**
     * Constructor de la clase
     **/
    public CasoUsoConsultas(Context context)
    {
        objConexion = new CasoUsoEstablecerConexionBD(context);
    }//Fin del constructor de la clase


    /*
        Se define el metodo para consultar las facultades
     */
    public CollectionReference obtenerListadoFacultades()
    {
        Log.w(TAG,"Listado de facultades");
        return  objConexion.conexion().collection("Collect_Ajust_App")
                .document("Facultades").collection("Collect_Facultades");
    }//Fin del metodo obtenerListadoFacultades

    /*
        Se define el metodo para consultar las carreras de las facultades
     */
    public CollectionReference obtenerListadoCarreras(String facultad)
    {
        Log.w(TAG,"Listado Carreras");
        return  objConexion.conexion().collection("Collect_Ajust_App")
                .document("Facultades").collection("Collect_Facultades")
                .document(facultad).collection("collect_carreras");
    }//Fin del metodo obtenerListadoCarreras

    /*
        Se define el metodo para realizar la consulta del usaurio
     */
    public Query obtenerDatosUsuario(String idUsuario)
    {
        Log.w(TAG,"Datos usuario");
        return  objConexion.conexion().collection("Collect_QA_UASD_BANI")
                .document("Usuarios").collection("collect_usuarios")
                .whereEqualTo("id",idUsuario);
    }//Fin del metodo obtenerDatosUsuario



}//Fin del metodo CasoUsoConsultas