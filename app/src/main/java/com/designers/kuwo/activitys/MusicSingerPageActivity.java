package com.designers.kuwo.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.SingerBiz;
import com.designers.kuwo.biz.bizimpl.SingerBizImpl;
import com.designers.kuwo.eneity.Song;
import com.designers.kuwo.utils.CustomApplication;
import com.designers.kuwo.utils.MusicPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MusicSingerPageActivity extends Activity {


    private ImageView imgMenuBar, ImgPageSinger;
    private TextView txtSingerMusic, txtSingerDescribe, txtMenuBar;
    private ListView listViewSingerSong;
    private Intent intent;
    private String tvsinger;
    private SingerBiz singerBiz;
    private Adapter adapter;
    private List<Song> songAllBySingerList;
    private List<Map<String,Object>> songAllBySingerLists;
    private CustomApplication customApplication;
    //playBar的组件
    private ImageView playimg;
    private ImageView playlist_menu;//播放列表按钮
    private TextView playbar_songName;
    private TextView playbar_singer;
    private ImageView playbar_play;
    private ImageView playbar_next;
    private Dialog playListDialog;
    private SeekBar seekBar;
    private PlayListAdapter playListAdapter;
    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_main_page);
        this.ImgPageSinger = (ImageView) findViewById(R.id.ImgPageSinger);
        this.txtSingerMusic = (TextView) findViewById(R.id.txtSingerMusic);
        this.txtSingerDescribe = (TextView) findViewById(R.id.txtSingerDescribe);
        this.imgMenuBar = (ImageView) findViewById(R.id.imgMenuBar);
        this.txtMenuBar = (TextView) findViewById(R.id.txtMenuBar);
        this.listViewSingerSong = (ListView) findViewById(R.id.listViewSingerSong);
        customApplication= (CustomApplication) getApplication();
        InitPlayBar();

        //获取上个页面传过来的值
        intent = getIntent();
        tvsinger = intent.getStringExtra("tvsinger");
        Log.i("页面间传值", "拿到的tvsinger------>" + tvsinger);
        this.txtSingerMusic.setText(tvsinger);
        //调用数据库取出数据
        singerBiz = new SingerBizImpl();
        //查出所有的歌曲信息,将信息存储到song的实体类中。
        songAllBySingerList=new ArrayList<Song>();
        songAllBySingerList=singerBiz.FindSongAllMsgBySinger(this,tvsinger);
        Log.i("查询回来的歌手歌曲", songAllBySingerList.toString());

        songAllBySingerLists=new ArrayList<Map<String,Object>>();
        songAllBySingerLists=singerBiz.FindSongAllMsgBySingers(this,tvsinger);

        adapter = new SingerPageAdapter(MusicSingerPageActivity.this);

        listViewSingerSong.setAdapter((ListAdapter) adapter);

        listViewSingerSong.setOnItemClickListener(new ItemClickEvent());

        this.imgMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicSingerPageActivity.this, MusicSortActivity.class);
                startActivity(intent);
            }
        });

    }

    class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Map<String, Object> song = songAllBySingerLists.get(position);
            Log.i("song", "song==:" + song.get("songName").toString());
            byte[] in = (byte[]) song.get("songImage");
            Bitmap bm = BitmapFactory.decodeByteArray(in, 0, in.length);
            playimg.setImageBitmap(bm);
            playbar_songName.setText(song.get("songName").toString());
            playbar_singer.setText(song.get("singer").toString());
            customApplication.setPlayingSong(song);
            customApplication.setPlayList(songAllBySingerLists);

            musicPlayer = new MusicPlayer(getApplicationContext(), customApplication, playimg, playbar_play,
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
    }

    /**
     * 初始化playbar
     */
    private void InitPlayBar() {
        playimg = (ImageView) findViewById(R.id.playimg);
        playbar_songName = (TextView) findViewById(R.id.playbar_songName);
        playbar_singer = (TextView) findViewById(R.id.playbar_singer);
        playbar_play = (ImageView) findViewById(R.id.playbar_play);
        playbar_next = (ImageView) findViewById(R.id.playbar_next);
        playlist_menu = (ImageView) findViewById(R.id.playlist_menu);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        playlist_menu.setOnClickListener(new CilckEvent());
    }

    private class CilckEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.playlist_menu :
                    playListDialog();
                    break;
                case R.id.playbar_next:
                    break;
            }
        }
    }

    class ItemClickEventSinger implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //跳转，传值
            Toast.makeText(MusicSingerPageActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private class SingerPageAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

        public SingerPageAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return songAllBySingerList.size();
        }

        @Override
        public Object getItem(int position) {
            return songAllBySingerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_singersong, null);
                holder.linearLayoutItemSinger= (LinearLayout) convertView.findViewById(R.id.LinearLayoutItemSinger);
                holder.txtItemMainSong = (TextView) convertView.findViewById(R.id.txtItemMainSong);
                holder.txtItemSingerSong = (TextView) convertView.findViewById(R.id.txtItemSingerSong);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //背景颜色
            if (position % 2 == 1) {
                holder.linearLayoutItemSinger.setBackgroundColor(getResources().getColor(R.color.musicform_listitem_color1));
            }else {
                holder.linearLayoutItemSinger.setBackgroundColor(getResources().getColor(R.color.musicform_listitem_color2));
            }
            //赋值
            holder.txtItemMainSong.setText(songAllBySingerList.get(position).getSinger());
            holder.txtItemSingerSong.setText(songAllBySingerList.get(position).getSongName());
            return convertView;
        }

        private class ViewHolder {
            private TextView txtItemSingerSong, txtItemMainSong;
            private LinearLayout linearLayoutItemSinger;
        }

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
        //customApplication.setPlayList(listItems);
        //MusicUtil.setObjectToShare(this, listItems, "playList");
        playListAdapter = new PlayListAdapter(this, customApplication.getPlayList());
        play_dialog_playlist.setAdapter(playListAdapter);
        play_dialog_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

}
