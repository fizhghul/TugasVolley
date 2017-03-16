package com.hafiizh.appnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {
    ImageView img1, img2, img3;
    ViewFlipper viewFlipper;
    Animation FadeIn, FadeOut;
    String gambar, gambar2, gambar3;
    TextView konten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra("judul"));
        konten = (TextView) findViewById(R.id.txtKonten);
        konten.setText(getIntent().getStringExtra("konten"));

        gambar = getIntent().getStringExtra("gambar");
        gambar2 = getIntent().getStringExtra("gambar2");
        gambar3 = getIntent().getStringExtra("gambar3");

        img1 = (ImageView) findViewById(R.id.gambar1);
        img2 = (ImageView) findViewById(R.id.gambar2);
        img3 = (ImageView) findViewById(R.id.gambar3);

        Glide.with(this).load("http://31.220.53.18/hafizhnaufal/uploads/" + gambar).placeholder(android.R.drawable.ic_menu_gallery).error(android.R.drawable.ic_menu_report_image).into(img1);
        Glide.with(this).load("http://31.220.53.18/hafizhnaufal/uploads/" + gambar2).placeholder(android.R.drawable.ic_menu_gallery).error(android.R.drawable.ic_menu_report_image).into(img2);
        Glide.with(this).load("http://31.220.53.18/hafizhnaufal/uploads/" + gambar3).placeholder(android.R.drawable.ic_menu_gallery).error(android.R.drawable.ic_menu_report_image).into(img3);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        FadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        FadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        viewFlipper.setInAnimation(FadeIn);
        viewFlipper.setOutAnimation(FadeOut);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
    }
}
