package com.designers.kuwo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.FastBlurUtil;
import com.designers.kuwo.utils.MainMusicFromListViewAdapter;
import com.designers.kuwo.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private Toolbar toolbar;
    private List<Map<String, ?>> musicFromlistItems;
    private ListView musicFormListView;//歌单列表
    private CircularImage main_photo_imgview;//主页左上角头像
    private CircularImage main_music_imgview;//主页单曲图片
    private CircularImage main_like_imgview;//主页我喜欢图片
    private CircularImage main_recent_view;//主页最近图片
    private CircularImage playbar_img_imgview;//主页播放栏头像
    private CircularImage menu_photo_imgview;//侧滑菜单头像
    private LinearLayout main_above_layout;//主页上面的布局
    private LinearLayout main_like_btn;//主页我喜欢按钮
    private LinearLayout main_music_btn;//主页单曲按钮
    private LinearLayout main_recent_btn;//主页最近按钮
    private TableLayout main_myMusicForm;//主页我的歌单按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicFromlistItems = initMusicFrom();
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        main_photo_imgview = (CircularImage) findViewById(R.id.main_photo_imgview);
        musicFormListView = (ListView) findViewById(R.id.musicFormListView);
        main_music_imgview = (CircularImage) findViewById(R.id.main_music_imgview);
        main_like_imgview = (CircularImage) findViewById(R.id.main_like_imgview);
        main_recent_view = (CircularImage) findViewById(R.id.main_recent_imgview);
        playbar_img_imgview = (CircularImage) findViewById(R.id.playbar_img_imgview);
        menu_photo_imgview = (CircularImage) findViewById(R.id.menu_photo_imgview);
        main_above_layout = (LinearLayout) findViewById(R.id.main_above_layout);
        main_like_btn = (LinearLayout) findViewById(R.id.main_like_btn);
        main_music_btn = (LinearLayout) findViewById(R.id.main_music_btn);
        main_recent_btn = (LinearLayout) findViewById(R.id.main_recent_btn);
        main_myMusicForm = (TableLayout) findViewById(R.id.main_myMusicForm);


        MainMusicFromListViewAdapter adapter = new MainMusicFromListViewAdapter(this, musicFromlistItems);
        main_like_btn.setOnClickListener(this);
        main_music_btn.setOnClickListener(this);
        main_recent_btn.setOnClickListener(this);
        main_myMusicForm.setOnClickListener(this);
        menu_photo_imgview.setOnClickListener(this);
        main_photo_imgview.setImageResource(R.drawable.playbarimg);
        main_music_imgview.setImageResource(R.drawable.img1);
        main_like_imgview.setImageResource(R.drawable.img2);
        main_recent_view.setImageResource(R.drawable.img3);
        playbar_img_imgview.setImageResource(R.drawable.playbarimg);
        menu_photo_imgview.setImageResource(R.drawable.img3);
        musicFormListView.setAdapter(adapter);

        //全屏
        StatusBarCompat.compat(this);

        //主页虚化
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.mainback1);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 20, bitmap.getHeight() / 20, false);
        Bitmap blurBitMap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
        BitmapDrawable drawable = new BitmapDrawable(blurBitMap);
        main_above_layout.setBackground(drawable);

    }

    //主页歌单列表数据
    public List<Map<String, ?>> initMusicFrom() {
        List<Map<String, ?>> listItems = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("formImageId", R.drawable.musicformimg);
            map.put("listTextFrist", "我的歌单" + i);
            map.put("listTextSecond", "我们都一样");
            map.put("listTextThird", i + "告白气球");
            listItems.add(map);
        }
        return listItems;
    }

    //侧滑菜单数据
    public List<Map<String, ?>> initMenu() {
        List<Map<String, ?>> listItems = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menuText", "第" + i + "item...");
            listItems.add(map);
        }
        return listItems;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //点击我喜欢按钮的事件
            case R.id.main_like_btn:
                intent = new Intent(this, LikeMusicActivity.class);
                startActivity(intent);
                break;

            //点击单曲按钮的事件
            case R.id.main_music_btn:
                intent = new Intent(this, SingleMusicActivity.class);
                startActivity(intent);
                break;

            //点击最近按钮的事件
            case R.id.main_recent_btn:
                intent = new Intent(this, RecentMusicActivity.class);
                startActivity(intent);
                break;


            //点击我的歌单按钮的事件
            case R.id.main_myMusicForm:
                intent = new Intent(this, MusicFormActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_photo_imgview:
                intent=new Intent(this,PersonalInforSetActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
