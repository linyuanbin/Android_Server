package com.lin.firstserver;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lin on 2017/3/26.
 */
public class Internet implements Runnable {

    private String urlStr;

    public Internet(String urlStr) {
        this.urlStr = urlStr;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlStr);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            Log.i("message------------", urlStr + "11111111");
            // 设置连接超时时间
            conn.setConnectTimeout(5000);
            Log.i("message------------", urlStr + "2222222");
            // 设置读取数据的超时时间
                   /*conn.setReadTimeout(5000);
                   conn.setUseCaches(false);// Post 请求不能使用缓存
                   // 设置允许读取服务器端返回的数据
                   conn.setDoInput(true);
                   // 5.连接服务器
                   //conn.connect();//如果是post请求，不要加这个，否则可能IllegalStateException: Already connected
                   //如果是get请求，参数写url中，就不需要post参数
                   conn.setDoOutput(true);//4.0中设置httpCon.setDoOutput(true),将导致请求以post方式提交*/
            //         if(true){
            //获得输入流
                      /* OutputStream out=conn.getOutputStream();
                       out.write(urlStr.getBytes());
                       out.flush();
                       out.close();*/
            Log.i("message------------", urlStr + "InputStream");
            if(conn!=null){
                Log.i("-----------------conn不空", " ");
                InputStream in = conn.getInputStream();
                Log.i("----------------login调用", "InputStream创建");


                //创建一个缓冲字节数
                byte[] buffer = new byte[in.available()];

                //byte[] buffer =new byte[1024];
                //在输入流中读取数据并存放到缓冲字节数组中
                in.read(buffer);

                //将字节转换成字符串
                String msg = new String(buffer);
                System.out.print(msg + "-------------------------msg");
                //showDialog(msg);
                in.close();//关闭数据流
            }else {
                Log.i("-----------------获取流失败！", "获取流失败");
            }

                   /*}
                   else{
                       //否则就关闭连接
                       conn.disconnect();
                       //showDialog("连接失败");
                   }*/

        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

