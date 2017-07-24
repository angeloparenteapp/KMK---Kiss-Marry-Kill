package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    private View mContentView;
    Context mContext;
    LinearLayout male;
    LinearLayout female;
    LinearLayout both;
    TextView youLike;
    TextView maleText;
    TextView femaleText;
    TextView bothText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.fullscreen_content);
        Utils.hide(mContentView);

        mContext = getApplicationContext();

        male = (LinearLayout) findViewById(R.id.male);
        female = (LinearLayout) findViewById(R.id.female);
        both = (LinearLayout) findViewById(R.id.both);

        youLike = (TextView) findViewById(R.id.you_like);
        maleText = (TextView) findViewById(R.id.male_text);
        femaleText = (TextView) findViewById(R.id.female_text);
        bothText = (TextView) findViewById(R.id.both_text);

        Utils.setTextViewCustomFont(mContext, youLike);
        Utils.setTextViewCustomFont(mContext, maleText);
        Utils.setTextViewCustomFont(mContext, femaleText);
        Utils.setTextViewCustomFont(mContext, bothText);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isOnline(mContext)) {
                    Intent intent = new Intent(mContext, GameActivity.class);
                    intent.putExtra("type", "male-players");
                    startActivity(intent);
                } else {
                    setContentView(R.layout.no_internet);
                    mContentView.setVisibility(View.GONE);

                    SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
                    swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isOnline(mContext)) {
                    Intent intent = new Intent(mContext, GameActivity.class);
                    intent.putExtra("type", "female-players");
                    startActivity(intent);
                } else {
                    setContentView(R.layout.no_internet);
                    mContentView.setVisibility(View.GONE);

                    SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
                    swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                }
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isOnline(mContext)) {
                    Intent intent = new Intent(mContext, GameActivity.class);
                    intent.putExtra("type", "both");
                    startActivity(intent);
                } else {
                    setContentView(R.layout.no_internet);
                    mContentView.setVisibility(View.GONE);

                    SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
                    swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hide(mContentView);
    }

    @Override
    protected void onRestart() {
        Utils.hide(mContentView);
        super.onRestart();
    }
}
