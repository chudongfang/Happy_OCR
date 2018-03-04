package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import static myapp.lenovo.viewpager.MyDocumentFragment.deleteBmobChildName;

/**
 * Created by Lenovo on 2016/11/6.
 */

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> groupName;
    private Map<String, List<String>> childName;
    private Map<String,List<Uri>> childDrawable;
    private LayoutInflater inflater;
    private Context context;
    private List<Boolean> isEditing;
    private MyDialogAddEdit myDialogAE;
    private MyDialogDelete myDialogD;
    private MyDialogRecognize myDialogR;
    private MyDialogDrawable myDialogDB;
    private Dialog myDialogS;
    private MyDialogChoose myDialogC;
    private EditText contentEditText;
    private int resId;

    public MyBaseExpandableListAdapter(List<String> groupName, Map<String, List<String>> childName, Map<String,List<Uri>> childDrawable,Context context, List<Boolean> isEditing) {

        this.groupName = groupName;
        this.childName = childName;
        this.childDrawable=childDrawable;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.isEditing = isEditing;
    }

    @Override
    public int getGroupCount() {
        return groupName.size();
    }

    @Override
    public int getChildrenCount(int i) {
        String gn = groupName.get(i);
        return childName.get(gn).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupName.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        String gn = groupName.get(i);
        return childName.get(gn).get(i1);
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
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.group_item, null);
        }
        ImageView pd = (ImageView) view.findViewById(R.id.pull_down_iv);
        TextView gnm = (TextView) view.findViewById(R.id.group_name_tv);
        TextView gnb = (TextView) view.findViewById(R.id.group_number_tv);
        TextView ln = (TextView) view.findViewById(R.id.line_tv);
        ImageView gd = (ImageView) view.findViewById(R.id.group_delete_iv);

        pd.setImageResource(R.drawable.pull_down_off);
        if (b) {
            pd.setImageResource(R.drawable.pull_down_on);
        }
        gnm.setText(groupName.get(i));
        gnb.setText(getChildrenCount(i) + "");
        if (isEditing.get(0) == false) {
            ln.setVisibility(View.GONE);
            gd.setVisibility(View.GONE);
            gnb.setVisibility(View.VISIBLE);
            gnm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        } else {
            gd.setVisibility(View.VISIBLE);
            ln.setVisibility(View.VISIBLE);
            gnb.setVisibility(View.GONE);
            gd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteDialog(context, i, -1);
                }
            });
            gnm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editGroupDialog(context, i, "修改标签名称", groupName.get(i));
                }
            });
        }
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.child_item, null);
        }
        ImageView cp = (ImageView) view.findViewById(R.id.child_picture_iv);
        TextView cw = (TextView) view.findViewById(R.id.child_word_iv);
        ImageView setting = (ImageView) view.findViewById(R.id.setting_iv);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewSetting = LayoutInflater.from(context).inflate(R.layout.child_setting, null);
                TextView settingEdit = (TextView) viewSetting.findViewById(R.id.edit_tv);
                TextView settingMove = (TextView) viewSetting.findViewById(R.id.move_tv);
                TextView settingDelete = (TextView) viewSetting.findViewById(R.id.delete_tv);
                settingEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String gn = groupName.get(i);
                        final String string=childName.get(gn).get(i1);
                        myDialogS.dismiss();
                        recognizeDialog(i,i1,string);
                    }
                });
                settingMove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialogC = new MyDialogChoose(context);
                        ListView listView = myDialogC.getListView();
                        final String[] groupString = new String[20];
                        for (int j = 0; j < groupName.size(); j++) {
                            groupString[j] = groupName.get(j);
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.choose_item, R.id.choose_item_tv, groupName);
                        listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterViewS, View viewS, int iS, long lS) {
                                Log.d("msg1", i + "---" + i1 + "---" + iS);
                                String gn = groupName.get(i);
                                String string=childName.get(gn).get(i1);
                                Uri uri=childDrawable.get(gn).get(i1);
                                MyDocumentFragment.deleteChild(i,i1);
                                MyDocumentFragment.addChild2(uri,string,iS);
                                MyDocumentFragment.moveBmobChildName(i,i1,iS);
                                myDialogC.dismiss();
                            }
                        });
                        myDialogS.dismiss();
                        myDialogC.show();
                    }
                });
                settingDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialogS.dismiss();
                        deleteDialog(context, i, i1);
                    }
                });
                myDialogS = new Dialog(context, R.style.MyDialog);
                myDialogS.setContentView(viewSetting);
                myDialogS.show();
            }
        });
        String gn = groupName.get(i);
        final String string=childName.get(gn).get(i1);
        final Uri uri=childDrawable.get(gn).get(i1);
        Log.d("uri",String.valueOf(uri));
        cp.setImageURI(uri);
        cw.setText(string);
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawableDialog(uri);
            }
        });
        cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recognizeDialog(i,i1,string);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void addChildDialog(Context context, String title, final int i) {
        myDialogAE = new MyDialogAddEdit(context, title, null);
        contentEditText = myDialogAE.getContentEditText();
        myDialogAE.setOnClickCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newContent = contentEditText.getText().toString();
                //MyDocumentFragment.addChild(newContent, i);
                myDialogAE.dismiss();
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

    public void editGroupDialog(Context context, final int i, String title, String content) {
        myDialogAE = new MyDialogAddEdit(context, title, content);
        contentEditText = myDialogAE.getContentEditText();
        myDialogAE.setOnClickCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newContent = contentEditText.getText().toString();
                MyDocumentFragment.editName(i, -1, newContent);
                myDialogAE.dismiss();
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

    public void deleteDialog(Context context, final int i, final int i1) {
        myDialogD = new MyDialogDelete(context, i1);
        myDialogD.setOnClickCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i1 < 0) {
                    MyDocumentFragment.deleteGroup(i);
                } else {
                    MyDocumentFragment.deleteChild(i, i1);
                    deleteBmobChildName(i,i1);
                }
                myDialogD.dismiss();
            }
        });
        myDialogD.setOnClickCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogD.dismiss();
            }
        });
        myDialogD.show();
    }

    public void drawableDialog(Uri uri) {
        myDialogDB = new MyDialogDrawable(context, uri);
        myDialogDB.setOnClickExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogDB.dismiss();
            }
        });
        myDialogDB.setOnClickConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogDB.dismiss();
            }
        });
        myDialogDB.show();
        Window window = myDialogDB.getWindow();
        window.setWindowAnimations(R.style.PopBottomAnim);
        window.setBackgroundDrawableResource(android.R.color.white);
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = 0;
        window.setAttributes(wlp);
    }

    public void recognizeDialog(final int i,final int i1,String string) {
        myDialogR = new MyDialogRecognize(context, string);
        myDialogR.setOnClickExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogR.dismiss();
            }
        });
        myDialogR.setOnClickEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dialogContent = myDialogR.getDialogContent();
                Button dialogEdit = myDialogR.getDialogEdit();
                String s = dialogEdit.getText().toString().trim();
                if (s.equals("编辑")) {
                    dialogContent.setFocusable(true);
                    dialogContent.setFocusableInTouchMode(true);
                    dialogContent.requestFocus();
                    dialogEdit.setText("保存");
                } else if (s.equals("保存")) {
                    String newContent = dialogContent.getText().toString().trim();
                    MyDocumentFragment.editName(i,i1,newContent);
                    dialogEdit.setText("编辑");
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
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
}
