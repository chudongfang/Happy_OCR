package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lenovo on 2016/11/8.
 */

public class MyDialogAddEdit extends Dialog{
    private TextView dialogTitle;
    private EditText dialogContent;
    private Button dialogCancel;
    private Button dialogConfirm;
    private LayoutInflater inflater;

    public MyDialogAddEdit(Context context, String title, String content){
        super(context,R.style.MyDialog);

        inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.edit_add_dialog_my,null);
        dialogTitle= (TextView) view.findViewById(R.id.title_tv);
        dialogContent= (EditText) view.findViewById(R.id.content_et);
        dialogCancel= (Button) view.findViewById(R.id.dialog_cancel_btn);
        dialogConfirm= (Button) view.findViewById(R.id.dialog_confirm_btn);

        dialogTitle.setText(title);
        dialogContent.setText(content);

        super.setContentView(view);
    }

    public EditText getContentEditText(){
        return dialogContent;
    }

    public void setOnClickCommitListener(View.OnClickListener listener){
        dialogConfirm.setOnClickListener(listener);
    }

    public void setOnClickCancelListener(View.OnClickListener listener){
        dialogCancel.setOnClickListener(listener);
    }
}
