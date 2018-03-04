package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Lenovo on 2016/11/13.
 */

public class MyDialogDelete extends Dialog{

    private TextView dialogTitle;
    private TextView dialogContent;
    private TextView dialogCancel;
    private TextView dialogConfirm;
    private LayoutInflater inflater;

    public MyDialogDelete(Context context,int i1){
        super(context,R.style.MyDialog);

        inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.delete_dialog_my,null);
        dialogTitle=(TextView) view.findViewById(R.id.name_tv);
        dialogContent=(TextView) view.findViewById(R.id.content_tv);
        dialogCancel= (TextView) view.findViewById(R.id.dialog_cancel_btn);
        dialogConfirm= (TextView) view.findViewById(R.id.dialog_confirm_btn);

        if(i1<0){
            dialogTitle.setText("删除标签");
            dialogContent.setText("确定删除该标签？");
        }
        else {
            dialogTitle.setText("删除该项");
            dialogContent.setText("确定删除该项？");
        }

        super.setContentView(view);
    }

    public void setOnClickCommitListener(View.OnClickListener listener){
        dialogConfirm.setOnClickListener(listener);
    }

    public void setOnClickCancelListener(View.OnClickListener listener){
        dialogCancel.setOnClickListener(listener);
    }
}
