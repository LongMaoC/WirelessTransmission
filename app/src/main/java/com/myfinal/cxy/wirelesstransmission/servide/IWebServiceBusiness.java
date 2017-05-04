package com.myfinal.cxy.wirelesstransmission.servide;

import java.io.IOException;

/**
 * Created by CXY on 2016/7/13.
 */
public interface IWebServiceBusiness {
    void start()throws IOException;
    void over();
    boolean isRun();
}
