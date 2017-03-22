package com.myfinal.cxy.wirelesstransmission.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.myfinal.cxy.wirelesstransmission.R;
import com.myfinal.cxy.wirelesstransmission.net.WebServer;
import com.myfinal.cxy.wirelesstransmission.utils.NetworkUtils;
import com.myfinal.cxy.wirelesstransmission.utils.T;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private    WebServer webServer =null;
    private TextView tv_textview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this ;

        tv_textview = (TextView)findViewById(R.id.tv_textView);

        findViewById(R.id.btn_start).setOnClickListener((v)->{
            try {
                String ip = NetworkUtils.getConnectWifiIp(context);
                if(!TextUtils.isEmpty(ip)){
                    if(webServer==null)webServer = new WebServer();
                    webServer.start();

                    tv_textview.setText("电脑浏览器访问:\nhttp://"+ ip+":"+ WebServer.Conf.prot);
                }else {
                    startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));
                    T.show(context,"请连接wifi!");
                }
            } catch (IOException e) {
                T.show(context,"开启服务失败!");
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener((v)->{
            if(webServer!=null){
                webServer.stop();
                webServer = null ;
                T.show(context,"已关闭连接!");
            }
        });
    }
}
