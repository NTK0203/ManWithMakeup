package com.example.ssu_makeup.survey_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ssu_makeup.R;
import com.example.ssu_makeup.SurveyActivity;

public class SurveyQ1_1Fragment extends Fragment implements View.OnClickListener {
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    //Button answer5;

    public static SurveyQ1_1Fragment newInstance() {
        return new SurveyQ1_1Fragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_survey_q1_1, container, false);
        answer1 = root.findViewById(R.id.answer1);
        answer2 = root.findViewById(R.id.answer2);
        answer3 = root.findViewById(R.id.answer3);
        answer4 = root.findViewById(R.id.answer4);
        //answer5 = root.findViewById(R.id.answer5);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);
        //answer5.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        setButtons();
        view.setBackgroundResource(R.drawable.button_round_corners_selected);
        ((SurveyActivity)getActivity()).replaceFragment(SurveyQ1_2Fragment.newInstance());
    }

    public void setButtons(){
        answer1.setBackgroundResource(R.drawable.button_round_corners);
        answer2.setBackgroundResource(R.drawable.button_round_corners);
        answer3.setBackgroundResource(R.drawable.button_round_corners);
        answer4.setBackgroundResource(R.drawable.button_round_corners);
        //answer5.setBackgroundResource(R.drawable.button_round_corners);
    }
}