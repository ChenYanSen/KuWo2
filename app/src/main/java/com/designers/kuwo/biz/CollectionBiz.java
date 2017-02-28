package com.designers.kuwo.biz;

import android.content.Context;

import com.designers.kuwo.entity.ICollection;
import com.designers.kuwo.entity.Song;

import java.util.List;

/**
 * Created by 跃 on 2017/2/22.
 */
public interface CollectionBiz {
    /**
     * 添加收藏
     * @param iCollection
     * @param context
     * @return
     */
    public boolean alterCollection(final ICollection iCollection, final Context context);

    /**
     * 取消收藏
     * @param song
     * @param singer
     * @param account
     * @param context
     * @return
     */
    public boolean removeCollection(final String song, final String singer, final String account, final Context context);

    /**
     * 查看是否收藏
     * @param song
     * @param singer
     * @param account
     * @param context
     * @return
     */
    public boolean findCollection(final String song, final String singer, final String account, final Context context);

    /**
     * 关联查询
     */

    public List<Song> findCollectionByName(final Context context,String songName,String singer);

    /**
     * 查出当前收藏表中的歌曲的所有数据
     */

    public List<Song> findCollectionSortAll(final Context context);
}
