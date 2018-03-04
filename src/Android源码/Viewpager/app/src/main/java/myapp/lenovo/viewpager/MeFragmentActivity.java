package myapp.lenovo.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class MeFragmentActivity extends Activity {
    private ExpandableListView elv;
    private MeBaseExpandableListAdapter ela;
    private List<Boolean> userInfo;
    private Integer emailStatus;
    private Integer phoneStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_fragment);

        setSettingExpandableListView();
    }

    public void setSettingExpandableListView(){
        userInfo=new ArrayList<>();
        BmobUser bmobUser=BmobUser.getCurrentUser();
        userInfo.add(bmobUser.getEmail()!=null);
        userInfo.add(bmobUser.getEmailVerified());
        userInfo.add(bmobUser.getMobilePhoneNumber()!=null);
        userInfo.add(bmobUser.getMobilePhoneNumberVerified());
        System.out.println(userInfo.get(0));
        System.out.println(userInfo.get(1));
        System.out.println(userInfo.get(2));
        System.out.println(userInfo.get(3));

        elv= (ExpandableListView) findViewById(R.id.setting_lv);
        ela=new MeBaseExpandableListAdapter(MeFragmentActivity.this,userInfo);
        elv.setAdapter(ela);
        ela.notifyDataSetChanged();
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if(i==1&&i1==0) {
                    Intent intent = new Intent(MeFragmentActivity.this, UpdatePasswordActivity.class);
                    intent.putExtra("openWay", i1);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
