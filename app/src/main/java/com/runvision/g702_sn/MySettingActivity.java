package com.runvision.g702_sn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.runvision.core.Const;
import com.runvision.utils.CameraHelp;
import com.runvision.utils.LogToFile;
import com.runvision.utils.SPUtil;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.lang.reflect.Field;

/**
 * 设置
 */
public class MySettingActivity extends FragmentActivity implements View.OnClickListener,
        RadialTimePickerDialogFragment.OnTimeSetListener {

    private String TAG = this.getClass().getSimpleName();
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private TextView threshold_1, threshold_n, wait_for_time, open_time, recognition_time, device_ip, vms_ip, vms_port, vms_uername, vms_password;
    private TextView timeBegin, timeEnd, new_password, confirm_passwork, version;
    private CheckBox cb_choice, cb_choice_1, cb_choice_n;
    private Spinner Preservation_time;
    private Button btn_Sure, btn_Refresh, btn_Back;
    private Context mContext;
    private int preservation_day;
    private TimePickerDialog tpd;
    private boolean select_begin = false;
    private boolean select_end = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_my_setting);
        // 全屏代码
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar();
        mContext = MySettingActivity.this;
        initView();
        initData();
    }

    private void initView() {
        version = findViewById(R.id.version);
        threshold_1 = findViewById(R.id.threshold_1);
        threshold_n = findViewById(R.id.threshold_n);
        wait_for_time = findViewById(R.id.wait_for_time);
        open_time = findViewById(R.id.open_time);
        recognition_time = findViewById(R.id.recognition_time);
        timeBegin = findViewById(R.id.select_sleep_time_begin);
        timeEnd = findViewById(R.id.select_sleep_time_end);
        timeBegin.setOnClickListener(this);
        timeEnd.setOnClickListener(this);

        device_ip = findViewById(R.id.device_ip);
        vms_ip = findViewById(R.id.vms_ip);
        vms_port = findViewById(R.id.vms_port);
        vms_uername = findViewById(R.id.vms_uername);
        vms_password = findViewById(R.id.vms_password);
        new_password = findViewById(R.id.dialog_new_pwd);
        confirm_passwork = findViewById(R.id.dialog_confirm_pwd);

        cb_choice = findViewById(R.id.cb_choice);
        cb_choice_1 = findViewById(R.id.cb_choice_1);
        cb_choice_n = findViewById(R.id.cb_choice_n);

        btn_Sure = findViewById(R.id.btn_Sure);
        btn_Sure.setOnClickListener(this);

        btn_Refresh = findViewById(R.id.btn_Refresh);
        btn_Refresh.setOnClickListener(this);

        btn_Back = findViewById(R.id.btn_Back);
        btn_Back.setOnClickListener(this);

        Preservation_time = findViewById(R.id.Preservation_time);
        Preservation_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sexNumber = MySettingActivity.this.getResources().getStringArray(R.array.user_time)[i];
                System.out.println(sexNumber);
                if (sexNumber.equals("120天")) {
                    preservation_day = 120;
                } else if (sexNumber.equals("90天")) {
                    preservation_day = 90;
                } else if (sexNumber.equals("60天")) {
                    preservation_day = 60;
                } else if (sexNumber.equals("30天")) {
                    preservation_day = 30;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void initData() {
        version.setText("基本信息配置" + "   (V" + LogToFile.getAppVersionName(mContext) + ")");
        threshold_1.setText(String.valueOf(SPUtil.getFloat(Const.KEY_CARDSCORE, Const.ONEVSONE_SCORE)));
        threshold_n.setText(String.valueOf(SPUtil.getFloat(Const.KEY_ONEVSMORESCORE, Const.ONEVSMORE_SCORE)));
        wait_for_time.setText(String.valueOf(SPUtil.getInt(Const.KEY_BACKHOME, Const.CLOSE_HOME_TIMEOUT)));
        open_time.setText(String.valueOf(SPUtil.getInt(Const.KEY_OPENDOOR, Const.CLOSE_DOOR_TIME)));
        recognition_time.setText(String.valueOf(SPUtil.getInt(Const.KEY_FACE_RECOGNITION_TIME, Const.FACE_RECOGNITION_TIME)));
        if (SPUtil.getString(Const.STARTIME, "").equals("")) {
            timeBegin.setText(Const.startime);
        } else {
            timeBegin.setText(SPUtil.getString(Const.STARTIME, ""));
        }
        if (SPUtil.getString(Const.ENDTIME, "").equals("")) {
            timeEnd.setText(Const.endtime);
        } else {
            timeEnd.setText(SPUtil.getString(Const.ENDTIME, ""));
        }

        device_ip.setText(CameraHelp.getIpAddress());

        //Preservation_time.set(SPUtil.getInt(Const.KEY_PRESERVATION_DAY,90));
        if (SPUtil.getInt(Const.KEY_PRESERVATION_DAY, 90) == 120) {
            Preservation_time.setSelection(0);
        } else if (SPUtil.getInt(Const.KEY_PRESERVATION_DAY, 90) == 90) {
            Preservation_time.setSelection(1);
        } else if (SPUtil.getInt(Const.KEY_PRESERVATION_DAY, 90) == 60) {
            Preservation_time.setSelection(2);
        } else if (SPUtil.getInt(Const.KEY_PRESERVATION_DAY, 90) == 30) {
            Preservation_time.setSelection(2);
        }
        //活体
        if (SPUtil.getBoolean(Const.KEY_ISOPENLIVE, Const.OPEN_LIVE)) {
            cb_choice.setChecked(true);
        } else {
            cb_choice.setChecked(false);
        }

        //1:1
        if (SPUtil.getBoolean(Const.KEY_ISOPEN_ONE, Const.OPEN_ONE_VS_ONE)) {
            cb_choice_1.setChecked(true);
        } else {
            cb_choice_1.setChecked(false);
        }

        //1:N
        if (SPUtil.getBoolean(Const.KEY_ISOPEN_N, Const.OPEN_ONE_VS_N)) {
            cb_choice_n.setChecked(true);
        } else {
            cb_choice_n.setChecked(false);
        }

        vms_ip.setText(SPUtil.getString(Const.KEY_VMSIP, ""));
        vms_port.setText(Integer.toString(SPUtil.getInt(Const.KEY_VMSPROT, 0)));
        vms_uername.setText(SPUtil.getString(Const.KEY_VMSUSERNAME, ""));
        vms_password.setText(SPUtil.getString(Const.KEY_VMSPASSWORD, ""));
        SPUtil.putString(Const.KEY_SETTING_PASSWORD, "");
    }

    private void setData() {
        SPUtil.addFloat(Const.KEY_CARDSCORE, Float.parseFloat(threshold_1.getText().toString().trim()));
        SPUtil.addFloat(Const.KEY_ONEVSMORESCORE, Float.parseFloat(threshold_n.getText().toString().trim()));
        SPUtil.putInt(Const.KEY_BACKHOME, Integer.parseInt(wait_for_time.getText().toString().trim()));
        SPUtil.putInt(Const.KEY_OPENDOOR, Integer.parseInt(open_time.getText().toString().trim()));
        SPUtil.putInt(Const.KEY_FACE_RECOGNITION_TIME, Integer.parseInt(recognition_time.getText().toString().trim()));
        SPUtil.putString(Const.STARTIME, timeBegin.getText().toString());
        SPUtil.putString(Const.ENDTIME, timeEnd.getText().toString());
        //修改本地IP
        updateSetting(device_ip.getText().toString().trim(), mContext);

        SPUtil.putInt(Const.KEY_PRESERVATION_DAY, preservation_day);

        if (cb_choice.isChecked()) {
            SPUtil.putBoolean(Const.KEY_ISOPENLIVE, true);
        } else {
            SPUtil.putBoolean(Const.KEY_ISOPENLIVE, false);
        }

        if (cb_choice_1.isChecked()) {
            SPUtil.putBoolean(Const.KEY_ISOPEN_ONE, true);
        } else {
            SPUtil.putBoolean(Const.KEY_ISOPEN_ONE, false);
        }

        if (cb_choice_n.isChecked()) {
            SPUtil.putBoolean(Const.KEY_ISOPEN_N, true);
        } else {
            SPUtil.putBoolean(Const.KEY_ISOPEN_N, false);
        }

        SPUtil.putString(Const.KEY_VMSIP, vms_ip.getText().toString().trim());
        SPUtil.putInt(Const.KEY_VMSPROT, Integer.parseInt(vms_port.getText().toString().trim()));
        SPUtil.putString(Const.KEY_VMSUSERNAME, vms_uername.getText().toString().trim());
        SPUtil.putString(Const.KEY_VMSPASSWORD, vms_password.getText().toString().trim());
        SPUtil.putString(Const.KEY_SETTING_PASSWORD, confirm_passwork.getText().toString().trim());

        Amendsuccess();
        // 通知Socket去连接服务器
        Const.WEB_UPDATE = true;
    }

    private void Amendsuccess() {
        //修改成功
        DialogInterface.OnCancelListener onCancelListener;
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("修改成功")
                .setIcon(R.mipmap.timg)
                .show();
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(Color.GREEN);
            mMessageView.setTextSize(25);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = 200;
            params.height = 100;
            dialog.getWindow().setAttributes(params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    // 修改设置IP地址
    public static int updateSetting(String deviceip, Context context) {
        if (deviceip.equals(CameraHelp.getIpAddress())) {
            return 1;
        }
        if (deviceip.equals("")) {
            return 2;
        }
        String[] Sip = deviceip.split("\\.");

        String str = Sip[0] + "." + Sip[1] + "." + Sip[2] + ".1";
        Log.i("aa", str + "," + deviceip);

        String[] staticIP = new String[]{deviceip,
                "255.255.255.0", str, "8.8.8.8"};
        Intent closeIntent = new Intent("com.snstar.networkparameters.ETH_CLOSE");
        context.sendBroadcast(closeIntent);

        Intent i = new Intent("com.snstar.networkparameters.ETHSETINGS");
        Bundle bundle = new Bundle();
        bundle.putSerializable("STATIC_IP", staticIP);
        i.putExtras(bundle);
        context.sendBroadcast(i);
        Intent iopen = new Intent("com.snstar.networkparameters.ETH_OPEN");
        context.sendBroadcast(iopen);

        return 3;
    }


    public void open() {
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Sure:
                if (!cb_choice_1.isChecked() && !cb_choice_n.isChecked()) {
                    Toast.makeText(mContext, "1:1和1:N不能同时关闭", Toast.LENGTH_LONG).show();
                } else if (!new_password.getText().toString().trim().equals(confirm_passwork.getText().toString().trim())){
                    Toast.makeText(mContext, "两次输入密码不一致，密码修改失败！", Toast.LENGTH_LONG).show();
                } else {
                    setData();
                }
                break;
            case R.id.btn_Refresh:
                initData();
                break;
            case R.id.btn_Back:
                finish();
                break;
            case R.id.select_sleep_time_begin:
                select_begin = true;
                selectTime();
                break;
            case R.id.select_sleep_time_end:
                select_end = true;
                selectTime();
                break;
            default:
                break;
        }
    }

    private void selectTime() {
        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(MySettingActivity.this)
                .setForced24hFormat();
        rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        if (select_begin) {
            select_begin = false;
            timeBegin.setText(hourString + ":" + minuteString);
        } else if (select_end) {
            select_end = false;
            timeEnd.setText(hourString + ":" + minuteString);
        }
    }

    public void hideNavigationBar() {
        Window window;
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.setAttributes(params);

        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= 0x00001000;
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public void onResume() {
        super.onResume();
        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
