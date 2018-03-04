package myapp.lenovo.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class UpdatePasswordActivity extends Activity {
    private EditText firstWord;
    private EditText secondWord;
    private EditText thirdWord;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private Button requestPhoneSMS;
    private LinearLayout secondLayout;
    private LinearLayout thirdLayout;
    private ImageButton updateBack;
    private TextView updateExit;
    private Button confirm;
    private TextView title;

    private CountDownTimer countDownTimer;
    private Intent intentLogin;
    private int openWay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        openWay=UpdatePasswordActivity.this.getIntent().getIntExtra("openWay",0);
        intentLogin=new Intent(UpdatePasswordActivity.this,LoginActivity.class);
        intentLogin.putExtra("fromUpdate",true);

        initView();

        if(openWay==4){
            thirdLayout.setVisibility(View.GONE);
            secondLayout.setVisibility(View.VISIBLE);
            requestPhoneSMS.setVisibility(View.GONE);
            requestPhoneSMS.setVisibility(View.VISIBLE);
            firstWord.setHint("手机");
            secondWord.setHint("验证码");
            firstImage.setImageResource(R.drawable.phone);
            secondImage.setImageResource(R.drawable.phone_sms);
            title.setText("验证手机");
        }
        else if(openWay==3){
            thirdLayout.setVisibility(View.GONE);
            secondLayout.setVisibility(View.GONE);
            requestPhoneSMS.setVisibility(View.GONE);
            firstWord.setHint("邮箱");
            firstImage.setImageResource(R.drawable.email);
            title.setText("验证邮箱");
        }
        else if(openWay==2){
            thirdLayout.setVisibility(View.VISIBLE);
            firstImage.setImageResource(R.drawable.phone);
            secondImage.setImageResource(R.drawable.phone_sms);
            thirdImage.setImageResource(R.drawable.password);
            firstWord.setHint("手机");
            secondWord.setHint("验证码");
            thirdWord.setHint("新密码");
            thirdWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            title.setText("找回密码");
        }
        else if(openWay==1){
            thirdLayout.setVisibility(View.GONE);
            secondLayout.setVisibility(View.GONE);
            requestPhoneSMS.setVisibility(View.GONE);
            firstWord.setHint("邮箱");
            firstImage.setImageResource(R.drawable.email);
            title.setText("找回密码");
        }
        else if(openWay==0){
            thirdLayout.setVisibility(View.GONE);
            secondLayout.setVisibility(View.VISIBLE);
            requestPhoneSMS.setVisibility(View.GONE);
            firstWord.setHint("旧密码");
            secondWord.setHint("新密码");
            firstImage.setImageResource(R.drawable.old_password);
            secondImage.setImageResource(R.drawable.new_password);
            firstWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            secondWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            title.setText("修改密码");
        }
    }

    public void initView(){
        firstWord= (EditText) findViewById(R.id.first_et);
        secondWord= (EditText) findViewById(R.id.second_et);
        thirdWord= (EditText) findViewById(R.id.third_et);
        firstImage= (ImageView) findViewById(R.id.first_iv);
        secondImage= (ImageView) findViewById(R.id.second_iv);
        thirdImage= (ImageView) findViewById(R.id.third_iv);
        requestPhoneSMS= (Button) findViewById(R.id.request_sms_btn);
        secondLayout= (LinearLayout) findViewById(R.id.second_layout);
        thirdLayout= (LinearLayout) findViewById(R.id.third_layout);
        updateBack= (ImageButton) findViewById(R.id.update_password_back_ib);
        updateExit= (TextView) findViewById(R.id.exit_tv);
        confirm= (Button) findViewById(R.id.confirm_btn);
        title= (TextView) findViewById(R.id.title_tv);
        updateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updateExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        requestPhoneSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=firstWord.getText().toString();
                requestSMS(phone);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (openWay==4){
                    String phone = firstWord.getText().toString();
                    String phoneSMS = secondWord.getText().toString();
                    verifyPhone(phone,phoneSMS);
                }
                else if(openWay==3){
                    String email=firstWord.getText().toString();
                    verifyEmail(email);
                }
                else if (openWay == 2) {
                    String phoneSMS = secondWord.getText().toString();
                    String password = thirdWord.getText().toString();
                    if (password.length() < 6) {
                        Toast.makeText(UpdatePasswordActivity.this, "密码不能少于六位，请重新输入", Toast.LENGTH_SHORT).show();
                        firstWord.setText(null);
                        secondWord.setText(null);
                        thirdWord.setText(null);
                    }
                    updatePasswordByPhone(phoneSMS, password);
                }
                else if(openWay==1){
                    String email=firstWord.getText().toString();
                    updatePasswordByEmail(email);
                }
                else if(openWay==0){
                    String oldPassword=firstWord.getText().toString();
                    String newPassword=secondWord.getText().toString();
                    updatePasswordByOldPassword(oldPassword,newPassword);
                }
            }
        });
    }

    public void requestSMS(String phone){
        BmobSMS.requestSMSCode(phone, "验证码", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e==null){
                    Log.d("SMS success",integer+"");
                    Toast.makeText(UpdatePasswordActivity.this,"验证码发送成功，请尽快使用",Toast.LENGTH_SHORT).show();
                    requestPhoneSMS.setClickable(false);
                    requestPhoneSMS.setBackgroundColor(Color.LTGRAY);
                    countDownTimer=new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long l) {
                            requestPhoneSMS.setText(l/1000+"秒");
                            requestPhoneSMS.setPadding(3,3,3,3);
                        }

                        @Override
                        public void onFinish() {
                            requestPhoneSMS.setClickable(true);
                            requestPhoneSMS.setBackgroundResource(R.drawable.phone_sms_background);
                            requestPhoneSMS.setText("重新发送");
                        }
                    }.start();
                }
                else{
                    Log.d("SMS fail",e.getMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updatePasswordByPhone(String phoneSMS,String password){
        BmobUser.resetPasswordBySMSCode(phoneSMS, password, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("reset success", "密码重置成功");
                    Toast.makeText(UpdatePasswordActivity.this,"密码重置成功",Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                    finish();
                }
                else{
                    Log.i("reset fail", "密码重置失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"密码重置失败，请重新输入",Toast.LENGTH_SHORT).show();
                    firstWord.setText("");
                    secondWord.setText("");
                    thirdWord.setText("");
                }
            }
        });
    }

    public void updatePasswordByEmail(String email){
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("reset success","密码重置成功");
                    Toast.makeText(UpdatePasswordActivity.this,
                            "找回密码邮件发送成功，请到邮箱中重置密码后重新登录",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("reset fail","密码重置失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"找回密码邮件发送失败",Toast.LENGTH_SHORT).show();
                    firstWord.setText("");
                }
            }
        });
    }

    public void updatePasswordByOldPassword(String oldPassword,String newPassword){
        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("reset success","密码重置成功");
                    Toast.makeText(UpdatePasswordActivity.this,"密码重置成功",Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                    finish();
                }
                else{
                    Log.d("reset fail","密码重置失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"密码重置失败，请重新输入",Toast.LENGTH_SHORT).show();
                    firstWord.setText("");
                    secondWord.setText("");
                }
            }
        });
    }

    public void verifyEmail(String email){
        MyUser bmobUser=new MyUser();
        bmobUser.setEmail(email);
        MyUser currentUser=BmobUser.getCurrentUser(MyUser.class);
        bmobUser.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("update success","haha");
                }else{
                    Log.d("update fail", "code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                }
            }
        });
        BmobUser.requestEmailVerify(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(UpdatePasswordActivity.this,"请求验证邮件成功，请到邮箱中进行激活",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("verify fail", "密码重置失败：code ="+e.getErrorCode()+",msg = "+e.getLocalizedMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"请求验证邮件失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verifyPhone(final String phone, String phoneSMS) {
        BmobSMS.verifySmsCode(phone, phoneSMS, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("verify success", "haha");
                    MyUser bmobUser=new MyUser();
                    bmobUser.setMobilePhoneNumber(phone);
                    bmobUser.setMobilePhoneNumberVerified(true);
                    MyUser currentUser=BmobUser.getCurrentUser(MyUser.class);
                    bmobUser.update(currentUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(UpdatePasswordActivity.this,"手机号码绑定成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(UpdatePasswordActivity.this,"手机号码绑定失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.d("verify fail", e.getMessage());
                    Toast.makeText(UpdatePasswordActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
}
