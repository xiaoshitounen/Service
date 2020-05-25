package swu.xl.service_forground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    //日志
    private static final String TAG = MyService.class.getSimpleName();

    //音乐播放器
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");

        //初始化操作
        mediaPlayer = MediaPlayer.create(this,R.raw.bg_music);
        mediaPlayer.setVolume(0.8f,0.8f);

        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
        String CHANNEL_ONE_NAME= "CHANNEL_ONE_ID";
        NotificationChannel notificationChannel= null;
        //进行8.0的判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel= new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(notificationChannel);
        }

        //添加下列代码将后台Service变成前台Service
        //构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        //新建Builder对象
        Notification.Builder builder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        builder.setContentTitle("前台服务通知的标题");//设置通知的标题
        builder.setContentText("前台服务通知的内容");//设置通知的内容
        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知的图标
        builder.setContentIntent(pendingIntent);//设置点击通知后的操作

        Notification notification = builder.build();//将Builder对象转变成普通的notification
        notification.flags|= Notification.FLAG_NO_CLEAR;
        startForeground(1, notification);//让Service变成前台Service,并在系统的状态栏显示出来
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
        return null;
    }
}
