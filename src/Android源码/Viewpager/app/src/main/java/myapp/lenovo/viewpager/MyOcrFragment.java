package myapp.lenovo.viewpager;


import android.content.Context;
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
public class MyOcrFragment extends Fragment {

    private ProgressBar progressBar;
    private WebView ocr;
    private String url="http://115.159.205.168/ocr_php/public/";
    private ValueCallback<Uri> myUploadMsg;
    private ValueCallback<Uri[]> myFilePathCallback;
    private OcrContent ocrContent;
    private Uri result;

    private static final int FILECHOOSER=1;
    private static final int FILECHOOSERFORANDROID5=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_ocr, container, false);
        progressBar= (ProgressBar) view.findViewById(R.id.progress_bar);
        ocr= (WebView) view.findViewById(R.id.web_view);
        initWebView();
        return view;
    }

    private void initWebView(){

        ocr.loadUrl(url);
        WebSettings webSettings=ocr.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ocr.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        ocr.setFocusable(true);
        ocr.setFocusableInTouchMode(true);
        ocr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(ocr.canGoBack()&&keyEvent.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    WebBackForwardList bfl=ocr.copyBackForwardList();
                    if(bfl.getCurrentIndex()>0){
                        ocr.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        ocr.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.java_obj.getSource(document.getElementById('wn').innerHTML);");
                super.onPageFinished(view, url);
            }
        });

        ocr.setWebChromeClient(new WebChromeClient(){
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
            if(myUploadMsg==null)
                return;
            if(data==null||resultCode!=RESULT_OK){
                result=null;
            }
            else{
                result=data.getData();
                Log.d("result", String.valueOf(result));
            }
            myUploadMsg.onReceiveValue(result);
            myUploadMsg=null;
        }
        else if(requestCode==FILECHOOSERFORANDROID5){
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

    @Override
    public void onAttach(Context context) {
        if(context!=null){
            ocrContent=(OcrContent)context;
        }
        super.onAttach(context);
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getSource(String html) {
            if(!html.equals("")&&html!=null) {
                ocrContent.getOcrContent(result,html);
            }
        }
    }

    public interface OcrContent{
        void getOcrContent(Uri uri,String html);
    }

}