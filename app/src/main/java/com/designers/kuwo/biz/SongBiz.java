package com.designers.kuwo.biz;

import android.content.Context;

import com.designers.kuwo.entity.Song;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/2/20.
 */
public interface SongBiz {

    public abstract void insert(final Context context, final Song song);

    public abstract List<Map<String, Object>> findAll(final Context context);

    //扫描出歌曲的文件夹
    public abstract List<Map<String,Object>> findFolder(final Context context);

    //扫描出文件夹的歌曲数目
    public abstract List<Map<String,Integer>> findSongNum(final Context context);
}
