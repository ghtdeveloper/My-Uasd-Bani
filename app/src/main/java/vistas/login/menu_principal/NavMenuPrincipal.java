package vistas.login.menu_principal;

import android.annotation.SuppressLint;
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
import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import vistas.login.ActividadInicioSesion;

public class NavMenuPrincipal extends AppCompatActivity {

    //Vistas
    private TextView textViewNombreCompletoUsuario;
    private TextView textViewCorreoUsuario;
    private TextView textViewOtrosDatos;
    private ImageView imageViewFotoUsuario;
    private Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    //objetos
    private Intent intentMenuPrincipal;
    private Uri uriPictUsuario;
    private GoogleSignInAccount account;
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
                R.id.nav_cerrar_sesion, R.id.nav_mi_perfil)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Datos Usuario Conectado
        textViewNombreCompletoUsuario = hView.findViewById(R.id.textNombreCompletoUsuario);
        textViewCorreoUsuario = hView.findViewById(R.id.textCorreoUsuario);
        textViewOtrosDatos = hView.findViewById(R.id.textOtrosDatosUsuario);
        imageViewFotoUsuario = hView.findViewById(R.id.imageViewPictUsuario);

        account = GoogleSignIn.getLastSignedInAccount(this);

        //Se especifica el metodo de acceso
        definirMetodoAcceso();
        //Se asigna la imagen al perfil del usuario
        Picasso.with(getApplicationContext()).load(uriPictUsuario).into(imageViewFotoUsuario);

        // Cerrar sesion
        navMenu.findItem(R.id.nav_cerrar_sesion).
                setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mostrarDialogoCerrarSesion();
                        return false;
                    }
                });
    }//Fin del metodo onCreate


    private void definirMetodoAcceso() {
        /*
            Se debe verificar que metodo de acceso esta utilizando el usuario
            para la toma de decisiones
         */
        if (ActividadInicioSesion.TYPE_ACCESS == 1) {
            cargarDatosAccesoUsuarioNormal();
        }
        if (ActividadInicioSesion.TYPE_ACCESS == 2) {
            cargarDatosAccesoUsuarioApiGoogle();
        }
    }//Fin del metodo definirMetodoAcceso


    /*
        Se define el metodo para cargar los datos
        del usuario conectado via el metodo normal de acceso.
     */
    private void cargarDatosAccesoUsuarioNormal() {
        intentMenuPrincipal = getIntent();
        textViewNombreCompletoUsuario.setText(intentMenuPrincipal.getStringExtra
                ("nombreCompleto"));
        textViewCorreoUsuario.setText(intentMenuPrincipal.getStringExtra("correoUsuario"));
        textViewOtrosDatos.setText(intentMenuPrincipal.getStringExtra("otrosDatos"));
        idUsuario = intentMenuPrincipal.getStringExtra("idUsuario");
        uriPictUsuario = Uri.parse(intentMenuPrincipal.getStringExtra("urlPict"));
        facultadUsuario = intentMenuPrincipal.getStringExtra("facultadUsuario");
    }//Fin del metodo cargarDatosUsuarioConectado


    /*
      Se define el metodo para cargar los datos
      del usuario conectado via el metodo google.
   */
    @SuppressLint("SetTextI18n")
    private void cargarDatosAccesoUsuarioApiGoogle()
    {
        intentMenuPrincipal = getIntent();
        textViewNombreCompletoUsuario.setText(intentMenuPrincipal.getStringExtra
                ("nombreCompletoUsuarioGoogle"));
        textViewCorreoUsuario.setText(intentMenuPrincipal.
                getStringExtra("correoUsuarioGoogle"));
        textViewOtrosDatos.setText("Acceso Google");
        //Se asigna la imagen de perfil
        uriPictUsuario = null;
        if(account!= null)
        {
            uriPictUsuario = account.getPhotoUrl();
            Picasso.with(getApplicationContext()).load(uriPictUsuario).into(imageViewFotoUsuario);
        }
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
        moveTaskToBack(true);
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

                if(account != null)
                {
                   FirebaseAuth.getInstance().signOut();

                }
                mostrarPantallaLogin();

            }//Fin del metodo onClick
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        account = null;
    }


}//Fin de la class NavMenu