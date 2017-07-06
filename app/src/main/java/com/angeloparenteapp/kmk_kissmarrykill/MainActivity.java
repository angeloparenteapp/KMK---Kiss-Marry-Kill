package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 100;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;

    //Firebase Auth
    private FirebaseAuth mFirebaseAuth;

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

        mFirebaseAuth = FirebaseAuth.getInstance();

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
                    intent.putExtra("type", "male");
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
                    intent.putExtra("type", "female");
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

        if (Utils.isOnline(getApplicationContext()) && mFirebaseAuth.getCurrentUser() != null) {
            // already signed in
            mUsername = mFirebaseAuth.getCurrentUser().getDisplayName();
        } else if (Utils.isOnline(getApplicationContext()) && mFirebaseAuth.getCurrentUser() == null) {
            // not signed in
            onSignedOutCleanup();
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                            .setTheme(R.style.sign_in_theme)
                            .build(), RC_SIGN_IN);
        } else {
            setContentView(R.layout.no_internet);
            mContentView.setVisibility(View.GONE);
            male.setVisibility(View.GONE);
            female.setVisibility(View.GONE);
            both.setVisibility(View.GONE);

            SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user != null) {
                    onSignedInInitialize(user.getDisplayName());
                }
                Toast.makeText(MainActivity.this, "Welcome, " + mUsername + "!", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign In Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void onSignedInInitialize(String userName) {
        mUsername = userName;
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
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
