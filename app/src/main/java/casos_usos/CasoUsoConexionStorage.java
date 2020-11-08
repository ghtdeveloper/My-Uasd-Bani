
package casos_usos;

/*
Clase definida para ser utilizada para crear la referencia
al Storage de Firebase para subir las imagenes
 */

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;

public class CasoUsoConexionStorage
{

   //Constructor de la class
     public CasoUsoConexionStorage(Context context)
     {} //Fin del constructor de la class

    public FirebaseStorage getFirebaseStorage()
    {
        return FirebaseStorage.getInstance();
    }//Fin del metodo getFirebaseStorage
}//Fin de la class CasoUsoConexionStorage
