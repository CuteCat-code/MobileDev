package ru.mirea.bachurinaaa.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_info#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_info extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nav_info() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_info.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_info newInstance(String param1, String param2) {
        nav_info fragment = new nav_info();
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
        // "Раздуваем" макет для фрагмента
        View view = inflater.inflate(R.layout.fragment_nav_info, container, false);

        // Получаем ссылки на TextView для обновления информации
        TextView industryTitle = view.findViewById(R.id.industryTitle);
        TextView industryDescription = view.findViewById(R.id.industryDescription);
        TextView industryTrends = view.findViewById(R.id.industryTrends);
        TextView industryJobs = view.findViewById(R.id.industryJobs);
        TextView industryTechnologies = view.findViewById(R.id.industryTechnologies);
        TextView industryFuture = view.findViewById(R.id.industryFuture);

        // Устанавливаем текст для каждого поля (можно использовать данные из аргументов)
        industryTitle.setText("Информационные технологии");
        industryDescription.setText("Описание: ИТ-индустрия охватывает разработку ПО, данные и кибербезопасность.");
        industryTrends.setText("Текущие тренды: ИИ, облачные технологии, IoT.");
        industryJobs.setText("Востребованные профессии: Разработчик ПО, специалист по данным.");
        industryTechnologies.setText("Новейшие технологии: Машинное обучение, блокчейн.");
        industryFuture.setText("Перспективы: Развитие и расширение карьерных возможностей.");

        return view;
    }
}