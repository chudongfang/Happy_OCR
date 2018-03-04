package myapp.lenovo.viewpager;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by Lenovo on 2016/12/10.
 */

public class MyUser extends BmobUser{
    private List<String> groupName;

    public List<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }
}
