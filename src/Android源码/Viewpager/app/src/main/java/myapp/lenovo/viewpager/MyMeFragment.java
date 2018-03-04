package myapp.lenovo.viewpager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class MyMeFragment extends Fragment {
    private ExpandableListView elv;
    private Button logout;
    private MeBaseExpandableListAdapter ela;
    private List<Boolean> userInfo;
    private MyUser bmobUser;

    public static final String qqAppId ="1105782685";
    public static final String qqAppKey="mhA6fkRdBN5K5PW0";
    private IUiListener iUiListener;
    private Tencent tencent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_me, container, false);
        setSettingExpandableListView(view);
        logout= (Button) view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    public void setSettingExpandableListView(View view){
        userInfo=new ArrayList<>();
        bmobUser=BmobUser.getCurrentUser(MyUser.class);
        userInfo.add(bmobUser.getEmail()!=null);
        userInfo.add(bmobUser.getEmailVerified());
        userInfo.add(bmobUser.getMobilePhoneNumber()!=null);
        userInfo.add(bmobUser.getMobilePhoneNumberVerified());
        System.out.println(userInfo.get(0));
        System.out.println(userInfo.get(1));
        System.out.println(userInfo.get(2));
        System.out.println(userInfo.get(3));

        elv= (ExpandableListView) view.findViewById(R.id.setting_lv);
        ela=new MeBaseExpandableListAdapter(getActivity(),userInfo);
        elv.setAdapter(ela);
        ela.notifyDataSetChanged();
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if(i==0&&i1==0) {
                    Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                    intent.putExtra("openWay", 0);
                    startActivity(intent);
                }
                if(i==2&&i1==0){
                    if(userInfo.get(0)&&userInfo.get(1)){
                    }
                    else {
                        Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                        intent.putExtra("openWay", 3);
                        startActivity(intent);
                        //userInfo.set(0,bmobUser.getEmail()!=null);
                        //userInfo.set(1,bmobUser.getEmailVerified());
                        //ela.notifyDataSetChanged();
                    }
                }
                else if(i==2&&i1==1){
                    if(userInfo.get(2)&&userInfo.get(3)){
                    }
                    else {
                        Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                        intent.putExtra("openWay", 4);
                        startActivity(intent);
                    }
                }
                if(i==3&&i1==0){
                    bmobThirdLoginByQQ();
                }
                else if (i==3&&i1==1){

                }
                if (userInfo.get(0) && !userInfo.get(2)) {
                    if(i==1&&i1==0) {
                        if (userInfo.get(1)) {
                            Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                            intent.putExtra("openWay", 1);
                            startActivity(intent);
                        }
                    }
                } else if (!userInfo.get(0) && userInfo.get(2)) {
                    if(i==1&&i1==0) {
                        if (userInfo.get(3)) {
                            Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                            intent.putExtra("openWay", 2);
                            startActivity(intent);
                        }
                    }
                } else if (userInfo.get(0) && userInfo.get(2)) {
                    if (i==1&&i1 == 0) {
                        if(userInfo.get(1)){
                            Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                            intent.putExtra("openWay", 1);
                            startActivity(intent);
                        }
                    }
                    if (i==1&&i1 == 1) {
                        if(userInfo.get(3)){
                            Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
                            intent.putExtra("openWay", 2);
                            startActivity(intent);
                        }
                    }
                }
                return false;
            }
        });
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
                        BmobUser.associateWithAuthData(authInfo, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "QQ绑定成功",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d("login", "qq login success");
                                } else {
                                    Toast.makeText(getActivity(), "QQ绑定成功",
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
                Toast.makeText(getActivity(),"QQ绑定出错"+uiError.errorCode+uiError.errorDetail,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(),"取消QQ绑定",Toast.LENGTH_SHORT).show();
            }
        };

        if(tencent==null){
            tencent= Tencent.createInstance(qqAppId,getActivity().getApplicationContext());
        }
        tencent.logout(getActivity());
        tencent.login(getActivity(),"all", iUiListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);

        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == 65) {
                tencent.handleLoginData(data, iUiListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
