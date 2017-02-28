package com.designers.kuwo.dao;

import android.database.sqlite.SQLiteDatabase;

import com.designers.kuwo.entity.ICollection;
import com.designers.kuwo.entity.Song;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 跃 on 2017/2/22.
 */
public interface CollectionDao {
    /**
     * 添加收藏
     * @param iCollection
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    public boolean insertCollection(final ICollection iCollection, final SQLiteDatabase sqLiteDatabase)throws SQLException;

    /**
     * 删除收藏
     * @param song
     * @param singer
     * @param account
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    public boolean deleteCollection(final String song, final String singer, final String account, final SQLiteDatabase sqLiteDatabase)throws SQLException;

    /**
     * 查询是否收藏过
     * @param song
     * @param singer
     * @param account
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    public boolean selectCollection(final String song, final String singer, final String account, final SQLiteDatabase sqLiteDatabase)throws SQLException;

    /**
     * 关联查询
     */
    public List<Song> selectCollectionByName(final SQLiteDatabase sqLiteDatabase,String songName,String singer)throws SQLException;

    /**
     * 关联查询collection表中的所有数据
     */
    public List<Song> selectCollectionSortAll(final SQLiteDatabase sqLiteDatabase)throws SQLException;
}
