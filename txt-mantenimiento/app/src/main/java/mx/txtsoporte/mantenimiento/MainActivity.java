package mx.txtsoporte.mantenimiento;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Pantalla de mantenimiento a pantalla completa.
 * Muestra un fondo negro con una leyenda editable.
 *
 * La leyenda se pasa como extra "legend" al lanzar la app, por ejemplo:
 *   adb shell am start --display 0 -n mx.txtsoporte.mantenimiento/.MainActivity -e legend "Actualizando, por favor espera"
 *
 * Si no se pasa, usa el texto por defecto de abajo (DEFAULT_LEGEND).
 */
public class MainActivity extends Activity {

    private static final String DEFAULT_LEGEND = "Actualizando, por favor espera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        enterImmersive();

        String legend = null;
        if (getIntent() != null) {
            legend = getIntent().getStringExtra("legend");
        }
        if (legend == null || legend.trim().isEmpty()) {
            legend = DEFAULT_LEGEND;
        }

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.BLACK);

        TextView tv = new TextView(this);
        tv.setText(legend);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(48, 0, 48, 0);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        root.addView(tv, lp);

        setContentView(root);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enterImmersive();
        }
    }

    private void enterImmersive() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onBackPressed() {
        // Bloquear la salida con el botón atrás (es una pantalla de bloqueo).
    }
}
