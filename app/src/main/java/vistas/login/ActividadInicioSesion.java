package vistas.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ghtdeveloper.my_uasd_bani.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import contratos.Contratos;
import herramientas.Transiciones;
import presentador.Presentador;
import vistas.login.menu_principal.NavMenuPrincipal;
import vistas.registro.ActividadRegistroUsuario;

public class ActividadInicioSesion extends AppCompatActivity implements Transiciones,
        Contratos.VistaActividadInicioSesion
{
    //Vistas
    private TextInputLayout textInputLayoutCorreo;
    private TextInputLayout textInputLayoutClave;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtClave;
    //Objetos
    private Presentador objPresentador;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        transicionInput();
        requestWindowFeature(Window.FEATURE_NO_TITLE);//Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.actividad_inicio_sesion);
        //Se inicializan los objetos
        objPresentador = new Presentador(ActividadInicioSesion.this);
        init();

    }//Fin del metodo onCreate

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
        TextView textViewRegistroUsuario = findViewById(R.id.textviewNoRegistrado);
        textInputLayoutCorreo = findViewById(R.id.textInputLayoutCorreoUsuario);
        textInputLayoutClave = findViewById(R.id.textInputLayoutClaveUsuario);
        txtClave = findViewById(R.id.txtClaveUsuario);
        txtCorreo = findViewById(R.id.txtCorreoUsuario);

        //Listeners
        textViewRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mostrarActividadRegistroUsuarios();
            }
        });

        Button btnAcceder = findViewById(R.id.btnAcceder);
       /* btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              autenticarUsuario();
            }
        });

        */
        mostrarRunProcees();
    }//Fin del metodo init

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

            //Se inicia el proceso de autenticacion
            /*objPresentador.autenticarUsuario(txtCorreo.getText().toString(),
                    txtClave.getText().toString());*/
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
        final ProgressBar progressBar = viewDialogo.findViewById(R.id.progressbar);
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(1700);
                    objPresentador.autenticarUsuario("martinezfriasedison@gmail.com",
                            "popy29");
                    //dialog.dismiss();
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
    protected void onDestroy() {
        super.onDestroy();
        objPresentador = null;
    }//Fin del metodo onDestroy

}//Fin de la clase ActividadInicioSesion