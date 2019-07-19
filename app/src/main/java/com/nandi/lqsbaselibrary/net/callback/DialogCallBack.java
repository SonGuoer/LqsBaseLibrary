package com.nandi.lqsbaselibrary.net.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.lzy.okgo.request.base.Request;

/**
 * Date:2019/1/23
 * Time:4:25 PM
 * author:qingsong
 */
public abstract class DialogCallBack<T> extends JsonCallBack<T> {

    private ProgressDialog dialog;

    private void initDialog(Context context, String message) {
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
    }

    public DialogCallBack(Context context, String message) {
        super();
        initDialog(context, message);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }


    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
