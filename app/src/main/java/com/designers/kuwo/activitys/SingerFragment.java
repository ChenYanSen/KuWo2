package com.designers.kuwo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.designers.kuwo.biz.SingerBiz;
import com.designers.kuwo.biz.bizimpl.SingerBizImpl;
import com.designers.kuwo.eneity.Singer;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.IndexView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SingerFragment extends Fragment {

    private ListView listViewSinger;          //歌手listview组件
    private TextView tv_word;            //漂浮的字符
    private IndexView iv_words;
    private ListViewAdapter adapter;
    //数据库的操作的一些声明
    private SingerBiz singerBiz;
    private List<Singer> singerFindList;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从数据库中读出来数据
        singerFindList = new ArrayList<Singer>();
        singerBiz = new SingerBizImpl();
        singerFindList = singerBiz.SingerFind(getActivity());//返回的是一个集合，集合里面是一个个的实体对象
        //遍历集合，将数据拿出来
        for (Singer s : singerFindList) {
            Log.i("Singers", "SingerFind:" + s.getSinger());
            Log.i("Singers", "SingerFind:" + s.getSongNum());
            Log.i("Singers", "SingerFind:" + s.getSongUri());
        }
        //排序
        Collections.sort(singerFindList, new Comparator<Singer>() {
            @Override
            public int compare(Singer lhs, Singer rhs) {
                return lhs.getPinyinName().compareTo(rhs.getPinyinName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_singer, null);
        //歌手listview
        this.listViewSinger = (ListView) view.findViewById(R.id.listViewSinger);
        this.iv_words = (IndexView) view.findViewById(R.id.iv_words);
        this.tv_word = (TextView) view.findViewById(R.id.tv_word);
        tv_word.setVisibility(View.GONE);
        //索引绑定监听器  设置监听字母下标索引的变化
        iv_words.setOnIndexChangeListener(new MyOnIndexChangeListener());
        //歌手内容适配器
        adapter = new ListViewAdapter(this.getActivity(),singerFindList);
        //listview 绑定适配器
        this.listViewSinger.setAdapter(adapter);
        //listview 的监听器
        this.listViewSinger.setOnItemClickListener(new ItemClickEvent());

        return view;
    }

    //歌手内容listView的点击监听事件
    private class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //拿到position，跳转过去，将值传到布局中去
            //获取控件上的值
            TextView tv = (TextView) view.findViewById(R.id.txtItemSinger);
            String singer = tv.getText().toString();
            Intent intent = new Intent(getActivity(), MusicSingerPageActivity.class);
            intent.putExtra("tvsinger", singer);
            startActivity(intent);
        }
    }

    //歌手内容填充的自定义适配器
    public class ListViewAdapter extends BaseAdapter {

        private Context context;
        private List<Singer> list;
        private ViewHolder viewHolder;
        private LayoutInflater inflater;
        public ListViewAdapter(Context context, List<Singer> list) {
            this.context = context;
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = inflater.inflate(R.layout.item_singer, null);
                viewHolder = new ViewHolder();
                viewHolder.itemTv = (TextView) convertView.findViewById(R.id.txtItemSinger);
                viewHolder.txtItemSongNum = (TextView) convertView.findViewById(R.id.txtItemSongNum);
                viewHolder.circularImageSinger = (CircularImage) convertView.findViewById(R.id.circularImageSinger);
                viewHolder.tv_word = (TextView) convertView.findViewById(R.id.tv_word);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String name = list.get(position).getSinger();
            String word = list.get(position).getPinyinName().substring(0, 1);
            viewHolder.itemTv.setText(name);
            viewHolder.tv_word.setText(word);
            viewHolder.txtItemSongNum.setText(list.get(position).getSongNum() + " 首歌曲");
            viewHolder.circularImageSinger.setImageResource(R.drawable.singers);
            if (position == 0) {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            } else {
                String preWord = list.get(position-1).getPinyinName().substring(0, 1);
                if (word.equals(preWord)) {
                    viewHolder.tv_word.setVisibility(View.GONE);
                }else {
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView itemTv, txtItemSongNum;
            CircularImage circularImageSinger;
            private TextView tv_word;
        }
    }

    // 当触摸的字母发生改变，这里接到回传，重新设置TextView
    class MyOnIndexChangeListener implements IndexView.OnIndexChangeListener {
        @Override
        public void onIndexChange(String word) {
            updateWord(word);
            updateListView(word); // A~Z
        }
    }

    private void updateListView(String word) {
        for (int i = 0; i < singerFindList.size(); i++) {
            String listWord = singerFindList.get(i).getPinyinName().substring(0, 1); // LIUDEHUA->L
            if (word.equals(listWord)) {
                //i是listView中的位置
                listViewSinger.setSelection(i); // 定位到ListView中的某个位置
                return;
            }
        }
    }

    private void updateWord(String word) {
        //显示
        tv_word.setVisibility(View.VISIBLE);
        tv_word.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //也是运行在主线程
                //System.out.println(Thread.currentThread().getName() + "------------");
                tv_word.setVisibility(View.GONE);
            }
        }, 500);
    }

}
