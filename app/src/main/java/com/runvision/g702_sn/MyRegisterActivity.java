package com.runvision.g702_sn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.runvision.bean.AppData;
import com.runvision.core.Const;
import com.runvision.db.User;
import com.runvision.myview.MyCameraSuf;
import com.runvision.thread.FaceFramTask;
import com.runvision.utils.CameraHelp;
import com.runvision.utils.DateTimeUtils;
import com.runvision.utils.FileUtils;
import com.runvision.utils.IDUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 注册
 */
public class MyRegisterActivity extends FragmentActivity implements View.OnClickListener {

    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Button btn_openCamera, addFace, btn_startImport, btn_close;
    private ImageView imageView;
    private LinearLayout reg_chooseOneImage;
    private TextView name, phone, age, idnum;
    private MyCameraSuf reg_MyCameraSuf;
    private Spinner type, sex;
    private boolean flag = true;//是否抓拍
    private Bitmap reg_bmp = null;
    private String choose_type, choose_sex;

    private FaceFramTask faceDetectTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_my_register);
        // 全屏代码
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar();
        mContext = MyRegisterActivity.this;
        initView();
        open();
    }

    private void stratThread() {
        if (faceDetectTask != null) {
            faceDetectTask.setRuning(false);
            faceDetectTask = null;
        }
        faceDetectTask = new FaceFramTask(reg_MyCameraSuf);
        faceDetectTask.setRuning(true);
        faceDetectTask.execute();
    }

    private void initView() {
        btn_openCamera = findViewById(R.id.btn_openPhone);
        btn_openCamera.setOnClickListener(this);
        imageView = findViewById(R.id.choose_bitmap);
        //  btn_startImport = view.findViewById(R.id.btn_startImport);
        //  btn_startImport.setOnClickListener(this);
        reg_chooseOneImage = (LinearLayout) findViewById(R.id.reg_chooseOneImage);
        name = findViewById(R.id.reg_name);
        phone = findViewById(R.id.reg_phone);
        type = findViewById(R.id.reg_type);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cardNumber = MyRegisterActivity.this.getResources().getStringArray(R.array.user_type)[i];
                System.out.println(cardNumber);
                choose_type = cardNumber;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addFace = (Button) findViewById(R.id.reg_addFace);
        addFace.setOnClickListener(this);
        reg_MyCameraSuf = (MyCameraSuf) findViewById(R.id.reg_myCameraSuf);
        reg_MyCameraSuf.setCameraType(1);
        reg_MyCameraSuf.openCamera();
        btn_close = findViewById(R.id.reg_close);
        btn_close.setOnClickListener(this);

        age = (TextView) findViewById(R.id.reg_age);
        idnum = (TextView) findViewById(R.id.reg_idnum);

        sex = (Spinner) findViewById(R.id.reg_sex);
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sexNumber = MyRegisterActivity.this.getResources().getStringArray(R.array.user_sex)[i];
                System.out.println(sexNumber);
                choose_sex = sexNumber;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void open() {
        Log.i(TAG, "FaceRegisterFrament->open()");
        flag = true;
        updateView();
        stratThread();
        if (reg_MyCameraSuf == null) {
            reg_MyCameraSuf = (MyCameraSuf) findViewById(R.id.reg_myCameraSuf);
        }
        reg_MyCameraSuf.setCameraType(1);
        reg_MyCameraSuf.openCamera();
    }

    public void close() {
        Log.i(TAG, "FaceRegisterFrament->close()");
        //关闭人脸框线程
        if (faceDetectTask != null) {
            faceDetectTask.setRuning(false);
            faceDetectTask.cancel(false);
            faceDetectTask = null;
        }
        System.out.println("close");
        reg_MyCameraSuf.releaseCamera();
        flag = false;
        Const.is_regFace = false;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_openPhone:
                openCamera();
                break;
            case R.id.reg_addFace:
                addFace();
                break;
            // case R.id.btn_startImport:
            //  batchImport();
            //    break;
            case R.id.reg_close:
                close();
                finish();
                break;
            default:
                break;
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

    private void openCamera() {
        if (!Const.is_regFace) {
            Const.is_regFace = true;
            btn_openCamera.setText("正在抓拍,请正视相机");
            btn_openCamera.setBackgroundColor(Color.GREEN);
        } else {
            Const.is_regFace = false;
            btn_openCamera.setText("开始抓拍");
            btn_openCamera.setBackgroundColor(Color.parseColor("#cccccc"));
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Const.REG_FACE) {
                btn_openCamera.setText("开始抓拍");
                btn_openCamera.setBackgroundColor(Color.parseColor("#cccccc"));
                reg_bmp = AppData.getAppData().getFaceBmp();
                AppData.getAppData().setFaceBmp(null);
                if (reg_bmp != null) {
                    imageView.setImageBitmap(reg_bmp);
                }
            }
        }
    };


    public void updateView() {
        new Thread(() -> {
            while (flag) {
                if (AppData.getAppData().getFlag() == Const.FLAG_CLEAN) {
                    continue;
                }
                handler.sendEmptyMessage(AppData.getAppData().getFlag());
                AppData.getAppData().setFlag(Const.FLAG_CLEAN);

            }
        }).start();
    }

    private void addFace() {
        if (reg_bmp == null) {
            Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String userName = name.getText().toString().trim();
        String userPhone = phone.getText().toString().trim();
        String userage = age.getText().toString().trim();
        String useridnum = idnum.getText().toString().trim();
        if (userName.equals("")) {
            Toast.makeText(mContext, "请输入名字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPhone.equals("")) {
            Toast.makeText(mContext, "请输入工号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userage.equals("")) {
            Toast.makeText(mContext, "请输入年龄", Toast.LENGTH_SHORT).show();
            return;
        } else if ((0 >= Integer.parseInt(userage)) && (Integer.parseInt(userage) >= 150)) {
            Toast.makeText(mContext, "输入年龄范围在0-150", Toast.LENGTH_SHORT).show();
            return;
        }

        if (useridnum.equals("")) {
            Toast.makeText(mContext, "请输入证件号", Toast.LENGTH_SHORT).show();
            return;
        }
        String time = DateTimeUtils.parseDataTimeToFormatString(new Date());
        //保存图片
        //生成随机图片ID
        String imageID = IDUtils.genImageName();
        // FileUtils.saveFile(reg_bmp, imageID, "FaceTemplate");
        //封装pojo

        //--------------------------------------------------------
        if (mSaveTemplate(reg_bmp, imageID)) {
            User user = new User(userName, choose_type, choose_sex, Integer.parseInt(userage), userPhone, useridnum, imageID, DateTimeUtils.getTime());
            //添加
            int id = MyApplication.faceProvider.addUserOutId(user);
            //System.out.println("add id:" + id);
            Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            //MyApplication.faceProvider.deleteUserById(id);
            Toast.makeText(mContext, "添加失败,请重新抓拍", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean mSaveTemplate(Bitmap bitmap, String mfilename) {
        boolean generateTemplate = false;
        String path = Environment.getExternalStorageDirectory() + "/FaceAndroid/Template/";
        String ImagePath = Environment.getExternalStorageDirectory() + "/FaceAndroid/Face/";
        bitmap = CameraHelp.alignBitmapForNv21(bitmap);//裁剪
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        byte[] nv21 = CameraHelp.bitmapToNv21(bitmap, w, h);//转nv21

        List<FaceInfo> result = new ArrayList<FaceInfo>();
        MyApplication.mFaceLibCore.FaceDetection(nv21, w, h, result);
        if ((MyApplication.mFaceLibCore.FaceDetection(nv21, w, h, result) == 0) && (result.size() > 0)) {
            FaceFeature faceFeature = new FaceFeature();
            int ret = MyApplication.mFaceLibCore.FaceFeatureExtract(nv21, w, h, result.get(0), faceFeature);
            if (ret == 0) {
                CameraHelp.saveFile(path, mfilename + ".data", faceFeature.getFeatureData());
                CameraHelp.saveImgToDisk(ImagePath, mfilename + ".jpg", bitmap);
                FileUtils.saveFile(bitmap, mfilename, "FaceTemplate");
                MyApplication.mList.put(mfilename, faceFeature.getFeatureData());
                generateTemplate = true;
                System.out.println("存入模板库");
            } else {
                generateTemplate = false;
                System.out.println("提取模版失败");
            }
        } else {
            generateTemplate = false;
            System.out.println("无人脸");
        }
        return generateTemplate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
    }
}
