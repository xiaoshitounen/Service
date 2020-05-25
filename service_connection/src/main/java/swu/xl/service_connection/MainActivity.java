package swu.xl.service_connection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ServiceConnection
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //在Activity与Service建立关联的时候调用

            //1.强制转化
            MyService.LocalBinder localBinder = (MyService.LocalBinder) service;

            //2.获取Service实例
            MyService myService = localBinder.getService();

            //3.通信
            Toast.makeText(myService, "当前的音量："+myService.getVolume(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //在Activity与Service解除关联的时候调用
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //意图
        Intent intent = new Intent(this, MyService.class);

        switch (v.getId()){
            case R.id.start:
                //绑定服务
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.stop:
                //解绑服务
                unbindService(serviceConnection);
                break;
        }
    }
}
