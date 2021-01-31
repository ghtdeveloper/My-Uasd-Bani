package casos_usos;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;


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


    /*
        Se define el query para la consulta del listado de las materias utilizando como parametro
        la facultad
     */
    public Query obtenerListadoMaterias(String valueFacultad )
    {
        Log.w(TAG,"Listado Materias x facultad");
        return  objConexion.conexion().collection("Collect_Ajust_App")
                .document("Facultades").collection("Collect_Facultades")
                .document(valueFacultad).collection("collect_materias");
    }//Fin del metodo obtenerListadoMaterias

    /*
    Se define el query para la consulta del listado de las materias utilizando como parametro
    el nombre del docente
     */
    public Query obtenerListadoMateriasxDocente(String facultad,String profesor)
    {
        Log.w(TAG,"Listado Materias x docentes");
        return  objConexion.conexion().collection("Collect_Ajust_App")
                .document("Facultades").collection("Collect_Facultades")
                .document(facultad).collection("collect_materias")
                .whereEqualTo("profesor",profesor);
    }//Fin del metodo obtenerListadoMateriasxDocente

    /*
        Se define el metodo para obtener los puntos de geolocalizacion  donde se imparte la materia
        segun  el id de la misma
     */

    public  Query obtenerPuntoGeografico(String facultad,String id)
    {
        Log.w(TAG,"Datos geografico lugar materia");
        return  objConexion.conexion().collection("Collect_Ajust_App")
                .document("Facultades").collection("Collect_Facultades")
                .document(facultad).collection("collect_materias")
                .whereEqualTo("id",id);
    }//Fin del metodo obtenerPuntoGeografico



}//Fin del metodo CasoUsoConsultas
