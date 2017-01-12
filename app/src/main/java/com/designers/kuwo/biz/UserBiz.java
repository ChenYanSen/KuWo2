package com.designers.kuwo.biz;

import android.content.Context;

import com.designers.kuwo.entity.User;

/**
 * Created by 跃 on 2017/1/12.
 */
public interface UserBiz {
    /**
     * 判断用户是否存在
     */
    public boolean userExists(final Context context, String account);

    /**
     * 用户注册
     */
    public boolean register(final Context context, User user);

}
