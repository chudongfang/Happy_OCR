package myapp.lenovo.viewpager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPracticeFragment extends Fragment {

    private ProgressBar progressBar;
    private WebView practice;
    private String url="http://115.159.205.168/ocr_php/public/train.php";
    private ValueCallback<Uri> myUploadMsg;
    private ValueCallback<Uri[]> myFilePathCallback;

    private static final int FILECHOOSER=1;
    private static final int FILECHOOSERFORANDROID5=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_practice, container, false);
        progressBar= (ProgressBar) view.findViewById(R.id.progress_bar);
        practice= (WebView) view.findViewById(R.id.web_view);
        initWebView();
        return view;
    }

    private void initWebView(){

        practice.loadUrl(url);
        WebSettings webSettings=practice.getSettings();
        webSettings.setJavaScriptEnabled(true);
        practice.setFocusable(true);
        practice.setFocusableInTouchMode(true);
        practice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(practice.canGoBack()&&keyEvent.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    WebBackForwardList bfl=practice.copyBackForwardList();
                    if(bfl.getCurrentIndex()>0){
                        practice.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        practice.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        practice.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
                else {
                    if(progressBar.getVisibility()==ProgressBar.INVISIBLE){
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType,String capture) {
                openFileChooserImpl(uploadMsg);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                openFileChooserForAndroid5(filePathCallback);
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
    }

    public void openFileChooserImpl(ValueCallback<Uri> uploadMsg){
        myUploadMsg=uploadMsg;
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"File Chooser"),FILECHOOSER);
    }

    public void openFileChooserForAndroid5(ValueCallback<Uri[]> filePathCallback){
        myFilePathCallback=filePathCallback;
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        Intent chooseIntent=new Intent(Intent.ACTION_CHOOSER);
        chooseIntent.putExtra(Intent.EXTRA_INTENT,intent);
        chooseIntent.putExtra(Intent.EXTRA_TITLE,"File Chooser");
        startActivityForResult(chooseIntent,FILECHOOSERFORANDROID5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILECHOOSER){
            Uri result;
            if(myUploadMsg==null)
                return;
            if(data==null||resultCode!=RESULT_OK){
                result=null;
            }
            else{
                result=data.getData();
            }
            myUploadMsg.onReceiveValue(result);
            myUploadMsg=null;
        }
        else if(requestCode==FILECHOOSERFORANDROID5){
            Uri result;
            if(myFilePathCallback==null)
                return;
            if(data==null||resultCode!=RESULT_OK){
                result=null;
            }
            else{
                result=data.getData();
            }
            if(result==null){
                myFilePathCallback.onReceiveValue(new Uri[]{});
            }
            else{
                myFilePathCallback.onReceiveValue(new Uri[]{result});
            }
            myFilePathCallback=null;
        }
    }
}

