package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lenovo on 2016/11/14.
 */

public class MyDialogDrawable extends Dialog {
    private TextView dialogTitle;
    private TextView dialogLine;
    private TextView dialogExit;
    private Button dialogConfirm;
    private ImageView dialogDrawable;
    private LayoutInflater inflater;

    public MyDialogDrawable(Context context, Uri uri){
        super(context,R.style.MyDialog);

        inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.drawable_dialog,null);
        dialogTitle=(TextView) view.findViewById(R.id.title_tv);
        dialogExit=(TextView) view.findViewById(R.id.exit_tv);
        dialogLine=(TextView) view.findViewById(R.id.line_tv);
        dialogDrawable=(ImageView) view.findViewById(R.id.drawable_iv);
        dialogConfirm= (Button) view.findViewById(R.id.confirm_btn);

        dialogDrawable.setImageURI(uri);

        super.setContentView(view);
    }

    public void setOnClickExitListener(View.OnClickListener listener){
        dialogExit.setOnClickListener(listener);
    }

    public void setOnClickConfirmListener(View.OnClickListener listener){
        dialogConfirm.setOnClickListener(listener);
    }
}
