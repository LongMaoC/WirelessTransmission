package com.myfinal.cxy.wirelesstransmission.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myfinal.cxy.wirelesstransmission.R;
import com.myfinal.cxy.wirelesstransmission.utils.ClipboardUtils;
import com.myfinal.cxy.wirelesstransmission.utils.T;

/**
 * Created by cxy on 17-4-10.
 */

public class StarActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        context = this ;

        findViewById(R.id.btn_ok).setOnClickListener(v -> {
            T.show(context,"内容已复制到剪切板");
            ClipboardUtils.getInstance(context).setClipboardContent(
                    ((TextView)findViewById(R.id.tv_github_path)).getText().toString()
            );
            finish();
        });
        findViewById(R.id.btn_no).setOnClickListener(v->{
            finish();
        });

    }
}
