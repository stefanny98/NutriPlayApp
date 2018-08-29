package com.job.nutriplayapp.additional;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.job.nutriplayapp.R;
import com.job.nutriplayapp.activities.JuegoActivity;

public class ViewDialog {
    Activity juegoac;
    public void showDialog(final Activity activity, String msg, String estado){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_view_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        TextView estadoText = dialog.findViewById(R.id.estado_text);

        text.setText(msg);
        estadoText.setText(estado);
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
