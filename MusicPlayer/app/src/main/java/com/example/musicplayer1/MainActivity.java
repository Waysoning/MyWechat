package com.example.musicplayer1;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView title, author;
    ImageButton play, stop,last,next;

    ActivityReceiver activityReceiver;

    public static final String CTL_ACTION=
            "org.crazyit.action.CTL_ACTION";
    public static final String UPDATE_ACTION=
            "org.crazyit.action.UPDATE_ACTION";
    int status = 0x11;
    String[] titleStrs = new String[] {"MOM","夏天的风","微微","I don't wanna see u anymore"};
    String[] authorStrs = new String[] {"蜡笔小新","温岚","傅如乔","NINEONE"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityReceiver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        //指定BroadCastReceiver监听的action
        filter.addAction(UPDATE_ACTION);
        registerReceiver(activityReceiver,filter);
        Intent intent = new Intent(this,MusicService.class);

        startService(intent);

        //找到对应控件
        play = this.findViewById(R.id.imgplay);
        stop = this.findViewById(R.id.imgstop);
        next = this.findViewById(R.id.imgnext);
        last = this.findViewById(R.id.imglast);
        title = this.findViewById(R.id.txttitle);
        author = this.findViewById(R.id.txtauthor);
        //添加监听
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        last.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent("org.crazyit.action.CTL_ACTION");
        //在主界面按下对应按钮，传递给service对应参数
        switch (view.getId())
        {
            case R.id.imgplay:
                intent.putExtra("control",1);
                break;
            case R.id.imgstop:
                intent.putExtra("control",2);
                break;
            case R.id.imgnext:
                intent.putExtra("control",3);
                break;
            case R.id.imglast:
                intent.putExtra("control",4);
                break;
        }
        sendBroadcast(intent);

    }
    private class ActivityReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取来自receive中intent的update消息，代表播放状态
            int update = intent.getIntExtra("update",-1);
            //获取来自receive中intent的curruent消息，代表正在播放的歌曲
            int current = intent.getIntExtra("current",-1);
            //如果状态为正在播放歌曲或暂停
            if(current>=0&&(update == 0x12||update == 0x13))
            {
                title.setText(titleStrs[current]);
                author.setText(authorStrs[current]);
            }
            //如果状态为未播放歌曲
            else
            {
                title.setText("未播放歌曲");
                author.setText("未播放歌曲");
            }
            switch (update)
            {
                //如果未播放歌曲，则播放图标为播放
                case 0x11:
                    play.setImageResource(R.drawable.play);
                    status=0x11;
                    break;
                //如果正在播放歌曲，则播放图标为暂停
                case 0x12:
                    play.setImageResource(R.drawable.pause);
                    status=0x12;
                    break;
                case 0x13:
                    play.setImageResource(R.drawable.play);
                    status=0x13;
                    break;
            }

        }
    }
}
