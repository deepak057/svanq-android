package com.mobile.mywebviewapplication.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.mobile.mywebviewapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView webView;
    Boolean progressBarVisible=false;
    ProgressBar progressBar;
    private FrameLayout frameProgress;


    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_web_view, container, false);
        frameProgress = (FrameLayout)view.findViewById(R.id.frame_progress);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBarWebView);

        webView=(WebView)view.findViewById(R.id.webviewbelow);
        webView.loadUrl("https://svanq.com/");
        showLoader();
        webView.setWebViewClient(new MyBrowser());
        webView.setWebChromeClient(new WebChromeClientHandler());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);


        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.getSettings().setDatabaseEnabled(true);
        return view;
    }

 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        getActivity().finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }*/


    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //showLoader();
            //count++;
            //  Log.d("#count","count value is : "+count);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            /*if(!url.equals(webURL)){
                backContainer.setVisibility(View.VISIBLE);
                count=count+1;
            }*/
            //   Log.d("#count","count value is : "+count);
            return true;
        }

        //Show loader on url load
        @Override
        public void onLoadResource (WebView view, String url) {

            // Toast.makeText(WebViewController.this, "data loading", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.d("unique", "came in onReceivedError in WebViewController");
            //   Toast.makeText(WebViewController.this, "data error", Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            hideLoader();
            // Toast.makeText(WebViewController.this, "loading finished", Toast.LENGTH_SHORT).show();
        }



      /*  @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }*/
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
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Log.d("chrome", "message in onCreateWindow"+resultMsg);
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
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
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.d("chrome", "message in onJsPrompt"+message);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
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
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);

// This next one is crazy. It's the DEFAULT location for your app's cache
// But it didn't work for me without this line
        webView.getSettings().setAppCachePath("/data/data/"+ getActivity().getPackageName() +"/cache");
        //  imgRefresh.setOnClickListener(new HandleNavBarFunction());
        // imgRefresh.setOnClickListener(new HandleNavBarFunction());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

}
