package presentador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.ghtdeveloper.my_uasd_bani.modelo.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import casos_usos.CasoRegistrarUsuario;
import casos_usos.CasoUsoActualizar;
import casos_usos.CasoUsoConsultas;
import casos_usos.CasoUsoIniciarSesion;
import casos_usos.CasoUsoSubirImagenes;
import contratos.Contratos;
import vistas.login.menu_principal.NavMenuPrincipal;


public class Presentador extends View implements Contratos.Presentador
{
    //Objetos Casos de uso
    private  static CasoRegistrarUsuario objRegistroUsuario;
    private  static CasoUsoIniciarSesion objInicioSesion;
    private  static CasoUsoConsultas objConsulta;
    private  static CasoUsoActualizar objActualizar;
    private  static CasoUsoSubirImagenes objSubirImagenes;
    private static Timestamp timestamp;
    //Objetos Model
    private static  Usuarios objUsuario;
    //Objetos
    private  Context context;

    /**
     *
     * Se define el constructor de la clase
     */
    public Presentador (Context contextApp)
    {
        super(contextApp);
        init(contextApp);
    }//Fin del constructor de la clase

    @Override
    public void init(Context context)
    {
        objRegistroUsuario = new CasoRegistrarUsuario(context);
        objInicioSesion = new CasoUsoIniciarSesion(context);
        timestamp = new Timestamp(System.currentTimeMillis());
        objUsuario = new Usuarios(context);
        objConsulta = new CasoUsoConsultas(context);
        objActualizar = new CasoUsoActualizar(context);
        objSubirImagenes = new CasoUsoSubirImagenes(context);
        this.context = context;

    }//Fin del metodo init()

    @Override
    public void registrarUsuario(Usuarios objUsuario)
    {
        objRegistroUsuario.registrarUsuario(objUsuario);

    }//Fin del metodo registrarUsuario

    @Override
    public Date establecerFechaCreacion(Context context)
    {
        return  timestamp;
    }//Fin del metodo establecerFechaCreacion

    @Override
    public void autenticarUsuario(String correo, String pass)
    {
        objInicioSesion.obtenerDataInicioSesion(correo, encodePassword(pass)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots)
                    {
                        if(queryDocumentSnapshots.isEmpty())
                        {
                            Log.e("Inicio sesion","Error autenticacion");
                            mostrarMensajeDatosErrones();
                        }else
                        {
                            Log.w("Inicio sesion","Autenticación exitosa");
                            Toast.makeText(context,"Bienvenido",Toast.LENGTH_LONG).show();
                            queryDocumentSnapshots.getQuery().get().
                                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        for(QueryDocumentSnapshot queryDocumentSnapshot :
                                                Objects.requireNonNull(task.getResult()))
                                        {
                                            //Se toma el nombre del usuario
                                            objUsuario.setNombre(queryDocumentSnapshot.
                                                    getString("nombre"));
                                            //Se toma el apellido del usuario
                                            objUsuario.setApellido(queryDocumentSnapshot.
                                                    getString("apellido"));
                                            //Se toma el correo
                                            objUsuario.setCorreo(queryDocumentSnapshot.getString
                                                    ("correo"));
                                            //Facultada del usuario
                                            objUsuario.setFacultad(queryDocumentSnapshot.
                                                    getString("facultad"));
                                            //Carrera
                                            objUsuario.setCarrera(queryDocumentSnapshot.
                                                    getString("carrera"));
                                            //Se toma el id
                                            objUsuario.setId(queryDocumentSnapshot.getString(
                                                    "id"));
                                            //Se toma el url de la imagen
                                            objUsuario.setUrlFotoPerfil(queryDocumentSnapshot.
                                                    getString("urlFotoPerfil"));
                                        }
                                    }//Fin del if
                                    //Se muestra la pantalla principal
                                    mostrarPantallaPrincipal();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.e("Inicio  sesion","Error al acceder"+ e.getMessage());
            }
        });
    }//Fin del metodo autenticarUsuario

    @Override
    public CollectionReference obtenerListadoFacultades()
    {
        return objConsulta.obtenerListadoFacultades();
    }//Fin del metodo obtenerListadoFacultades

    @Override
    public CollectionReference obtenerListadoCarreras(String facultad) {
        return objConsulta.obtenerListadoCarreras(facultad);
    }//Fin del metodo obtenerListadoCarreras

    @Override
    public Query obtenerDatosUsuario(String idUsuario)
    {
        return objConsulta.obtenerDatosUsuario(idUsuario);
    }//Fin del metodo

    @Override
    public DocumentReference actualizarDatos(String idDocumento)
    {
        return objActualizar.actualizarDatos(idDocumento);
    }//Fin del metodo actualizarDatos


    @Override
    public StorageReference refenceImagenUsuario(String idUsaurio,String nombreArchivo) {
        return objSubirImagenes.referencePict(idUsaurio,nombreArchivo);
    }//Fin del metodo refenceImagenUsuario

    @Override
    public Query obtenerListadoMaterias(String facultad)
    {
        return objConsulta.obtenerListadoMaterias(facultad);
    }//Fin del metodo obtenerListadoMaterias

    @Override
    public Query obtenerListadoMateriasxDocente(String facultad, String docente)
    {
        return objConsulta.obtenerListadoMateriasxDocente(facultad, docente);
    }//Fin del metodo obtenerListadoMateriasxDocente

    @Override
    public Query datosCoordenadasMateria(String facultad, String id)
    {
        return objConsulta.obtenerPuntoGeografico(facultad, id);
    }//Fin del metodo datosCoordenadasMateria

    /*
         Se muestra la pantalla Principal
         */
    private void mostrarPantallaPrincipal()
    {
        Intent intent = new Intent(getContext(), NavMenuPrincipal.class);
        //Se prepara para enviar el intent a la actividad del menu principal
        intent.putExtra("nombreCompleto",objUsuario.getNombre()+ " "+
                objUsuario.getApellido());//Nombre Completo usuario
        intent.putExtra("correoUsuario",objUsuario.getCorreo());//Correo
        intent.putExtra("otrosDatos",objUsuario.getFacultad() + "\n"+objUsuario.
                getCarrera());
        intent.putExtra("facultadUsuario",objUsuario.getFacultad());
        intent.putExtra("idUsuario",objUsuario.getId());
        intent.putExtra("urlPict",objUsuario.getUrlFotoPerfil());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }//Fin del metodo mostrarPantallaPrincipal

    /*
        Se despliega un mensaje indicando que hubo un error
        al autenticar el usuario
     */
    private void mostrarMensajeDatosErrones()
    {
        Toast.makeText(context,"Favor validar Credenciales",Toast.LENGTH_LONG).show();
    }//Fin del metodo mostrarMensajeDatosErrones

    /*
        Metodo para cifrar la clave del usuario
     */
    public static String encodePassword(String pass)
    {
        try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        return hexString.toString();
         }   catch(Exception ex){
         throw new RuntimeException(ex);
    }
    }//Fin del metodo encodePassword


}//Fin de la class Presentador
