package com.designers.kuwo.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.UserBiz;
import com.designers.kuwo.biz.bizimpl.UserBizImpl;
import com.designers.kuwo.entity.User;

public class Register extends Activity {
   private EditText register_edtUserName;
    private EditText register_edtPassword,register_edtPassword_second;
    private Button register_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.register_edtUserName= (EditText) findViewById(R.id.register_edtUserName);
        this.register_edtPassword= (EditText) findViewById(R.id.register_edtUserName);
        this.register_edtPassword_second= (EditText) findViewById(R.id.register_edtPassword_second);
        this.register_Register= (Button) findViewById(R.id.register_Register);
        this.register_Register.setOnClickListener(x->{
            String account=register_edtUserName.getText().toString().trim();
            String password=register_edtPassword.getText().toString().trim();
            String passwordSecond=register_edtPassword_second.getText().toString().trim();
            if(passwordSecond.equals(password)){
                UserBiz userBiz=new UserBizImpl();
                User user=new User();
                user.setAccount(account);
                user.setPassword(password);
                boolean flag;
                flag=userBiz.userExists(Register.this,account);
                if(!flag){
                    flag=userBiz.register(Register.this, user);
                    if(flag){
                        showCustomToast("注册成功！");
                    }else {
                        showCustomToast("账号已存在");
                    }
                }
            }else {
                showCustomToast("两次输入密码不一致");
            }


        });

    }
    // 自定义toast信息显示
    public void showCustomToast(String message) {
        // 1.创建一个LayoutInflater接口对象
        LayoutInflater inflater = getLayoutInflater();
        // 2.使用inflater对象中的inflate方法绑定自定义Toast布局文件,同事指向该布局文件中的根标记节点
        View layout = inflater.inflate(R.layout.toast_custom, null);

        // 3.获取该布局文件中的TextView组件
        TextView toastMessage = (TextView) layout
                .findViewById(R.id.toastMessage);
        // 4.为TextView赋值
        toastMessage.setText(message);
        // 5.实例化一个Toast组件对象，并进行显示
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        // 将步骤4设置好的定制布局与当前的Toast对象进行绑定
        toast.setView(layout);
        // 6.显示Toast组件
        toast.show();
    }
}
