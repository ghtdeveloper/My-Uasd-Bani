package casos_usos;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.ghtdeveloper.my_uasd_bani.modelo.Usuarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * Creado por Edison Martinez on 02,Monday,2020
 **/
public class CasoRegistrarUsuario
{
   /** Variable INFO TAG**/
   private final String TAG="Registro Usuarios";
   /**Objetos Firebase**/
   private final CasoUsoEstablecerConexionBD objConexion;
   /**Id Generado por Firebase**/
    public static String idobtenido;

    /**
     * Constructor de la clase
     */
    public  CasoRegistrarUsuario(Context context)
    {
        objConexion = new CasoUsoEstablecerConexionBD(context);
    }//Fin del constructor


    /**
     * A continuacion se define el metodo para realizar  el registro
     * de los usuarios
     **/
    public void  registrarUsuario(Usuarios objUsuario)
    {
        objConexion.conexion().collection("Collect_QA_UASD_BANI").
                document("Usuarios").collection("collect_usuarios")
                .add(objUsuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                //Se obtiene el ID generado por Firebase
                idobtenido = documentReference.getId();
                //Se actualiza el campo ID en el documento de Firebase
                documentReference.update("id",idobtenido).addOnSuccessListener
                        (new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.w(TAG,"ID actualizado al crear el documento");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.e(TAG,"Registro no agregado "+e.getMessage());
            }
        });
    }//Fin del metodo registrarUsuario


}//Fin de la class CasoRegistrarUsuario
