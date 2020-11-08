package casos_usos;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;

/**
 * Creado por Edison Martinez on 05,Thursday,2020
 **/
public class CasoUsoActualizar
{
    /**Variable TAG INFO**/
    private  static String TAG = "Actualización de usuario";
    /**Objeto Firebase**/
    private  CasoUsoEstablecerConexionBD objConexionBD;

    /**
     * Constructor de la clase
     */
    public CasoUsoActualizar(Context context)
    {
        objConexionBD = new CasoUsoEstablecerConexionBD(context);
    }//Fin del constructor de la clase


    /**
     * Metodo para actualizar los datos del usuario
     * Nombre, Apellido, Correo (la clave no va)
     */
    public DocumentReference actualizarDatos(String idDocumento)
    {
        Log.w(TAG,"Actualizando datos");
        return  objConexionBD.conexion().collection("Collect_QA_UASD_BANI").
                document("Usuarios").collection("collect_usuarios")
                .document(idDocumento);
    }//Fin del metodo actualizarDatos



}//Fin de la class CasoUsoActualizar