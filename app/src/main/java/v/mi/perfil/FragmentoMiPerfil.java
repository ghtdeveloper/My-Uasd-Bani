package v.mi.perfil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import contratos.Contratos;
import presentador.Presentador;
import vistas.login.menu_principal.NavMenuPrincipal;
import vistas.registro.ActividadRegistroUsuario;


public class FragmentoMiPerfil extends Fragment implements Contratos.VistaFragmentoMiPerfil
{

    //Vista
    private View root;
    private TextInputEditText txtNombreUsuario;
    private TextInputEditText txtApellidoUsuario;
    private TextInputEditText txtCorreoUsuario;
    private Spinner spinnerFacultad;
    private Spinner spinnerCarrera;
    //Variables
    private String facultadObtenida;
    private String carreraObtenida;


    //Objetos
    private Presentador objPresentador;


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

        //Se carga la data
        obtenerDataFirebase();

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
                   for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                   {
                       txtNombreUsuario.setText(documentSnapshot.getString("nombre"));
                       txtNombreUsuario.setTextColor(Color.MAGENTA);

                       txtApellidoUsuario.setText(documentSnapshot.getString("apellido"));
                       txtApellidoUsuario.setTextColor(Color.MAGENTA);

                       txtCorreoUsuario.setText(documentSnapshot.getString("correo"));
                       txtCorreoUsuario.setTextColor(Color.MAGENTA);

                        facultadObtenida = documentSnapshot.getString("facultad");
                       carreraObtenida = documentSnapshot.getString("carrera");
                   }
                }
                cargarListaFacultades();
                spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                               long id)
                    {
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
    public void onDestroy() {
        super.onDestroy();
        root = null;
        objPresentador = null;
    }
}//Fin de la class FragmentoMiPerfil