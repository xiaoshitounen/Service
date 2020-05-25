package swu.xl.service_connection;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("Registered")
public class MyService extends Service {

    //日志
    private static final String TAG = MyService.class.getSimpleName();

    //音乐播放器
    private MediaPlayer mediaPlayer;
    //音乐音量
    private float volume = 0.8f;

    //Binder
    private IBinder iBinder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");

        //初始化操作
        mediaPlayer = MediaPlayer.create(this,R.raw.bg_music);
        mediaPlayer.setVolume(volume,volume);
        //开始播放
        mediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStart");

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
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"unbind");
        return super.onUnbind(intent);
    }

    //获取Service实例
    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }

    //获取当前音乐的进度
    public float getVolume(){
        return volume;
    }
}
