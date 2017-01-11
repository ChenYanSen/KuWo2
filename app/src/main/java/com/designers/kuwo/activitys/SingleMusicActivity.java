package com.designers.kuwo.activitys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.StatusBarCompat;
import com.designers.kuwo.utils.SwipeBackLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/6.
 */
public class SingleMusicActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private List<Map<String, Object>> listItems;
    private ListView music_listview;
    private ImageView back;
    private TextView toolbar_txt;
    private CircularImage playbar_img_imgview;
    private LinearLayout music_dialog_add;//对话框添加按钮
    private LinearLayout music_dialog_selectall;//对话框全选按钮
    private LinearLayout music_dialog_delete;//对话框删除按钮
    private LinearLayout music_dialog_cancle;//对话框取消按钮
    private SwipeBackLayout swipeLayout;
    private LinearLayout music_search;//搜索按钮
    private ImageView selectMany;//多选按钮
    private MusicListViewAdapter adapter;
    private MusicSelectListViewAdapter selectAdapter;
    private Dialog dialog;//长按item底部弹出的对话框
    private int oldPosition = -1;//用来记录上一次所点按钮的位置
    private int totalHight = 0;
    private boolean flag = false;//是否处于多选

    private List<Integer> selectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_like);

        listItems = init();
        back = (ImageView) findViewById(R.id.back);
        toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
        swipeLayout = (SwipeBackLayout) findViewById(R.id.swipeLayout);
        music_listview = (ListView) findViewById(R.id.music_listview);
        playbar_img_imgview = (CircularImage) findViewById(R.id.playbar_img_imgview);
        music_search = (LinearLayout) findViewById(R.id.music_search);
        selectMany = (ImageView) findViewById(R.id.selectMany);

        toolbar_txt.setText("单曲");
        playbar_img_imgview.setImageResource(R.drawable.playbarimg);
        swipeLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
        adapter = new MusicListViewAdapter(this);
        selectAdapter = new MusicSelectListViewAdapter(this);
        music_listview.setAdapter(adapter);

        back.setOnClickListener(this);
        selectMany.setOnClickListener(this);
        music_listview.setOnItemClickListener(this);
        music_listview.setOnItemLongClickListener(this);

        //全屏
        StatusBarCompat.compat(this);
    }

    public List<Map<String, Object>> init() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap();
            map.put("musicname", "告白气球");
            map.put("songer", "周杰伦");
            map.put("expend", false);
            map.put("checked", false);
            items.add(map);
        }
        return items;
    }

    //歌曲item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!flag) {
            Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        }
    }

    //歌曲item长点击事件
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (!flag) {
            music_listview.setAdapter(selectAdapter);
            setDialog();
            flag = true;
        }
        return false;
    }

    //按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.selectMany:
                if (!flag) {
                    music_listview.setAdapter(selectAdapter);
                    setDialog();
                    flag = true;
                } else {
                    music_listview.setAdapter(adapter);
                    dialog.dismiss();
                    flag = false;
                }
                break;

            case R.id.music_dilog_add:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                music_listview.setAdapter(adapter);
                dialog.dismiss();
                flag = false;
                break;

            case R.id.music_dilog_selectall:
                Toast.makeText(this, "全选", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < listItems.size(); i++) {
                    listItems.get(i).put("checked", true);
                    if (!selectList.contains(i))
                        selectList.add(i);
                }
                selectAdapter.notifyDataSetChanged();
                flag = false;
                break;

            case R.id.music_dilog_delete:
                Toast.makeText(this, "删除" + selectList.toString(), Toast.LENGTH_SHORT).show();
                music_listview.setAdapter(adapter);
                listItems = init();
                selectList.clear();
                dialog.dismiss();
                flag = false;
                break;

            case R.id.music_dilog_cancle:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                music_listview.setAdapter(adapter);
                listItems = init();
                selectList.clear();
                dialog.dismiss();
                flag = false;
                break;
        }
    }

    //歌曲列表的适配器
    public class MusicListViewAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MusicListViewAdapter(Context context) {
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
                convertView = inflater.inflate(R.layout.musicform_music_item_layout, null);
                viewHolder.itemMusic_name_txt = (TextView) convertView.findViewById(R.id.itemMusic_name_txt);
                viewHolder.itemMusic_songer_txt = (TextView) convertView.findViewById(R.id.itemMusic_songer_txt);
                viewHolder.itemMusic_btn = (ImageButton) convertView.findViewById(R.id.itemMusic_btn);
                viewHolder.expend = (LinearLayout) convertView.findViewById(R.id.expend);
                viewHolder.music_expend_add = (LinearLayout) convertView.findViewById(R.id.music_expend_add);
                viewHolder.music_expend_info = (LinearLayout) convertView.findViewById(R.id.music_expend_info);
                viewHolder.music_expend_delete = (LinearLayout) convertView.findViewById(R.id.music_expend_delete);
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
            viewHolder.itemMusic_name_txt.setText(map.get("musicname").toString());
            viewHolder.itemMusic_songer_txt.setText(map.get("songer").toString());
            //按钮绑定点击事件
            viewHolder.itemMusic_btn.setOnClickListener(new ViewOCL(position));
            viewHolder.music_expend_add.setOnClickListener(new ViewOCL());
            viewHolder.music_expend_info.setOnClickListener(new ViewOCL());
            viewHolder.music_expend_delete.setOnClickListener(new ViewOCL());
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
                    case R.id.itemMusic_btn:
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
                            View viewItem = adapter.getView(i, null, music_listview);
                            viewItem.measure(0, 0);
                            totalHight += viewItem.getMeasuredHeight();
                        }
                        ViewGroup.LayoutParams params = music_listview.getLayoutParams();
                        params.height = totalHight + (music_listview.getDividerHeight() * (music_listview.getCount() - 1));
                        music_listview.setLayoutParams(params);
                        adapter.notifyDataSetChanged();
                        break;

                    case R.id.music_expend_add:
                        Toast.makeText(SingleMusicActivity.this, "添加", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.music_expend_info:
                        Toast.makeText(SingleMusicActivity.this, "信息", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.music_expend_delete:
                        Toast.makeText(SingleMusicActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }

        private class ViewHolder {
            public TextView itemMusic_name_txt;
            public TextView itemMusic_songer_txt;
            public ImageButton itemMusic_btn;
            public LinearLayout expend;
            public LinearLayout music_expend_add;
            public LinearLayout music_expend_info;
            public LinearLayout music_expend_delete;
        }
    }

    //歌曲多选适配器
    public class MusicSelectListViewAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MusicSelectListViewAdapter(Context context) {
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
                Log.i("test", position + "为空");
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.musicform_music_item__select_layout, null);
                viewHolder.itemMusic_name_txt = (TextView) convertView.findViewById(R.id.itemMusic_name_txt);
                viewHolder.itemMusic_songer_txt = (TextView) convertView.findViewById(R.id.itemMusic_songer_txt);
                viewHolder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
                convertView.setTag(viewHolder);
            } else {
                Log.i("test", position + "不为空");
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((boolean) map.get("checked")) {
                        map.put("checked", false);
                        selectList.remove(selectList.indexOf(position));
                    } else {
                        map.put("checked", true);
                        selectList.add(position);
                    }
                }
            });
            viewHolder.check.setChecked((boolean) map.get("checked"));
            viewHolder.itemMusic_name_txt.setText(map.get("musicname").toString());
            viewHolder.itemMusic_songer_txt.setText(map.get("songer").toString());
            return convertView;
        }

        private class ViewHolder {
            public TextView itemMusic_name_txt;
            public TextView itemMusic_songer_txt;
            private CheckBox check;
        }
    }

    //点击多选按钮之后弹出的对话框
    public void setDialog() {
        dialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.music_bottom_dialog, null);
        music_dialog_add = (LinearLayout) root.findViewById(R.id.music_dilog_add);
        music_dialog_selectall = (LinearLayout) root.findViewById(R.id.music_dilog_selectall);
        music_dialog_delete = (LinearLayout) root.findViewById(R.id.music_dilog_delete);
        music_dialog_cancle = (LinearLayout) root.findViewById(R.id.music_dilog_cancle);
        music_dialog_add.setOnClickListener(this);
        music_dialog_selectall.setOnClickListener(this);
        music_dialog_delete.setOnClickListener(this);
        music_dialog_cancle.setOnClickListener(this);
        dialog.setContentView(root);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        //不获取焦点，即弹出对话框菜单之后可继续操作界面
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        //lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
