package com.designers.kuwo.biz;

import android.content.Context;

import com.designers.kuwo.entity.Singer;

import java.util.List;

/**
 * Created by PC-CWB on 2017/2/21.
 */
public interface SingerBiz {

    //用于歌手页面的数据赋值
    public List<Singer> SingerFind(final Context context);

    //通过歌手来查询该歌手的所有歌曲
    public List<Singer> FindSongBySinger(final Context context,String singer);
}
