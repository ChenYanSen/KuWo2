package com.designers.kuwo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.StatusBarCompat;

import java.util.ArrayList;

public class MusicSortActivity extends FragmentActivity {

    //返回键
    private ImageView imgMenuBar;
    //中间字
    private TextView txtMenuBar;

    //单曲
    private TextView txtSingle;
    //歌手
    private TextView txtSinger;
    //专辑
    private TextView txtAlbum;
    //文件夹
    private TextView txtFolder;
    //实现Tab滑动效果
    private ViewPager vPager;
    //动画图片
    private ImageView cursor;

    //动画图片偏移量
    private int offset = 0;
    private int position_one;
    private int position_two;
    private int position_three;

    //动画图片宽度
    private int bmpW;

    //当前页卡编号
    private int currIndex = 0;

    //存放Fragment
    private ArrayList<Fragment> fragmentArrayList;

    //管理Fragment
    private FragmentManager fragmentManager;

    public Context context;

    public static final String TAG = "MusicSortActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_sort);
        context = this;

        //设置全屏
        StatusBarCompat.compat(this);

        //初始化TextView
        InitTextView();

        //初始化ImageView
        InitImageView();

        //初始化Fragment
        InitFragment();

        //初始化ViewPager
        InitViewPager();

        this.imgMenuBar = (ImageView) findViewById(R.id.imgMenuBar);
        this.txtMenuBar = (TextView) findViewById(R.id.txtMenuBar);

        this.imgMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicSortActivity.this, MusicSortActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        //设置为竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        //single
        txtSingle = (TextView) findViewById(R.id.txtSingle);
        //singer
        txtSinger = (TextView) findViewById(R.id.txtSinger);
        //album
        txtAlbum = (TextView) findViewById(R.id.txtAlbum);
        //folder
        txtFolder = (TextView) findViewById(R.id.txtFolder);

        //click event
        txtSingle.setOnClickListener(new MFOnClickListener(0));
        txtSinger.setOnClickListener(new MFOnClickListener(1));
        txtAlbum.setOnClickListener(new MFOnClickListener(2));
        txtFolder.setOnClickListener(new MFOnClickListener(3));
    }

    /**
     * init pagerTab content
     */
    private void InitViewPager() {

        vPager = (ViewPager) findViewById(R.id.vPager);
        //*********************vPager绑定适配器***********************//
        vPager.setAdapter(new MusicFragmentAdapter(fragmentManager, fragmentArrayList));

        ////让ViewPager缓存3个页面
        vPager.setOffscreenPageLimit(3);

        //设置当前它默认显示第一页
        vPager.setCurrentItem(0);

        //将顶部文字恢复默认值
        resetTextViewTextColor();
        txtSingle.setTextColor(getResources().getColor(R.color.colorPrimary));

        //设置viewpager页面滑动监听事件
        vPager.setOnPageChangeListener(new MFOnPageChangeListener());

    }

    /**
     * 初始化动画
     */

    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // 获取分辨率宽度
        int screenW = dm.widthPixels;  //屏幕宽度

        bmpW = (int) (screenW / 4);

        //设置动画图片宽度
        setBmpW(cursor, bmpW);
        offset = 0;

        //动画图片偏移量赋值
        position_one = (int) (screenW / 4.0);
        position_two = position_one * 2;
        position_three = position_one * 3;
    }

    /**
     * 初始化Fragment，并添加到ArrayList中
     */

    private void InitFragment() {

        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new SingleFragment());
        fragmentArrayList.add(new SingerFragment());
        fragmentArrayList.add(new AlbumFragment());
        fragmentArrayList.add(new FolderFragment());

        fragmentManager = getSupportFragmentManager();


    }

    /**
     * 头标点击监听
     */
    public class MFOnClickListener implements View.OnClickListener {

        private int index;

        public MFOnClickListener(int i) {
            this.index = i;
        }

        @Override
        public void onClick(View v) {
            vPager.setCurrentItem(index);
        }
    }

    /**
     * 页卡切换监听
     */
    public class MFOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position) {
                //当前为页卡1
                case 0:
                    //  page2 -> page1
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        resetTextViewTextColor();
                        txtSingle.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 2) {
                        //  page3 -> page1
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        resetTextViewTextColor();
                        txtSingle.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 3) {
                        //  page4 -> page1
                        animation = new TranslateAnimation(position_three, 0, 0, 0);
                        resetTextViewTextColor();
                        txtSingle.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    break;
                //当前为页卡2
                case 1:
                    //  page1 -> page2
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        resetTextViewTextColor();
                        txtSinger.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 2) {
                        //  page3 -> page2
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        resetTextViewTextColor();
                        txtSinger.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 3) {
                        //  page4 -> page2
                        animation = new TranslateAnimation(position_three, position_one, 0, 0);
                        resetTextViewTextColor();
                        txtSinger.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    break;
                //当前为页卡3
                case 2:
                    //  page1 -> page3
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_two, 0, 0);
                        resetTextViewTextColor();
                        txtAlbum.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 1) {
                        //  page2 -> page3
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        resetTextViewTextColor();
                        txtAlbum.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 3) {
                        //  page4 -> page3
                        animation = new TranslateAnimation(position_three, position_two, 0, 0);
                        resetTextViewTextColor();
                        txtAlbum.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    break;
                //当前为页卡4
                case 3:
                    //   page1 -> page4
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_three, 0, 0);
                        resetTextViewTextColor();
                        txtFolder.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 1) {
                        //   page2 -> page4
                        animation = new TranslateAnimation(position_one, position_three, 0, 0);
                        resetTextViewTextColor();
                        txtFolder.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else if (currIndex == 2) {
                        //   page3 -> page4
                        animation = new TranslateAnimation(position_three, position_three, 0, 0);
                        resetTextViewTextColor();
                        txtFolder.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    break;

            }
            currIndex = position;

            animation.setFillAfter(true);//true:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    /**
     * 设置动画图片宽度
     */

    private void setBmpW(ImageView imageView, int mWidth) {
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        para.width = mWidth;
        imageView.setLayoutParams(para);
    }

    /**
     * 将顶部文字恢复默认值ֵ
     */
    private void resetTextViewTextColor() {

        txtSingle.setTextColor(getResources().getColor(R.color.music_sort_top_tab_color));
        txtSinger.setTextColor(getResources().getColor(R.color.music_sort_top_tab_color));
        txtAlbum.setTextColor(getResources().getColor(R.color.music_sort_top_tab_color));
        txtFolder.setTextColor(getResources().getColor(R.color.music_sort_top_tab_color));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music_sort, menu);
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
