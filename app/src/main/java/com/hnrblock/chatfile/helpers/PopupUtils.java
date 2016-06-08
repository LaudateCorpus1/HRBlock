package com.hnrblock.chatfile.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hnrblock.chatfile.R;

public class PopupUtils {

    public static void createPopup(Context context, String header,
                                   String message) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.generic_dialog);
        dialog.setTitle(null);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView desc = (TextView) dialog.findViewById(R.id.desc);

        if (header.equalsIgnoreCase("")) {
            title.setVisibility(View.GONE);
        } else {
            title.setText(header);
        }
        desc.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static Dialog createForgotPassPopup(Context context, String header,
                                             String message) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgot_pass);
        dialog.setTitle(null);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView desc = (TextView) dialog.findViewById(R.id.desc);

//        if (header.equalsIgnoreCase("")) {
//            title.setVisibility(View.GONE);
//        } else {
        title.setText("Login Error");
//        }
        desc.setText(header);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
//                dialog.dispatchTouchEvent(new MotionEvent(dialog));
            }
        });
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }
}