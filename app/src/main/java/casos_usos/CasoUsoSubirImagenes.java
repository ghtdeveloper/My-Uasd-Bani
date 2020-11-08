package casos_usos;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

/**
 * Creado por Edison Martinez on 07,Saturday,2020
 **/
public class CasoUsoSubirImagenes {
    /**
     * TAG INFO
     **/
    private final String TAG = "Caso uso subir imagenes";
    /**
     * Objeto Firebase
     **/
    private StorageReference reference;
    /**
     * Objeto Referencia Storage
     **/
    private final CasoUsoConexionStorage objStorage;

    /**
     * Constructor de la clase'
     */
    public CasoUsoSubirImagenes(Context context) {
        objStorage = new CasoUsoConexionStorage(context);

    }//Fin del constructor de la clase

    /**
     * Metodo definido para subir imagenes al file Storage
     **/
    public void subirImagenesPictUsuarios(@NotNull Uri uriImagen) {
        reference = objStorage.getFirebaseStorage().getReference()
                .child("/Imagenes").child("Usuarios/" + uriImagen.getPathSegments());
        reference.putFile(uriImagen);
    }//Fin del metodo subirImagenesPictUsuarios

    /**
     * Metodo definido para ser la referencia de los archivos
     * colocados en el storage
     */
    public StorageReference referencePict(String idUsuario,String nombreArchivo)
    {
        return reference = objStorage.getFirebaseStorage().getReference()
                .child("/Imagenes").child("/Usuarios").child(idUsuario+"/"+nombreArchivo);
    }//Fin del metodo referencePict


}//Fin de la class CasoUsoSubirImagenes
