package owen.ross.carguru.UI.Articles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import owen.ross.carguru.R;


public class ArticleFragment extends Fragment {



    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article, container, false);
        //Create a webview
        WebView wvArticles = (WebView) view.findViewById(R.id.wvArticles);
        wvArticles.loadUrl("https://driving.ca/category/auto-news/");
        // Inflate the layout for this fragment
        return view;
    }
}