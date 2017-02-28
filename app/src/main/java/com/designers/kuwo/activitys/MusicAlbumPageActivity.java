package com.designers.kuwo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.designers.kuwo.biz.AlbumBiz;
import com.designers.kuwo.biz.bizimpl.AlbumBizImpl;
import com.designers.kuwo.entity.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-CWB on 2017/1/11.
 */
public class MusicAlbumPageActivity extends Activity {


    private ImageView imgMenuBar, imgAlbumPage;
    private TextView txtAlbumName, txtAlbumDetial, txtMenuBar;
    private ListView listViewAlbumSong;
    private Intent intent;
    private AlbumBiz albumBiz;
    private List<Album> songFindByAlbumList;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_album_page);
        this.txtAlbumName = (TextView) findViewById(R.id.txtAlbumName);
        this.txtAlbumDetial = (TextView) findViewById(R.id.txtAlbumDetial);
        this.imgMenuBar = (ImageView) findViewById(R.id.imgMenuBar);
        this.txtMenuBar = (TextView) findViewById(R.id.txtMenuBar);
        this.imgAlbumPage = (ImageView) findViewById(R.id.imgAlbumPage);
        this.listViewAlbumSong = (ListView) findViewById(R.id.listViewAlbumSong);
        intent = getIntent();
        String albums = intent.getStringExtra("tvalbums");

        this.txtAlbumName.setText(albums);
        albumBiz = new AlbumBizImpl();
        songFindByAlbumList = new ArrayList<Album>();
        songFindByAlbumList = albumBiz.songFindByAlbum(MusicAlbumPageActivity.this, albums);

        Log.i("查询回来的歌手歌曲", songFindByAlbumList.toString());

        adapter = new AlbumPageAdapter(MusicAlbumPageActivity.this, songFindByAlbumList);
        this.listViewAlbumSong.setAdapter((ListAdapter) adapter);

        this.imgMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicAlbumPageActivity.this, MusicSortActivity.class);
                startActivity(intent);
            }
        });
    }

    private class AlbumPageAdapter extends BaseAdapter {

        private Context context;
        private List<Album> listitems;
        private LayoutInflater inflater;

        public AlbumPageAdapter(Context context, List<Album> listitems) {
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
                holder.linearLayoutItemSinger = (LinearLayout) convertView.findViewById(R.id.LinearLayoutItemSinger);
                holder.txtItemMainSong = (TextView) convertView.findViewById(R.id.txtItemMainSong);
                holder.txtItemSingerSong = (TextView) convertView.findViewById(R.id.txtItemSingerSong);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //赋值
            if (position % 2 == 1) {
                holder.linearLayoutItemSinger.setBackgroundColor(getResources().getColor(R.color.musicform_listitem_color1));
            }else {
                holder.linearLayoutItemSinger.setBackgroundColor(getResources().getColor(R.color.musicform_listitem_color2));
            }
            holder.txtItemMainSong.setText(listitems.get(position).getAlbumName());
            holder.txtItemSingerSong.setText(listitems.get(position).getSongName());
            return convertView;
        }

        private class ViewHolder {
            private TextView txtItemSingerSong, txtItemMainSong;
            private LinearLayout linearLayoutItemSinger;
        }
    }
}
