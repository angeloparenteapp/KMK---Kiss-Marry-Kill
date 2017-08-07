package com.angeloparenteapp.kmk_kissmarrykill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.angeloparenteapp.kmk_kissmarrykill.Utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PlayerDetails extends AppCompatActivity {

    View mContentView;
    Context mContext;

    String name;

    EditText editText;
    Button selectImage;
    Button submit;
    Button next;
    Person user = new Person();
    SharedPreferences configurationDone;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    FirebaseStorage mFirebaseStorage;
    StorageReference mPhotosStorageReference;

    ValueEventListener listener;
    int id;

    private final int RC_PHOTO_PICKER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        mContext = getApplicationContext();
        mContentView = findViewById(R.id.player_details_full);
        Utils.hide(mContentView);

        user.setKill(0);
        user.setKiss(0);
        user.setMarry(0);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        configurationDone = PlayerDetails.this.getSharedPreferences("done", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = configurationDone.edit();
        editor.putInt("done", 0);
        editor.apply();

        mPhotosStorageReference = mFirebaseStorage.getReference().child("male-players");
        mDatabaseReference = mFirebaseDatabase.getReference().child("male-players");

        listener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = ((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editText = findViewById(R.id.name_edit_text);
        selectImage = findViewById(R.id.select_image_button);
        next = findViewById(R.id.button_next);
        submit = findViewById(R.id.ok);

        Utils.setButtonCustomFont(mContext, selectImage);
        Utils.setButtonCustomFont(mContext, next);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editText.getText().toString();

                if (name.equals("") || user.getUrl() == null) {
                    if (name.equals("") && user.getUrl() != null) {
                        Toast.makeText(mContext, "Please insert your name", Toast.LENGTH_SHORT).show();
                    } else if (name.equals("") && user.getUrl() == null) {
                        Toast.makeText(mContext, "Please insert your name and select your photo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Please select your photo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    SharedPreferences.Editor editor = configurationDone.edit();
                    editor.putInt("done", 1);
                    editor.apply();

                    user.setName(name);
                    mDatabaseReference.child("" + (id)).setValue(user);
                }

                removeListener(listener);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener(listener);
                startActivity(new Intent(PlayerDetails.this, MainActivity.class));
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
    }

    public void removeListener(ValueEventListener listener) {
        mDatabaseReference.removeEventListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {

            Uri selectImageUri = data.getData();
            StorageReference photoRef = mPhotosStorageReference.child(selectImageUri.getLastPathSegment());
            photoRef.putFile(selectImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    if (downloadUrl != null) {
                        user.setName(null);
                        user.setUrl(downloadUrl.toString());
                    } else {
                        user.setName(null);
                        user.setUrl(null);
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeListener(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListener(listener);
    }

    @Override
    public void onBackPressed() {
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
