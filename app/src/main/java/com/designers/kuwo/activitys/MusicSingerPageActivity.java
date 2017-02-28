package com.designers.kuwo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.SingerBiz;
import com.designers.kuwo.biz.bizimpl.SingerBizImpl;
import com.designers.kuwo.entity.Singer;

import java.util.ArrayList;
import java.util.List;

public class MusicSingerPageActivity extends Activity {


    private ImageView imgMenuBar, ImgPageSinger;
    private TextView txtSingerMusic, txtSingerDescribe, txtMenuBar;
    private ListView listViewSingerSong;
    private Intent intent;
    private String tvsinger;
    private SingerBiz singerBiz;
    private List<Singer> songFindBySingerList;
    private Adapter adapter;

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

        //获取上个页面传过来的值
        intent = getIntent();
        tvsinger = intent.getStringExtra("tvsinger");
        Log.i("页面间传值", "拿到的tvsinger------>" + tvsinger);

        this.txtSingerMusic.setText(tvsinger);
        //调用数据库取出数据
        singerBiz = new SingerBizImpl();
        songFindBySingerList = new ArrayList<Singer>();
        songFindBySingerList = singerBiz.FindSongBySinger(this, tvsinger);

        Log.i("查询回来的歌手歌曲", songFindBySingerList.toString());

        adapter = new SingerPageAdapter(MusicSingerPageActivity.this, songFindBySingerList);


        listViewSingerSong.setAdapter((ListAdapter) adapter);


        this.imgMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicSingerPageActivity.this, MusicSortActivity.class);
                startActivity(intent);
            }
        });

    }


    private class SingerPageAdapter extends BaseAdapter {

        private Context context;
        private List<Singer> listitems;
        private LayoutInflater inflater;

        public SingerPageAdapter(Context context, List<Singer> listitems) {
            this.context = context;
            this.listitems = listitems;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listitems.size();
        }

        @Override
        public Object getItem(int position) {
            return listitems.get(position);
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
            holder.txtItemMainSong.setText(listitems.get(position).getSinger());
            holder.txtItemSingerSong.setText(listitems.get(position).getSongName());
            return convertView;
        }

        private class ViewHolder {
            private TextView txtItemSingerSong, txtItemMainSong;
            private LinearLayout linearLayoutItemSinger;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_singer_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
