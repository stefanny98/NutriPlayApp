package com.tecsup.nutriplayapp.additional;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecsup.nutriplayapp.R;

public class ViewDialog {
    Activity juegoac;
    String fallo = "¡Fallaste!";
    public void showDialog(final Activity activity, String msg, String estado){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_view_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView estadoText = dialog.findViewById(R.id.estado_text);
        ImageView imgEstado = dialog.findViewById(R.id.img_estadoJuego);
        text.setText(msg);
        estadoText.setText(estado);

        if(estado == fallo){
            imgEstado.setBackgroundColor(Color.RED);
        }
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();

            }
        });
        dialog.show();
    }
}
