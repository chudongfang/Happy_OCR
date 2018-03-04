package myapp.lenovo.viewpager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Lenovo on 2016/11/13.
 */

public class MyDialogChoose extends Dialog{

    private TextView chooseTitle;
    private TextView chooseLine;
    private ListView listView;
    private LayoutInflater inflater;

    public MyDialogChoose(Context context){
        super(context,R.style.MyDialog);

        inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.choose_dialog_my,null);
        chooseTitle= (TextView) view.findViewById(R.id.choose_title_tv);
        chooseLine= (TextView) view.findViewById(R.id.title_line_tv);
        listView= (ListView) view.findViewById(R.id.choose_lv);

        super.setContentView(view);
    }

    public ListView getListView(){
        return listView;
    }

}
