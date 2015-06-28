package com.disaintworking.konnect;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class AddPeople extends ActionBarActivity {

    private Button addButton;
    private Button uploadImageButton;
    private Button cameraButton;
    final private Context context = this;
    Toast mToast;
    public static DatabaseOps db;
    private ImageView imageView;
    private static int RESULT_LOAD_IMG = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mTempName;
    private Bitmap pictureObject;
    private String mImageURI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);



        final Intent myIntent = getIntent();

        addButton = (Button) findViewById(R.id.addButton);
        uploadImageButton = (Button) findViewById(R.id.uploadImage);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameEditText = (EditText)findViewById(R.id.editTextName);
                final EditText phoneEditText = (EditText) findViewById(R.id.editTextPhone);

                boolean isTrue = true;

                if (nameEditText == null || phoneEditText == null){
                    isTrue = false;
                    showToast("Please Fill In All Fields");
                }

                else {
                    if (phoneEditText.getText().length() != 8){
                        isTrue = false;
                        showToast("Please Enter a Legit Number");
                    }

                    else {
                        if (isTrue){
                            mTempName = nameEditText.getText().toString();
                            mImageURI = saveToInternalStorage(pictureObject);
                            db.add(nameEditText.getText().toString(), phoneEditText.getText().toString(), mImageURI);
                            nameEditText.setText("", TextView.BufferType.EDITABLE);
                            phoneEditText.setText("", TextView.BufferType.EDITABLE);

                            showToast("Added");


                            finish();
                        }
                    }
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_people, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToast(String textToShow) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_SHORT);
        mToast.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // To Handle Gallery Result
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImageUri = data.getData();
            String[] fileColumn = { MediaStore.Images.Media.DATA };

            Cursor imageCursor = getContentResolver().query(selectedImageUri,
                    fileColumn, null, null, null);
            imageCursor.moveToFirst();

            int fileColumnIndex = imageCursor.getColumnIndex(fileColumn[0]);
            String picturePath = imageCursor.getString(fileColumnIndex);

            pictureObject = BitmapFactory.decodeFile(picturePath);

        }
/*
        // TO Handle Camera result
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            pictureObject = (Bitmap) extras.get("data");

        }

        */
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,mTempName + ".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
        }
        return directory.getAbsolutePath();
    }
}
