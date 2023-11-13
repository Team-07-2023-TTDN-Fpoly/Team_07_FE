package com.team.team_07_fe.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.team.team_07_fe.R;

public class LoadingDialog {

    private Dialog progressDialog;
    private Context context;
    private String text ="";
    private TextView tvLoad;
    public LoadingDialog(Context context){
        this.context = context;
        progressDialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);

        tvLoad = view.findViewById(R.id.textView);

        if(!text.isEmpty()){
            tvLoad.setText(text);
        }

        progressDialog.setCancelable(false);
        progressDialog.setContentView(view);
        Window window = progressDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }
    }

    public void show() {
        if(progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismiss() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if(tvLoad != null && !text.isEmpty()) {
            tvLoad.setText(text);
        }
    }
}
