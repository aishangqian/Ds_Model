package com.example.lx_sm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        //获取资源id
        editText = findViewById(R.id.edtext);
        imageView = findViewById(R.id.img);

        //点击按钮扫描二维码
        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedPremission();
            }
        });

        //点击按钮生成二维码
        findViewById(R.id.produce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatQRCode();
            }
        });

    }

    //判断是否在6.0以上，6.0以上需要动态添加权限
    private void checkedPremission(){
        //第一步，判断系统版本是否为6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //第二步：checkSelfPermission判断有没有此权限
            //第一个参数：上下文
            //第二个参数：我们想要判断的权限，此处为相机权限
            //PackageCompat.PERMISSION_GRANTED条件，权限有没有被授予
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                //如果没授予，则申请权限
                //第一个：上下文
                //第二个：要申请的权限数组，此处为相机权限
                //第三个：请求码，startActivityForResult一样
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},100);
            }
        }else {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //进行跳转
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

    }

    //生成二维码
    private void creatQRCode(){
        QRTask qrTask = new QRTask(this,imageView,editText);
        qrTask.execute(editText.getText().toString());
    }

    static class QRTask extends AsyncTask<String,Void,Bitmap>{

        private WeakReference<Context> mContext;
        private WeakReference<ImageView> imageView;

        public QRTask(Context context,ImageView mimageView,EditText editText){
            mContext = new WeakReference<>(context);
            imageView = new WeakReference<>(mimageView);

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }

            int size = mContext.get().getResources().getDimensionPixelOffset(R.dimen.qr_code_size);
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null){
                imageView.get().setImageBitmap(bitmap);
            }else {
                Toast.makeText(mContext.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
