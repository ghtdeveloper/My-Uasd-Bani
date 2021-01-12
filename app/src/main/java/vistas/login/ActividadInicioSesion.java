package vistas.login;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import java.io.IOException;
import java.util.Objects;
import contratos.Contratos;
import herramientas.Transiciones;
import presentador.Presentador;
import vistas.login.menu_principal.NavMenuPrincipal;
import vistas.registro.ActividadRegistroUsuario;

public class ActividadInicioSesion extends AppCompatActivity implements Transiciones,
        Contratos.VistaActividadInicioSesion
{
    //Variables
    private static final int RC_SIGN_IN =1 ;
    public  static int TYPE_ACCESS = 0;
    //Vistas
    private TextInputLayout textInputLayoutCorreo;
    private TextInputLayout textInputLayoutClave;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtClave;
    //Objetos
    private Presentador objPresentador;
    private AlertDialog dialog;
    private FirebaseAuth mAuth;
    //Objetos Google
    private  GoogleSignInClient googleSignInClient;
    private  Intent intInicioSessionGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        transicionInput();
        setContentView(R.layout.actividad_inicio_sesion);
        //Se inicializan los objetos
        mAuth = FirebaseAuth.getInstance();
        objPresentador = new Presentador(ActividadInicioSesion.this);
        init();//Cast a las vistas
    }//Fin del metodo onCreate

    @Override
    protected void onStart() {
        super.onStart();
       FirebaseUser currenteUser = mAuth.getCurrentUser();
         if(currenteUser != null)
         {
               //Si no es nulo quiere decir que hay un usuario ya logeado por la auth de google
               TYPE_ACCESS =2 ;//Acceso via Google
               intInicioSessionGoogle = new Intent(getApplicationContext(),NavMenuPrincipal.class);
               intInicioSessionGoogle.putExtra("nombreCompletoUsuarioGoogle",currenteUser.
                       getDisplayName());
               Log.w("onStart","Tenemos un usuario ya logeado");
               intInicioSessionGoogle.putExtra("correoUsuarioGoogle",currenteUser.
                       getEmail());
               startActivity(intInicioSessionGoogle);
               Toast.makeText(getApplicationContext(),"Bienvenido nuevamente "+ currenteUser.
                       getDisplayName(),Toast.LENGTH_LONG).show();
         }//Fin del if
        else {
              Log.w("onStart"," NO Tenemos un usuario logeado");
        }
    }//Fin del metodo onStart

    @Override
    public void transicionInputMenuPrincipal()
    { }

    @Override
    public void transicionInput()
    {
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
    }//Fin del metodo transicionInput

    @Override
    public void transicionExit()
    { }

    @Override
    public void init()
    {
        //Cast a  las vistas
        TextView textViewRegistroUsuario = findViewById(R.id.textRegistraste);
        TextView textViewResetClave  = findViewById(R.id.textViewResetClave);
        textInputLayoutCorreo = findViewById(R.id.textInputLayoutCorreoUsuario);
        textInputLayoutClave = findViewById(R.id.textInputLayoutClaveUsuario);
        txtClave = findViewById(R.id.txtClaveUsuario);
        txtCorreo = findViewById(R.id.txtCorreoUsuario);
        ImageView butttonAceesGoogle = findViewById(R.id.imageViewAccesGoogle);
        Button btnAcceder = findViewById(R.id.btnAcceder);
        //Listeners
        textViewRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mostrarActividadRegistroUsuarios();
            }
        });
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               autenticarUsuario();
               TYPE_ACCESS = 1;//Acceso normal
                //mostrarRunProcees();
            }
        });
        /*
            Se configura el accso por el API GOOGLE
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.
                DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.textOauthID))
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        /*
            Listener Boton de acceso con API Google
         */
        butttonAceesGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
        /*
            Listener Reset Clave
         */
        textViewResetClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getApplicationContext(),"Reinicio clave",Toast.LENGTH_LONG)
                        .show();*/
                mostrarDialogoResetClave();
            }
        });
    }//Fin del metodo init

    private void mostrarDialogoResetClave()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_reset_clave,null);
        builder.setView(dialogView);
        final AlertDialog dialogReset = builder.create();
        //Cast a las vistas
        ImageButton btnCerrarDialogo = dialogView.findViewById(R.id.btnCerrarDialogoReset);
        Button btnEnviarCorreo = dialogView.findViewById(R.id.btnReiniciarClave);
        final TextInputEditText textInputEditTextCorreo = dialogView.findViewById
                (R.id.txtCorreoUsuarioReset);

        /*
            Listeners
         */
        btnCerrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogReset.dismiss();
            }
        });
        btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if(Objects.requireNonNull(textInputEditTextCorreo.getText()).toString().isEmpty())
               {
                   Toast.makeText(ActividadInicioSesion.this,
                           "Debe de especifcar un correo",
                           Toast.LENGTH_LONG).show();
                   dialogReset.dismiss();
               }
               else
               {
                   //enviarCorreo(textInputEditTextCorreo.getText().toString());
                   Toast.makeText(getApplicationContext(),
                           "Correo Enviado exitosamente",Toast.LENGTH_LONG).show();
                   dialogReset.dismiss();
               }
            }
        });
         dialogReset.getLayoutInflater();
         dialogReset.show();
    }//Fin del metodo mostrarDialogoResetClave

    @Override
    public void mostrarActividadRegistroUsuarios()
    {
        //Objetos
        Intent intentLogin = new Intent(this, ActividadRegistroUsuario.class);
        startActivity(intentLogin, ActivityOptions.makeSceneTransitionAnimation(this).
                toBundle());
    }//Fin del metodo mostrarActividadRegistroUsuarios

    @Override
    public void autenticarUsuario()
    {
        if(Objects.requireNonNull(txtCorreo.getText()).toString().isEmpty() ||
                Objects.requireNonNull(txtClave.getText()).toString().isEmpty())
        {
            textInputLayoutCorreo.setErrorEnabled(true);
            textInputLayoutCorreo.setError("El campo es obligatorio");

            textInputLayoutClave.setErrorEnabled(true);
            textInputLayoutClave.setError("El campo es obligatorio");
        }else
        {
            textInputLayoutCorreo.setErrorEnabled(false);
            textInputLayoutCorreo.setError("");
            textInputLayoutClave.setErrorEnabled(true);
            textInputLayoutClave.setError("");
            mostrarRunProcees();
        }//Fin del else

    }//Fin del metodo autenticarUsuario

    @SuppressLint("SetTextI18n")
    @Override
    public void mostrarRunProcees()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View viewDialogo = inflater.inflate(R.layout.layout_dialogo_progress_bar,null);
        TextView textViewTituloProgress = viewDialogo.findViewById(R.id.textVieewProcesando);
        textViewTituloProgress.setText("Iniciando Sesión.....");
        builder.setView(viewDialogo);
        //Cast a las vistas
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(1700);
                   objPresentador.autenticarUsuario(Objects.requireNonNull(txtCorreo.
                                    getText()).toString(),
                            Objects.requireNonNull(txtClave.getText()).toString());
                    /*Acceso automatico
                    objPresentador.autenticarUsuario("martinezfriasedison@gmail.com",
                            "aaaaaaa");*/
                    dialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//Fin del metodo run
        }).start();
        dialog = builder.create();
        dialog.getLayoutInflater();
        dialog.show();

    }//Fin del metodo mostrarRunProcees

    @Override
    public void signInGoogle()
    {
        Intent signGoogle = googleSignInClient.getSignInIntent();
        startActivityForResult(signGoogle,RC_SIGN_IN);
    }//Fin del metodo signInGoogle


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount cuenta = task.getResult(ApiException.class);
                assert cuenta != null;
                //Log.w("DATA", Objects.requireNonNull(cuenta.getDisplayName()));
                firebaseAuthGoogle(Objects.requireNonNull(cuenta).getIdToken());
            } catch (ApiException e) {
               Log.w("Error",ApiException.class.toString());
            }
        }//Fin del if

    }//FIn del metodo onActivityResult

    @Override
    public void firebaseAuthGoogle(String idToken)
    {
        AuthCredential credenciales = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credenciales).addOnCompleteListener
                (new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Log.w("Acceso Google Firebase","El acceso con firebaseAuth esta" +
                            " completo");
                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG)
                            .show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    assert user != null;
                    //Log.w("INFO OBTENIDA", Objects.requireNonNull(user.getEmail()));
                    /*
                        Se actualizo el UI y se envia los datos a la actividad principal
                     */
                    actualizarUI(user);
                }else
                {
                    Log.e("Error al autenticar","Error al iniciar proceso" +
                            "de autenticacion"+ task.getException());
                    Toast.makeText(getApplicationContext(),"Error al autenticar"
                    ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }//Fin del metodo firebaseAuthGoogle

    @Override
    public void actualizarUI(FirebaseUser usuario)
    {
        TYPE_ACCESS =2 ;//Acceso via Google
        intInicioSessionGoogle = new Intent(getApplicationContext(),NavMenuPrincipal.class);
        intInicioSessionGoogle.putExtra("nombreCompletoUsuarioGoogle",usuario.
                getDisplayName());
        intInicioSessionGoogle.putExtra("correoUsuarioGoogle",usuario.getEmail());
        startActivity(intInicioSessionGoogle);
    }//Fin del metodo actualizarUI

    @Override
    protected void onDestroy() {
        super.onDestroy();
        objPresentador = null;
    }//Fin del metodo onDestroy

}//Fin de la clase ActividadInicioSesion