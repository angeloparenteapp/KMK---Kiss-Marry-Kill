package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    View mContentView;
    Context mContext;
    LinearLayout male;
    LinearLayout female;
    LinearLayout both;
    TextView youLike;
    TextView maleText;
    TextView femaleText;
    TextView bothText;

    String userName;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        userName = "ANONYMOUS";

        startSignIn();

        mContentView = findViewById(R.id.fullscreen_content);
        Utils.hide(mContentView);

        mContext = getApplicationContext();

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        both = findViewById(R.id.both);

        youLike = findViewById(R.id.you_like);
        maleText = findViewById(R.id.male_text);
        femaleText = findViewById(R.id.female_text);
        bothText = findViewById(R.id.both_text);

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

                    SwipeRefreshLayout swipe = findViewById(R.id.swipe);
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

                    SwipeRefreshLayout swipe = findViewById(R.id.swipe);
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

                    SwipeRefreshLayout swipe = findViewById(R.id.swipe);
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

    public void startSignIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            // Not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                            .build(), RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(MainActivity.this, PlayerDetails.class));
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getApplicationContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), "Dunno", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(getApplicationContext(), "Dunno!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hide(mContentView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utils.hide(mContentView);
    }
}
