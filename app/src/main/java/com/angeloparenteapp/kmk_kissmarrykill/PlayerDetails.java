package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;

public class PlayerDetails extends AppCompatActivity {

    View mContentView;
    Context mContext;

    String name;

    EditText editText;
    Button selectImage;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        mContentView = findViewById(R.id.player_details_full);
        Utils.hide(mContentView);

        mContext = getApplicationContext();

        editText = findViewById(R.id.name_edit_text);
        selectImage = findViewById(R.id.select_image_button);
        next = findViewById(R.id.button_next);

        Utils.setButtonCustomFont(mContext, selectImage);
        Utils.setButtonCustomFont(mContext, next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(mContext, "Please insert your name", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(PlayerDetails.this, MainActivity.class));
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
        super.onRestart();
        Utils.hide(mContentView);
    }
}
