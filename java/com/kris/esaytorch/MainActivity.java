package com.kris.esaytorch;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    CameraManager cameraManager;
    String cameraId;
    Boolean state=false;
    ImageView imgvw;
   Button button,btsos;
    Toast toast;
   boolean doubleclicktoexit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      imgvw =(ImageView) findViewById(R.id.imageView);
      button=(Button) findViewById(R.id.button);
      btsos=(Button) findViewById(R.id.btsos);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void func2onroff(View view) {
        if(state == false){
            try{
                cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
                cameraId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,!state);
                imgvw.setImageResource(R.drawable.onbg);
                state=true;

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
                cameraId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,!state);
                imgvw.setImageResource(R.drawable.offbg);
                state=false;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
//sos signal code
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void funcsos(View view)  {
        cameraManager= (CameraManager) getSystemService(CAMERA_SERVICE);
        String str="0101010101";
        long blinkdelay=400;
        try{

        for(int i=0;i<str.length();i++)
        {

            if(str.charAt(i)=='0'){
                String cameraId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,true);
            }
            else {
                String cameraId=cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,false);
            }
            //wait delay
            try{
                Thread.sleep(blinkdelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if(doubleclicktoexit){
            finishAffinity();
            toast.cancel();
        }
        else{
            doubleclicktoexit=true;
           toast= Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT);
           toast.show();
            Handler handler=new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleclicktoexit=false;
                }
            },1500);
        }
    }
}