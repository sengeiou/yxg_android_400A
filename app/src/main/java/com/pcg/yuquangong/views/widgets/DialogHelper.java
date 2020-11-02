package com.pcg.yuquangong.views.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pcg.yuquangong.R;
import com.pcg.yuquangong.model.Constant;

public class DialogHelper {

    AlertDialog mCommonDialog = null;

    private static DialogHelper sDialogHelper = new DialogHelper();

    public static DialogHelper getInstance() {
        return sDialogHelper;
    }

    public Dialog showGenderDialog(Context context, int currentGender, final View.OnClickListener boyListener,
                                   final View.OnClickListener girlListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View content = LayoutInflater.from(context).inflate(R.layout.layout_gender_dialog, null);
        TextView tvGenderBoy = content.findViewById(R.id.tvDialogGenderBoy);
        TextView tvGenderGirl = content.findViewById(R.id.tvDialogGenderGirl);

        if (currentGender == Constant.Member.GENDER_BOY) {
            tvGenderBoy.setTypeface(Typeface.DEFAULT_BOLD);
            tvGenderGirl.setTypeface(Typeface.DEFAULT);
        } else if (currentGender == Constant.Member.GENDER_GIRL) {
            tvGenderBoy.setTypeface(Typeface.DEFAULT);
            tvGenderGirl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            tvGenderBoy.setTypeface(Typeface.DEFAULT);
            tvGenderGirl.setTypeface(Typeface.DEFAULT);
        }

        tvGenderBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boyListener.onClick(v);
                mCommonDialog.dismiss();
            }
        });

        tvGenderGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girlListener.onClick(v);
                mCommonDialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(content);
        mCommonDialog = builder.create();
        mCommonDialog.setCanceledOnTouchOutside(false);
        mCommonDialog.show();

        return mCommonDialog;
    }

    public Dialog showAppUpdateDialog(Context context, String title, String content, boolean forceUpdate,
                                      final View.OnClickListener confirmListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_app_update, null);

        TextView tvDialogTitle = view.findViewById(R.id.tvDialogTitle);
        TextView tvDialogContent = view.findViewById(R.id.tvDialogContent);
        TextView tvDialogCancel = view.findViewById(R.id.tvDialogCancel);
        TextView tvDialogConfirm = view.findViewById(R.id.tvDialogConfirm);

        tvDialogTitle.setText(title);
        tvDialogContent.setText(content);

        if (forceUpdate) {
            tvDialogCancel.setVisibility(View.GONE);
        } else {
            tvDialogCancel.setVisibility(View.VISIBLE);
        }

        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommonDialog.dismiss();
            }
        });

        tvDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.onClick(v);
            }
        });

        if (forceUpdate) {
            builder.setCancelable(false);
        } else {
            builder.setCancelable(true);
        }

        builder.setView(view);
        mCommonDialog = builder.create();

        if (forceUpdate) {
            mCommonDialog.setCanceledOnTouchOutside(false);
        } else {
            mCommonDialog.setCanceledOnTouchOutside(true);
        }

        mCommonDialog.show();

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//
//        lp.copyFrom(mCommonDialog.getWindow().getAttributes());
//        lp.width = 780;
//        lp.height = 500;
////        lp.x = -170;
////        lp.y = 100;
//        mCommonDialog.getWindow().setAttributes(lp);

        return mCommonDialog;

    }

    public Dialog showConfirmDialog(Context context, String title, final View.OnClickListener confirmListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View content = LayoutInflater.from(context).inflate(R.layout.layout_confirm_dialog, null);
        TextView tvTitle = content.findViewById(R.id.tvDialogTitle);
        TextView tvCancel = content.findViewById(R.id.tvDialogCancel);
        TextView tvConfirm = content.findViewById(R.id.tvDialogConfirm);

        tvTitle.setText(title);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommonDialog.dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.onClick(v);
                mCommonDialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(content);
        mCommonDialog = builder.create();
        mCommonDialog.setCanceledOnTouchOutside(false);
        mCommonDialog.show();

        return mCommonDialog;
    }

    public Dialog showErrorConnectDialog(Context context) {
        //错误提示
        //
        //1、请检查磁头连接线
        //2、请联系我们
        //
        //售后电话：086-0757-83368806

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View content = LayoutInflater.from(context).inflate(R.layout.layout_error_dialog, null);
        TextView tvConfirm = content.findViewById(R.id.tvDialogConfirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommonDialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.setView(content);
        mCommonDialog = builder.create();
        mCommonDialog.setCanceledOnTouchOutside(false);
        mCommonDialog.show();

        return mCommonDialog;

    }

}
