package com.example.lx_sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class LoginActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView xingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取资源ID
        xingView = findViewById(R.id.zXing);
        xingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        xingView.startCamera();
        xingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        xingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xingView.onDestroy();
        Toast.makeText(LoginActivity.this, 11+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (result!=null){
            Intent intent = new Intent(LoginActivity.this,ContentActivity.class);
            intent.putExtra("result",result);
            startActivity(intent);
        }
        Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

        //环境改变，是否变暗，变暗isDark为true
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        //打开相机失败
    }
}
