package com.myfinal.cxy.wirelesstransmission.servide;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.myfinal.cxy.wirelesstransmission.net.WebServer;

import java.io.IOException;

/**
 * Created by cxy on 17-5-3.
 */
public class WebService extends Service {
    private final WebServiceBusiness business = new WebServiceBusiness();
    private final WebServer webServer = new WebServer();

    //相当于中间人对象
    public class WebServiceBusiness extends Binder implements IWebServiceBusiness {

        @Override
        public void start() throws IOException {
            startWeb();
        }

        @Override
        public void over() {
            overWeb();
        }

        @Override
        public boolean isRun() {
            return webServer.isRun();
        }

    }

    public void startWeb() throws IOException {
        webServer.start();
    }

    public void overWeb() {
        webServer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webServer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return business;
    }
}
