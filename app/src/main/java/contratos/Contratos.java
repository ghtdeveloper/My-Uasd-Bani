package contratos;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.ghtdeveloper.my_uasd_bani.modelo.Usuarios;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

/**
 * Creado por Edison Martinez on 14,Wednesday,2020
 **/
public interface Contratos
{
    interface  VistaActividadInicioSesion
    {
        void init();
        void mostrarActividadRegistroUsuarios();
        void autenticarUsuario();
        void mostrarRunProcees();
    }//Fin de la interfaz VistaActividadInicioSesion


    interface  VistaActividadRegistroUsuario
    {
        void init();
        void mostrarActividadLogin();
        void registrarNuevoUsuario();
        void cargarListaFacultades();
        void cargarListaCarrera(String facultad);
        void mostrarProgressBar();
        void intentCapturaFoto();
        void intentAccesGaleria();
        void showMenuPopup(View view);
        void subirImagenes();

    }//Fin de la interfaz VistaActividadRegistroUsuario

    interface  VistaFragmentoInformacion
    {
        void init();
        void mostrarRunProcess();
    }//Fin de la interfaz VistaFragmentoInformacion


    interface  VistaFragmentoMiPerfil
    {
        void init();
        void obtenerDataFirebase();
        void cargarListaFacultades();
        void cargarListaCarrera(String facultad);
        void activarVistas();
        void actualizarDatos();
        void showMenuPopUp();
        void subirImagenes();
        void mostrarProgressBar();
    }//Fin de la interfaz VistaFragmentoMiPerfil


    interface Presentador
    {
        void init(Context context);//Para inicializar los objetos
        void registrarUsuario(Usuarios objUsuario);
        Date establecerFechaCreacion(Context context);
        void autenticarUsuario(String corre, String pass);
        CollectionReference obtenerListadoFacultades();
        CollectionReference obtenerListadoCarreras(String facultad);
        Query obtenerDatosUsuario(String idUsuario);
        DocumentReference actualizarDatos(String idDocumento);
        void subirImagenesUsuarios( Uri uriPict);
        StorageReference refenceImagenUsuario(String idUsaurio,String nombreArchivo);


    }//Fin de la interfaz Presentador



}//Fin de la interfaz Contratos
