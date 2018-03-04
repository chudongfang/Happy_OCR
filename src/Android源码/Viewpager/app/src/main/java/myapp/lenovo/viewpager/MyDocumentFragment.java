package myapp.lenovo.viewpager;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDocumentFragment extends Fragment {

    private static List<String> groupName;
    private static Map<String, List<String>> childName;
    private static Map<String,List<Uri>> childDrawable;
    private static MyBaseExpandableListAdapter ela;
    private ExpandableListView elv;
    private MyDialogAddEdit myDialogAE;
    private MyDialogChoose myDialogC;
    private MyDialogDelete myDialogD;
    private MyDialogRecognize myDialogR;
    private EditText contentEditText;
    private ImageView ag;
    private List<Boolean> isEditing;
    private boolean changeMenuItem;
    private String content;
    private ImageView editChoose;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeMenuItem=false;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_document, container, false);

        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
        if(bmobUser.getGroupName()==null){
            Log.d("bmob","first");
            initDataFirst();
            updateBmobGroupName();
        }
        else {
            Log.d("bmob","not first");
            initDataNotFirst();
        }

        editChoose= (ImageView) view.findViewById(R.id.edit_iv);
        editChoose.setImageResource(R.drawable.edit_selector);
        isEditing=new ArrayList<>();
        isEditing.add(0,false);
        ela = new MyBaseExpandableListAdapter(groupName, childName, childDrawable,getContext(),isEditing);
        elv = (ExpandableListView) view.findViewById(R.id.expandable_list_view);
        elv.setAdapter(ela);
        ela.notifyDataSetChanged();
        ag= (ImageView) view.findViewById(R.id.group_add_iv);
        setListener();
        return view;
    }

    private void initDataFirst() {

        groupName = new ArrayList<>();
        childName = new HashMap<>();
        childDrawable=new HashMap<>();

        groupName.add("我的文档");
        groupName.add("未归档");
        groupName.add("文件");
        groupName.add("备忘录");
        groupName.add("名片");
        groupName.add("证件");

        for(int i=0;i<6;i++){
            List<String> cn=new ArrayList<>();
            childName.put(groupName.get(i),cn);
        }

        for(int i=0;i<6;i++){
            List<Uri> cd=new ArrayList<>();
            childDrawable.put(groupName.get(i),cd);
        }

    }

    private void initDataNotFirst() {

        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);

        groupName = new ArrayList<>();
        childName = new HashMap<>();
        childDrawable=new HashMap<>();

        groupName=bmobUser.getGroupName();

        for(int i=0;i<groupName.size();i++){
            List<String> cn=new ArrayList<>();
            childName.put(groupName.get(i),cn);
        }

        for(int i=0;i<groupName.size();i++){
            List<Uri> cd=new ArrayList<>();
            childDrawable.put(groupName.get(i),cd);
        }

    }

    public void getOcrContent(Uri uri,String html){
        addChild(uri,html,1);
    }

    private void setListener() {

        editChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEditing.get(0)==false){
                    editChoose.setImageResource(R.drawable.save_selector);
                    isEditing.remove(0);
                    isEditing.add(0,true);
                    ela.notifyDataSetChanged();
                }
                else if(isEditing.get(0)==true) {
                    editChoose.setImageResource(R.drawable.edit_selector);
                    isEditing.remove(0);
                    isEditing.add(0,false);
                    ela.notifyDataSetChanged();
                }
            }
        });

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {

            }
        });

        elv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                if(ExpandableListView.getPackedPositionType(l)==ExpandableListView.PACKED_POSITION_TYPE_GROUP){
                    changeMenuItem=true;
                    getActivity().getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                    isEditing.remove(0);
                    isEditing.add(0,true);
                    ela.notifyDataSetChanged();
                    return true;
                }
                else if(ExpandableListView.getPackedPositionType(l)==ExpandableListView.PACKED_POSITION_TYPE_CHILD){
                }
                return false;
            }
        });

        ag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroupDialog("新增标签",null);
            }
        });
    }

    public void addGroup(final String newGroupName) {
        groupName.add(newGroupName);
        List<String> cn = new ArrayList<>();
        List<Uri> cd=new ArrayList<>();
        childName.put(newGroupName, cn);
        childDrawable.put(newGroupName,cd);
        ela.notifyDataSetChanged();
        updateBmobGroupName();
    }

    public void addChild(Uri uri, final String newChildName, final int i) {
        String gn = groupName.get(i);
        List<String> cn = childName.get(gn);
        List<Uri> cd=childDrawable.get(gn);
        cn.add(newChildName);
        cd.add(uri);
        String path=getImagePath(uri);
        addBmobChildName(path,newChildName,i);
        //ela.notifyDataSetChanged();
    }

    public static void addChild2(Uri uri, final String newChildName, final int i) {
        String gn = groupName.get(i);
        List<String> cn = childName.get(gn);
        List<Uri> cd=childDrawable.get(gn);
        cn.add(newChildName);
        cd.add(uri);
    }

    public static void deleteGroup(int i) {
        String gn = groupName.get(i);
        childName.remove(gn);
        childDrawable.remove(gn);
        groupName.remove(i);
        ela.notifyDataSetChanged();
        updateBmobGroupName();
    }

    public static void deleteChild(int i, int i1) {
        String gn = groupName.get(i);
        List<String> cn = childName.get(gn);
        List<Uri> cd=childDrawable.get(gn);
        cn.remove(i1);
        cd.remove(i1);
        ela.notifyDataSetChanged();
    }

    public static void editName(int i, int i1, String newName) {
        if (i1 < 0) {
            String gn = groupName.get(i);
            List<String> cn = childName.get(gn);
            List<Uri> cd=childDrawable.get(gn);
            childName.put(newName, cn);
            childDrawable.put(newName,cd);
            childName.remove(gn);
            childDrawable.remove(gn);
            groupName.set(i, newName);
            updateBmobGroupName();

        } else {
            String gn = groupName.get(i);
            List<String> cn = childName.get(gn);
            cn.set(i1, newName);
            childName.put(gn, cn);
            editBmobChildName(i,i1,newName);
        }
        ela.notifyDataSetChanged();
    }

    private String getImagePath(Uri uri) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static void addBmobChildName(final String path, final String newChildName, final int i){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MyUser currentUser=BmobUser.getCurrentUser(MyUser.class);
                final BmobFile bmobFile=new BmobFile(new File(path));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.d("net",bmobFile.getUrl());
                            Log.d("upload success","haha");
                            ChildItem childItem=new ChildItem();
                            childItem.setPicture(bmobFile);
                            childItem.setWord(newChildName);
                            childItem.setUser(currentUser);
                            childItem.setNum(i);
                            childItem.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Log.d("save success","haha");
                                    }else{
                                        Log.d("save fail","wuwu:"+e.getMessage());
                                    }
                                }
                            });
                        }
                        else {
                            Log.d("upload fail",e.getErrorCode()+"-------"+e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    public static void deleteBmobChildName(int i, final int i1){
        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<ChildItem> query=new BmobQuery<ChildItem>();
        query.addWhereEqualTo("user",bmobUser);
        query.addWhereEqualTo("num",i);
        query.order("createdAt");
        query.findObjects(new FindListener<ChildItem>() {
            @Override
            public void done(final List<ChildItem> list, BmobException e) {
                if(e==null){
                    Log.d("find success","haha");
                    ChildItem ci=list.get(i1);
                    ChildItem deleteChild=new ChildItem();
                    deleteChild.setObjectId(ci.getObjectId());
                    deleteChild.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.d("delete success","haha");
                            }
                            else {
                                Log.d("delete fail",e.getMessage());
                            }
                        }
                    });
                }
                else {
                    Log.d("find fail",e.getMessage());
                }
            }
        });
    }

    public static void editBmobChildName(int i, final int i1, final String newName){
        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<ChildItem> query=new BmobQuery<ChildItem>();
        query.addWhereEqualTo("user",bmobUser);
        query.addWhereEqualTo("num",i);
        query.order("createdAt");
        query.findObjects(new FindListener<ChildItem>() {
            @Override
            public void done(final List<ChildItem> list, BmobException e) {
                if(e==null){
                    Log.d("find success","haha");
                    ChildItem ci=list.get(i1);
                    ChildItem editChild=new ChildItem();
                    editChild.setWord(newName);
                    editChild.update(ci.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.d("edit success","haha");
                            }
                            else {
                                Log.d("edit fail",e.getMessage());
                            }
                        }
                    });
                }
                else {
                    Log.d("find fail",e.getMessage());
                }
            }
        });
    }

    public static void moveBmobChildName(int i, final int i1, final int iS){
        MyUser bmobUser=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<ChildItem> query=new BmobQuery<ChildItem>();
        query.addWhereEqualTo("user",bmobUser);
        query.addWhereEqualTo("num",i);
        query.order("createdAt");
        query.findObjects(new FindListener<ChildItem>() {
            @Override
            public void done(final List<ChildItem> list, BmobException e) {
                if(e==null){
                    Log.d("find success","haha");
                    ChildItem ci=list.get(i1);
                    ChildItem moveChild=new ChildItem();
                    moveChild.setNum(iS);
                    moveChild.update(ci.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.d("move success","haha");
                            }
                            else {
                                Log.d("move fail",e.getMessage());
                            }
                        }
                    });
                }
                else {
                    Log.d("find fail",e.getMessage());
                }
            }
        });
    }

    public static void updateBmobGroupName(){
        MyUser newUser=new MyUser();
        newUser.setGroupName(groupName);
        MyUser currentUser= BmobUser.getCurrentUser(MyUser.class);
        newUser.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("edit success","haha");
                }
                else {
                    Log.d("edit fail",e.getMessage());
                }
            }
        });
    }

    public void addGroupDialog(String title,String content) {
        myDialogAE=new MyDialogAddEdit(getContext(),title,null);
        contentEditText=myDialogAE.getContentEditText();
        myDialogAE.setOnClickCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newContent=contentEditText.getText().toString();
                if(!TextUtils.isEmpty(contentEditText.getText())){
                    addGroup(newContent);
                    myDialogAE.dismiss();
                }
            }
        });
        myDialogAE.setOnClickCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogAE.dismiss();
            }
        });
        myDialogAE.show();
    }

    public void recognizeDialog(){
        myDialogR=new MyDialogRecognize(getActivity(),content);
        myDialogR.setOnClickExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogR.dismiss();
            }
        });
        myDialogR.setOnClickEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dialogContent=myDialogR.getDialogContent();
                Button dialogEdit=myDialogR.getDialogEdit();
                String s = dialogEdit.getText().toString().trim();
                if (s.equals("编辑")) {
                    dialogContent.setFocusable(true);
                    dialogContent.setFocusableInTouchMode(true);
                    dialogContent.requestFocus();
                    dialogEdit.setText("保存");
                } else if (s.equals("保存")) {
                    content = dialogContent.getText().toString().trim();
                    Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        myDialogR.show();
        Window window = myDialogR.getWindow();
        window.setWindowAnimations(R.style.PopBottomAnim);
        window.setBackgroundDrawableResource(android.R.color.white);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = 0;
        window.setAttributes(wlp);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(changeMenuItem==true){
            MenuItem item=menu.findItem(R.id.edit_btn);
            item.setIcon(R.drawable.save_selector);
            changeMenuItem=false;
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.edit_btn:
                if(isEditing.get(0)==false){
                    item.setIcon(R.drawable.save_selector);
                    isEditing.remove(0);
                    isEditing.add(0,true);
                    ela.notifyDataSetChanged();
                }
                else if(isEditing.get(0)==true) {
                    item.setIcon(R.drawable.edit_selector);
                    isEditing.remove(0);
                    isEditing.add(0,false);
                    ela.notifyDataSetChanged();
                }
            break;
        }
        return true;
    }

}
