package herramientas;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class TranformadorViewPager implements ViewPager.PageTransformer
{
    //Variables
    private static  final float ESCALA_MINIMA = 0.85f;//MIN_SCALE
    private static final float  ALPHA_MINIMA = 0.5f;//MIN_ALPHA

    @Override
    public void transformPage(@NonNull View view, float position)
    {
        int anchoPagina = view.getWidth();
        int alturaPagina = view.getHeight();

        if(position < -1)
        {
            view.setAlpha(0f);
        }
        else if (position <= 1)
        {
            float factorEscala = Math.max(ESCALA_MINIMA, 1 - Math.abs(position));//scaleFactor
            float margenVector = alturaPagina * (1 - factorEscala)/2;//verMargin
            float margenHorizontal = anchoPagina * (1- factorEscala)/2;//horzMargin
            if(position < 0)
            {
                view.setTranslationX(margenHorizontal - margenVector / 2);
            } else
            {
                view.setTranslationX(-margenHorizontal + margenVector / 2);
            }

            view.setScaleX(factorEscala);
            view.setScaleY(factorEscala);

            view.setAlpha(ESCALA_MINIMA +
                    (factorEscala - ESCALA_MINIMA) /
                            (1 - ESCALA_MINIMA) * (1 - ALPHA_MINIMA));
        } else
        {
            view.setAlpha(0f);
        }
    }//Fin del metodo transformPage
}//Fin de la class TranformadorViewPager
