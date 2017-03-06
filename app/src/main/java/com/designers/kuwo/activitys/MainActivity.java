package com.designers.kuwo.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.RecentBiz;
import com.designers.kuwo.biz.SongBiz;
import com.designers.kuwo.biz.bizimpl.RecentBizImpl;
import com.designers.kuwo.biz.bizimpl.SongBizImpl;
import com.designers.kuwo.eneity.Recent;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.CustomApplication;
import com.designers.kuwo.utils.FastBlurUtil;
import com.designers.kuwo.utils.MusicPlayer;
import com.designers.kuwo.utils.SortMusicAdapter;
import com.designers.kuwo.utils.StatusBarCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 */

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<Map<String, Object>> listItems;
    private ListView main_sortmusic_listview;
    private SortMusicAdapter adapter;
    private PlayListAdapter playListAdapter;
    private Toolbar toolbar;
    private CircularImage main_photo_imgview;//主页左上角头像
    private ImageView main_music_imgview;//主页单曲图片
    private ImageView main_like_imgview;//主页我喜欢图片
    private ImageView main_recent_view;//主页最近图片
    private ImageView playimg;
    private CircularImage menu_photo_imgview;//侧滑菜单头像
    private LinearLayout main_above_layout;//主页上面的布局
    private LinearLayout main_like_btn;//主页我喜欢按钮
    private LinearLayout main_music_btn;//主页单曲按钮
    private LinearLayout main_recent_btn;//主页最近按钮
    private LinearLayout main_musicform_btn;//主页我的歌单按钮
    private ImageView main_musicform_imgview;
    private CircularImage main_record_imgview;
    private CircularImage main_sound_imgview;
    private CircularImage main_vadio_imgview;
    private ImageView playlist_menu;
    private TextView playbar_songName;
    private TextView playbar_singer;
    private ImageView playbar_play;
    private ImageView playbar_next;
    private SeekBar seekBar;
    private Dialog playListDialog;
    private boolean once;
    private TableRow localMusic;

    private CustomApplication customApplication;
    private String userName;
    private List<Map<String, Object>> playList = new ArrayList<>();
    private SongBiz songBiz = new SongBizImpl();
    private RecentBiz recentBiz = new RecentBizImpl();
    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customApplication = (CustomApplication) getApplication();
        userName = customApplication.getUserName();
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        main_photo_imgview = (CircularImage) findViewById(R.id.main_photo_imgview);
        main_music_imgview = (ImageView) findViewById(R.id.main_music_imgview);
        main_like_imgview = (ImageView) findViewById(R.id.main_like_imgview);
        main_recent_view = (ImageView) findViewById(R.id.main_recent_imgview);
        playimg = (ImageView) findViewById(R.id.playimg);
        menu_photo_imgview = (CircularImage) findViewById(R.id.menu_photo_imgview);
        main_musicform_imgview = (ImageView) findViewById(R.id.main_musicform_imgview);
        main_record_imgview = (CircularImage) findViewById(R.id.main_record_imgview);
        main_above_layout = (LinearLayout) findViewById(R.id.main_above_layout);
        main_like_btn = (LinearLayout) findViewById(R.id.main_like_btn);
        main_music_btn = (LinearLayout) findViewById(R.id.main_music_btn);
        main_recent_btn = (LinearLayout) findViewById(R.id.main_recent_btn);
        main_musicform_btn = (LinearLayout) findViewById(R.id.main_musicform_btn);
        main_sound_imgview = (CircularImage) findViewById(R.id.main_sound_imgview);
        main_vadio_imgview = (CircularImage) findViewById(R.id.main_vadio_imgview);
        main_sortmusic_listview = (ListView) findViewById(R.id.main_sortmusic_listview);
        playlist_menu = (ImageView) findViewById(R.id.playlist_menu);
        playbar_songName = (TextView) findViewById(R.id.playbar_songName);
        playbar_singer = (TextView) findViewById(R.id.playbar_singer);
        playbar_play = (ImageView) findViewById(R.id.playbar_play);
        playbar_next = (ImageView) findViewById(R.id.playbar_next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        localMusic= (TableRow) findViewById(R.id.localMusic);//本地音乐
        localMusic.setOnClickListener(this);
        main_like_btn.setOnClickListener(this);
        main_music_btn.setOnClickListener(this);
        main_recent_btn.setOnClickListener(this);
        main_musicform_btn.setOnClickListener(this);
        playlist_menu.setOnClickListener(this);
        playbar_play.setOnClickListener(this);
        playbar_next.setOnClickListener(this);
        main_sortmusic_listview.setOnItemClickListener(this);

        main_photo_imgview.setImageResource(R.drawable.playbarimg);
        main_music_imgview.setImageResource(R.drawable.main_music);
        main_vadio_imgview.setImageResource(R.drawable.img1);
        main_sound_imgview.setImageResource(R.drawable.img3);
        main_like_imgview.setImageResource(R.drawable.main_like);
        main_recent_view.setImageResource(R.drawable.main_recent);
        main_musicform_imgview.setImageResource(R.drawable.main_musicform);
        main_record_imgview.setImageResource(R.drawable.img2);
        menu_photo_imgview.setImageResource(R.drawable.img3);
        if (CustomApplication.mediaPlayer.isPlaying()) {
            playbar_play.setImageResource(R.drawable.stop);
        }

        //全屏
        StatusBarCompat.compat(this);
        //主页虚化
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.mainback1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 20, bitmap.getHeight() / 20, false);
        Bitmap blurBitMap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
        BitmapDrawable drawable = new BitmapDrawable(blurBitMap);
        main_above_layout.setBackground(drawable);

        updatabar.post(updatarun);
    }

    //音乐排行榜数据
    public List<Map<String, Object>> init() {
        List<Map<String, Object>> listItems = songBiz.selectByRank(this);
        return listItems;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //点击我喜欢按钮的事件
            case R.id.main_like_btn:
                intent = new Intent(this, LikeMusicActivity.class);
                startActivity(intent);
                break;

            //点击单曲按钮的事件
            case R.id.main_music_btn:
                intent = new Intent(this, SingleMusicActivity.class);
                startActivity(intent);
                break;

            //点击最近按钮的事件
            case R.id.main_recent_btn:
                intent = new Intent(this, RecentMusicActivity.class);
                startActivity(intent);
                break;


            //点击我的歌单按钮的事件
            case R.id.main_musicform_btn:
                intent = new Intent(this, MusicFormActivity.class);
                startActivity(intent);
                break;

            case R.id.playlist_menu:
                playListDialog();
                break;

            case R.id.playbar_play:
                musicPlayer = new MusicPlayer(this, customApplication, playimg, playbar_play,
                        playbar_songName, playbar_singer);
                musicPlayer.play();
                break;

            case R.id.playbar_next:
                musicPlayer = new MusicPlayer(this, customApplication, playimg, playbar_play,
                        playbar_songName, playbar_singer);
                musicPlayer.next();
                break;
            case R.id.localMusic:
                intent=new Intent(MainActivity.this,MusicSortActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String songName = listItems.get(position).get("songName").toString();
        String singer = listItems.get(position).get("singer").toString();
        int i = songBiz.selectRank(MainActivity.this, songName, singer);
        songBiz.updateRank(MainActivity.this, songName, singer, i + 1);
        if (recentBiz.songExist(MainActivity.this, songName)) {
            recentBiz.update(MainActivity.this, new Recent(songName, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
        } else {
            recentBiz.insert(MainActivity.this, new Recent(songName, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
        }
        //加入播放列表
        if (!once) {
            customApplication.setPlayList(listItems);
            once = true;
        }

        //设置播放栏的内容并播放
        Map<String, Object> song = listItems.get(position);
        byte[] in = (byte[]) song.get("songImage");
        Bitmap bm = BitmapFactory.decodeByteArray(in, 0, in.length);
        playimg.setImageBitmap(bm);
        playbar_songName.setText(song.get("songName").toString());
        playbar_singer.setText(song.get("singer").toString());
        customApplication.setPlayingSong(song);

        musicPlayer = new MusicPlayer(MainActivity.this, customApplication, playimg, playbar_play,
                playbar_songName, playbar_singer);
        try {
            CustomApplication.mediaPlayer.reset();
            CustomApplication.mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(song.get("songUri").toString()));
            CustomApplication.mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        musicPlayer.play();
    }

    //播放列表适配器
    public class PlayListAdapter extends BaseAdapter {
        private Context context;
        private List<Map<String, Object>> listItems;
        private LayoutInflater inflater;

        public PlayListAdapter(Context context, List<Map<String, Object>> listItems) {
            this.context = context;
            this.listItems = listItems;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.play_dialog_item, null);
                viewHolder.play_dialog_songName = (TextView) convertView.findViewById(R.id.play_dialog_songName);
                viewHolder.play_dialog_singer = (TextView) convertView.findViewById(R.id.play_dialog_singer);
                viewHolder.play_dialog_delete = (ImageButton) convertView.findViewById(R.id.play_dialog_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.play_dialog_songName.setText(listItems.get(position).get("songName").toString());
            viewHolder.play_dialog_singer.setText(listItems.get(position).get("singer").toString());

            //按钮绑定点击事件

            return convertView;
        }

        private class ViewHolder {
            public TextView play_dialog_songName;
            public TextView play_dialog_singer;
            public ImageButton play_dialog_delete;
        }

    }

    //点击播放列表按钮弹出的对话框
    public void playListDialog() {
        playListDialog = new Dialog(this, R.style.BottomAddDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.play_dialog, null);
        ListView play_dialog_playlist = (ListView) root.findViewById(R.id.play_dialog_playlist);
        playListAdapter = new PlayListAdapter(MainActivity.this, customApplication.getPlayList());
        play_dialog_playlist.setAdapter(playListAdapter);
        play_dialog_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置播放栏的内容并播放
                Map<String, Object> song = customApplication.getPlayList().get(position);
                byte[] in = (byte[]) song.get("songImage");
                Bitmap bm = BitmapFactory.decodeByteArray(in, 0, in.length);
                playimg.setImageBitmap(bm);
                playbar_songName.setText(song.get("songName").toString());
                playbar_singer.setText(song.get("singer").toString());
                customApplication.setPlayingSong(song);

                musicPlayer = new MusicPlayer(MainActivity.this, customApplication, playimg, playbar_play,
                        playbar_songName, playbar_singer);
                try {
                    CustomApplication.mediaPlayer.reset();
                    CustomApplication.mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(song.get("songUri").toString()));
                    CustomApplication.mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                musicPlayer.play();
                playListDialog.dismiss();
            }
        });
        playListDialog.setContentView(root);
        Window dialogWindow = playListDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = 750;
        dialogWindow.setAttributes(lp);
        playListDialog.show();
    }

    @Override
    protected void onResume() {
        if (CustomApplication.mediaPlayer.isPlaying()) {
            playbar_play.setImageResource(R.drawable.stop);
        } else {
            playbar_play.setImageResource(R.drawable.play);
        }
        listItems = init();
        adapter = new SortMusicAdapter(this, listItems);
        main_sortmusic_listview.setAdapter(adapter);
        Map<String, Object> playingSong = customApplication.getPlayingSong();
        if (playingSong != null) {
            byte[] in = (byte[]) playingSong.get("songImage");
            Bitmap bm = BitmapFactory.decodeByteArray(in, 0, in.length);
            playimg.setImageBitmap(bm);
            playbar_songName.setText(playingSong.get("songName").toString());
            playbar_singer.setText(playingSong.get("singer").toString());
        }
        musicPlayer = new MusicPlayer(this, customApplication, playimg, playbar_play,
                playbar_songName, playbar_singer);
        super.onResume();
    }

    //seekBar同步音乐播放线程
    Handler updatabar = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updatabar.post(updatarun);
        }
    };

    Runnable updatarun = new Runnable() {
        @Override
        public void run() {

            int currentPosition = CustomApplication.mediaPlayer.getCurrentPosition();
            int total = CustomApplication.mediaPlayer.getDuration();
            seekBar.setMax(total);
            seekBar.setProgress(currentPosition);
            updatabar.postDelayed(updatarun, 1000);
           /* //通过seekBar的当前进度与最大值比较，当相等时说明歌曲播放完，这时候停止动画 设置播放按钮的图标
            if (currentPosition == 100) {
                while (CustomApplication.mediaPlayer.isPlaying()) {
                    //改变playFlag=0,让其处于待播放状态
                    playFlag = 0;
                    //设置播放按钮图标
                    playMusic.setImageResource(R.drawable.ic_media_play_3);
                }
            }*/
        }
    };
}
