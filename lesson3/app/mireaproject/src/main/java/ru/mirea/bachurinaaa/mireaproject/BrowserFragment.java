package ru.mirea.bachurinaaa.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrowserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowserFragment newInstance(String param1, String param2) {
        BrowserFragment fragment = new BrowserFragment();
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

    // URL по умолчанию
    private static final String DEFAULT_URL = "https://www.google.com";

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // "Раздуваем" макет для фрагмента
        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        // Инициализация WebView
        webView = view.findViewById(R.id.webView);

        // Установка настроек WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Включаем JavaScript (если необходимо)
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Установка WebViewClient, чтобы открывать ссылки внутри WebView
        webView.setWebViewClient(new WebViewClient());

        // Загрузка страницы по умолчанию
        webView.loadUrl(DEFAULT_URL);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Очищаем ресурсы WebView
        if (webView != null) {
            webView.destroy();
        }
    }
}