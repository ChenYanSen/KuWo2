package com.designers.kuwo.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.SingerBiz;
import com.designers.kuwo.biz.bizimpl.SingerBizImpl;
import com.designers.kuwo.entity.Singer;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.SlideBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class SingerFragment extends Fragment {

    private ListView listViewSinger;          //歌手listview组件
    private ListViewAdapter adapter;

    //检索表
    private SlideBar slideBar;                //右侧索引栏
    private TextView float_letter;            //漂浮的字符
    private List<Singer> indexSingers = new ArrayList<Singer>();
    private List<Singer> newSingers = new ArrayList<Singer>();//新的序列

    private HashMap<String, Integer> selector;//存放含有索引字母的位置
    private LinearLayout layoutIndex;

    private String[] indexStr = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};

    private int height;
    private boolean flag = false;

    //数据库的操作的一些声明
    private SingerBiz singerBiz;
    private List<Singer> singerFindList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setData();
        //从数据库中读出来数据
        singerFindList = new ArrayList<Singer>();
        singerBiz = new SingerBizImpl();
        singerFindList = singerBiz.SingerFind(getActivity());//返回的是一个集合，集合里面是一个个的实体对象
        //遍历集合，将数据拿出来
        for (Singer s : singerFindList) {
            Log.i("Singers", "SingerFind:" + s.getSinger());
            Log.i("Singers", "SingerFind:" + s.getSongNum());
            Log.i("Singers", "SingerFind:" + s.getSongUri());
            indexSingers.add(new Singer(s.getSinger(),s.getSongNum(),s.getSongUri()));//indexSingers里面存放了许多歌手，歌手的名字，头像，数目是知道的
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_singer, null);
        //歌手listview
        this.listViewSinger = (ListView) view.findViewById(R.id.listViewSinger);

        //索引
        this.layoutIndex = (LinearLayout) view.findViewById(R.id.sliderBar2);
        this.layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
        this.float_letter = (TextView) view.findViewById(R.id.float_letter);
        this.float_letter.setVisibility(View.GONE);
        String[] allNames = sortIndex(indexSingers);
        SortList(allNames);

        //歌手内容适配器

        adapter = new ListViewAdapter(this.getActivity(), newSingers);

        this.listViewSinger.setAdapter(adapter);

        //this.listViewSinger.setOnItemSelectedListener(new ItemClickEvent());
        this.listViewSinger.setOnItemClickListener(new ItemClickEvent());

        selector = new HashMap<String, Integer>();
        for (int j = 0; j < indexStr.length; j++) {
            for (int i = 0; i < newSingers.size(); i++) {
                if (newSingers.get(i).getSinger().equals(indexStr[j])) {
                    selector.put(indexStr[j], i);
                }
            }
        }
        return view;
    }

    //歌手内容listView的点击监听事件
    private class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //拿到position，跳转过去，将值传到布局中去
            //获取控件上的值
            TextView tv= (TextView) view.findViewById(R.id.txtItemSinger);
            String singer=tv.getText().toString();
            Intent intent = new Intent(getActivity(), MusicSingerPageActivity.class);
            intent.putExtra("tvsinger",singer);
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
        public boolean isEnabled(int position) {
            if (list.get(position).getSinger().length() == 1)// 如果是字母索引
                return false;// 表示不能点击
            return super.isEnabled(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String item = list.get(position).getSinger();
            viewHolder = new ViewHolder();
            if (item.length() == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.index,
                        null);
                viewHolder.indexTv = (TextView) convertView
                        .findViewById(R.id.indexTv);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_singer,
                        null);
                viewHolder.itemTv = (TextView) convertView
                        .findViewById(R.id.txtItemSinger);
                viewHolder.txtItemSongNum = (TextView) convertView.findViewById(R.id.txtItemSongNum);
                viewHolder.circularImageSinger = (CircularImage) convertView.findViewById(R.id.circularImageSinger);
            }
            if (item.length() == 1) {
                viewHolder.indexTv.setText(list.get(position).getSinger());
            } else {
                viewHolder.itemTv.setText(list.get(position).getSinger());
                viewHolder.txtItemSongNum.setText(list.get(position).getSongNum()+" 首歌曲");
                viewHolder.circularImageSinger.setImageResource(R.drawable.singers);
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView indexTv;
            private TextView itemTv, txtItemSongNum;
            CircularImage circularImageSinger;
        }
    }

    //快速索引获取所有的名字集合，重新排序获得一个新的list集合
    private void SortList(String[] allNames) {
        for (int i = 0; i < allNames.length; i++) {
            if (allNames[i].length() != 1) {
                for (int j = 0; j < indexSingers.size(); j++) {
                    if (allNames[i].equals(indexSingers.get(j).getPinyinName())) {
                        Singer singer = new Singer(indexSingers.get(j).getSinger(), indexSingers.get(j).getPinyinName(),
                                indexSingers.get(j).getSongNum(), indexSingers.get(j).getSingerUri());
                        newSingers.add(singer);
                    }
                }
            } else {
                newSingers.add(new Singer(allNames[i]));
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (!flag) {// 这里为什么要设置个flag进行标记，
            height = layoutIndex.getMeasuredHeight() / indexStr.length;
            getIndexView();
            flag = true;
        }
    }

    //获取排序后的新数据
    public String[] sortIndex(List<Singer> singer) {
        TreeSet<String> set = new TreeSet<String>();
        //获取初始化数据源中的首字母，添加到set中
        for (Singer singer1 : indexSingers) {
            set.add(PinYinUtils.getPinYinHeadChar(singer1.getSinger().substring(0, 1)));
        }
        //新数组的长度为元数据加上set的大小
        String[] names = new String[indexSingers.size() + set.size()];
        int i = 0;
        for (String string : set) {
            names[i] = string;
            i++;
        }

        String[] pinYinNames = new String[indexSingers.size()];

        for (int j = 0; j < indexSingers.size(); j++) {
            indexSingers.get(j).setPinyinName(PinYinUtils.getPinYin(indexSingers.get(j).getSinger().toString()));
            pinYinNames[j] = PinYinUtils.getPinYin(indexSingers.get(j).getSinger().toString());
        }
        //将原数据拷贝到新数据中
        System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
        //自动按照首字母排序
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    //绘制索引列表

    public void getIndexView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(getActivity());
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setPadding(10, 0, 10, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.length) {//防止越界

                        String key = indexStr[index];
                        if (selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (listViewSinger.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                listViewSinger.setSelectionFromTop(
                                        pos + listViewSinger.getHeaderViewsCount(), 0);
                            } else {
                                listViewSinger.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            float_letter.setVisibility(View.VISIBLE);
                            float_letter.setText(indexStr[index]);
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#606060"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#00ffffff"));
                            float_letter.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }
}
