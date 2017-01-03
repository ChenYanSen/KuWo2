package com.designers.kuwo.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.MusicFromListViewAdapter;
import com.designers.kuwo.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private List<Map<String, ?>> musicFromlistItems;
    private ListView musicFormListView;//歌单列表
//    private List<Map<String, ?>> menuListItems;
//    private ListView menu_item_listview;//侧滑栏列表
    private CircularImage main_photo_imgview;//主页左上角头像
    private CircularImage main_music_imgview;//主页单曲按钮
    private CircularImage main_like_imgview;//主页我喜欢按钮
    private CircularImage main_recent_view;//主页最近按钮
    private CircularImage playbar_img_imgview;//主页播放栏头像
    private CircularImage menu_photo_imgview;//侧滑菜单头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicFromlistItems = initMusicFrom();
        //menuListItems = initMenu();
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        main_photo_imgview = (CircularImage) findViewById(R.id.main_photo_imgview);
        musicFormListView = (ListView) findViewById(R.id.musicFormListView);
        //menu_item_listview = (ListView) findViewById(R.id.menu_item_listview);
        main_music_imgview = (CircularImage) findViewById(R.id.main_music_imgview);
        main_like_imgview = (CircularImage) findViewById(R.id.main_like_imgview);
        main_recent_view = (CircularImage) findViewById(R.id.main_recent_imgview);
        playbar_img_imgview = (CircularImage) findViewById(R.id.playbar_img_imgview);
        menu_photo_imgview = (CircularImage) findViewById(R.id.menu_photo_imgview);


        MusicFromListViewAdapter adapter = new MusicFromListViewAdapter(this, musicFromlistItems);
        //MenuListViewAdapter menuAdapter = new MenuListViewAdapter(this, menuListItems);

        main_photo_imgview.setImageResource(R.drawable.playbarimg);
        main_music_imgview.setImageResource(R.drawable.img1);
        main_like_imgview.setImageResource(R.drawable.img2);
        main_recent_view.setImageResource(R.drawable.img3);
        playbar_img_imgview.setImageResource(R.drawable.playbarimg);
        menu_photo_imgview.setImageResource(R.drawable.img3);
        musicFormListView.setAdapter(adapter);
        //menu_item_listview.setAdapter(menuAdapter);

        //toolbar.setTitle("呵呵");
        //toolbar.setLogo(R.drawable.l2);
        //setSupportActionBar(toolbar);
        StatusBarCompat.compat(this);
    }

    public List<Map<String, ?>> initMusicFrom() {
        List<Map<String, ?>> listItems = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("formImageId", R.drawable.musicformimg);
            map.put("listText", "我的歌单" + i);
            map.put("size", i + "首歌曲");
            listItems.add(map);
        }
        return listItems;
    }

    public List<Map<String, ?>> initMenu() {
        List<Map<String, ?>> listItems = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menuText", "第" + i+"item...");
            listItems.add(map);
        }
        return listItems;
    }

}
