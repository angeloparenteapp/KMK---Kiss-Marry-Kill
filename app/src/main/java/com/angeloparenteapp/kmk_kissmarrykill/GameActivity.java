package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //context and intent for gender
    public View mContentView;
    public Context mContext;
    public String type;
    public DecimalFormat df;

    //Views
    public Button submitButton, nextButton;
    public ImageButton homeButton, kissButton1, kissButton2, kissButton3, marryButton1, marryButton2, marryButton3, killButton1, killButton2, killButton3;
    public ImageView firstImage, secondImage, thirdImage;
    public TextView textPercKiss1, textPercKiss2, textPercKiss3, textPercMarry1, textPercMarry2, textPercMarry3, textPercKill1, textPercKill2, textPercKill3, name1, name2, name3;
    public ProgressBar progressBar1, progressBar2, progressBar3;
    public View transparency1, transparency2, transparency3;

    //value for kiss, marry and kill
    public float kissInt1, marryInt1, killInt1, kissInt2, marryInt2, killInt2, kissInt3, marryInt3, killInt3;
    public float newKiss1, newKiss2, newKiss3, newMarry1, newMarry2, newMarry3, newKill1, newKill2, newKill3;

    //percentage for people
    public float percentage1;
    public float percentage2;
    public float percentage3;

    //random int to get random image
    public int randomInt;
    public int randomInt1;
    public int randomInt2;
    public int randomInt3;

    //Firebase references
    public DatabaseReference mDatabaseMale;
    public DatabaseReference mDatabaseFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mContext = getApplicationContext();
        mContentView = findViewById(R.id.activity_game_fullscreen);
        Utils.hide(mContentView);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);

        mDatabaseMale = FirebaseDatabase.getInstance().getReference();
        mDatabaseFemale = FirebaseDatabase.getInstance().getReference();

        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress3);

        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        name3 = (TextView) findViewById(R.id.name3);
        Utils.setTextViewCustomFont(mContext, name1);
        Utils.setTextViewCustomFont(mContext, name2);
        Utils.setTextViewCustomFont(mContext, name3);

        transparency1 = findViewById(R.id.transparency1);
        transparency2 = findViewById(R.id.transparency2);
        transparency3 = findViewById(R.id.transparency3);

        transparency1.setVisibility(View.INVISIBLE);
        transparency2.setVisibility(View.INVISIBLE);
        transparency3.setVisibility(View.INVISIBLE);

        firstImage = (ImageView) findViewById(R.id.firstImage);
        secondImage = (ImageView) findViewById(R.id.secondImage);
        thirdImage = (ImageView) findViewById(R.id.thirdImage);

        textPercKiss1 = (TextView) findViewById(R.id.textPercKiss1);
        textPercKiss2 = (TextView) findViewById(R.id.textPercKiss2);
        textPercKiss3 = (TextView) findViewById(R.id.textPercKiss3);
        textPercKill1 = (TextView) findViewById(R.id.textPercKill1);
        textPercKill2 = (TextView) findViewById(R.id.textPercKill2);
        textPercKill3 = (TextView) findViewById(R.id.textPercKill3);
        textPercMarry1 = (TextView) findViewById(R.id.textPercMarry1);
        textPercMarry2 = (TextView) findViewById(R.id.textPercMarry2);
        textPercMarry3 = (TextView) findViewById(R.id.textPercMarry3);

        Utils.setTextViewCustomFont(mContext, textPercKiss1);
        Utils.setTextViewCustomFont(mContext, textPercKiss2);
        Utils.setTextViewCustomFont(mContext, textPercKiss3);
        Utils.setTextViewCustomFont(mContext, textPercKill1);
        Utils.setTextViewCustomFont(mContext, textPercKill2);
        Utils.setTextViewCustomFont(mContext, textPercKill3);
        Utils.setTextViewCustomFont(mContext, textPercMarry1);
        Utils.setTextViewCustomFont(mContext, textPercMarry2);
        Utils.setTextViewCustomFont(mContext, textPercMarry3);

        submitButton = (Button) findViewById(R.id.submitButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        kissButton1 = (ImageButton) findViewById(R.id.kiss1);
        kissButton2 = (ImageButton) findViewById(R.id.kiss2);
        kissButton3 = (ImageButton) findViewById(R.id.kiss3);

        marryButton1 = (ImageButton) findViewById(R.id.marry1);
        marryButton2 = (ImageButton) findViewById(R.id.marry2);
        marryButton3 = (ImageButton) findViewById(R.id.marry3);

        killButton1 = (ImageButton) findViewById(R.id.kill1);
        killButton2 = (ImageButton) findViewById(R.id.kill2);
        killButton3 = (ImageButton) findViewById(R.id.kill3);

        kissButton1.setSelected(false);
        kissButton1.setVisibility(ImageButton.VISIBLE);
        kissButton1.setBackground(getDrawable(R.drawable.kiss));

        kissButton2.setSelected(false);
        kissButton2.setVisibility(ImageButton.VISIBLE);
        kissButton2.setBackground(getDrawable(R.drawable.kiss));

        kissButton3.setSelected(false);
        kissButton3.setVisibility(ImageButton.VISIBLE);
        kissButton3.setBackground(getDrawable(R.drawable.kiss));

        marryButton1.setSelected(false);
        marryButton1.setVisibility(ImageButton.VISIBLE);
        marryButton1.setBackground(getDrawable(R.drawable.marry));

        marryButton2.setSelected(false);
        marryButton2.setVisibility(ImageButton.VISIBLE);
        marryButton2.setBackground(getDrawable(R.drawable.marry));

        marryButton3.setSelected(false);
        marryButton3.setVisibility(ImageButton.VISIBLE);
        marryButton3.setBackground(getDrawable(R.drawable.marry));

        killButton1.setSelected(false);
        killButton1.setVisibility(ImageButton.VISIBLE);
        killButton1.setBackground(getDrawable(R.drawable.kill));

        killButton2.setSelected(false);
        killButton2.setVisibility(ImageButton.VISIBLE);
        killButton2.setBackground(getDrawable(R.drawable.kill));

        killButton3.setSelected(false);
        killButton3.setVisibility(ImageButton.VISIBLE);
        killButton3.setBackground(getDrawable(R.drawable.kill));

        submitButton.setVisibility(Button.INVISIBLE);
        nextButton.setVisibility(Button.INVISIBLE);
        Utils.setButtonCustomFont(mContext, submitButton);
        Utils.setButtonCustomFont(mContext, nextButton);

        homeButton = (ImageButton) findViewById(R.id.homeButton);

        setRandomImage();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        kissButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kissButton1.isSelected()) {
                    kissButton1.setSelected(false);
                    kissButton1.setBackground(getDrawable(R.drawable.kiss));

                    if (marryButton3.isSelected() || killButton3.isSelected()) {
                        kissButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton2.isSelected() || killButton2.isSelected()) {
                        kissButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton2.isSelected() || marryButton3.isSelected()) {
                        marryButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton2.isSelected() || killButton3.isSelected()) {
                        killButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!kissButton1.isSelected()) {
                    kissButton1.setSelected(true);
                    kissButton1.setBackground(getDrawable(R.drawable.kisspressed));

                    marryButton1.setVisibility(ImageButton.INVISIBLE);
                    killButton1.setVisibility(ImageButton.INVISIBLE);

                    kissButton2.setVisibility(ImageButton.INVISIBLE);
                    kissButton3.setVisibility(ImageButton.INVISIBLE);

                    kissButton2.setSelected(false);
                    kissButton2.setBackground(getDrawable(R.drawable.kiss));
                    kissButton3.setSelected(false);
                    kissButton3.setBackground(getDrawable(R.drawable.kiss));

                    okToSubmit();
                }
            }
        });

        marryButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marryButton1.isSelected()) {
                    marryButton1.setSelected(false);
                    marryButton1.setBackground(getDrawable(R.drawable.marry));

                    if (killButton3.isSelected() || kissButton3.isSelected()) {
                        marryButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton2.isSelected() || kissButton2.isSelected()) {
                        marryButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton2.isSelected() || kissButton3.isSelected()) {
                        kissButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton2.isSelected() || killButton3.isSelected()) {
                        killButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!marryButton1.isSelected()) {
                    marryButton1.setSelected(true);
                    marryButton1.setBackground(getDrawable(R.drawable.marrypressed));

                    kissButton1.setVisibility(ImageButton.INVISIBLE);
                    killButton1.setVisibility(ImageButton.INVISIBLE);

                    marryButton2.setVisibility(ImageButton.INVISIBLE);
                    marryButton3.setVisibility(ImageButton.INVISIBLE);

                    marryButton2.setSelected(false);
                    marryButton2.setBackground(getDrawable(R.drawable.marry));
                    marryButton3.setSelected(false);
                    marryButton3.setBackground(getDrawable(R.drawable.marry));

                    okToSubmit();
                }
            }
        });

        killButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (killButton1.isSelected()) {
                    killButton1.setSelected(false);
                    killButton1.setBackground(getDrawable(R.drawable.kill));

                    if (marryButton3.isSelected() || kissButton3.isSelected()) {
                        killButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton2.isSelected() || kissButton2.isSelected()) {
                        killButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton3.isSelected() || marryButton2.isSelected()) {
                        marryButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton3.isSelected() || kissButton2.isSelected()) {
                        kissButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!killButton1.isSelected()) {
                    killButton1.setSelected(true);
                    killButton1.setBackground(getDrawable(R.drawable.killpressed));

                    kissButton1.setVisibility(ImageButton.INVISIBLE);
                    marryButton1.setVisibility(ImageButton.INVISIBLE);

                    killButton2.setVisibility(ImageButton.INVISIBLE);
                    killButton3.setVisibility(ImageButton.INVISIBLE);

                    killButton2.setSelected(false);
                    killButton2.setBackground(getDrawable(R.drawable.kill));
                    killButton3.setSelected(false);
                    killButton3.setBackground(getDrawable(R.drawable.kill));

                    okToSubmit();
                }
            }
        });

        kissButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kissButton2.isSelected()) {
                    kissButton2.setSelected(false);
                    kissButton2.setBackground(getDrawable(R.drawable.kiss));

                    if (marryButton1.isSelected() || killButton1.isSelected()) {
                        kissButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton3.isSelected() || killButton3.isSelected()) {
                        kissButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton1.isSelected() || marryButton3.isSelected()) {
                        marryButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton1.isSelected() || killButton3.isSelected()) {
                        killButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!kissButton2.isSelected()) {
                    kissButton2.setSelected(true);
                    kissButton2.setBackground(getDrawable(R.drawable.kisspressed));

                    marryButton2.setVisibility(ImageButton.INVISIBLE);
                    killButton2.setVisibility(ImageButton.INVISIBLE);

                    kissButton1.setVisibility(ImageButton.INVISIBLE);
                    kissButton3.setVisibility(ImageButton.INVISIBLE);

                    kissButton1.setSelected(false);
                    kissButton1.setBackground(getDrawable(R.drawable.kiss));
                    kissButton3.setSelected(false);
                    kissButton3.setBackground(getDrawable(R.drawable.kiss));

                    okToSubmit();
                }
            }
        });

        marryButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marryButton2.isSelected()) {
                    marryButton2.setSelected(false);
                    marryButton2.setBackground(getDrawable(R.drawable.marry));

                    if (killButton1.isSelected() || kissButton1.isSelected()) {
                        marryButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton3.isSelected() || kissButton3.isSelected()) {
                        marryButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton1.isSelected() || kissButton3.isSelected()) {
                        kissButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton1.isSelected() || killButton3.isSelected()) {
                        killButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!marryButton2.isSelected()) {
                    marryButton2.setSelected(true);
                    marryButton2.setBackground(getDrawable(R.drawable.marrypressed));

                    kissButton2.setVisibility(ImageButton.INVISIBLE);
                    killButton2.setVisibility(ImageButton.INVISIBLE);

                    marryButton1.setVisibility(ImageButton.INVISIBLE);
                    marryButton3.setVisibility(ImageButton.INVISIBLE);

                    marryButton1.setSelected(false);
                    marryButton1.setBackground(getDrawable(R.drawable.marry));
                    marryButton3.setSelected(false);
                    marryButton3.setBackground(getDrawable(R.drawable.marry));

                    okToSubmit();
                }
            }
        });

        killButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (killButton2.isSelected()) {
                    killButton2.setSelected(false);
                    killButton2.setBackground(getDrawable(R.drawable.kill));

                    if (marryButton1.isSelected() || kissButton1.isSelected()) {
                        killButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton3.isSelected() || kissButton3.isSelected()) {
                        killButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton1.isSelected() || kissButton3.isSelected()) {
                        kissButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton1.isSelected() || marryButton3.isSelected()) {
                        marryButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!killButton2.isSelected()) {
                    killButton2.setSelected(true);
                    killButton2.setBackground(getDrawable(R.drawable.killpressed));

                    kissButton2.setVisibility(ImageButton.INVISIBLE);
                    marryButton2.setVisibility(ImageButton.INVISIBLE);

                    killButton1.setVisibility(ImageButton.INVISIBLE);
                    killButton3.setVisibility(ImageButton.INVISIBLE);

                    killButton1.setSelected(false);
                    killButton1.setBackground(getDrawable(R.drawable.kill));
                    killButton3.setSelected(false);
                    killButton3.setBackground(getDrawable(R.drawable.kill));

                    okToSubmit();
                }
            }
        });

        kissButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kissButton3.isSelected()) {
                    kissButton3.setSelected(false);
                    kissButton3.setBackground(getDrawable(R.drawable.kiss));

                    if (marryButton1.isSelected() || killButton1.isSelected()) {
                        kissButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton2.isSelected() || killButton2.isSelected()) {
                        kissButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton1.isSelected() || marryButton2.isSelected()) {
                        marryButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton1.isSelected() || killButton2.isSelected()) {
                        killButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!kissButton3.isSelected()) {
                    kissButton3.setSelected(true);
                    kissButton1.setSelected(false);
                    kissButton2.setSelected(false);

                    kissButton3.setBackground(getDrawable(R.drawable.kisspressed));

                    marryButton3.setVisibility(ImageButton.INVISIBLE);
                    killButton3.setVisibility(ImageButton.INVISIBLE);

                    kissButton1.setVisibility(ImageButton.INVISIBLE);
                    kissButton2.setVisibility(ImageButton.INVISIBLE);

                    kissButton1.setBackground(getDrawable(R.drawable.kiss));

                    kissButton2.setBackground(getDrawable(R.drawable.kiss));

                    okToSubmit();
                }
            }
        });

        marryButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marryButton3.isSelected()) {

                    marryButton3.setSelected(false);
                    marryButton3.setBackground(getDrawable(R.drawable.marry));

                    if (killButton1.isSelected() || kissButton1.isSelected()) {
                        marryButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton2.isSelected() || kissButton2.isSelected()) {
                        marryButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton1.isSelected() || kissButton2.isSelected()) {
                        kissButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (killButton1.isSelected() || killButton2.isSelected()) {
                        killButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!marryButton3.isSelected()) {
                    marryButton3.setSelected(true);
                    marryButton3.setBackground(getDrawable(R.drawable.marrypressed));

                    kissButton3.setVisibility(ImageButton.INVISIBLE);
                    killButton3.setVisibility(ImageButton.INVISIBLE);

                    marryButton1.setVisibility(ImageButton.INVISIBLE);
                    marryButton2.setVisibility(ImageButton.INVISIBLE);

                    marryButton1.setSelected(false);
                    marryButton1.setBackground(getDrawable(R.drawable.marry));
                    marryButton2.setSelected(false);
                    marryButton2.setBackground(getDrawable(R.drawable.marry));

                    okToSubmit();
                }
            }
        });

        killButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (killButton3.isSelected()) {
                    killButton3.setSelected(false);
                    killButton3.setBackground(getDrawable(R.drawable.kill));

                    if (marryButton1.isSelected() || kissButton1.isSelected()) {
                        killButton1.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton1.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton2.isSelected() || kissButton2.isSelected()) {
                        killButton2.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        killButton2.setVisibility(ImageButton.VISIBLE);
                    }

                    if (kissButton1.isSelected() || kissButton2.isSelected()) {
                        kissButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        kissButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    if (marryButton1.isSelected() || marryButton2.isSelected()) {
                        marryButton3.setVisibility(ImageButton.INVISIBLE);
                    } else {
                        marryButton3.setVisibility(ImageButton.VISIBLE);
                    }

                    okToSubmit();

                } else if (!killButton3.isSelected()) {
                    killButton3.setSelected(true);
                    killButton3.setBackground(getDrawable(R.drawable.killpressed));

                    kissButton3.setVisibility(ImageButton.INVISIBLE);
                    marryButton3.setVisibility(ImageButton.INVISIBLE);

                    killButton1.setVisibility(ImageButton.INVISIBLE);
                    killButton2.setVisibility(ImageButton.INVISIBLE);

                    killButton1.setSelected(false);
                    killButton1.setBackground(getDrawable(R.drawable.kill));
                    killButton2.setSelected(false);
                    killButton2.setBackground(getDrawable(R.drawable.kill));

                    okToSubmit();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name1.setVisibility(View.INVISIBLE);
                name2.setVisibility(View.INVISIBLE);
                name3.setVisibility(View.INVISIBLE);

                transparency1.setVisibility(View.VISIBLE);
                transparency2.setVisibility(View.VISIBLE);
                transparency3.setVisibility(View.VISIBLE);

                kissButton1.setClickable(false);
                kissButton2.setClickable(false);
                kissButton3.setClickable(false);

                marryButton1.setClickable(false);
                marryButton2.setClickable(false);
                marryButton3.setClickable(false);

                killButton1.setClickable(false);
                killButton2.setClickable(false);
                killButton3.setClickable(false);

                submitButton.setClickable(false);

                if (Utils.isOnline(mContext)) {

                    if (kissButton1.isSelected() && marryButton2.isSelected() && killButton3.isSelected()) {

                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("kiss").setValue(newKiss1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("marry").setValue(newMarry2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("kill").setValue(newKill3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("kiss").setValue(newKiss1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("marry").setValue(newMarry2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("kill").setValue(newKill3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("kiss").setValue(newKiss1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("marry").setValue(newMarry2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("kill").setValue(newKill3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("kiss").setValue(newKiss1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("marry").setValue(newMarry2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("kill").setValue(newKill3);
                            }
                        }

                        percentage1 = ((newKiss1) / (newKiss1 + marryInt1 + killInt1)) * 100;
                        percentage2 = ((newMarry2) / (kissInt2 + newMarry2 + killInt2)) * 100;
                        percentage3 = ((newKill3) / (kissInt3 + marryInt3 + newKill3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercKiss1.setVisibility(View.VISIBLE);
                        textPercKiss1.setText("" + firstPercentage + " %");
                        textPercMarry2.setVisibility(View.VISIBLE);
                        textPercMarry2.setText("" + secondPercentage + " %");
                        textPercKill3.setVisibility(View.VISIBLE);
                        textPercKill3.setText("" + thirdPercentage + " %");

                    } else if (kissButton1.isSelected() && marryButton3.isSelected() && killButton2.isSelected()) {
                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("kiss").setValue(newKiss1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("kill").setValue(newKill2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("marry").setValue(newMarry3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("kiss").setValue(newKiss1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("kill").setValue(newKill2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("marry").setValue(newKill3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("kiss").setValue(newKiss1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("kill").setValue(newKill2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("marry").setValue(newMarry3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("kiss").setValue(newKiss1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("kill").setValue(newKill2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("marry").setValue(newKill3);
                            }
                        }

                        percentage1 = ((newKiss1) / (newKiss1 + marryInt1 + killInt1)) * 100;
                        percentage2 = ((newKill2) / (kissInt2 + marryInt2 + newKill2)) * 100;
                        percentage3 = ((newMarry3) / (kissInt3 + newMarry3 + killInt3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercKiss1.setVisibility(View.VISIBLE);
                        textPercKiss1.setText("" + firstPercentage + " %");
                        textPercKill2.setVisibility(View.VISIBLE);
                        textPercKill2.setText("" + secondPercentage + " %");
                        textPercMarry3.setVisibility(View.VISIBLE);
                        textPercMarry3.setText("" + thirdPercentage + " %");

                    } else if (kissButton2.isSelected() && marryButton1.isSelected() && killButton3.isSelected()) {
                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("marry").setValue(newMarry1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("kiss").setValue(newKiss2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("kill").setValue(newKill3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("marry").setValue(newMarry1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("kiss").setValue(newKiss2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("kill").setValue(newKill3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("marry").setValue(newMarry1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("kiss").setValue(newKiss2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("kill").setValue(newKill3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("marry").setValue(newMarry1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("kiss").setValue(newKiss2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("kill").setValue(newKill3);
                            }
                        }

                        percentage1 = ((newMarry1) / (kissInt1 + newMarry1 + killInt1)) * 100;
                        percentage2 = ((newKiss2) / (newKiss2 + marryInt2 + killInt2)) * 100;
                        percentage3 = ((newKill3) / (kissInt3 + marryInt3 + newKill3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercMarry1.setVisibility(View.VISIBLE);
                        textPercMarry1.setText("" + firstPercentage + " %");
                        textPercKiss2.setVisibility(View.VISIBLE);
                        textPercKiss2.setText("" + secondPercentage + " %");
                        textPercKill3.setVisibility(View.VISIBLE);
                        textPercKill3.setText("" + thirdPercentage + " %");

                    } else if (kissButton2.isSelected() && marryButton3.isSelected() && killButton1.isSelected()) {
                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("kill").setValue(newKill1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("kiss").setValue(newKiss2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("marry").setValue(newMarry3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("kill").setValue(newKill1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("kiss").setValue(newKiss2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("marry").setValue(newMarry3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("kill").setValue(newKill1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("kiss").setValue(newKiss2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("marry").setValue(newMarry3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("kill").setValue(newKill1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("kiss").setValue(newKiss2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("marry").setValue(newMarry3);
                            }
                        }

                        percentage1 = ((newKill1) / (kissInt1 + marryInt1 + newKill1)) * 100;
                        percentage2 = ((newKiss2) / (newKiss2 + marryInt2 + killInt2)) * 100;
                        percentage3 = ((newMarry3) / (kissInt3 + newMarry3 + killInt3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercKill1.setVisibility(View.VISIBLE);
                        textPercKill1.setText("" + firstPercentage + " %");
                        textPercKiss2.setVisibility(View.VISIBLE);
                        textPercKiss2.setText("" + secondPercentage + " %");
                        textPercMarry3.setVisibility(View.VISIBLE);
                        textPercMarry3.setText("" + thirdPercentage + " %");

                    } else if (kissButton3.isSelected() && marryButton1.isSelected() && killButton2.isSelected()) {
                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("marry").setValue(newMarry1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("kill").setValue(newKill2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("kiss").setValue(newKiss3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("marry").setValue(newMarry1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("kill").setValue(newKill2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("kiss").setValue(newKiss3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("marry").setValue(newMarry1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("kill").setValue(newKill2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("kiss").setValue(newKiss3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("marry").setValue(newMarry1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("kill").setValue(newKill2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("kiss").setValue(newKiss3);
                            }
                        }

                        percentage1 = ((newMarry1) / (kissInt1 + newMarry1 + killInt1)) * 100;
                        percentage2 = ((newKill2) / (kissInt2 + marryInt2 + newKill2)) * 100;
                        percentage3 = ((newKiss3) / (newKiss3 + marryInt3 + killInt3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercMarry1.setVisibility(View.VISIBLE);
                        textPercMarry1.setText("" + firstPercentage + " %");
                        textPercKill2.setVisibility(View.VISIBLE);
                        textPercKill2.setText("" + secondPercentage + " %");
                        textPercKiss3.setVisibility(View.VISIBLE);
                        textPercKiss3.setText("" + thirdPercentage + " %");

                    } else if (kissButton3.isSelected() && marryButton2.isSelected() && killButton1.isSelected()) {
                        nextButton.setVisibility(Button.VISIBLE);

                        if (type.equals("male")) {
                            mDatabaseMale.child("male").child("" + randomInt1).child("kill").setValue(newKill1);
                            mDatabaseMale.child("male").child("" + randomInt2).child("marry").setValue(newMarry2);
                            mDatabaseMale.child("male").child("" + randomInt3).child("kiss").setValue(newKiss3);
                        } else if (type.equals("female")) {
                            mDatabaseMale.child("female").child("" + randomInt1).child("kill").setValue(newKill1);
                            mDatabaseMale.child("female").child("" + randomInt2).child("marry").setValue(newMarry2);
                            mDatabaseMale.child("female").child("" + randomInt3).child("kiss").setValue(newKiss3);
                        } else {
                            if (randomInt == 1) {
                                mDatabaseMale.child("male").child("" + randomInt1).child("kill").setValue(newKill1);
                                mDatabaseMale.child("male").child("" + randomInt2).child("marry").setValue(newMarry2);
                                mDatabaseMale.child("male").child("" + randomInt3).child("kiss").setValue(newKiss3);
                            } else {
                                mDatabaseMale.child("female").child("" + randomInt1).child("kill").setValue(newKill1);
                                mDatabaseMale.child("female").child("" + randomInt2).child("marry").setValue(newMarry2);
                                mDatabaseMale.child("female").child("" + randomInt3).child("kiss").setValue(newKiss3);
                            }
                        }

                        percentage1 = ((newKill1) / (kissInt1 + marryInt1 + newKill1)) * 100;
                        percentage2 = ((newMarry2) / (kissInt2 + newMarry2 + killInt2)) * 100;
                        percentage3 = ((newKiss3) / (newKiss3 + marryInt3 + killInt3)) * 100;

                        String firstPercentage = df.format(percentage1);
                        String secondPercentage = df.format(percentage2);
                        String thirdPercentage = df.format(percentage3);

                        textPercKill1.setVisibility(View.VISIBLE);
                        textPercKill1.setText("" + firstPercentage + " %");
                        textPercMarry2.setVisibility(View.VISIBLE);
                        textPercMarry2.setText("" + secondPercentage + " %");
                        textPercKiss3.setVisibility(View.VISIBLE);
                        textPercKiss3.setText("" + thirdPercentage + " %");
                    }
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isOnline(mContext)) {

                    startAgain();

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

    public void okToSubmit() {
        if (kissButton1.isSelected() && marryButton2.isSelected() && killButton3.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else if (kissButton1.isSelected() && marryButton3.isSelected() && killButton2.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else if (kissButton2.isSelected() && marryButton1.isSelected() && killButton3.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else if (kissButton2.isSelected() && marryButton3.isSelected() && killButton1.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else if (kissButton3.isSelected() && marryButton1.isSelected() && killButton2.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else if (kissButton3.isSelected() && marryButton2.isSelected() && killButton1.isSelected()) {
            submitButton.setVisibility(Button.VISIBLE);
        } else {
            submitButton.setVisibility(Button.INVISIBLE);
        }
    }

    public void startAgain() {

        name1.setVisibility(View.VISIBLE);
        name2.setVisibility(View.VISIBLE);
        name3.setVisibility(View.VISIBLE);

        transparency1.setVisibility(View.INVISIBLE);
        transparency2.setVisibility(View.INVISIBLE);
        transparency3.setVisibility(View.INVISIBLE);

        submitButton.setClickable(true);

        kissButton1.setClickable(true);
        kissButton2.setClickable(true);
        kissButton3.setClickable(true);

        marryButton1.setClickable(true);
        marryButton2.setClickable(true);
        marryButton3.setClickable(true);

        killButton1.setClickable(true);
        killButton2.setClickable(true);
        killButton3.setClickable(true);

        progressBar1.setVisibility(ProgressBar.VISIBLE);
        progressBar2.setVisibility(ProgressBar.VISIBLE);
        progressBar3.setVisibility(ProgressBar.VISIBLE);

        textPercKiss1.setVisibility(View.GONE);
        textPercKiss2.setVisibility(View.GONE);
        textPercKiss3.setVisibility(View.GONE);
        textPercKill1.setVisibility(View.GONE);
        textPercKill2.setVisibility(View.GONE);
        textPercKill3.setVisibility(View.GONE);
        textPercMarry1.setVisibility(View.GONE);
        textPercMarry2.setVisibility(View.GONE);
        textPercMarry3.setVisibility(View.GONE);

        submitButton.setVisibility(Button.INVISIBLE);
        nextButton.setVisibility(Button.INVISIBLE);

        kissButton1.setSelected(false);
        kissButton1.setVisibility(ImageButton.VISIBLE);
        kissButton1.setBackground(getDrawable(R.drawable.kiss));

        kissButton2.setSelected(false);
        kissButton2.setVisibility(ImageButton.VISIBLE);
        kissButton2.setBackground(getDrawable(R.drawable.kiss));

        kissButton3.setSelected(false);
        kissButton3.setVisibility(ImageButton.VISIBLE);
        kissButton3.setBackground(getDrawable(R.drawable.kiss));

        marryButton1.setSelected(false);
        marryButton1.setVisibility(ImageButton.VISIBLE);
        marryButton1.setBackground(getDrawable(R.drawable.marry));

        marryButton2.setSelected(false);
        marryButton2.setVisibility(ImageButton.VISIBLE);
        marryButton2.setBackground(getDrawable(R.drawable.marry));

        marryButton3.setSelected(false);
        marryButton3.setVisibility(ImageButton.VISIBLE);
        marryButton3.setBackground(getDrawable(R.drawable.marry));

        killButton1.setSelected(false);
        killButton1.setVisibility(ImageButton.VISIBLE);
        killButton1.setBackground(getDrawable(R.drawable.kill));

        killButton2.setSelected(false);
        killButton2.setVisibility(ImageButton.VISIBLE);
        killButton2.setBackground(getDrawable(R.drawable.kill));

        killButton3.setSelected(false);
        killButton3.setVisibility(ImageButton.VISIBLE);
        killButton3.setBackground(getDrawable(R.drawable.kill));

        setRandomImage();
    }

    public void setRandomImage() {

        firstImage.setImageResource(0);
        secondImage.setImageResource(0);
        thirdImage.setImageResource(0);

        Random random = new Random();

        if (type.equals("male")) {

            randomInt1 = random.nextInt(100);
            randomInt2 = random.nextInt(100);
            randomInt3 = random.nextInt(100);

            if ((randomInt1 != randomInt2) && (randomInt1 != randomInt3) && (randomInt2 != randomInt3)) {
                ValueEventListener listener = mDatabaseMale.child("male").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<Person> listPerson = new ArrayList<>();
                        for (DataSnapshot personDataSnapshot : dataSnapshot.getChildren()) {
                            Person person = personDataSnapshot.getValue(Person.class);
                            listPerson.add(person);
                        }
                        setImageView(listPerson, randomInt1, randomInt2, randomInt3);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        setRandomImage();
                    }
                });

                mDatabaseMale.removeEventListener(listener);
            } else {
                setRandomImage();
            }

        } else if (type.equals("female")) {

            randomInt1 = random.nextInt(100);
            randomInt2 = random.nextInt(100);
            randomInt3 = random.nextInt(100);

            if ((randomInt1 != randomInt2) && (randomInt1 != randomInt3) && (randomInt2 != randomInt3)) {
                ValueEventListener listener = mDatabaseFemale.child("female").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<Person> listPerson = new ArrayList<>();
                        for (DataSnapshot personDataSnapshot : dataSnapshot.getChildren()) {
                            Person person = personDataSnapshot.getValue(Person.class);
                            listPerson.add(person);
                        }
                        setImageView(listPerson, randomInt1, randomInt2, randomInt3);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        setRandomImage();
                    }
                });

                mDatabaseFemale.removeEventListener(listener);
            } else {
                setRandomImage();
            }

        } else {
            Random random1 = new Random();
            randomInt = random1.nextInt(2);

            if (randomInt == 1) {

                randomInt1 = random.nextInt(100);
                randomInt2 = random.nextInt(100);
                randomInt3 = random.nextInt(100);

                if ((randomInt1 != randomInt2) && (randomInt1 != randomInt3) && (randomInt2 != randomInt3)) {
                    ValueEventListener listener = mDatabaseMale.child("male").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ArrayList<Person> listPerson = new ArrayList<>();
                            for (DataSnapshot personDataSnapshot : dataSnapshot.getChildren()) {
                                Person person = personDataSnapshot.getValue(Person.class);
                                listPerson.add(person);
                            }
                            setImageView(listPerson, randomInt1, randomInt2, randomInt3);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            setRandomImage();
                        }

                    });

                    mDatabaseMale.removeEventListener(listener);
                } else {
                    setRandomImage();
                }

            } else {

                randomInt1 = random.nextInt(100);
                randomInt2 = random.nextInt(100);
                randomInt3 = random.nextInt(100);

                if ((randomInt1 != randomInt2) && (randomInt1 != randomInt3) && (randomInt2 != randomInt3)) {
                    ValueEventListener listener = mDatabaseFemale.child("female").addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            ArrayList<Person> listPerson = new ArrayList<>();
                            for (DataSnapshot personDataSnapshot : dataSnapshot.getChildren()) {
                                Person person = personDataSnapshot.getValue(Person.class);
                                listPerson.add(person);
                            }
                            setImageView(listPerson, randomInt1, randomInt2, randomInt3);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            setRandomImage();
                        }
                    });

                    mDatabaseFemale.removeEventListener(listener);
                } else {
                    setRandomImage();
                }
            }
        }
    }

    public void setImageView(ArrayList<Person> list, int randomInt1, int randomInt2, int randomInt3) {

        String url1 = list.get(randomInt1).getUrl();
        kissInt1 = list.get(randomInt1).getKiss();
        marryInt1 = list.get(randomInt1).getMarry();
        killInt1 = list.get(randomInt1).getKill();
        newKiss1 = kissInt1 + 1;
        newMarry1 = marryInt1 + 1;
        newKill1 = killInt1 + 1;
        name1.setText(list.get(randomInt1).getName());

        String url2 = list.get(randomInt2).getUrl();
        kissInt2 = list.get(randomInt2).getKiss();
        marryInt2 = list.get(randomInt2).getMarry();
        killInt2 = list.get(randomInt2).getKill();
        newKiss2 = kissInt2 + 1;
        newMarry2 = marryInt2 + 1;
        newKill2 = killInt2 + 1;
        name2.setText(list.get(randomInt2).getName());

        String url3 = list.get(randomInt3).getUrl();
        kissInt3 = list.get(randomInt3).getKiss();
        marryInt3 = list.get(randomInt3).getMarry();
        killInt3 = list.get(randomInt3).getKill();
        newKiss3 = kissInt3 + 1;
        newMarry3 = marryInt3 + 1;
        newKill3 = killInt3 + 1;
        name3.setText(list.get(randomInt3).getName());

        Glide.with(mContext).load(url1).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar1.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar1.setVisibility(View.GONE);
                return false;
            }
        }).into(firstImage);

        Glide.with(mContext).load(url2).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar2.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar2.setVisibility(View.GONE);
                return false;
            }
        }).into(secondImage);

        Glide.with(mContext).load(url3).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar3.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar3.setVisibility(View.GONE);
                return false;
            }
        }).into(thirdImage);
    }

    @Override
    protected void onResume() {
        Utils.hide(mContentView);
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Utils.hide(mContentView);
        super.onRestart();
    }
}