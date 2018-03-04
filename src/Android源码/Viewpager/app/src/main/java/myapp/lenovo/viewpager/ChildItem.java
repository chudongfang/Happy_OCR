package myapp.lenovo.viewpager;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Lenovo on 2016/12/10.
 */

public class ChildItem extends BmobObject {
    private BmobFile picture;
    private String word;
    private MyUser user;
    private Integer num;

    public BmobFile getPicture() {
        return picture;
    }

    public String getWord() {
        return word;
    }

    public MyUser getUser() {
        return user;
    }

    public Integer getNum() {
        return num;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
