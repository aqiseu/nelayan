package com.com.android.bidfishnelayan.view;

import com.com.android.bidfishnelayan.R;
import com.com.android.bidfishnelayan.data.preference.PreferenceManager;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class IntroActivity extends AppIntro {

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);

        if (!preferenceManager.isFirstTimeLaunch()) {
            Intent mainIntent = new Intent(IntroActivity.this,LoginActivity.class);
            IntroActivity.this.startActivity(mainIntent);
            IntroActivity.this.finish();
        }

        SliderPage kotak = new SliderPage();
        kotak.setTitle("Apa Itu Kotak Keluhan?");
        kotak.setDescription("Kotak Keluhan adalah sebuah kotak ajaib yang menampung aspirasi kalian ke BEMF-IK dan dari kotak tersebut kami dapat mengevaluasi hal tersebut");
        kotak.setImageDrawable(R.drawable.img_slider_one_auctioneer);
        kotak.setBgColor(getResources().getColor(R.color.colorPrimary));
        addSlide(AppIntroFragment.newInstance(kotak));
        SliderPage suara = new SliderPage();
        suara.setTitle("Aspirasi Apa Yang Dapat Disampaikan?");
        suara.setDescription("Kalian dapat mengkritk tentang kinerjs BEMF-IK, kinerja dosen, kinerja fakultas, fasilitas, keluhan kalian dan harapan saran dari kalian.");
        suara.setImageDrawable(R.drawable.img_slider_two_auctioneer);
        suara.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(suara));

        SliderPage title = new SliderPage();
        title.setTitle("Ayo Keluarkan Suara Kita!");
        title.setImageDrawable(R.drawable.img_slider_three_auctioneer);
        title.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(title));

        setSkipText(getString(R.string.label_skip));
        setDoneText(getString(R.string.label_next));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Intent mainIntent = new Intent(IntroActivity.this,LoginActivity.class);
        startActivity(mainIntent);
        finish();
        preferenceManager.setFirstTimeLaunch(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent mainIntent = new Intent(IntroActivity.this,LoginActivity.class);
        startActivity(mainIntent);
        finish();
        preferenceManager.setFirstTimeLaunch(false);
    }
}
