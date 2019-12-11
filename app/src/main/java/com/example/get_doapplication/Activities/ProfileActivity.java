package com.example.get_doapplication.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.get_doapplication.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class ProfileActivity extends AppCompatActivity {
TextView fname,lname,changepin;
ImageView ImageProfile;

    int GALLERY = 2, CAMERA = 1;
    Intent CamIntent, GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    int width, height;
    File file;
    String pathFile;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fname=findViewById(R.id.TxtFname);
        lname=findViewById(R.id.TxtLname);
        changepin=findViewById(R.id.ChangePin);
        ImageProfile=findViewById(R.id.ImageProfile);

        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

ImageProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showPictureDialog();
    }
});
    }
    public void withEditText(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("With Edit Text");

        final EditText input = new EditText(ProfileActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                GetImageFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    //for camera
    private void takePhotoFromCamera() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photofile = null;
            photofile = createPhotoFile();

            if (photofile != null) {
                pathFile = photofile.getAbsolutePath();
                Uri uriPhoto = FileProvider.getUriForFile(ProfileActivity.this, "android-profile-page-master.provider", photofile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto);
                startActivityForResult(takePic, 3);
            }
        }
    }

    private File createPhotoFile() {
        String name=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File PicDirectory=getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        File image=null;
        try{
            image=File.createTempFile(name,".jpg",PicDirectory);

        }catch (Exception e){
            Log.d("mylog","Excep"+e.toString());
        }
        return image;
    }

    //from gallery
    public void GetImageFromGallery(){

        GalIntent = new Intent(Intent.ACTION_PICK ,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {

            ImageCropFunction();

        } else if (requestCode == 2) {

            if (data != null) {

                uri = data.getData();

                ImageCropFunction();

            }
        } else if (requestCode == 1) {

            if (data != null) {

                Bundle bundle = data.getExtras();

                Bitmap bitmap = bundle.getParcelable("data");

                ImageProfile.setImageBitmap(bitmap);

            }
        }
    }
    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 4);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
        //Image Crop Code End Here

//        public void EnableRuntimePermission(){
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,
//                    Manifest.permission.CAMERA))
//            {
//
//                Toast.makeText(ProfileActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{
//                        Manifest.permission.CAMERA}, RequestPermissionCode);
//
//            }
//        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ProfileActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ProfileActivity.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
