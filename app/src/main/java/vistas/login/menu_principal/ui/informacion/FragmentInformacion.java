package vistas.login.menu_principal.ui.informacion;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ghtdeveloper.my_uasd_bani.R;

import java.util.Objects;

import contratos.Contratos;

public class FragmentInformacion extends Fragment implements  Contratos.VistaFragmentoInformacion
{
    //Vista
    private  View root;
    private WebView webView;
    //Objetos
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragmento_informacion, container, false);
        //Se cargan la vista
        init();
        return root;
    }//Fin del metodo onCreateView

    @Override
    public void init()
    {
        webView = root.findViewById(R.id.webViewInfo);
        //Se carga el dialogo Run Process
        mostrarRunProcess();
    }//Fin del metodo init

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void cargarWebView()
    {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        //Variables
        String URL = "https://www.uasd.edu.do/index.php/informacion-general";
        webView.loadUrl(URL);
    }//Fin del metodo cargarWebView

    @SuppressLint("SetTextI18n")
    @Override
    public void mostrarRunProcess()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialogo = inflater.inflate(R.layout.layout_dialogo_progress_bar,null);
        TextView textViewTituloProgress = viewDialogo.findViewById(R.id.textVieewProcesando);
        textViewTituloProgress.setText("Obteniendo información.....");
        builder.setView(viewDialogo);
        //Cast a las vistas
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (this) {
                    try {
                        wait(1500);
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cargarWebView();
                                dialog.dismiss();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        dialog = builder.create();
        dialog.getLayoutInflater();
        dialog.show();
    }//Fin del metodo mostrarRunProcess

    @Override
    public void onDestroy() {
        super.onDestroy();
        root = null;
    }
}//Fin de la clase FragmentInformacion