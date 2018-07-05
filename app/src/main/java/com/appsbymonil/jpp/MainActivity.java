package com.appsbymonil.jpp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar ;

import org.w3c.dom.Text;

import static android.widget.Toast.makeText;
import static com.appsbymonil.jpp.R.id.img;

public class MainActivity extends AppCompatActivity {
    private WebView webView ;
    private RelativeLayout relativeLayout ;
    private SwipeRefreshLayout swipe ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = (WebView)findViewById(R.id.web);
        relativeLayout = (RelativeLayout)findViewById(R.id.lay);
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe);

        useWeb();

    }

    public void WebAction(){

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        final String url = "http://sws.jecrcuniversity.edu.in/parent-login/index.php" ;
        webView.loadUrl(url);
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl("file:///android_asset/error.html");

            }

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                swipe.setRefreshing(false);
            }

        });

    }

    public void useWeb(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        if(isConnectingToInternet(getApplicationContext()))   {
            webView.setVisibility(View.VISIBLE);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    WebAction();
                }
            });

            WebAction();



        }
        else{
            // show alert
            relativeLayout.setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(android.R.id.content), "No Internet Connection !!!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            useWeb();




                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
            //Toast.makeText(getApplicationContext(), "no internet", Toast.LENGTH_LONG).show();


        }

    }

    private boolean isConnectingToInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.



            return false;
        } else
            return true;

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }

    }
}