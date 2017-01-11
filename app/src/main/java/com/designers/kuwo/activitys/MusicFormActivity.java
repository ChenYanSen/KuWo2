package com.designers.kuwo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的歌单列表
 */
public class MusicFormActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List<Map<String, Object>> listItems;
    private ListView musicform_listview;
    private MusicFromListViewAdapter adapter;
    private CircularImage playbar_img_imgview;//播放栏头像
    private ImageView showlist_btn;//点击不显示ListView
    private LinearLayout buildform;//点击新建歌单按钮
    private TextView musicform_num;//歌单的数量
    private ImageView back;
    private int oldPosition = -1;//用来记录上一次所点按钮的位置
    private int totalHight = 0;//用来保存李listview的总高度
    private boolean once = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicform);

        listItems = init();
        back = (ImageView) findViewById(R.id.back);
        musicform_listview = (ListView) findViewById(R.id.musicform_listview);
        playbar_img_imgview = (CircularImage) findViewById(R.id.playbar_img_imgview);
        showlist_btn = (ImageView) findViewById(R.id.showlist_btn);
        buildform = (LinearLayout) findViewById(R.id.buildform);
        musicform_num = (TextView) findViewById(R.id.musicform_num);

        playbar_img_imgview.setImageResource(R.drawable.playbarimg);
        adapter = new MusicFromListViewAdapter(this);
        musicform_listview.setAdapter(adapter);

        back.setOnClickListener(this);
        showlist_btn.setOnClickListener(this);
        buildform.setOnClickListener(this);
        musicform_listview.setOnItemClickListener(this);

        //全屏
        StatusBarCompat.compat(this);
    }

    public List<Map<String, Object>> init() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap();
            map.put("name", "我的歌单" + i);
            map.put("size", i + "首");
            map.put("expend", false);
            items.add(map);
        }
        return items;
    }

    //歌单item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, FromMusicActivity.class);
        startActivity(intent);
    }

    //页面按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            //点击新建歌单
            case R.id.buildform:
                break;

            //点击不显示listview
            case R.id.showlist_btn:
                if (!once) {
                    musicform_listview.setVisibility(View.GONE);
                    showlist_btn.setImageResource(R.drawable.more);
                    once = true;
                } else {
                    musicform_listview.setVisibility(View.VISIBLE);
                    showlist_btn.setImageResource(R.drawable.less);
                    once = false;
                }
                break;
        }
    }


    //歌单列表的适配器
    public class MusicFromListViewAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MusicFromListViewAdapter(Context context) {
            this.context = context;
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
            Map map = listItems.get(position);
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.musicform_item_layout, null);
                viewHolder.itemMusicForm_name_txt = (TextView) convertView.findViewById(R.id.itemMusicForm_name_txt);
                viewHolder.itemMusicForm_num_txt = (TextView) convertView.findViewById(R.id.itemMusicForm_num_txt);
                viewHolder.itemMusicForm_btn = (ImageButton) convertView.findViewById(R.id.itemMusicForm_btn);
                viewHolder.expend = (LinearLayout) convertView.findViewById(R.id.expend);
                viewHolder.musicform_expend_edit = (LinearLayout) convertView.findViewById(R.id.musicform_expend_edit);
                viewHolder.musicform_expend_delete = (LinearLayout) convertView.findViewById(R.id.musicform_expend_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据expend值判断是否展开
            if ((boolean) map.get("expend")) {
                viewHolder.expend.setVisibility(View.VISIBLE);
            } else {
                viewHolder.expend.setVisibility(View.GONE);
            }
            viewHolder.itemMusicForm_name_txt.setText(map.get("name").toString());
            viewHolder.itemMusicForm_num_txt.setText(map.get("size").toString());
            //按钮绑定点击事件
            viewHolder.itemMusicForm_btn.setOnClickListener(new ViewOCL(position));
            viewHolder.musicform_expend_edit.setOnClickListener(new ViewOCL());
            viewHolder.musicform_expend_delete.setOnClickListener(new ViewOCL());
            return convertView;
        }

        private class ViewOCL implements View.OnClickListener {
            //记录所点button的位置
            private int position;
            private Map map;
            private boolean expend;

            public ViewOCL(int position) {
                this.position = position;
            }

            public ViewOCL() {
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //歌单item上btn的点击事件
                    case R.id.itemMusicForm_btn:
                        map = listItems.get(position);
                        expend = (boolean) map.get("expend");
                        if (oldPosition == position) {
                            if (expend) {
                                oldPosition = -1;
                            }
                            map.put("expend", !expend);
                        } else {
                            if (oldPosition >= 0) {
                                listItems.get(oldPosition).put("expend", false);
                            }
                            oldPosition = position;
                            map.put("expend", true);
                        }
                        for (int i = 0; i < adapter.getCount(); i++) {
                            View viewItem = adapter.getView(i, null, musicform_listview);
                            viewItem.measure(0, 0);
                            totalHight += viewItem.getMeasuredHeight();
                        }
                        ViewGroup.LayoutParams params = musicform_listview.getLayoutParams();
                        params.height = totalHight + (musicform_listview.getDividerHeight() * (musicform_listview.getCount() - 1));
                        musicform_listview.setLayoutParams(params);
                        adapter.notifyDataSetChanged();
                        break;

                    //点击btn之后，显示的编辑按钮事件
                    case R.id.musicform_expend_edit:
                        Toast.makeText(MusicFormActivity.this, "编辑", Toast.LENGTH_SHORT).show();
                        break;

                    //点击btn之后，显示的删除按钮事件
                    case R.id.musicform_expend_delete:
                        Toast.makeText(MusicFormActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }

        private class ViewHolder {
            public TextView itemMusicForm_name_txt;
            public TextView itemMusicForm_num_txt;
            public ImageButton itemMusicForm_btn;
            public LinearLayout expend;
            public LinearLayout musicform_expend_edit;
            public LinearLayout musicform_expend_delete;
        }
    }
}
