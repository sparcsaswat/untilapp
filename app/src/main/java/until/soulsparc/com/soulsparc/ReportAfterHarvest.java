package until.soulsparc.com.soulsparc;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReportAfterHarvest extends AppCompatActivity {
    ImageView personImg, cameraImg;
    TextView dateOfSeed, dateCulti;
    //image related start
    private final int REQUEST_CODE_STORAGE_PERMS = 321;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private String mCurrentPhotoPath;
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private static final int ACTION_TAKE_GALLERY_PIC = 0;
    public String imgValue = "blank";
    int targetW;
    int targetH;
    String imagepath1;
    int clickedStatus = 0;
    private String mImageUrl = "";
    ProgressDialog progress;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    Spinner seedSource, waterSource;
    String waterValue, seedValue;
    private int STORAGE_PERMISSION_CODE = 1;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_after_harvest);
        personImg = findViewById(R.id.personImg);
        cameraImg = findViewById(R.id.camera);
        dateOfSeed = findViewById(R.id.dateofSeed);
        dateCulti = findViewById(R.id.dateCulti);
        seedSource = findViewById(R.id.seedSource);
        waterSource = findViewById(R.id.waterSource);
        save = findViewById(R.id.save);

        if (ContextCompat.checkSelfPermission(ReportAfterHarvest.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ReportAfterHarvest.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }
        //image related start
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    if (personImg.getDrawable() == null) {
                        SelectImage();
                    }
                }
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        dateOfSeed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportAfterHarvest.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateCulti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReportAfterHarvest.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String[] waters = new String[]{
                "Select Source",
                "River",
                "Stream",
                "Irrigation ditches",
                "Open Canal",
                "Water Well"
        };
        List<String> waterList = new ArrayList<>(Arrays.asList(waters));
        ArrayAdapter<String> waterAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinneritem, waterList);
        waterAdapter.setDropDownViewResource(R.layout.spinnerfront);
        waterSource.setAdapter(waterAdapter);


        waterSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                waterValue = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        String[] seeds = new String[]{
                "Select Source",
                "River",
                "Stream",
                "Irrigation ditches",
                "Open Canal",
                "Water Well"
        };
        List<String> seedList = new ArrayList<>(Arrays.asList(seeds));
        ArrayAdapter<String> seedAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinneritem, seedList);
        seedAdapter.setDropDownViewResource(R.layout.spinnerfront);
        seedSource.setAdapter(seedAdapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReportAfterHarvest.this, "Successfully Reported", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(i);
            }
        });
    }

    //Permission methods

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ReportAfterHarvest.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MEDIA_CONTENT_CONTROL}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MEDIA_CONTENT_CONTROL}, STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

//image code

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ReportAfterHarvest.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    dispatchTakePictureIntent(1);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent(int actionCode) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "CameraSample";
    }

    //activity start result for camera new
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        // requestLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
//                break;
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();

                }
                break;
            } // ACTION_TAKE_PHOTO_B
            case ACTION_TAKE_GALLERY_PIC: {
                if (requestCode == SELECT_FILE) {
                    Uri selectedImageUri = data.getData();
                    if (personImg.getDrawable() == null) {
                        personImg.setImageURI(selectedImageUri);
                    } else {
                        personImg.setImageURI(selectedImageUri);
                        // Toast.makeText(HomeScreen.this, "Cannot capture more photos", Toast.LENGTH_SHORT).show();
                    }
                    mCurrentPhotoPath = selectedImageUri.toString();
                    imagepath1 = mCurrentPhotoPath;
                    clickedStatus = 1;
                    if (Build.VERSION.SDK_INT >= 19) {
                        mCurrentPhotoPath = UtilityGetPath.getRealPathFromURI_API19(getApplicationContext(), selectedImageUri);
                        imgValue = "captured";
                    } else {
                        mCurrentPhotoPath = UtilityGetPath.getRealPathFromURI_API11to18(getApplicationContext(), selectedImageUri);
                        imgValue = "captured";
                    }
                    imagepath1 = mCurrentPhotoPath;
                    clickedStatus = 1;
                    //   imageupdate();
                }
            }
        } // switch
    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
        }

    }

    private void setPic() {

        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        /* Get the size of the ImageView */


        // if (personImg.getDrawable() == null) {
        targetW = personImg.getWidth();
        targetH = personImg.getHeight();

        imagepath1 = mCurrentPhotoPath;
        clickedStatus = 1;
        Log.d("ImagePath", "1" + imagepath1);
        //  } else {
        //   Toast.makeText(HomeScreen.this, "Cannot capture more photos", Toast.LENGTH_SHORT).show();
        //  }

        int targetW = personImg.getWidth();
        int targetH = personImg.getHeight();

        /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        /*Rotate the file according to the device to portait mode*/
        Bitmap bmp = rotatePhoto(mCurrentPhotoPath, bitmap);
        /* Associate the Bitmap to the ImageView */

        if (personImg.getDrawable() == null) {


            personImg.setImageBitmap(bmp);
        } else {
            personImg.setImageBitmap(bmp);
        }
        //assign value to the image variable
        imgValue = "captured";
        // imageupdate();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private Bitmap rotatePhoto(String photoPath, Bitmap bitmap) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                return rotatedBitmap;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                return rotatedBitmap;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                return rotatedBitmap;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
                return rotatedBitmap;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfSeed.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateCulti.setText(sdf.format(myCalendar1.getTime()));
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), BaseActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

}
