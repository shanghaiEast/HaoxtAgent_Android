package com.haoxt.agent.activity.home.transaction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.widget.MyDialog;


public class SelectTimePopup extends MyDialog implements View.OnClickListener {

    private final Activity mActivity;
    private LinearLayout mLayout;
    private boolean isSetEnd;
    private TextView mStartTv, mEndTv;
    private OnConfirmTimeListener mOnConfirmTimeListener;

    public SelectTimePopup(Activity activity) {
        super(activity);
        mActivity = activity;
        initView();
    }


    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mLayout = (LinearLayout) inflater.inflate(R.layout.popup_time, null);
        TextView cancelTv = mLayout.findViewById(R.id.cancel_tv);
        TextView confirmTv = mLayout.findViewById(R.id.confirm_tv);
        mStartTv = mLayout.findViewById(R.id.start_tv);
        mEndTv = mLayout.findViewById(R.id.end_tv);
        setTextView();
        DatePicker datePicker = mLayout.findViewById(R.id.date_picker);
        datePicker.getCalendarView().setOnDateChangeListener((calendarView, year, month, day) -> {
            setTime(year, month, day);
        });
        mStartTv.setOnClickListener(this);
        mEndTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);
    }

    private void setTextView() {
        if (isSetEnd) {
            mStartTv.setSelected(false);
            mEndTv.setSelected(true);
        } else {
            mStartTv.setSelected(true);
            mEndTv.setSelected(false);
        }
    }

    private void setTime(int year, int month, int day) {
        if (isSetEnd) {
            mEndTv.setText(year + "-" + (month + 1) + "-" + day);
        } else {
            mStartTv.setText(year + "-" + (month + 1) + "-" + day);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                if (isShowing())
                    dismiss();
                break;
            case R.id.confirm_tv:
                if (isShowing())
                    dismiss();
                if (mOnConfirmTimeListener != null) {
                    mOnConfirmTimeListener.onConfirmTime(mStartTv.getText().toString(), mEndTv.getText().toString());
                }
                break;
            case R.id.start_tv:
                isSetEnd = false;
                setTextView();
                break;
            case R.id.end_tv:
                isSetEnd = true;
                setTextView();
                break;
        }
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
        this.setCancelable(false);
        this.setContentView(mLayout);
    }

    public interface OnConfirmTimeListener {
        void onConfirmTime(String startTime, String endTime);
    }

    public void setOnConfirmTimeListener(OnConfirmTimeListener listener) {
        mOnConfirmTimeListener = listener;
    }

}
