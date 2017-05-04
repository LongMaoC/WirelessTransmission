package com.myfinal.cxy.wirelesstransmission.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myfinal.cxy.wirelesstransmission.R;
import com.myfinal.cxy.wirelesstransmission.net.WebServer;
import com.myfinal.cxy.wirelesstransmission.servide.IWebServiceBusiness;
import com.myfinal.cxy.wirelesstransmission.servide.WebService;
import com.myfinal.cxy.wirelesstransmission.utils.ClipboardUtils;
import com.myfinal.cxy.wirelesstransmission.utils.NetworkUtils;
import com.myfinal.cxy.wirelesstransmission.utils.T;


/**
 * @Author cxy
 * @time 17-3-22 下午6:02
 * @email chenxingyu1112@gmail.com
 */
public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView tv_textview;
    private PowerManager.WakeLock mWakeLock;
    private Button btnCtrl;
    private ImageView iv_star;
    private Intent serviceIntent = null;
    private IWebServiceBusiness webServiceBusiness = null;
    private MyServiceConnection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
        initView();
    }

    private void initView() {
        if (connection != null && webServiceBusiness != null) {
            String ip = NetworkUtils.getConnectWifiIp(context);
            if (!TextUtils.isEmpty(ip)) {
                changeCtrl(webServiceBusiness.isRun(), ip);
            }
        }
    }

    public void changeCtrl(boolean toRun, String ip) {
        if (toRun) {//運行
            tv_textview.setText("http://" + ip + ":" + WebServer.Conf.prot);
            btnCtrl.setTag(1);
            btnCtrl.setText(R.string.stop_server_btn_text);
        } else {//停止
            btnCtrl.setText(R.string.start_server_btn_text);
            tv_textview.setText(R.string.main_ps);
            btnCtrl.setTag(0);
        }
    }

    private void initListener() {
        //开始
        btnCtrl.setOnClickListener((v) -> {
            if (Integer.parseInt(String.valueOf(btnCtrl.getTag())) == 0) {
                try {
                    String ip = NetworkUtils.getConnectWifiIp(context);
                    if (!TextUtils.isEmpty(ip)) {
                        if (webServiceBusiness == null) return;
                        webServiceBusiness.start();
                        changeCtrl(true, ip);
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        T.show(context, getResources().getString(R.string.connection_wifi));
                    }
                } catch (Exception e) {
                    T.show(context, getResources().getString(R.string.start_server_error));
                }
            } else {
                changeCtrl(false, null);
                webServiceBusiness.over();
                T.show(context, getResources().getString(R.string.stop_server_msg));
            }
        });

        iv_star.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setTitle("评价!");
            builder.setMessage("如果感觉不错,给个star呗!\nhttps://github.com/LongMaoC/WirelessTransmission\n\n  1.可点击[ok!],地址复制到剪切板.\n  2.点击启动,通过pc浏览器获取剪切板内容");
            builder.setPositiveButton("OK!", (dialog, which) -> {
                ClipboardUtils.getInstance(context).setClipboardContent("https://github.com/LongMaoC/WirelessTransmission");
                Toast.makeText(context,"内容已复制到剪切板",Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("下次再说", null);
            builder.show();
        });

        //常亮
        ((CheckBox) (findViewById(R.id.cb_checkBox))).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mWakeLock != null) mWakeLock.acquire();
            } else {
                if (mWakeLock != null) mWakeLock.release();
            }
        });
    }

    private void init() {
        context = this;
        iv_star = (ImageView) findViewById(R.id.iv_star);
        tv_textview = (TextView) findViewById(R.id.tv_textView);
        btnCtrl = (Button) findViewById(R.id.btn_ctrl);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

        serviceIntent = new Intent(MainActivity.this, WebService.class);
        connection = new MyServiceConnection();
    }

    private class MyServiceConnection implements ServiceConnection {
        //服务连接成功时
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            webServiceBusiness = (IWebServiceBusiness) service;
        }

        //服务失去连接时
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            if (webServiceBusiness != null) {
                webServiceBusiness.over();
            }
        }
        if (connection != null) {
            unbindService(connection);
        }
    }
}
