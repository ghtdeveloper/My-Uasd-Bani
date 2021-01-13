package vistas.login.menu_principal.ui.listado_materias_horarios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ghtdeveloper.my_uasd_bani.R;
import com.ghtdeveloper.my_uasd_bani.modelo.Materias;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import adaptadores.AdaptadorListMaterias;
import adaptadores.detail_map.FragmentoMapaDetail;
import contratos.Contratos;
import presentador.Presentador;
import vistas.login.menu_principal.NavMenuPrincipal;


public class FragmentoListadoMaterias extends Fragment implements Contratos.
        VistaFragmentoListadoMaterias,AdaptadorListMaterias.onItemClickCoordenadas
{
    //Vista
    private  View root;
    private RecyclerView recyclerViewListMaterias;
    private TextView textViewTituloFacultad;
    private Spinner spinnerValorFiltro;
    private FloatingActionButton btnFiltro;
    //Objetos
    private AdaptadorListMaterias objAdaptador;
    private Presentador objPresentador;
    private FirestoreRecyclerOptions<Materias> options;
    private AlertDialog dialog;
    private SharedPreferences.Editor editor;
    private  SharedPreferences preferences;
    //Variables
    private String tipoFiltro;
    private String valueFiltroSpinner;
    private String valuePreference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Se asigna el tema para este fragmento
        requireContext()
                .getTheme().applyStyle(R.style.materialDesignbyLogin, false);
        root = inflater.inflate(R.layout.fragment_listado_materias,
                container, false);
        //Se inicializan los objetos
        objPresentador = new Presentador(getContext());
        preferences  = requireActivity().getSharedPreferences(
                "filtroFacultad",Context.MODE_PRIVATE);
        //Cast a las vistas
        init();
        return root;
    }//Fin del metodo onCreateView

    @Override
    public void onStart()
    {
        super.onStart();
        cargarRecyClerView();//Se cargar el Recylcler view con los datos de las materias
    }//Fin del metodo onStart

    @Override
    public void init()
    {
         btnFiltro = root.findViewById(R.id.btnFiltro);
        recyclerViewListMaterias = root.findViewById(R.id.recyclerViewListMateriasHorarios);
        textViewTituloFacultad = root.findViewById(R.id.textViewTituloFacultad);
        //Configuracion RecyclerView
        recyclerViewListMaterias.setLayoutManager(new LinearLayoutManager(getContext()));
        Toolbar toolbarListMaterias = root.findViewById(R.id.toolbarListMaterias);
        //Configuracion Toolbar
        ((AppCompatActivity) requireActivity())
                .setSupportActionBar(toolbarListMaterias);
        //Texto Toolbar

        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("Materias & Horarios");
        //Icono Toolbar
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setHomeAsUpIndicator(R.drawable.ic_menu_white_24);
        //Se habilita el boton de retroceder
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);

        //Listeners
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSelectFiltro();
            }
        });
    }//Fin del metodo init

    @SuppressLint("SetTextI18n")
    @Override
    public void cargarRecyClerView()
    {
        //Set Adaptador primero
        valuePreference = preferences.getString("valorFacultad","no especificado");
        textViewTituloFacultad.setText("Materias: Facultad: "+valuePreference);
        options = new FirestoreRecyclerOptions.Builder<Materias>()
                .setQuery(objPresentador.obtenerListadoMaterias(valuePreference),
                        Materias.class).build();
        objAdaptador = new AdaptadorListMaterias(options);
        objAdaptador.setOnItemClickCoordenadas(this);
        recyclerViewListMaterias.setAdapter(objAdaptador);
        objAdaptador.startListening();
    }//Fin del metodo cargarRecyClerView

    @Override
    public void cargarRecyclerViewFilter(String value)
    {
        textViewTituloFacultad.setText(String.format("Materias: Facultad: %s", value));
        options = new FirestoreRecyclerOptions.Builder<Materias>()
                .setQuery(objPresentador.obtenerListadoMaterias(value),
                        Materias.class).build();
        objAdaptador = new AdaptadorListMaterias(options);
        objAdaptador.setOnItemClickCoordenadas(this);
        recyclerViewListMaterias.setAdapter(objAdaptador);
        objAdaptador.startListening();
    }//Fin del metodo cargarRecyclerViewFilter

    private void cargarRecyclerViewFilterxDocente(String docente)
    {
        textViewTituloFacultad.setText(String.format("Materias: Facultad: %s", NavMenuPrincipal.
                facultadUsuario));
        options = new FirestoreRecyclerOptions.Builder<Materias>()
                .setQuery(objPresentador.obtenerListadoMateriasxDocente(NavMenuPrincipal.
                                facultadUsuario,docente),
                        Materias.class).build();
        objAdaptador = new AdaptadorListMaterias(options);
        objAdaptador.setOnItemClickCoordenadas(this);
        recyclerViewListMaterias.setAdapter(objAdaptador);
        objAdaptador.startListening();
    }//Fin del metodo cargarRecyclerViewFilterxDocente


    @Override
    public void mostrarDialogoSelectFiltro()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.
                dialog_select_filtro,null);
        builder.setView(dialogView);
        //Cast a las vistas
        ImageButton btnCerrarDialogo = dialogView.findViewById(R.id.btnCloseDialogo);
        Spinner spinnerTipoFiltro = dialogView.findViewById(R.id.spinnerSelectTipoFiltro);
        Button btnSiguienteDialogo = dialogView.findViewById(R.id.btnSiiguienteDialog);
        //Se carga el adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.listFiltros,
            R.layout.spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoFiltro.setAdapter(adapter);
        //Listeners
        btnCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        spinnerTipoFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Capturo el valor seleccionado por el usuario
                tipoFiltro = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {}//No hago nada
        });
        btnSiguienteDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Avanzo al siguiete dialogo
                dialog.dismiss();
                mostrarDialogoFiltroPrincipal();
            }
        });
        dialog = builder.create();
        dialog.getLayoutInflater();
        dialog.show();
    }//Fin del metodo mostrarDialogoSelectFiltro

    @SuppressLint("InflateParams")
    @Override
    public void mostrarDialogoFiltroPrincipal()
    {
        editor = preferences.edit();//Editor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_filtro_principal, null);
        builder.setView(dialogView);
        //Cast a las vistas
        ImageButton btnCerrarDialogo = dialogView.findViewById(R.id.btnCloseDialogoPrincipal);
        Button btnAplicarFiltro = dialogView.findViewById(R.id.btnAplicarFiltro);
        TextView textViewTituloDialogo = dialogView.findViewById(R.id.textViewTituloDialogo);
        textViewTituloDialogo.setText(String.format("Filtrar por %s", tipoFiltro));
        spinnerValorFiltro = dialogView.findViewById(R.id.spinnerValueFiltro);
        //Se asigna el adaptador segun el valor del filtro previo select por el usuario
        if(tipoFiltro.equals("Facultades"))
        {
            cargarListaFacultades();
        }
        if(tipoFiltro.equals("Docentes"))
        {
            cargarListaDocentes();
        }
        //Listeners
        btnCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        spinnerValorFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                valueFiltroSpinner  = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnAplicarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se aplica el filtro y se recarga el recyclerView
                if(tipoFiltro.equals("Facultades"))
                {
                    cargarRecyclerViewFilter(valueFiltroSpinner);
                    editor.putString("valorFacultad",valueFiltroSpinner);
                    editor.apply();
                }
                if(tipoFiltro.equals("Docentes"))
                {
                    cargarRecyclerViewFilterxDocente(valueFiltroSpinner);
                }
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.getLayoutInflater();
        dialog.show();
    }//Fin del metodo mostrarDialogoFiltroPrincipal

    @Override
    public void cargarListaFacultades()
    {
        final ArrayList<String>listFacultades = new ArrayList<>();
        objPresentador.obtenerListadoFacultades().
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable
                    FirebaseFirestoreException error)
            {
                assert value != null;
                for(DocumentSnapshot documentSnapshot : value)
                {
                    String idDocumentos = documentSnapshot.getId();
                    listFacultades.add(idDocumentos);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),R.layout.spinner_style,listFacultades
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerValorFiltro.setAdapter(adapter);
                }
            }
        });

    }//Fin del metodo cargarListaFacultades

    @Override
    public void cargarListaDocentes()
    {
        final ArrayList<String>listDocentes = new ArrayList<>();
        objPresentador.obtenerListadoMaterias(NavMenuPrincipal.facultadUsuario).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(DocumentSnapshot documentSnapshot : Objects.requireNonNull(task
                                    .getResult()))
                            {
                                /*Log.w("Listado Docentes",documentSnapshot.getString
                                        ("profesor"));*/
                                listDocentes.add(Objects.requireNonNull(documentSnapshot.getString
                                        ("profesor")).trim());
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                        requireContext(),R.layout.spinner_style,listDocentes
                                );
                                adapter.setDropDownViewResource(android.R.
                                        layout.simple_spinner_dropdown_item);
                                spinnerValorFiltro.setAdapter(adapter);
                            }
                        }
                    }
                });
    }//Fin del metodo cargarListaDocentes

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
        objPresentador = null;
        objAdaptador =null;
        options = null;
    }//Fin del metodo onDestroy

    @Override
    public void onItemClick(int posicion)
    {
        Fragment fragment = new FragmentoMapaDetail();
        Bundle bundle = new Bundle();
        bundle.putString("idMateria",objAdaptador.getIdMateria());
        bundle.putString("facultad",valuePreference);
        fragment.setArguments(bundle);
        btnFiltro.setVisibility(View.INVISIBLE);
        textViewTituloFacultad.setText("");
        textViewTituloFacultad.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().setTransition
                (FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.layout_listado_materias,fragment).commit();
    }//Fin del metodo onItemClick

}//Fin de la clase FragmentoListadoMaterias