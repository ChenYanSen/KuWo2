package com.designers.kuwo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.utils.CircularImage;

public class LoginActivity extends Activity {
    private ImageView loginHeadPicture;
    private TextView edtUserName;
    private TextView edtPassword;
    private TextView btnLogin;
    private TextView btnRegister;
    private TextView login_miss_passwrod;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //从 SharedPreferences读出储存的账号
        sharedPreferences = this.getSharedPreferences("userInfor", this.MODE_PRIVATE);
        String name = sharedPreferences.getString("names", "user");
        this.edtUserName = (TextView) findViewById(R.id.edtUserName);
        //显示读出的账号
        if (!"user".equals(name)) {
            this.edtUserName.setText(name);
        }
        this.edtPassword = (TextView) findViewById(R.id.edtPassword);
        this.btnLogin = (TextView) findViewById(R.id.btnLogin);
        this.btnRegister = (TextView) findViewById(R.id.btnRegister);
        this.login_miss_passwrod = (TextView) findViewById(R.id.login_miss_passwrod);

        this.loginHeadPicture = (CircularImage) findViewById(R.id.loginHeadPicture);
        //圆头像
        Bitmap bitMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.login_image);
        this.loginHeadPicture.setImageBitmap(bitMap);
        //登录点击按钮
        this.btnLogin.setOnClickListener(x -> {
            //保存到sharePreferncess
            String names = this.edtUserName.getText().toString();
            String userPasswrod = this.edtPassword.getText().toString();
            if ("chen".equals(names) && ("123".equals(userPasswrod))) {
                editor = sharedPreferences.edit();
                editor.putString("names", names);
                editor.commit();
            }
        });
        //注册
        this.btnRegister.setOnClickListener(x->{
            startActivity(new Intent(LoginActivity.this,Register.class));
            finish();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
