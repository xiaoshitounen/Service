package swu.xl.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {
    //日志
    private static final String TAG = MyService.class.getSimpleName();

    //音乐播放器
    private MediaPlayer mediaPlayer;

    //Binder
    private IBinder iBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");

        //初始化操作
        mediaPlayer = MediaPlayer.create(this,R.raw.bg_music);
        mediaPlayer.setVolume(0.8f,0.8f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStart");

        //开始播放
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //结束播放
        mediaPlayer.stop();

        Log.d(TAG,"onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return iBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d(TAG,"unbind");
        super.unbindService(conn);
    }

    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
}
