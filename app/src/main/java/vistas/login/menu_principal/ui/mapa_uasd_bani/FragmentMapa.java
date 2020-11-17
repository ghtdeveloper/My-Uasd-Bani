package vistas.login.menu_principal.ui.mapa_uasd_bani;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import contratos.Contratos;


public class FragmentMapa extends Fragment implements Contratos.VistaFragmentoMapa
{

    //Vista
    private View root;
    private Spinner spinnerUbicaciones;
    //Variables
    private String valorSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Se asigna el tema para este fragmento
        requireContext()
                .getTheme().applyStyle(R.style.materialDesignbyLogin,false);
        root = inflater.inflate(R.layout.fragment_mapa, container, false);
        return root;
    }//Fin del metodo onCreateView


    //Map Layout
    private  final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            Log.w("DATA SPINNER",getValorSpinner());
            LatLng latLng = new LatLng(18.484087,-69.939378);
            googleMap.addMarker(new MarkerOptions().position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.mapLocalidad);
        if(supportMapFragment != null)
        {
           supportMapFragment. getMapAsync(onMapReadyCallback);
        }
        init();
    }//Fin del metodo onViewCreated

    @Override
    public void init()
    {
        Toolbar toolbarMapa = root.findViewById(R.id.toolbarMapa);
        spinnerUbicaciones = root.findViewById(R.id.spinnerLugares);
        //Configuracion Toolbar
        ((AppCompatActivity) requireActivity())
                .setSupportActionBar(toolbarMapa);
        //Texto Toolbar
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("Mapa UASD-BANI");
        //Icono Toolbar
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setHomeAsUpIndicator(R.drawable.ic_menu_white_24);
        //Se habilita el boton de retroceder
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);
        //Adaptador Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.listUbicaciones,R.layout.spinner_style);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUbicaciones.setAdapter(adapter);
        //Listerner Adapter
        spinnerUbicaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                valorSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }//Fin del metodo init

    private String getValorSpinner()
    {
        return valorSpinner;
    }

}//Fin de la class FragmentMapa