package com.designers.kuwo.dao.daoimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.designers.kuwo.dao.CollectionDao;
import com.designers.kuwo.entity.ICollection;
import com.designers.kuwo.entity.Song;
import com.designers.kuwo.sqlite.SQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃 on 2017/2/22.
 */
public class CollectionDaoImpl implements CollectionDao {

    private boolean flag = false;
    private SQLManager sqlManager;

    public CollectionDaoImpl() {
        this.sqlManager = new SQLManager();
    }

    /**
     * 添加收藏
     *
     * @param iCollection
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    @Override
    public boolean insertCollection(ICollection iCollection, SQLiteDatabase sqLiteDatabase) throws SQLException {
        String singer = iCollection.getSinger();
        String songName = iCollection.getSongName();
        String account = iCollection.getAccount();
        String sql = "insert into collection(SongName,singer,account) values(?,?,?)";
        Log.i("数据库操作:", sql);
        String[] bindArgs = new String[]{songName, singer, account};
        flag = sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
        Log.i("数据库操作：", "dao层执行了   insertCollection    。。。。flag=" + flag);
        return flag;
    }


    /**
     * 根据用户信息及歌曲，歌手删除收藏
     *
     * @param song
     * @param singer
     * @param account
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    @Override
    public boolean deleteCollection(String song, String singer, String account, SQLiteDatabase sqLiteDatabase) throws SQLException {
        String sql = "delete from collection where songName=? and singer=? and account= ?";
        String[] bindArgs = new String[]{song, singer, account};
        flag = sqlManager.execWrite(sqLiteDatabase, sql, bindArgs);
        Log.i("数据库操作：", "dao层执行了   deleteCollection   。。。。flag=" + flag);
        return flag;
    }

    /**
     * 判断是否收藏
     *
     * @param song
     * @param singer
     * @param account
     * @param sqLiteDatabase
     * @return
     * @throws SQLException
     */
    @Override
    public boolean selectCollection(String song, String singer, String account, SQLiteDatabase sqLiteDatabase) throws SQLException {
        String sql = "select * from collection where SongName=? and singer=? and account=?";
        String[] bindArgs = new String[]{song, singer, account};
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, bindArgs);
        if (cursor.moveToNext()) {
            flag = true;
        } else {
            flag = false;
        }
        Log.i("数据库操作：", "dao层执行了判断是否收藏的操作     selectCollection     。。。。flag  =  " + flag);
        return flag;
    }

    @Override
    public List<Song> selectCollectionByName(SQLiteDatabase sqLiteDatabase, String songName, String singer) {
        List<Song> collectSongList = new ArrayList<Song>();
        String sql =
                "select collection.songName,collection.singer,songs.singerUri，songs.songUri，songs.songImage，songs.singlyrics，songs.albumUri，" +
                        "songs.time，songs.information，songs.folder" +
                        "from collection inner join songs on collection.SongName=songs.songName and collection.singer=songs.singer where collection.SongName=? and collection.singer=?";
        String selectionArgs[] = new String[]{songName, singer};
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        while (cursor.moveToNext()) {
            Song song = new Song();
            song.setSongName(cursor.getString(0));
            song.setSinger(cursor.getString(1));
            song.setSingerUri(cursor.getString(2));
            song.setSongUri(cursor.getString(3));
            song.setSongImage(cursor.getString(4));
            song.setSingLyrics(cursor.getString(5));
            song.setAlbumUri(cursor.getString(6));
            song.setTime(cursor.getString(7));
            song.setInformation(cursor.getString(8));
            song.setFolder(cursor.getString(9));
            collectSongList.add(song);
        }
        return collectSongList;
    }

    @Override
    public List<Song> selectCollectionSortAll(SQLiteDatabase sqLiteDatabase) throws SQLException {
        List<Song> collectSongList = new ArrayList<Song>();
        String sql =
                "SELECT collection.SongName,collection.singer,songs.singerUri,songs.songUri,songs.albumUri," +
                        "songs.time,songs.folder " +
                        "FROM songs INNER JOIN collection ON collection.SongName=songs.songName and collection.singer=songs.singer";
        String selectionArgs[] = new String[]{};
        Cursor cursor = sqlManager.execRead(sqLiteDatabase, sql, selectionArgs);
        while (cursor.moveToNext()) {
            Song song = new Song();
            song.setSongName(cursor.getString(0));
            song.setSinger(cursor.getString(1));
            song.setSingerUri(cursor.getString(2));
            song.setSongUri(cursor.getString(3));
            //song.setSongImage(cursor.getString(4));
            //song.setSingLyrics(cursor.getString(5));
            song.setAlbumUri(cursor.getString(4));
            song.setTime(cursor.getString(5));
            //song.setInformation(cursor.getString(8));
            //song.setFolder(cursor.getString(7));
            collectSongList.add(song);
        }
        return collectSongList;
    }

}
