package com.learn.dimdimasdim.bidfishnelayan.view;

import com.learn.dimdimasdim.bidfishnelayan.R;
import com.learn.dimdimasdim.bidfishnelayan.data.preference.PreferenceManager;
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

        addSlide(SampleSlide.newInstance(R.layout.slider_one_layout));
        addSlide(SampleSlide.newInstance(R.layout.slider_two_layout));
        addSlide(SampleSlide.newInstance(R.layout.slider_three_layout));

//        SliderPage slider1 = new SliderPage();
//        slider1.setTitle("Welcome to BidFish");
//        slider1.setDescription("Kotak Keluhan adalah sebuah kotak ajaib yang menampung aspirasi kalian ke BEMF-IK dan dari kotak tersebut kami dapat mengevaluasi hal tersebut");
//        slider1.setImageDrawable(R.drawable.fish);
//        slider1.setBgColor(getResources().getColor(R.color.colorPrimary));
//        addSlide(AppIntroFragment.newInstance(slider1));
//
//        SliderPage slider2 = new SliderPage();
//        slider2.setTitle("Aspirasi Apa Yang Dapat Disampaikan?");
//        slider2.setDescription("Kalian dapat mengkritk tentang kinerjs BEMF-IK, kinerja dosen, kinerja fakultas, fasilitas, keluhan kalian dan harapan saran dari kalian.");
//        slider2.setImageDrawable(R.drawable.img_slider_2_auctioneer);
//        slider2.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
//        addSlide(AppIntroFragment.newInstance(slider2));
//
//        SliderPage slider3 = new SliderPage();
//        slider3.setTitle("Ayo Keluarkan Suara Kita!");
//        slider3.setImageDrawable(R.drawable.img_slider_3_auctioneer);
//        slider3.setBgColor(getResources().getColor(R.color.colorAccent));
//        addSlide(AppIntroFragment.newInstance(slider3));

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
