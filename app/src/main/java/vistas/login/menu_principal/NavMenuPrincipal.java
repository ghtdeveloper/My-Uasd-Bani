package vistas.login.menu_principal;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import vistas.login.ActividadInicioSesion;

public class NavMenuPrincipal extends AppCompatActivity {

    //Vistas
    private TextView textViewNombreCompletoUsuario;
    private TextView textViewCorreoUsuario;
    private TextView textViewOtrosDatos;
    private ImageView imageViewFotoUsuario;
    private  Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    //objetos
    private Intent intentMenuPrincipal;
    private Uri uriPictUsuario;

    //Variables
    public static String idUsuario;
    public static String facultadUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu_principal);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        final ActionBarDrawerToggle drawerAction = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.btnOpen,
                        R.string.btnClose);
        drawerAction.setDrawerIndicatorEnabled(false);
        drawerAction.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
                drawerAction.setHomeAsUpIndicator(R.drawable.ic_menu_white_24);
            }
        });

        Menu navMenu = navigationView.getMenu();
        View hView = navigationView.getHeaderView(0);

                // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_listado_materias, R.id.nav_informacion, R.id.nav_mapa,
                R.id.nav_cerrar_sesion,R.id.nav_mi_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //   NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Datos Usuario Conectado
        textViewNombreCompletoUsuario = hView.findViewById(R.id.textNombreCompletoUsuario);
        textViewCorreoUsuario = hView.findViewById(R.id.textCorreoUsuario);
        textViewOtrosDatos = hView.findViewById(R.id.textOtrosDatosUsuario);
        imageViewFotoUsuario = hView.findViewById(R.id.imageViewPictUsuario);

        //Se cargan las informaciones correspondientes
        cargarDatosUsuarioConectado();

        //Se asigna la imagen al perfil del usuario
        Picasso.with(getApplicationContext()).load(uriPictUsuario).into(imageViewFotoUsuario);

        // Cerrar sesion
        navMenu.findItem(R.id.nav_cerrar_sesion).
                setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                mostrarDialogoCerrarSesion();
                return false;
            }
        });
    }//Fin del metodo onCreate

    /*
        Se define el metodo para cargar los datos
        del usuario conectado.
     */
    private void cargarDatosUsuarioConectado()
    {
       intentMenuPrincipal = getIntent();
       textViewNombreCompletoUsuario.setText(intentMenuPrincipal.getStringExtra
               ("nombreCompleto"));
       textViewCorreoUsuario.setText(intentMenuPrincipal.getStringExtra("correoUsuario"));
       textViewOtrosDatos.setText(intentMenuPrincipal.getStringExtra("otrosDatos"));
       idUsuario = intentMenuPrincipal.getStringExtra("idUsuario");
        uriPictUsuario =Uri.parse(intentMenuPrincipal.getStringExtra("urlPict"));
        facultadUsuario = intentMenuPrincipal.getStringExtra("facultadUsuario");
    }//Fin del metodo cargarDatosUsuarioConectado

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }//No retroceder

    /**
     * Se define el metodo para cerrar Sesion
     */
    private void mostrarDialogoCerrarSesion()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.textTituloCerrarSesion);
        builder.setPositiveButton("Cerrar sesión", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Al hacer click en Cerrar sesion desconecto el usuario de la app
               mostrarPantallaLogin();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Al hacer click en cancelar no haga nada pues
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }//Fin del metodo cerrarSesion

/*
    Metodo para mostrar el login
 */
    private void mostrarPantallaLogin()
    {
        intentMenuPrincipal = new Intent(getApplicationContext(), ActividadInicioSesion.class);
        startActivity(intentMenuPrincipal, ActivityOptions
                .makeSceneTransitionAnimation(this).toBundle());
    }//Fin del metodo mostrarPantallaLogin


}//Fin de la class NavMenu