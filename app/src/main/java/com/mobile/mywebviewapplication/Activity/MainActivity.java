package com.mobile.mywebviewapplication.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mobile.mywebviewapplication.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
  private WebView webView;
    Boolean progressBarVisible=false;
    ProgressBar progressBar;
    private FrameLayout frameProgress;
    private Bitmap bitmap;
    private static final int REQUEST_FINE_LOCATION = 1;
    private String mGeolocationOrigin;
    private   GeolocationPermissions.Callback mGeolocationCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colortheme));
        }


        frameProgress = (FrameLayout)findViewById(R.id.frame_progress);
        progressBar=(ProgressBar)findViewById(R.id.progressBarWebView);

        webView=(WebView)findViewById(R.id.webviewbelow);

         webView.loadUrl("https://svanq.com");
         //showLoader();

                webView.setWebViewClient(new MyBrowser());

        webView.setWebChromeClient(new WebChromeClientHandler());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheMaxSize( 10 * 1024 * 1024 ); // 10MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );

        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().getAllowFileAccess();
      //  webView.setLayerType(View.LAYE  mWebView.clearCache(true);  mWebView.clearCache(true);R_TYPE_HARDWARE, null);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccessFromFileURLs();
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setSaveFormData(true);

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
    //    String html = getHTML();
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        webView.getSettings().setAllowFileAccess(true);
     //   webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
    //    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);


        webView.clearFormData();
        webView.clearCache(true);
     //   webView.getSettings().setPluginState(WebSettings.PluginState.ON);
       // webView.loadDataWithBaseURL("", "", mimeType, encoding, "");
        enableHTML5AppCache();


    }

    private String getHTML() {
        String html1 = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "J2fB5XWj6IE"
                + "?fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n";
        return html1;


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    private class MyBrowser extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showLoader();
            //count++;
          //  Log.d("#count","count value is : "+count);
        }
        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
            Log.e("view","view is visible  "+view+"url is  "+url);
        /*   new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   hideLoader();
               }
           },2000);*/


        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.e("webview","http error request in chrome  "+request+"   "+errorResponse);
            /*refresh.setVisibility(View.VISIBLE);
            if (webViewProgressBar.isShown())
                webViewProgressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Unexpected error occurred.Reload page again.", Toast.LENGTH_SHORT).show();*/

        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            /*if(!url.equals(webURL)){
                backContainer.setVisibility(View.VISIBLE);
                count=count+1;
            }*/
         //   Log.d("#count","count value is : "+count);
            //Toast.makeText(myActivity.this, url, Toast.LENGTH_SHORT).show(); //Debugging purposes
            if (url.endsWith(".mp4"))
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/mp4");
                view.getContext().startActivity(intent);
            }
            else
            {
                view.loadUrl(url);
            }

            return true;
        }

        //Show loader on url load
        @Override
        public void onLoadResource (WebView view, String url) {
                 view.getSettings().setPluginState(WebSettings.PluginState.ON);
                 Log.e("onload","on load resource webview  "+url);
            if(url.contains("https://api.svanq.com/uploads/")){
                String v=url.substring(url.indexOf("https"),url.indexOf("s/"));
                String id=url.substring(v.length()+2);
             //   Log.e("idofvideo","video iid   of idd  is "+id +"   "+v+"    "+v.length());
                if(id.contains(".mp4")){

                    String videoid=id.replaceAll(".mp4","");
                   // Log.e("videoidss","video id is "+videoid);
                  //  view.loadUrl(getJavScriptInjection(videoid));
                }
                if(url.contains(".mp4")){


                    //String frameVideo = "<html><body><br><iframe width=\"50\" height=\"50\" src="+url+" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                   // view.loadData(frameVideo, "text/html", "utf-8");

                 /*   try {
                        bitmap = retriveVideoFrameFromVideo(url);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }*/

                }

            }
            // Toast.makeText(WebViewController.this, "data loading", Toast.LENGTH_SHORT).show();
        }


        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

            Log.e("interceptrequest","requesrt intercept   "+request+"   "+webView);
           // hideLoader();
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.d("unique", "came in onReceivedError in WebViewController");
            //   Toast.makeText(WebViewController.this, "data error", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
       //   hideLoader();
            // Toast.makeText(WebViewController.this, "loading finished", Toast.LENGTH_SHORT).show();


          //  view.loadUrl(getJavScriptInjection());
        }
        public  Bitmap retriveVideoFrameFromVideo(String p_videoPath)

        {
            Bitmap m_bitmap = null;
            MediaMetadataRetriever m_mediaMetadataRetriever = null;
            try
            {
                m_mediaMetadataRetriever = new MediaMetadataRetriever();
                m_mediaMetadataRetriever.setDataSource(p_videoPath);
                m_bitmap = m_mediaMetadataRetriever.getFrameAtTime();
            }
            catch (Exception m_e)
            {
            }
            finally
            {
                if (m_mediaMetadataRetriever != null)
                {
                    m_mediaMetadataRetriever.release();
                }
            }
            return m_bitmap;
        }





      /*@Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }*/
    }

    private String getJavScriptInjection(String videoid) {


        String videojava= "$(function () {\n" +
                "     let videoJs = videojs("+videoid+");\n" +
                "     videoJs.one('play', function () {\n" +
                "          this.currentTime(0);\n" +
                "     });\n" +
                "});";
        return videojava;
    }

    private void hideLoader()
    {
        if(progressBarVisible)
        {
            //progressBar.setVisibility(View.INVISIBLE);
            frameProgress.setVisibility(View.INVISIBLE);
            progressBarVisible=false;
         //   Log.d("#count","hideLoader() called : "+count+" times");
        }
    }

    private void showLoader() {
        if (!progressBarVisible) {
            progressBarVisible = true;
            //progressBar.setVisibility(View.VISIBLE);
            frameProgress.setVisibility(View.VISIBLE);
           // Log.d("#count", "showLoader() called : " + count + " times");
        }
    }
    private class WebChromeClientHandler extends WebChromeClient {

        public WebChromeClientHandler() {
            super();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d("chrome", "new progress "+newProgress);
            if(newProgress==100){
                hideLoader();
            }
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Log.d("chrome", "message in onCreateWindow"+resultMsg);
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            final Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(255, 0, 0 ,0);
          /*  if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawARGB(255, 100, 100 ,100);

            }*/
                return bitmap;

        }
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            String perm = Manifest.permission.ACCESS_FINE_LOCATION;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                    ContextCompat.checkSelfPermission(MainActivity.this, perm) == PackageManager.PERMISSION_GRANTED) {
                // we're on SDK < 23 OR user has already granted permission
                callback.invoke(origin, true, false);
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, perm)) {
                    // ask the user for permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {perm}, REQUEST_FINE_LOCATION);

                    // we will use these when user responds
                    mGeolocationOrigin = origin;
                    mGeolocationCallback = callback;
                }
            }
            callback.invoke(origin, true, false);
        }

        public void onShowCustomView (View view, WebChromeClient.CustomViewCallback callback) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("video/*");
            startActivity(intent);
        }


        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("chrome", "message in onJsAlert"+message);
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Log.d("chrome", "message in onJsConfirm"+message);
            return super.onJsConfirm(view, url, message, result);
        }
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                    }
                }
            });
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.d("chrome", "message in onJsPrompt"+message);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }


        @Nullable
        @Override
        public View getVideoLoadingProgressView() {

            return super.getVideoLoadingProgressView();
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d("chrome", "message in onConsoleMessage"+consoleMessage);
            return super.onConsoleMessage(consoleMessage);
        }

    }

    private void enableHTML5AppCache() {
        webView.getSettings().setDomStorageEnabled(true);

// Set cache size to 8 mb by default. should be more than enough
        //webView.getSettings().setAppCacheMaxSize(1024*1024*8);

// This next one is crazy. It's the DEFAULT location for your app's cache
// But it didn't work for me without this line
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() +"/cache");
        webView.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath()+"/database");
      //  imgRefresh.setOnClickListener(new HandleNavBarFunction());
       // imgRefresh.setOnClickListener(new HandleNavBarFunction());


        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_FINE_LOCATION:
                boolean allow = false;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // user has allowed this permission
                    allow = true;
                }
                if (mGeolocationCallback != null) {
                    // call back to web chrome client
                    mGeolocationCallback.invoke(mGeolocationOrigin, allow, false);
                }
                break;
        }
    }
}
