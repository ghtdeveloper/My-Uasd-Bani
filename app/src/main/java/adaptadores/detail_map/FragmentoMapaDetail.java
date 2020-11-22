package adaptadores.detail_map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import contratos.Contratos;
import presentador.Presentador;
import vistas.login.menu_principal.ui.listado_materias_horarios.FragmentoListadoMaterias;

public class FragmentoMapaDetail extends Fragment implements Contratos.VistaFragmentoMapaDetail
{
    //Vista
    private FragmentContainerView fragMap;
    private ImageButton btnZoomOut;
    private ImageButton btnZoomIn;
    private Button btnEstiloMapa;
    private  Button btnEstiloSatelite;
    //Objetos
    private Bundle bundle;
    private Presentador objPresentador;
    private GoogleMap map;

    private final OnMapReadyCallback callback = new OnMapReadyCallback()
    {
        @Override
        public void onMapReady(final GoogleMap googleMap)
        {
            map = googleMap;
            objPresentador.datosCoordenadasMateria(getFacultad(), getIdMateria()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : Objects.
                                    requireNonNull(task.getResult())) {

                                LatLng geopoint = new LatLng(Objects.requireNonNull
                                        (documentSnapshot.
                                                getGeoPoint("pgeolocalizacion"))
                                        .getLatitude(),
                                        Objects.requireNonNull(documentSnapshot.
                                                getGeoPoint("pgeolocalizacion"))
                                                .getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(geopoint)
                                        .title(documentSnapshot.getString("lugar")));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                                        (geopoint,95.0f));

                                    }
                                }
                            }
                    });
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragmento_mapa_detail, container, false);
    }//Fin del metodo onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //Presentador
        objPresentador = new Presentador(getContext());
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        init(view);
    }//Fin del metodo onViewCreated

    @Override
    public void init(View view)
    {
        //Vista
        Toolbar toolbarDetailFragment = view.findViewById(R.id.toolbarMapaMateriaGeo);
        fragMap = view.findViewById(R.id.map);
        btnZoomOut = view.findViewById(R.id.btnZoomOutListMaria);
        btnZoomIn = view.findViewById(R.id.btnZoomInListMateria);
        btnEstiloMapa = view.findViewById(R.id.btnMapaStyleNormalListMat);
        btnEstiloSatelite = view.findViewById(R.id.btnMapaStyleSatelitelListMat);
        //Configuracion Toolbar
        ((AppCompatActivity) requireActivity())
                .setSupportActionBar(toolbarDetailFragment);
        //Texto Toolbar
        Objects.requireNonNull(((AppCompatActivity) requireActivity())
                .getSupportActionBar()).setTitle("Detalle Ubicación");
        //Icono Toolbar
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setHomeAsUpIndicator(R.drawable.ic_arrow_white_black_26dp);

        //Se habilita el boton de retroceder
        Objects.requireNonNull(((AppCompatActivity)
                requireActivity()).getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);

        toolbarDetailFragment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFragmentoAnterior();
            }
        });
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        btnEstiloMapa.setOnClickListener(new View.OnClickListener() {
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
    public void mostrarFragmentoAnterior()
    {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.layout_fragmento_mapa_detail,new FragmentoListadoMaterias()).
                commit();
        fragMap.setVisibility(View.INVISIBLE);
        btnZoomOut.setVisibility(View.INVISIBLE);
        btnEstiloMapa.setVisibility(View.INVISIBLE);
        btnEstiloSatelite.setVisibility(View.INVISIBLE);
        btnZoomIn.setVisibility(View.INVISIBLE);
    }//Fin del metodo mostrarFragmentoAnterior

    @Override
    public String getIdMateria()
    {
        bundle = getArguments();
        assert bundle != null;
        return bundle.getString("idMateria");
    }//Fin del metodo getIdMateria

    @Override
    public String getFacultad()
    {
        bundle = getArguments();
        assert bundle != null;
        return bundle.getString("facultad");
    }//Fin del metodo getFacultad


}//Fin de la class FragmentoMapaDetail