package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lenovo on 2016/11/13.
 */

public class MyDialogRecognize extends Dialog{

    private TextView dialogTitle;
    private TextView dialogLine;
    private TextView dialogExit;
    private Button dialogEdit;
    private EditText dialogContent;
    private LayoutInflater inflater;

    public MyDialogRecognize(Context context,String content){
        super(context,R.style.MyDialog);

        inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.recognize_dialog,null);
        dialogTitle=(TextView) view.findViewById(R.id.title_tv);
        dialogExit=(TextView) view.findViewById(R.id.exit_tv);
        dialogLine=(TextView) view.findViewById(R.id.line_tv);
        dialogContent=(EditText) view.findViewById(R.id.word_et);
        dialogEdit= (Button) view.findViewById(R.id.edit_btn);

        dialogContent.setText(content);

        super.setContentView(view);
    }

    public EditText getDialogContent() {
        return dialogContent;
    }

    public Button getDialogEdit() {
        return dialogEdit;
    }

    public void setOnClickExitListener(View.OnClickListener listener){
        dialogExit.setOnClickListener(listener);
    }

    public void setOnClickEditListener(View.OnClickListener listener){
        dialogEdit.setOnClickListener(listener);
    }
}
