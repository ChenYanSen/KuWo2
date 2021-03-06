package com.designers.kuwo.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.designers.kuwo.R;
import com.designers.kuwo.biz.UserBiz;
import com.designers.kuwo.biz.bizimpl.UserBizImpl;
import com.designers.kuwo.eneity.User;
import com.designers.kuwo.utils.CircularImage;
import com.designers.kuwo.utils.StatusBarCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class PersonalInforSetActivity extends Activity {
    private TableRow personal_nike, personal_signature, hobby_tr, personal_sex, personal_area;
    private TextView personal_nike_name_edit, user_nickName_tv;
    private TextView personal_name_signature_edit, signature_tv;
    private TextView personal_hobby_edit;
    private TextView personal_sex_set;
    private TextView personal_area_edit;
    private CircularImage avatar_image;
    private int sexindex = 0;
    private TextView mProvinceTextView, mDistinguishTextView, mCityTextView;
    private ImageView back;
    //头像路径
    private Uri avatarUri;
    //省市级联
    private ArrayList<String> mProvinceList;
    private ArrayList<String> mCityList;
    private ArrayList<String> mDistinguishList;
    private JSONObject mJsonObject = null;
    private int mProvinceId = 0;// 省id
    private int mCityId = 0;// 市id
    private int mDistinguishId = 0;// 区id
    boolean city = false, distinguish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infor_set);
        this.mProvinceTextView = (TextView) findViewById(R.id.mProvinceTextView);
        this.mDistinguishTextView = (TextView) findViewById(R.id.mDistinguishTextView);
        this.mCityTextView = (TextView) findViewById(R.id.mCityTextView);
        this.avatar_image= (CircularImage) findViewById(R.id.avatar_image);
        this.back= (ImageView) findViewById(R.id.back);
        //圆头像
        Bitmap bitMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.login_image);
        this.avatar_image.setImageBitmap(bitMap);
       // this.avatar_image.setImageResource(R.drawable.login_image);
        this.personal_name_signature_edit = (TextView) findViewById(R.id.personal_name_signature_edit);
        this.signature_tv = (TextView) findViewById(R.id.signature_tv);
        this.personal_nike_name_edit = (TextView) findViewById(R.id.personal_nike_name_edit);
        this.user_nickName_tv = (TextView) findViewById(R.id.user_nickName_tv);
        this.personal_signature = (TableRow) findViewById(R.id.personal_signature);
        this.personal_nike = (TableRow) findViewById(R.id.personal_nike);
        this.hobby_tr = (TableRow) findViewById(R.id.hobby_tr);
        this.personal_hobby_edit = (TextView) findViewById(R.id.personal_hobby_edit);
        this.personal_sex = (TableRow) findViewById(R.id.personal_sex);
        this.personal_sex_set = (TextView) findViewById(R.id.personal_sex_edit);
        //昵称编辑点击事件
        this.personal_nike.setOnClickListener(x -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(PersonalInforSetActivity.this).inflate(R.layout.activity_personal_nake_set, null);
            final EditText personal_nake_input = (EditText) view.findViewById(R.id.personal_nake_input);
            final TextView personal_title_set = (TextView) view.findViewById(R.id.personal_title_set);
            personal_title_set.setText("昵称");

            builder.setView(view);
            builder.setPositiveButton("确定", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String str = personal_nake_input.getText().toString().trim();
                    personal_nike_name_edit.setText(str);
                    user_nickName_tv.setText(str);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();


        });
        //个性签名点击事件
        this.personal_signature.setOnClickListener(x -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(PersonalInforSetActivity.this).inflate(R.layout.activity_personal_nake_set, null);
            final EditText personal_nake_input = (EditText) view.findViewById(R.id.personal_nake_input);
            final TextView personal_title_set = (TextView) view.findViewById(R.id.personal_title_set);
            personal_title_set.setText("个性签名");
            builder.setView(view);
            builder.setPositiveButton("确定", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String str = personal_nake_input.getText().toString().trim();
                    personal_name_signature_edit.setText(str);
                    signature_tv.setText(str);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        });
        //个人爱好点击事件
        this.hobby_tr.setOnClickListener(x -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("爱好");
            final String[] hobbys = getResources().getStringArray(R.array.hobby);
            final StringBuilder sb = new StringBuilder();
            final boolean[] checkedItem = new boolean[hobbys.length];

            builder.setMultiChoiceItems(R.array.hobby, checkedItem, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkedItem[which] = isChecked;
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < hobbys.length; i++) {
                        if (checkedItem[i]) {
                            sb.append(hobbys[i] + " ");
                        }
                    }
                    personal_hobby_edit.setText(sb.toString());
                    dialog.dismiss();
                }

            }).show();
        });
        //性别点击事件
        this.personal_sex.setOnClickListener(x -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("性别");
            final String[] hobbys = getResources().getStringArray(R.array.hobby);
            final StringBuilder sb = new StringBuilder();
            final boolean[] checkedItem = new boolean[hobbys.length];
            final String[] sex = new String[]{"男", "女", "保密"};

            builder.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sexindex = which;
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    personal_sex_set.setText(sex[sexindex]);
                    dialog.dismiss();
                }
            }).show();
        });
        //选择地区点击事件
        //选择省
        this.mProvinceTextView.setOnClickListener(x -> {
            DialogShow(getProvinceData(), 1, "选择省");
            city = true;
            distinguish = true;
        });
        //选择市
        this.mCityTextView.setOnClickListener(x -> {
            if (city) {
                DialogShow(mCityList, 2, "选择市");
            }
        });
//选择地区
        this.mDistinguishTextView.setOnClickListener(x -> {
            if (distinguish) {
                DialogShow(mDistinguishList, 3, "选择地区");
            }
        });

        this.back.setOnClickListener(x -> {
            //往数据库中添加个人信息
            User user=new User();
            user.setNickname(personal_nike_name_edit.getText().toString().trim());
           // user.setAvatarUri();
            UserBiz userBiz=new UserBizImpl();

            startActivity(new Intent(PersonalInforSetActivity.this, MainActivity.class));
        });
        this.avatar_image.setOnClickListener(x->{
            selectedSDImage();
        });
        StatusBarCompat.compat(this);
    }
    //数据装配省市
    /* 显示省市区的dialog */
    public void DialogShow(final ArrayList<String> mList, final int type, String str) {
        ActionSheetDialog dialog = new ActionSheetDialog(PersonalInforSetActivity.this)
                .builder().setTitle(str).setCancelable(false)
                .setCanceledOnTouchOutside(false);
        for (int i = 0; i < mList.size(); i++) {
            final String item = mList.get(i);
            final int position = i;
            dialog.addSheetItem(mList.get(i), ActionSheetDialog.SheetItemColor.Blue,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            if (type == 1) {
                                mProvinceTextView.setText(item);
                                mProvinceId = position;
                                mCityList = getCityData();
                                mCityTextView.setText(mCityList.get(0));
                                mDistinguishList = getDistinguish();
                                mDistinguishTextView.setText(mDistinguishList.get(0));
                                mCityTextView.setEnabled(true);
                                mDistinguishTextView.setEnabled(true);
                            } else if (type == 2) {
                                mCityTextView.setText(item);
                                mCityId = position;
                                mDistinguishList = getDistinguish();
                                mDistinguishTextView.setText(mDistinguishList
                                        .get(0));
                            } else {
                                mDistinguishId = position;
                                mDistinguishTextView.setText(item);
                            }
                        }
                    });
        }
        dialog.show();

    }
    //---------------
    /* 得到省 */
    private ArrayList<String> getProvinceData() {
        // TODO Auto-generated method stub
        InputStream inputStream = getResources()
                .openRawResource(R.raw.location);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();

        try {
            mJsonObject = new JSONObject(str);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ArrayList<String> mList = new ArrayList<String>();
        for (int i = 0; i < mJsonObject.length(); i++) {
            try {
                JSONObject provinceObject = mJsonObject.getJSONObject((i + 1)
                        + "");
                mList.add(provinceObject.getString("province_name"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mList;
    }
    /* 得到市 */
    private ArrayList<String> getCityData() {
        JSONObject cityObject = null;
        try {
            cityObject = mJsonObject.getJSONObject((mProvinceId + 1) + "")
                    .getJSONObject("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> mList = new ArrayList<String>();
        for (int i = 0; i < cityObject.length(); i++) {
            try {
                JSONObject cityJSONObject = cityObject.getJSONObject((i + 1)
                        + "");
                mList.add(cityJSONObject.getString("city_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mList;
    }
    /* 得到区 */
    public ArrayList<String> getDistinguish() {
        JSONObject areaObject = null;
        try {
            areaObject = mJsonObject.getJSONObject((mProvinceId + 1) + "")
                    .getJSONObject("city").getJSONObject((mCityId + 1) + "")
                    .getJSONObject("area");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> mList = new ArrayList<String>();
        for (int i = 0; i < areaObject.length(); i++) {
            try {
                String cityString = areaObject.getString((i + 1) + "");
                mList.add(cityString.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mList;
    }




    /**
     * 自定义方法：使用Intent调用系统提供的相册功能，使用startActivityForResult获取用户选择的照片图片
     * */
    private void selectedSDImage() {
        Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        intentAlbum.setType("image/*");
        startActivityForResult(intentAlbum, 200);
    }

    // 从写onActivityResult系统方法，获取读取到的图片数据，并绑定到ImageView组件上
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver cenContentResolver = getContentResolver();
        Bitmap bitMap = null;
        // 判断用于接受的Activity
        if (requestCode == 200) {
            avatarUri = data.getData();// 获取图片uri
            try {
                bitMap = MediaStore.Images.Media.getBitmap(cenContentResolver,
                        avatarUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.avatar_image.setImageBitmap(bitMap);
        }
    }
}
