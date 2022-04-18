package com.example.prac9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;

public class FileSelectActivity extends AppCompatActivity {
    private File mPrivateRootDir;
    private File mImagesDir;

    private Uri fileUri;

    File[] mImageFiles;
    String[] mImageFileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        ListView listView = findViewById(R.id.listView);

        Intent mResultIntent = new Intent("com.example.prac9.ACTION_RETURN_FILE");

        mPrivateRootDir = getFilesDir();
        mImagesDir = new File(mPrivateRootDir, "images");
        mImageFiles = mImagesDir.listFiles();

        setResult(Activity.RESULT_CANCELED, null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                File requestFile = new File(mImageFileNames[position]);

                try {
                    fileUri = FileProvider.getUriForFile(FileSelectActivity.this, "com.example.prac9.fileprovider", requestFile);

                    if (fileUri != null) {
                        mResultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        mResultIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));

                        FileSelectActivity.this.setResult(Activity.RESULT_OK, mResultIntent);
                    } else {
                        mResultIntent.setDataAndType(null, "");

                        FileSelectActivity.this.setResult(Activity.RESULT_CANCELED, mResultIntent);
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("File Selector", "The selected file can't be shared: " + requestFile.getName());
                }
            }
        });
    }
}