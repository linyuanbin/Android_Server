package com.lin.firstserver;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SyncStatusObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import android.os.Handler;

import java.net.URLEncoder;
import java.util.List;
import java.util.logging.LogRecord;

public class MainActivity extends Activity implements View.OnClickListener{ //AppCompatActivity
    private EditText userName,userPassword;
    private Button login,cancel;
    private String urlStr=null;
    private Handler mHandler; //主线程
    private Thread mThread; //子线程
    private String msg;//接收到的服务回馈消息
    private ImageView img;

    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    img.setImageBitmap(bmp);
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName= (EditText) findViewById(R.id.id_name);
        userPassword= (EditText) findViewById(R.id.id_password);
        login= (Button) findViewById(R.id.id_login);
        cancel= (Button) findViewById(R.id.id_cancel);
        img= (ImageView) findViewById(R.id.image);
        //给按钮添加监听事件
        login.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Log.i("创建成功！","");
    }


    /**
     * 自定义一个消息提示窗口
     */

    private void showDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登入情况");
        builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(MainActivity.this,"sure",Toast.LENGTH_SHORT).show();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void showDialog2(){  //退出软件提示窗口
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void login(String username,String password){
        Log.i("message","login被调用---------------");
        //要访问的HttpServlet
         urlStr="http://192.168.0.18:8080/TotemDown/LoginServe?";
        //要传递的数据
        String query = "username="+username+"&password="+password;
        /*try {
            query = URLEncoder.encode(query, "utf-8");
            Log.i("----------query",query+"-------");
            // 如有中文一定要加上，在接收方用相应字符转码即可
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }*/

        urlStr+=query;
        if(urlStr!=null){

            mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.i("handleMessage------msg",msg+"");
            }
        };
            mThread=new Thread(){
                @Override
                public void run() {
                    try { //http://localhost:8080/TotemDown/
                        //urlStr="http://192.168.0.18:8080/TotemDown/LoginServer?username=lin&password=123";
                        URL url = new URL(urlStr);
                        //获得连接林
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-type", "text/html");
                        conn.setRequestProperty("Accept-Charset", "utf-8");
                        conn.setRequestProperty("contentType", "utf-8");
                        Log.i("message------------", urlStr + "11111111");
                        // 设置连接超时时间
                        conn.setConnectTimeout(5000);
                        Log.i("message------------", urlStr + "2222222");
                        // 设置读取数据的超时时间
                   conn.setReadTimeout(5000);
                   conn.setUseCaches(false);// Post 请求不能使用缓存
                   // 设置允许读取服务器端返回的数据
                   conn.setDoInput(true);
                   // 5.连接服务器
                  // conn.connect();//如果是post请求，不要加这个，否则可能IllegalStateException: Already connected
                   //如果是get请求，参数写url中，就不需要post参数
                   conn.setDoOutput(true);//4.0中设置httpCon.setDoOutput(true),将导致请求以post方式提交
                                 if(true){
                        //获得输入流
                       /*OutputStream out=conn.getOutputStream();
                       out.write(urlStr.getBytes());
                       out.flush();
                       out.close();*/
                        Log.i("message------------", urlStr + "InputStream");
                        if(conn!=null){
                            Log.i("-----------------conn不空", conn+"");
                            InputStream in = conn.getInputStream();
                            Log.i("----------------调用", "InputStream创建");
                            Log.i("----------------调用", in+"");
                            //创建一个缓冲字节数
                            //byte[] buffer = new byte[in.available()];
                            Log.i("----------login调用avai11", in.available()+"");
                            byte[] buffer =new byte[84];
                            //在输入流中读取数据并存放到缓冲字节数组中
                            in.read(buffer);
                            Log.i("----------login调用avai22", in.available()+"");
                            //将字节转换成字符串
                            msg = new String(buffer); //解析出服务器放回的字符串

                            Bitmap bmp = getURLimage(msg);
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = bmp;
                            System.out.println("000");
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中

                            Log.i("-----------------msg", msg+"");
                            in.close();//关闭数据流
                        }else {
                            Log.i("-----------------获取流失败！", "获取流失败");
                        }
                   }
                   else{
                       //否则就关闭连接
                       conn.disconnect();
                       showDialog("连接失败");
                   }

                    } catch (ProtocolException e1) {
                        e1.printStackTrace();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }//run()
            }; //new Thread
            mThread.start();  //启动子线程
        }
//        showDialog(msg);
    } //login

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.id_login){
            Log.i("clicl-----","clicl");
            String nameString = userName.getText().toString();
            String password = userPassword.getText().toString();
            login(nameString,password);

            Log.i("nisdnsid","sfsicniscns");
}
        if(v.getId()==R.id.id_cancel){
            showDialog2();
            //MainActivity.this.finish();
        }
    }


}
