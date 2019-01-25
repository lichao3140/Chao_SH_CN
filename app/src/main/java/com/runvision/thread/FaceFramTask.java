package com.runvision.thread;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.arcsoft.face.LivenessInfo;
import com.runvision.bean.AppData;
import com.runvision.bean.FaceInfoss;
import com.runvision.bean.ImageStack;
import com.runvision.core.Const;
import com.runvision.g702_sn.MyApplication;
import com.runvision.myview.MyCameraSuf;
import com.runvision.utils.CameraHelp;
import com.runvision.utils.SPUtil;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/5/29.
 */
public class FaceFramTask extends AsyncTask<Void, Rect, Void> {

    private String TAG = "FaceFramTask";
    private Handler handler;
    public boolean isRuning = true;
    private ImageStack imageStack;
    private MyCameraSuf mCameraView;
    private List<com.arcsoft.face.FaceInfo> result = new ArrayList<>();
    private List<LivenessInfo> livenessInfoList = new ArrayList<>();

    byte[] des;
    private boolean flag=false;
    public static boolean faceflag=false;

    public void setRuning(boolean runing) {
        isRuning = runing;
    }

    public FaceFramTask(Handler handler, MyCameraSuf mCameraView) {
        this.handler = handler;
        this.mCameraView = mCameraView;
        imageStack = mCameraView.getImgStack();
    }

    public FaceFramTask(MyCameraSuf mCameraView) {
        flag=true;
        this.mCameraView = mCameraView;
        imageStack = mCameraView.getImgStack();
    }
    @Override
    protected Void doInBackground(Void... params) {
        while (isRuning) {
            //G702---90   G701---270
            des = CameraHelp.rotateCamera(imageStack.pullImageInfo().getData(), 640, 480, 270);
            MyApplication.mFaceLibCore.FaceDetection(des, 480, 640, result);
            if (result.size() != 0) {
                int face_left = result.get(0).getRect().left;
                int face_top = result.get(0).getRect().top;
                int face_right = result.get(0).getRect().right;
                int face_bottom = result.get(0).getRect().bottom;
                Log.i(TAG, "左:" + face_left + ", 上:" + face_top + ", 右:" + face_right + ", 下:" + face_bottom);

                publishProgress(result.get(0).getRect());
                Boolean live=true;
                if(SPUtil.getBoolean(Const.KEY_ISOPENLIVE, Const.OPEN_LIVE)) {
                    live = MyApplication.mFaceLibCore.Livingthing(des, 480, 640, result,livenessInfoList);
                }
                faceflag = true;
                if((!flag) && (live)) {
                    FaceInfoss info = new FaceInfoss(des,result.get(0));
                    Message msg = new Message();
                    msg.obj = info;
                    msg.what = Const.MSG_FACE;
                    handler.sendMessage(msg);
                }
            } else {
                Log.e(TAG, "无人脸");
                if (imageStack != null) {
                    imageStack.clearAll();
                }
                faceflag=false;
                publishProgress(new Rect(0, 0, 0, 0));
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Rect... values) {
        super.onProgressUpdate(values);
        mCameraView.setFacePamaer(values[0]);
        if (mCameraView.getCamerType() == 1 && result != null && Const.is_regFace && flag) {
                Bitmap map = CameraHelp.getBitMap(des);
                AppData.getAppData().setFaceBmp(CameraHelp.getXFaceImgByInfraredJpg(values[0].left, values[0].top, values[0].right, values[0].bottom, map));
                AppData.getAppData().setFlag(Const.REG_FACE);
                Const.is_regFace=false;
        }

    }
}
