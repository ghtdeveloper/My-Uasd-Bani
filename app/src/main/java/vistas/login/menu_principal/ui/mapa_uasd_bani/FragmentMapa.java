package vistas.login.menu_principal.ui.mapa_uasd_bani;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import java.util.Objects;
import contratos.Contratos;


public class FragmentMapa extends Fragment implements Contratos.VistaFragmentoMapa,
        OnMapReadyCallback
{
    //Vistas
    private View root;
    private Chip btnUasdBani;
    private Chip btnEscuelaCabral;
    private Chip btnEdificioJulieta;
    private Chip btnEdificioSerret;
    private Chip btnLiceoBillini;
    private ImageButton btnZoomIn;
    private ImageButton btnZoomOut;
    //Objetos
    private LatLng latLngPos;
    private GoogleMap map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //Se asigna el tema para este fragmento
        requireContext()
                .getTheme().applyStyle(R.style.materialDesignbyLogin,false);
        root =  inflater.inflate(R.layout.fragment_mapa, container, false);
        init();
        return root;
    }//Fin del metodo onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //Configuracion Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.mapLocalidad);
        if(supportMapFragment != null)
        {
            supportMapFragment.getMapAsync(this);
        }
    }//Fin del metodo onViewCreated

    @Override
    public void init()
    {
        Toolbar toolbarMapa = root.findViewById(R.id.toolbarMapa);
        btnUasdBani = root.findViewById(R.id.btnUasdBani);
        btnEscuelaCabral = root.findViewById(R.id.btnEscuelaCabral);
        btnEdificioJulieta = root.findViewById(R.id.btnEdificioJulieta);
        btnEdificioSerret = root.findViewById(R.id.btnEdificioCarlosSerret);
        btnLiceoBillini = root.findViewById(R.id.btnEdificioLiceoGregorioBillini);
        btnZoomIn = root.findViewById(R.id.btnZoomIn);
        btnZoomOut= root.findViewById(R.id.btnZoomOut);
        Button btnEstiloNormal = root.findViewById(R.id.btnMapaStyleNormal);
        Button btnEstiloSatelite = root.findViewById(R.id.btnMapaStyleSatelite);
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
        btnEstiloNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        btnEstiloSatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

    }//Fin del metodo init

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        //Vistas
        final float zoom = 180.0f;
        map = googleMap;


        btnUasdBani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Ubicacion Bani",Toast.LENGTH_LONG).show();
                latLngPos = new LatLng(18.278175, -70.331517);
                googleMap.addMarker(new MarkerOptions().title("UASD BANI").position(latLngPos));
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        map.getUiSettings().setMapToolbarEnabled(true);
                        return false;
                    }
                });
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLngPos));


            }
        });
        btnEscuelaCabral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngPos = new LatLng(18.290711, -70.332188);
                googleMap.addMarker(new MarkerOptions().title("Escuela Aquiles Cabral")
                        .position(latLngPos));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPos,zoom));
            }
        });
        btnEdificioJulieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngPos = new LatLng(18.278007, -70.331715);
                googleMap.addMarker(new MarkerOptions().title("Edificio Julieta")
                        .position(latLngPos));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPos,zoom));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }
        });
        btnEdificioSerret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngPos = new LatLng(18.278686, -70.332323);
                googleMap.addMarker(new MarkerOptions().title("Edificio Carlos Serret")
                        .position(latLngPos));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPos,zoom));
            }
        });
        btnLiceoBillini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngPos = new LatLng(18.2776071, -70.3377431);
                googleMap.addMarker(new MarkerOptions().title("Liceo Francisco Gregorio Billini")
                        .position(latLngPos));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPos,zoom));
            }
        });

        //Por defecto se ejecutara bajo la siguiente posicion
        latLngPos = new LatLng(18.278175, -70.331517);
        googleMap.addMarker(new MarkerOptions().title("UASD BANI").position(latLngPos));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngPos,zoom));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                map.getUiSettings().setMapToolbarEnabled(true);
                return false;
            }
        });

        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }//Fin del metodo onMapReady


}//Fin de la class FragmentMapa