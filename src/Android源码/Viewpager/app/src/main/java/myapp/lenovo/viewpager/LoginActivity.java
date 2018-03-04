package myapp.lenovo.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity {
    private EditText curAccount;
    private EditText curPassword;
    private Button entry;
    private TextView register;
    private TextView forgetPassword;
    private TextView phoneEntry;
    private ImageButton qqLogin;
    private ImageButton weiboLogin;
    private IUiListener iUiListener;

    public static final String qqAppId ="1105782685";
    public static final String qqAppKey="mhA6fkRdBN5K5PW0";
    public static Tencent tencent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("create","haha");

        initBmob();
        //autoLoginCacheUser();

        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView(){
        curAccount= (EditText) findViewById(R.id.account_et);
        curPassword= (EditText) findViewById(R.id.password_et);
        entry= (Button) findViewById(R.id.entry_btn);
        forgetPassword= (TextView) findViewById(R.id.forget_password_tv);
        register= (TextView) findViewById(R.id.register_tv);
        phoneEntry= (TextView) findViewById(R.id.phone_entry_tv);
        phoneEntry.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        phoneEntry.getPaint().setAntiAlias(true);
        qqLogin= (ImageButton) findViewById(R.id.qq_logo_iv);
        weiboLogin= (ImageButton) findViewById(R.id.weibo_logo_iv);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=curAccount.getText().toString();
                String password=curPassword.getText().toString();
                loginBmobUserWithEmail(data,password);
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        phoneEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,PhoneLoginActivity.class);
                startActivity(intent);
            }
        });
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bmobThirdLoginByQQ();
            }
        });
        weiboLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void initBmob(){
        Bmob.initialize(LoginActivity.this,"167be2b330d3485eaad70348455b3853");
        BmobConfig bmobConfig=new BmobConfig.Builder(LoginActivity.this)
                .setApplicationId("167be2b330d3485eaad70348455b3853")
                .build();
        Bmob.initialize(bmobConfig);
    }

    public void loginBmobUserWithEmail(String data,String password){
        final MyUser bmobUser=new MyUser();
        bmobUser.setUsername(data);
        bmobUser.setPassword(password);
        bmobUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    Log.d("login success1",myUser.toString());
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.d("login fail0",e.toString());
                    Toast.makeText(LoginActivity.this,"登录失败，请重新输入",Toast.LENGTH_SHORT).show();
                    curAccount.setText("");
                    curPassword.setText("");
                }
            }
        });
    }

    public void autoLoginCacheUser(){
        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
        if(bmobUser!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void bmobThirdLoginByQQ(){
        iUiListener=new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (o != null) {
                    JSONObject jsonObject = (JSONObject) o;
                    try {
                        String accessToken = jsonObject.getString(com.tencent.
                                connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.
                                connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.
                                connect.common.Constants.PARAM_OPEN_ID);
                        Log.d("accessToken", accessToken);
                        Log.d("expires", expires);
                        Log.d("openId", openId);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.
                                BmobThirdUserAuth.SNS_TYPE_QQ, accessToken, expires, openId);
                        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {

                            @Override
                            public void done(JSONObject jsonObject, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(LoginActivity.this, "QQ登录成功",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("login", "qq login success");
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "QQ登录失败",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("login", "qq login fail");
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(UiError uiError) {
                Toast.makeText(LoginActivity.this,"QQ登录出错"+uiError.errorCode+uiError.errorDetail,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,"取消QQ绑定",Toast.LENGTH_SHORT).show();
            }
        };

        if(tencent==null){
            tencent=Tencent.createInstance(qqAppId,getApplicationContext());
        }
        tencent.logout(LoginActivity.this);
        tencent.login(LoginActivity.this,"all", iUiListener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == 65) {
                tencent.handleLoginData(data, iUiListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

