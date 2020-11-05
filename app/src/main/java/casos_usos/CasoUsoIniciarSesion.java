package casos_usos;

import android.content.Context;
import com.google.firebase.firestore.Query;

/**
 * Creado por Edison Martinez on 02,Monday,2020
 **/
public class CasoUsoIniciarSesion
{
    /**Objeto Firebase para la conexion**/
    private CasoUsoEstablecerConexionBD objConexion;


    /**
     * Se define el constructor de la clase
     **/
    public  CasoUsoIniciarSesion (Context context)
    {
        objConexion = new CasoUsoEstablecerConexionBD( context);
    }//Fin del constructor de la clase

    /**
     *
     * @param correo = correo del usuario
     * @param clave = clave del usuario
     * @return = se retornara los datos de ese usuario
     */
    public Query obtenerDataInicioSesion(String correo,String clave)
    {
        return  objConexion.conexion().collection("Collect_QA_UASD_BANI")
                .document("Usuarios").collection("collect_usuarios")
                .whereEqualTo("correo",correo).whereEqualTo("claveAcceso",clave);
    }//Fin del metodo obtenerDataInicioSesion

}//Fin de la class CasoUsoIniciarSesion
