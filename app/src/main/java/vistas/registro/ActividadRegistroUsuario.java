package vistas.registro;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ghtdeveloper.my_uasd_bani.R;
import com.ghtdeveloper.my_uasd_bani.modelo.Usuarios;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import casos_usos.CasoRegistrarUsuario;
import contratos.Contratos;
import herramientas.Transiciones;
import presentador.Presentador;
import vistas.login.ActividadInicioSesion;

public class ActividadRegistroUsuario extends AppCompatActivity  implements
        Contratos.VistaActividadRegistroUsuario, Transiciones, PopupMenu.OnMenuItemClickListener
{
    //Vistas
    //EditeTex
    private TextInputEditText txtPrimerNombre;
    private TextInputEditText txtPrimerApellido;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtPassword;
    private TextInputEditText txtConfirmarPasswrod;
    //TextInputLayout
    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutApellido;
    private TextInputLayout textInputLayoutCorreo;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    //ImageView
    private ImageView imageViewUsuario;
    //Spinners
    private Spinner spinnerFacultad;
    private Spinner spinnerCarrera;
    //Objetos
    private  Presentador objPresentador;
    private AlertDialog dialog;
    private Intent intentRegistroUsuario;
    private Uri uriImage;
    //Variables
    private static String facultadSelect;
    private static String carreraSelect;
    private final int REQUEST_CAMERA =0;
    private final int REQUEST_GALERIA =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        transicionInput();
        //Se asigna el tema para este fragmento
        Objects.requireNonNull(this)
                .getTheme().applyStyle(R.style.materialDesignbyLogin,false);
        setContentView(R.layout.actividad_registro_usuario);
        //Se inicializan los objetos
        objPresentador = new Presentador(ActividadRegistroUsuario.this);
        init();
    }//Fin del metodo onCreate


    @Override
    public void init()
    {
        //Cast a las vistas
        //Vistas
        ImageButton btnGuardarUsuario = findViewById(R.id.btnGuardarUsuario);
        txtPrimerNombre = findViewById(R.id.txtPrimerNombre);
        txtPrimerApellido = findViewById(R.id.txtPrimerApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtClave);
        txtConfirmarPasswrod = findViewById(R.id.txtConfirmarClave);
        //TextInputLayout
        textInputLayoutNombre = findViewById(R.id.textInputLayoutPrimerNombre);
        textInputLayoutApellido = findViewById(R.id.textInputLayoutPrimerApellido);
        textInputLayoutCorreo = findViewById(R.id.textInputLayoutCorreo);
        textInputLayoutPassword = findViewById(R.id.textLayoutClave);
        textInputLayoutConfirmPassword = findViewById(R.id.textLayoutConfirmarClave);
        imageViewUsuario = findViewById(R.id.imageViewUsuario);
        //Configuracion Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarReg);
        toolbar.setTitle(R.string.textToolbarRegistroUsuario);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        //Spinner
        spinnerFacultad = findViewById(R.id.spinnerFacultad);
        //Se carga su adaptador
        cargarListaFacultades();
        //Carreras
        spinnerCarrera = findViewById(R.id.spinnerCarrera);

        //Listeners
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarActividadLogin();
            }
        });
        btnGuardarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registrarNuevoUsuario();
            }
        });
        spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                facultadSelect = parent.getItemAtPosition(position).toString();
                cargarListaCarrera(facultadSelect);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                carreraSelect = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }//Fin del metodo init


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapPict;
        if(requestCode ==REQUEST_CAMERA && resultCode == RESULT_OK)
        {
            assert data != null;
            Bundle bundle = data.getExtras();
            assert  bundle != null;
            bitmapPict = (Bitmap) bundle.get("data");
            imageViewUsuario.setImageBitmap(bitmapPict);
        }//Fin del if captura camara

        if(requestCode == REQUEST_GALERIA && resultCode == RESULT_OK)
        {
            //Uri
            assert data != null;
            uriImage = data.getData();
            assert  uriImage != null;
            try {
                bitmapPict = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uriImage));
                imageViewUsuario.setImageBitmap(bitmapPict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }//Fin del if galeria pict
    }//Fin del metodo onActivityResult

    @Override
    public void mostrarActividadLogin()
    {
        Intent intent = new Intent(this, ActividadInicioSesion.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }//Fin del metodo mostrarActividadLogin

    /*
        Se define el metodo para registrar el nuevo usuario
     */
    @Override
    public void registrarNuevoUsuario()
    {
        //Realizar Validaciones
       if(Objects.requireNonNull(txtPrimerNombre.getText()).toString().isEmpty() ||
       Objects.requireNonNull(txtPrimerApellido.getText()).toString().isEmpty() ||
               Objects.requireNonNull(txtCorreo.getText()).toString().isEmpty()
       || Objects.requireNonNull(txtPassword.getText()).toString().isEmpty() ||
               Objects.requireNonNull(txtConfirmarPasswrod.getText()).toString()
               .isEmpty())
       {
           textInputLayoutNombre.setErrorEnabled(true);
           textInputLayoutNombre.setError("Favor verificar campo");

           textInputLayoutApellido.setErrorEnabled(true);
           textInputLayoutApellido.setError("Favor verificar campo");

           textInputLayoutCorreo.setErrorEnabled(true);
           textInputLayoutCorreo.setError("Favor verificar campo");

           textInputLayoutPassword.setErrorEnabled(true);
           textInputLayoutPassword.setError("Favor verificar campo");

           textInputLayoutConfirmPassword.setErrorEnabled(true);
           textInputLayoutConfirmPassword.setError("Favor verificar campo");
       }

        else
        {
            textInputLayoutNombre.setErrorEnabled(false);
            textInputLayoutNombre.setError("");

            textInputLayoutApellido.setErrorEnabled(false);
            textInputLayoutApellido.setError("");

            textInputLayoutCorreo.setErrorEnabled(false);
            textInputLayoutCorreo.setError("");

            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutPassword.setError("");

            textInputLayoutConfirmPassword.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setError("");

            if(!txtPassword.getText().toString().equals(txtConfirmarPasswrod.getText().toString()))
            {
                textInputLayoutPassword.setErrorEnabled(true);
                textInputLayoutPassword.setError("Las contraseñas no coinciden");

                textInputLayoutConfirmPassword.setErrorEnabled(true);
                textInputLayoutConfirmPassword.setError("Las contraseñas no coinciden");
            }else
            {
                textInputLayoutPassword.setErrorEnabled(false);
                textInputLayoutPassword.setError("");

                textInputLayoutConfirmPassword.setErrorEnabled(true);
                textInputLayoutConfirmPassword.setError("");

                //Se registra el usaurio
                objPresentador.registrarUsuario(new Usuarios(Objects.requireNonNull(txtPrimerNombre.
                        getText()).toString(), Objects.requireNonNull(txtPrimerApellido.getText()).
                        toString(), Objects.requireNonNull(txtCorreo.getText()).toString(),
                        facultadSelect,carreraSelect, Presentador.encodePassword
                        (Objects.requireNonNull(txtPassword.getText()).toString()),objPresentador.
                        establecerFechaCreacion(getApplicationContext())));
                //Se muestra el progress Bar
                mostrarProgressBar();
            }
        }
    }//Fin del metodo registrarNuevoUsuario

    @Override
    public void cargarListaFacultades()
    {
        final ArrayList<String> listFacultades = new ArrayList<>();
        objPresentador.obtenerListadoFacultades().
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error)
            {
                assert queryDocumentSnapshots != null;
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                {
                    String idDocumentos = documentSnapshot.getId();
                   listFacultades.add(idDocumentos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            ActividadRegistroUsuario.this, android.
                            R.layout.simple_list_item_1,
                            listFacultades);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFacultad.setAdapter(adapter);
                }
            }
        });
    }//Fin del metodo cargarListaFacultades

    @Override
    public void cargarListaCarrera(String facultad)
    {
        final ArrayList<String> listCarreras = new ArrayList<>();
        objPresentador.obtenerListadoCarreras(facultad).addSnapshotListener
                (new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error)
            {
                assert queryDocumentSnapshots != null;
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                {
                    String idDocumentos = documentSnapshot.getId();
                    listCarreras.add(idDocumentos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            ActividadRegistroUsuario.this,
                            android.R.layout.simple_list_item_1,listCarreras);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCarrera.setAdapter(adapter);
                }
            }
        });
    }//Fin del metodo cargarListaCarrera

    @Override
    public void mostrarProgressBar()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActividadRegistroUsuario.this);
        LayoutInflater inflater = getLayoutInflater();
        View viewDialogo = inflater.inflate(R.layout.layout_dialogo_progress_bar,null);
        builder.setView(viewDialogo);
        //Objetos
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (this) {
                    try {
                        wait(1500);
                         runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                mostrarActividadLogin();
                                Toast.makeText(getApplicationContext(),"Registro completado",
                                        Toast.LENGTH_LONG).show();
                                subirImagenes();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        dialog = builder.create();
        dialog.getLayoutInflater();
        dialog.show();

    }//Fin del metodo mostrarProgressBar

    @Override
    public void intentCapturaFoto()
    {
        intentRegistroUsuario = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentRegistroUsuario.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intentRegistroUsuario,REQUEST_CAMERA);
        }
    }//Fin del metodo intentCapturaFoto

    @Override
    public void intentAccesGaleria()
    {
        intentRegistroUsuario = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(intentRegistroUsuario,REQUEST_GALERIA);
    }//Fin del metodo intentAccesGaleria

    @Override
    public void showMenuPopup(View view)
    {
        PopupMenu popupMenu = new PopupMenu(ActividadRegistroUsuario.this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        popupMenu.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menu_imagenes,popupMenu.getMenu());
        popupMenu.show();
    }//Fin del metodo showMenuPopu

    @Override
    public void subirImagenes()
    {
        Bitmap bitmapPictUsuario = ((BitmapDrawable) imageViewUsuario.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapPictUsuario.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        //Byte Data Imagen
        byte[] dataPict = byteArrayOutputStream.toByteArray();
        /*
            Se realiza la carga de la imagen
         */
        UploadTask uploadTask = objPresentador.refenceImagenUsuario(CasoRegistrarUsuario.idobtenido,
                "PICT_USER"+"_"+CasoRegistrarUsuario.idobtenido)
                .putBytes(dataPict);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Error","Error al subir la imagen al storage");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.w("Exito","Imagen subida exitosamente");
            }
        });
        //Se descarga el URL
        uploadTask.continueWithTask(new Continuation<UploadTask.
                TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                return objPresentador.refenceImagenUsuario(CasoRegistrarUsuario.idobtenido,
                        "PICT_USER"+"_"+CasoRegistrarUsuario.idobtenido).
                        getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task)
            {
                if(task.isSuccessful())
                {
                    Uri url = task.getResult();
                    assert url != null;
                    String urlPict = url.toString();
                    objPresentador.actualizarDatos(CasoRegistrarUsuario.idobtenido)
                            .update("urlFotoPerfil",urlPict);
                } }
        });
    }//Fin del metodo subirImagenes


    @Override
    public void transicionInputMenuPrincipal()
    { }

    @Override
    public void transicionInput()
    {
        android.transition.Slide slide = new android.transition.Slide();
        slide.setDuration(500);
        getWindow().setEnterTransition(slide);
    }//Fin del metodo

    @Override
    public void transicionExit()
    { }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        objPresentador = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case  R.id.menuTomarFoto:
                intentCapturaFoto();
                return  true;

            case R.id.menuGaleria:
                intentAccesGaleria();
                return true;

            default:
                return  false;
        }
    }//Fin del metodo onMenuItemClick


}//Fin de la actividad ActividadRegistroUsuario