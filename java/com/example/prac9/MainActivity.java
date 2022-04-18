package com.example.prac9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                this.handleImageShare(intent);
            }
        }
    }

    public void shareText(View view) {
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "My text");
        sendIntent.setType("type/plain");

        startActivity(sendIntent);
    }

    public void shareBinary(View view) {
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.not_fso));
        sendIntent.setType("image/png");

        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
    }

    public void sendSeveralImages(View view) {
        ArrayList<Uri> imageUris = new ArrayList<>();

        Uri imageUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.not_fso);
        Uri imageUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.not_fso_2);

        imageUris.add(imageUri1);
        imageUris.add(imageUri2);

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        sendIntent.setType("image/*");

        startActivity(Intent.createChooser(sendIntent, "Share images to.."));
    }

    private void handleImageShare(Intent intent) {
        // smth
    }

}