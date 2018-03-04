package myapp.lenovo.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Lenovo on 2016/11/25.
 */

public class MeBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] settingName = new String[]{"修改密码", "忘记密码", "手机或邮箱验证", "绑定第三方账号"};
    private String[] elseLogin = new String[]{"绑定QQ", "绑定微博"};
    private List<Boolean> userInfo;

    public MeBaseExpandableListAdapter(Context context, List<Boolean> userInfo) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.userInfo=userInfo;
    }

    @Override
    public int getGroupCount() {
        return 4;
    }

    @Override
    public int getChildrenCount(int i) {
        if (i==0){
            return 1;
        }
        else if (i==1) {
            if (userInfo.get(0) && userInfo.get(2)) {
                return 2;
            } else {
                return 1;
            }
        }
        else if (i==2) {
            return 2;
        } else {
            return 2;
        }
    }

    @Override
    public Object getGroup(int i) {
        return settingName[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return i1;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.group_item_setting, null);
        }
        TextView groupName= (TextView) view.findViewById(R.id.group_name_tv);
        ImageView pullSetting = (ImageView) view.findViewById(R.id.pull_setting_iv);
        groupName.setText(settingName[i]);
        pullSetting.setImageResource(R.drawable.pull_down_setting);
        if (b) {
            pullSetting.setImageResource(R.drawable.pull_up_setting);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        switch (i) {
            case 0:
                view = inflater.inflate(R.layout.reset_password, null);
                TextView rp = (TextView) view.findViewById(R.id.reset_password_tv);
                ImageView rr = (ImageView) view.findViewById(R.id.right_reset_iv);
                rp.setText("通过原密码修改密码");
                rr.setImageResource(R.drawable.to_right_setting);
                break;
            case 1:
                view = inflater.inflate(R.layout.find_password, null);
                TextView fp = (TextView) view.findViewById(R.id.find_password_tv);
                TextView fv = (TextView) view.findViewById(R.id.find_verify_tv);
                ImageView fr = (ImageView) view.findViewById(R.id.right_find_iv);
                if (userInfo.get(0) && !userInfo.get(2)) {
                    if(i1==0) {
                        fp.setText("通过邮箱找回密码");
                        fv.setVisibility(View.GONE);
                        fr.setVisibility(View.VISIBLE);
                        if (!userInfo.get(1)) {
                            fv.setText("未验证");
                            fv.setVisibility(View.VISIBLE);
                            fr.setVisibility(View.GONE);
                        }
                    }
                } else if (!userInfo.get(0) && userInfo.get(2)) {
                    if(i1==0) {
                        fp.setText("通过手机找回密码");
                        fv.setVisibility(View.GONE);
                        fr.setVisibility(View.VISIBLE);
                        if (!userInfo.get(3)) {
                            fv.setText("未验证");
                            fv.setVisibility(View.VISIBLE);
                            fr.setVisibility(View.GONE);
                        }
                    }
                } else if (userInfo.get(0) && userInfo.get(2)) {
                    if (i1 == 0) {
                        fp.setText("通过邮箱找回密码");
                        fv.setVisibility(View.GONE);
                        fr.setVisibility(View.VISIBLE);
                        if(!userInfo.get(1)){
                            fv.setText("未验证");
                            fv.setVisibility(View.VISIBLE);
                            fr.setVisibility(View.GONE);
                        }
                    }
                    if (i1 == 1) {
                        fp.setText("通过手机找回密码");
                        fv.setVisibility(View.GONE);
                        fr.setVisibility(View.VISIBLE);
                        if(!userInfo.get(3)){
                            fv.setText("未验证");
                            fv.setVisibility(View.VISIBLE);
                            fr.setVisibility(View.GONE);
                        }
                    }
                } else if (!userInfo.get(0) && !userInfo.get(2)) {
                    if(i1==0){
                        fp.setText("暂未添加和验证邮箱和手机，请前往设置");
                        fv.setVisibility(View.GONE);
                        fr.setVisibility(View.GONE);
                    }
                }
                break;
            case 2:
                view = inflater.inflate(R.layout.bind, null);
                ImageView bp = (ImageView) view.findViewById(R.id.bind_iv);
                TextView bw = (TextView) view.findViewById(R.id.bind_tv);
                TextView bd= (TextView) view.findViewById(R.id.bound_tv);
                ImageView br= (ImageView) view.findViewById(R.id.right_bind_iv);
                if(i1==0){
                    bp.setImageResource(R.drawable.email);
                    if(userInfo.get(0)){
                        BmobUser bmobUser=BmobUser.getCurrentUser();
                        String email=bmobUser.getEmail();
                        String emailP=email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
                        bw.setText(emailP);
                        if(userInfo.get(1)){
                            bd.setText("已验证");
                            bd.setVisibility(View.VISIBLE);
                            br.setVisibility(View.GONE);
                        }
                        else {
                            bd.setVisibility(View.GONE);
                            br.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        bw.setText("未添加");
                        bd.setVisibility(View.GONE);
                        br.setVisibility(View.VISIBLE);
                    }
                }
                if(i1==1){
                    bp.setImageResource(R.drawable.phone);
                    if(userInfo.get(2)){
                        BmobUser bmobUser=BmobUser.getCurrentUser();
                        String phone=bmobUser.getMobilePhoneNumber();
                        String phoneP=phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                        bw.setText(phoneP);
                        if(userInfo.get(3)){
                            bd.setText("已验证");
                            bd.setVisibility(View.VISIBLE);
                            br.setVisibility(View.GONE);
                        }
                        else {
                            bd.setVisibility(View.GONE);
                            br.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        bw.setText("未添加");
                        bd.setVisibility(View.GONE);
                        br.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 3:
                view = inflater.inflate(R.layout.else_login, null);
                ImageView logo= (ImageView) view.findViewById(R.id.logo_iv);
                TextView el = (TextView) view.findViewById(R.id.else_login_tv);
                if (i1==0){
                    logo.setImageResource(R.drawable.qq2);
                }
                else if(i1==1){
                    logo.setImageResource(R.drawable.weibo2);
                }
                el.setText(elseLogin[i1]);
                break;
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
