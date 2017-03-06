package com.designers.kuwo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.AlbumBiz;
import com.designers.kuwo.biz.bizimpl.AlbumBizImpl;
import com.designers.kuwo.eneity.Album;
import com.designers.kuwo.utils.CircularImage;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private ListView listViewSinger;
    private AlbumFragmentAdapter adapter;
    private List<Album> albumFindList;
    private AlbumBiz albumBiz;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从数据库中读出来数据
        albumBiz=new AlbumBizImpl();
        albumFindList=new ArrayList<Album>();
        albumFindList=albumBiz.AlbumFind(getActivity());
        for (Album a : albumFindList) {
            Log.i("专辑查出来的歌曲：", "------->" + a.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album, null);

        this.listViewSinger = (ListView) view.findViewById(R.id.listViewAlbum);
        adapter = new AlbumFragmentAdapter(this.getActivity(), albumFindList);
        this.listViewSinger.setAdapter(adapter);
        this.listViewSinger.setOnItemClickListener(new ItemClickEvent());
        return view;
    }

    //listView的点击监听事件
    private class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //拿到position，跳转过去，将值传到布局中去
            TextView tv= (TextView) view.findViewById(R.id.txtItemSinger);
            String albums=tv.getText().toString();
            Intent intent = new Intent(getActivity(), MusicAlbumPageActivity.class);
            intent.putExtra("tvalbums",albums);
            startActivity(intent);
        }
    }

    public class AlbumFragmentAdapter extends BaseAdapter {

        private Context context;
        private List<Album> listitems;
        private LayoutInflater inflater;

        public AlbumFragmentAdapter(Context context, List<Album> listitems) {
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
            //拿到视图
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_singer, null);
                holder.txtItemSinger = (TextView) convertView.findViewById(R.id.txtItemSinger);
                holder.txtItemSongNum = (TextView) convertView.findViewById(R.id.txtItemSongNum);
                holder.circularImageSinger = (CircularImage) convertView.findViewById(R.id.circularImageSinger);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //视图上赋值
            holder.txtItemSinger.setText(listitems.get(position).getAlbumName().toString());
            holder.txtItemSongNum.setText(listitems.get(position).getSongNum() + "首");
            //holder.circularImageSinger.setBackgroundResource((Integer) listitems.get(position).get("itemCir"));
            holder.circularImageSinger.setBackgroundResource(R.drawable.album);
            return convertView;
        }

        private class ViewHolder {
            CircularImage circularImageSinger;
            TextView txtItemSinger, txtItemSongNum;
        }
    }
}
