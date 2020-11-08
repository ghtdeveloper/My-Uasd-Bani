package v.mi.perfil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import casos_usos.CasoRegistrarUsuario;
import contratos.Contratos;
import presentador.Presentador;
import vistas.login.ActividadInicioSesion;
import vistas.login.menu_principal.NavMenuPrincipal;
import vistas.registro.ActividadRegistroUsuario;


public class FragmentoMiPerfil extends Fragment implements Contratos.VistaFragmentoMiPerfil,
        PopupMenu.OnMenuItemClickListener
{

    //Vista
    private View root;
    private TextInputEditText txtNombreUsuario;
    private TextInputEditText txtApellidoUsuario;
    private TextInputEditText txtCorreoUsuario;
    private Spinner spinnerFacultad;
    private Spinner spinnerCarrera;
    private ImageButton btnEditarFotoPerfil;
    private Button btnEditarCampos;
    private Button btnActualizarCampos;
    private ImageView imageViewFotoPerfil;
    //Variables
    private String facultadObtenida;
    private String carreraObtenida;
    private String facultadSelect;
    private String carreraSelect;
    private final int REQUEST_CAPTURE_PICT = 0;
    private final int REQUEST_GALELY=1;
    //Objetos
    private Presentador objPresentador;
    private Uri uriImageProfile;
    private Uri uriImageIntent;
    private Intent intent;
    private AlertDialog dialog;



    //Constructor de la clase
    public FragmentoMiPerfil() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Se asigna el tema para este fragmento
        requireContext()
                .getTheme().applyStyle(R.style.materialDesignbyLogin,false);
        root = inflater.inflate(R.layout.fragmento_mi_perfil, container, false);
        //Se inicializan los objetos
        objPresentador = new Presentador(getContext());
        init();
        return  root;
    }//Fin del metodo onCreateView


    @Override
    public void init()
    {
        txtNombreUsuario = root.findViewById(R.id.txtPrimerNombreData);
        txtApellidoUsuario = root.findViewById(R.id.txtPrimerApellidoConsulta);
        txtCorreoUsuario = root.findViewById(R.id.txtCorreoConsulta);
        spinnerFacultad = root.findViewById(R.id.spinnerFacultadConsulta);
        spinnerCarrera = root.findViewById(R.id.spinnerCarreraConsulta);
        btnEditarCampos = root.findViewById(R.id.btnEditar);
        btnActualizarCampos = root.findViewById(R.id.btnActualizar);
        btnEditarFotoPerfil = root.findViewById(R.id.btnEditarImagen);
        imageViewFotoPerfil = root.findViewById(R.id.imageViewUsuarioProfile);

        //Se carga la data
        obtenerDataFirebase();

        //Listeners
        btnEditarCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activarVistas();
            }
        });
        btnActualizarCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });
        btnEditarFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopUp();
            }
        });


    }//Fin del metodo init


    @Override
    public void obtenerDataFirebase()
    {
        objPresentador.obtenerDatosUsuario(NavMenuPrincipal.idUsuario).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                   for(QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull
                           (task.getResult()))
                   {
                       txtNombreUsuario.setText(documentSnapshot.getString("nombre"));
                       txtNombreUsuario.setTextColor(Color.MAGENTA);

                       txtApellidoUsuario.setText(documentSnapshot.getString("apellido"));
                       txtApellidoUsuario.setTextColor(Color.MAGENTA);

                       txtCorreoUsuario.setText(documentSnapshot.getString("correo"));
                       txtCorreoUsuario.setTextColor(Color.MAGENTA);

                        facultadObtenida = documentSnapshot.getString("facultad");
                       carreraObtenida = documentSnapshot.getString("carrera");

                       uriImageProfile = Uri.parse(documentSnapshot.getString("urlFotoPerfil"));
                       Picasso.with(getContext()).load(uriImageProfile).into(imageViewFotoPerfil);

                   }
                }
                cargarListaFacultades();
                spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id)
                    {
                        facultadSelect = parent.getItemAtPosition(position).toString();

                        for(int i =0; i < parent.getCount(); i++)
                        {
                            if(facultadObtenida.equals(parent.getItemAtPosition(i)))
                            {
                                spinnerFacultad.setSelection(i);
                            }
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
                cargarListaCarrera(facultadObtenida);
                spinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id)
                    {
                        carreraSelect = parent.getItemAtPosition(position).toString();

                        for(int j =0; j < parent.getCount(); j++)
                        {
                            if(carreraObtenida.equals(parent.getItemAtPosition(j)))
                            {
                                spinnerCarrera.setSelection(j);
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

            }//Fin del metodo onComplete
        });
    }///Fin del metodo obtenerDataFirebase

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
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.
                                getDocuments())
                        {
                            String idDocumentos = documentSnapshot.getId();
                            listFacultades.add(idDocumentos);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    getContext(), android.
                                    R.layout.simple_list_item_1,
                                    listFacultades);
                            adapter.setDropDownViewResource(android.R.layout.
                                    simple_spinner_dropdown_item);
                            spinnerFacultad.setAdapter(adapter);
                            spinnerFacultad.setEnabled(false);
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
                                    getContext(),
                                    android.R.layout.simple_list_item_1,listCarreras);
                            adapter.setDropDownViewResource(android.R.layout.
                                    simple_spinner_dropdown_item);
                            spinnerCarrera.setAdapter(adapter);
                            spinnerCarrera.setEnabled(false);
                        }
                    }
                });
    }//Fin del metodo cargarListaCarrera

    @Override
    public void activarVistas()
    {
        txtNombreUsuario.setEnabled(true);
        txtApellidoUsuario.setEnabled(true);

        txtCorreoUsuario.setEnabled(false);//No se edita

        spinnerCarrera.setEnabled(true);
        spinnerFacultad.setEnabled(true);

        btnActualizarCampos.setVisibility(View.VISIBLE);
        btnEditarCampos.setVisibility(View.GONE);

        btnEditarFotoPerfil.setVisibility(View.VISIBLE);
    }//Fin del metodo activarVistas

    @Override
    public void actualizarDatos()
    {
        if(Objects.requireNonNull(txtCorreoUsuario.getText()).toString().isEmpty()||
                Objects.requireNonNull(txtApellidoUsuario.getText()).toString()
                .isEmpty())
        {
            Snackbar.make(root,"Debe de agregar de completar todos los campos",Snackbar
                    .LENGTH_SHORT).show();
        }else
        {
            objPresentador.actualizarDatos(NavMenuPrincipal.idUsuario).
                    update("nombre", Objects.requireNonNull(txtNombreUsuario.getText()).toString(),
                            "apellido",txtApellidoUsuario.getText().toString(),
                            "facultad",facultadSelect,"carrera",carreraSelect,
                            "fechaModf",objPresentador.establecerFechaCreacion(getContext()));
            mostrarProgressBar();
        }//Fin del else
    }//Fin del metodo actualizarDatos


    private  void refrescarPantalla()
    {
        Intent intent = new Intent(getContext(), ActividadInicioSesion.class);
        startActivity(intent);
    }//Fin del metodo  refrescarPantalla



    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
        objPresentador = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuTomarFoto:
                intentCapturaFoto();
                return true;

            case R.id.menuGaleria:
                intentAccesGaleria();
                return  true;

            default:
                return false;
        }//Fin del switch
    }//Fin del metodo onMenuItemClick


    private void intentAccesGaleria()
    {
        intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_GALELY);
    }//Fin del metodo intentAccesGaleria


    private void intentCapturaFoto()
    {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(requireActivity().getPackageManager())!= null)
        {
            startActivityForResult(intent,REQUEST_CAPTURE_PICT);
        }
    }//Fin del metodo intentCapturaFoto


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapPict;

        if(requestCode == REQUEST_CAPTURE_PICT && resultCode==Activity.RESULT_OK)
        {
            assert  data!= null;
            Bundle bundle = data.getExtras();
            assert bundle != null;
            bitmapPict = ((Bitmap) bundle.get("data"));
            imageViewFotoPerfil.setImageBitmap(bitmapPict);
        }
        if(requestCode == REQUEST_GALELY && resultCode == Activity.RESULT_OK)
        {
            assert  data!= null;
            uriImageProfile = data.getData();
            assert  uriImageProfile != null;
            try {
                bitmapPict = BitmapFactory.decodeStream(getActivity().getContentResolver()
                .openInputStream(uriImageProfile));
                imageViewFotoPerfil.setImageBitmap(bitmapPict);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }//Fin del metodo onActivityResult

    @Override
    public void showMenuPopUp()
    {
        PopupMenu popupMenu = new PopupMenu(getContext(),btnEditarFotoPerfil);
        MenuInflater inflater = popupMenu.getMenuInflater();
        popupMenu.setOnMenuItemClickListener(FragmentoMiPerfil.this);
        inflater.inflate(R.menu.menu_imagenes, popupMenu.getMenu());
        popupMenu.show();
    }//Fin del metodo shwMenuPopUp

    @Override
    public void subirImagenes()
    {
        Bitmap bitmapPictUsuari = ((BitmapDrawable) imageViewFotoPerfil.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapPictUsuari.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        //Byte data imagen
        byte[] datapict = byteArrayOutputStream.toByteArray();
        /*
            Se realiza la carga de la imagen
         */
        UploadTask uploadTask = objPresentador.refenceImagenUsuario(NavMenuPrincipal.idUsuario,
                "PICT_USER"+"_"+NavMenuPrincipal.idUsuario)
                .putBytes(datapict);
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
                return objPresentador.refenceImagenUsuario(NavMenuPrincipal.idUsuario,
                        "PICT_USER"+"_"+NavMenuPrincipal.idUsuario).
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
                    objPresentador.actualizarDatos(NavMenuPrincipal.idUsuario)
                            .update("urlFotoPerfil",urlPict);
                } }
        });
    }//Fin del metodo subirImagenes

    @Override
    public void mostrarProgressBar()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                refrescarPantalla();
                                Toast.makeText(getContext(),"Datos actualizados exitosamente",
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

}//Fin de la class FragmentoMiPerfil