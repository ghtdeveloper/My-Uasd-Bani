package vistas.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.view.WindowManager;

import com.ghtdeveloper.my_uasd_bani.R;

import contratos.Contratos;
import herramientas.Transiciones;

public class ActividadInicioSesion extends AppCompatActivity implements Transiciones
{

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

    }//Fin del metodo onCreate


    @Override
    public void transicionInputMenuPrincipal()
    {

    }
    @Override
    public void transicionInput()
    {
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
    }//Fin del metodo transicionInput

    @Override
    public void transicionExit() {

    }




}//Fin de la clase ActividadInicioSesion