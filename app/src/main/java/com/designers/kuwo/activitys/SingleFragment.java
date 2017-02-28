package com.designers.kuwo.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.CollectionBiz;
import com.designers.kuwo.biz.SongBiz;
import com.designers.kuwo.biz.bizimpl.CollectionBizImp;
import com.designers.kuwo.biz.bizimpl.SongBizImpl;
import com.designers.kuwo.entity.ICollection;
import com.designers.kuwo.entity.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleFragment extends Fragment {

    private ListView listViewSingle;
    private ImageView dialogAddPlayList;
    private ListView dialogAddListView;
    private TextView dialogcancelAdd;
    private SingleFragmentAdapter adapter;
    private SongBiz songBiz;
    private CollectionBiz collectionBiz;
    private List<Map<String, Object>> songList;
    boolean flagExist = false;//判断是否收藏标记

    //测试收藏查询数据
    private List<Song> collectSongList;

    private int oldPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //数据库的操作，查询出所有的歌曲
        songList = new ArrayList<Map<String, Object>>();
        songBiz = new SongBizImpl();
        songList = songBiz.findAll(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, null);
        //在一个fragment中添加一个listview
        this.listViewSingle = (ListView) view.findViewById(R.id.listViewSingle);
        adapter = new SingleFragmentAdapter(this.getActivity(), getData());
        listViewSingle.setAdapter(adapter);
        return view;
    }

    //获取数据库的数据源
    private ArrayList<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < songList.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("extend", false);     //为每个item设置为false标记，默认都处于收缩状态
            items.add(item);
        }
        return items;
    }

    private class SingleFragmentAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Map<String, Object>> listitems;
        private LayoutInflater inflater;

        private boolean extend = false;
        ViewHolder holder = null;

        public SingleFragmentAdapter(Context context, ArrayList<Map<String, Object>> listitems) {
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
            //拿到视图
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.item_single, null);
                holder = new ViewHolder();
                holder.txtItemSingleSong = (TextView) convertView.findViewById(R.id.txtItemSingleSong);
                holder.txtItemSingleSinger = (TextView) convertView.findViewById(R.id.txtItemSingleSinger);
                holder.imgBtnItemSingleDown = (ImageButton) convertView.findViewById(R.id.imgBtnItemSingleDown);
                holder.itemHideLinearLayoutAdd = (LinearLayout) convertView.findViewById(R.id.itemHideLinearLayoutAdd);
                holder.itemHideLinearLayoutCollect = (LinearLayout) convertView.findViewById(R.id.itemHideLinearLayoutCollect);
                holder.itemHideLinearLayoutRemove = (LinearLayout) convertView.findViewById(R.id.itemHideLinearLayoutRemove);
                holder.itemHideLinearLayoutMessage = (LinearLayout) convertView.findViewById(R.id.itemHideLinearLayoutMessage);
                holder.itemHideLinearLayout = (LinearLayout) convertView.findViewById(R.id.itemHideLinearLayout);
                holder.imgHideCollect1 = (ImageView) convertView.findViewById(R.id.imgHideCollect1);
                holder.txtHideCollect = (TextView) convertView.findViewById(R.id.txtHideCollect);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //视图上赋值
            holder.txtItemSingleSong.setText(songList.get(position).get("songName").toString());
            holder.txtItemSingleSong.setTextColor(getResources().getColor(R.color.textcolor_black));
            holder.txtItemSingleSinger.setText(songList.get(position).get("singer").toString());
            holder.txtItemSingleSinger.setTextColor(getResources().getColor(R.color.textcolor_black));

            //对于按钮，检测它的状态，看是处于隐藏状态还是处于伸展状态
            if ((boolean) listitems.get(position).get("extend")) {
                holder.itemHideLinearLayout.setVisibility(View.VISIBLE);
            } else {
                holder.itemHideLinearLayout.setVisibility(View.GONE);
            }

            holder.imgBtnItemSingleDown.setOnClickListener(new View.OnClickListener() {

                private int totalHight = 0;

                @Override
                public void onClick(View v) {
                    //拿到位置
                    extend = (boolean) listitems.get(position).get("extend");
                    if (oldPosition == position) {
                        if (extend) {
                            oldPosition = -1;
                        }
                        listitems.get(position).put("extend", !extend);
                    } else {
                        if (oldPosition >= 0) {
                            listitems.get(oldPosition).put("extend", false);
                        }
                        oldPosition = position;
                        listitems.get(position).put("extend", true);
                    }
                    for (int i = 0; i < 7; i++) {
                        View viewItem = adapter.getView(i, null, listViewSingle);
                        viewItem.measure(0, 0);
                        totalHight += viewItem.getMeasuredHeight();
                    }
                    Log.i("= totalHight =", totalHight + "");
                    ViewGroup.LayoutParams params = listViewSingle.getLayoutParams();
                    params.height = totalHight + (listViewSingle.getDividerHeight() * 6);
                    listViewSingle.setLayoutParams(params);
                    adapter.notifyDataSetChanged();
                }
            });

            holder.itemHideLinearLayoutAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context, R.style.my_dialog);
                    View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add, null);
                    //窗口上的一些组件，
                    dialogAddPlayList = (ImageView) dialogView.findViewById(R.id.dialogAddPlayList);
                    dialogAddListView = (ListView) dialogView.findViewById(R.id.dialogAddListView);
                    dialogcancelAdd = (TextView) dialogView.findViewById(R.id.cancelAdd);
                    dialog.setContentView(dialogView);
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.dialogStyle);

                    window.getDecorView().setPadding(0, 0, 0, 0);
                    //获得window窗口的属性
                    WindowManager.LayoutParams lp = window.getAttributes();
                    //设置窗口宽度为充满全屏
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    //设置窗口高度
                    lp.height = getResources().getDisplayMetrics().heightPixels / 2;
                    //将设置好的属性set回去
                    window.setAttributes(lp);
                    dialog.show();

                    //测试数据
                    String playList[] = {"歌单一", "歌单二", "歌单三", "歌单四"};
                    ArrayAdapter adapter = new ArrayAdapter(context,R.layout.item_new_folder,playList);

                    dialogAddListView.setAdapter(adapter);

                    dialogcancelAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialogAddPlayList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();//点击添加按钮，先将目前的dialog隐藏
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setInverseBackgroundForced(true);//白色
                            View view = LayoutInflater.from(context).inflate(R.layout.activity_personal_nake_set, null);
                            final EditText personal_nake_input = (EditText) view.findViewById(R.id.personal_nake_input);
                            final TextView personal_title_set = (TextView) view.findViewById(R.id.personal_title_set);
                            personal_title_set.setText("新建歌单");
                            builder.setView(view);
                            builder.setPositiveButton("确定", new Dialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String str = personal_nake_input.getText().toString().trim();
                                    Toast.makeText(context, "成功添加至：" + str, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new Dialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "您点击了取消创建歌单！", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    });
                }
            });
            holder.itemHideLinearLayoutCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String song = songList.get(position).get("songName").toString();
                    String singer = songList.get(position).get("singer").toString();
                   // String songUri = songList.get(position).get("songUri").toString();
                    String account = "admin";
                    ICollection c = new ICollection();
                    c.setAccount(account);
                    c.setSongName(song);
                    c.setSinger(singer);
                    //c.setSongUri(songUri);
                    collectionBiz = new CollectionBizImp();
                    flagExist = collectionBiz.findCollection(song,
                            singer, "admin", getActivity());
                    if (!flagExist) {
                        collectionBiz.alterCollection(c, getActivity());
                        //holder.imgHideCollect1.setVisibility(View.GONE);
                        Toast.makeText(context, "恭喜你收藏成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        collectionBiz.removeCollection(song, singer, account, getActivity());
                        //holder.imgHideCollect1.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "恭喜你取消收藏成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.itemHideLinearLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用删除业务
                    //=================测试收藏业务借用==================
                    collectionBiz=new CollectionBizImp();
                    collectSongList=new ArrayList<Song>();
                    collectSongList=collectionBiz.findCollectionSortAll(getActivity());
                    Log.i("测试查询收藏表=========",collectSongList.toString());
                    Toast.makeText(context, "恭喜你删除成功！", Toast.LENGTH_SHORT).show();
                }
            });
            holder.itemHideLinearLayoutMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "这是一些歌曲信息", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

    }

    private class ViewHolder {
        LinearLayout itemHideLinearLayout, itemHideLinearLayoutAdd, itemHideLinearLayoutRemove,
                itemHideLinearLayoutMessage, itemHideLinearLayoutCollect;
        ImageView itemCursor, imgHideCollect1;
        TextView txtItemSingleSong, txtItemSingleSinger, txtHideCollect;
        ImageButton imgBtnItemSingleDown;
    }

}
