package dk.easv.friendsv2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dk.easv.friendsv2.Model.BEFriend;


public class DetailActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSION_TO_SMS_CODE = 1;
    static final int RESULT_DELETED = 2137;
    static final int RESULT_CREATED = 1234;
    DatePickerDialog picker;
    String TAG = MainActivity.TAG;
    EditText etName,etPhone,etEmail,etURL,etBirthday,etAddress;
    Button smsButt,callButt;
    BEFriend friend;
    boolean modeUpdate;
    ImageView picture,mail_Icon,www_Icon,save_Icon,remove_Icon,calendar_Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        friend = (BEFriend) getIntent().getSerializableExtra("friend");
        modeUpdate = (Boolean) getIntent().getSerializableExtra("modeUpdate");
        findViews();
        etName.setClickable(false);
        setGUI();
        setButtonsFunctionality();

    }

    // When back arrow is clicked, activity is finished and user redirected to the main activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }




    private void setButtonsFunctionality() {
        smsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showYesNoDialog();
            }
        });
        callButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });
        www_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowser();
            }
        });
        mail_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        save_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        remove_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        removeFriend();
            }
        });
        calendar_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

    }
    private void pickDate(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(DetailActivity.this,
                android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dateAsString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etBirthday.setText(dateAsString);
                    }
                }, year, month, day);
        picker.show();
    }

    private void save() {
        Intent i = new Intent();
        friend.setName(etName.getText().toString());
        friend.setURL(etURL.getText().toString());
        friend.setEmail(etEmail.getText().toString());
        friend.setPhone(etPhone.getText().toString());
        friend.setBirthday(etBirthday.getText().toString());
        friend.setAddress(etAddress.getText().toString());
        i.putExtra("friend", friend);

        if(modeUpdate)
        setResult(Activity.RESULT_OK,i);
        else
        setResult(RESULT_CREATED,i);

        finish();
    }
    private void removeFriend(){
        setResult(RESULT_DELETED,new Intent());
        finish();
    }


    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + friend.getPhone()));
        startActivity(intent);
    }
    private void startBrowser()
    {
        String url = friend.getURL();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { friend.getEmail() };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hej, Hope that it is ok, Best Regards android...;-)");
        startActivity(emailIntent);
    }

    private void sendSMS() {
        Toast.makeText(this, "An sms will be send", Toast.LENGTH_LONG)
                .show();



        Log.d(TAG, "Build version = " + android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d(TAG, "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_TO_SMS_CODE);
                return;

            }
            else
                Log.d(TAG, "permission to SEND_SMS granted!");

        }

        SmsManager m = SmsManager.getDefault();
        String text = "Hi, it goes well on the android course...";
        m.sendTextMessage(friend.getPhone(), null, text, null, null);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode,
                                            String[] permissions,
                                            int[] grantResults)
    {

        if (requestCode == PERMISSION_TO_SMS_CODE) {
            Log.d(TAG, "Permission: " + permissions[0] + " - grantResult: " + grantResults[0]);

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SmsManager m = SmsManager.getDefault();

                String text = "Hi, it goes well on the android course...";
                m.sendTextMessage(friend.getPhone(), null, text, null, null);
            }
        }
        else
            Log.d(TAG, "Unknown permission request code: " + requestCode);

    }
    private void startSMSActivity()
    {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + friend.getPhone()));
        sendIntent.putExtra("sms_body", "Hi, it goes well on the android course...");
        startActivity(sendIntent);
    }
    private void showYesNoDialog()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("SMS Handling")
                .setMessage("Click Direct if SMS should be send directly. Click Start to start SMS app...")
                .setCancelable(true)
                .setPositiveButton("Direct",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        DetailActivity.this.sendSMS();
                    }
                })
                .setNegativeButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DetailActivity.this.startSMSActivity();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    private void findViews() {
        picture = findViewById(R.id.picture);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etURL = findViewById(R.id.etURL);
        etEmail = findViewById(R.id.etEmail);
        etBirthday = findViewById(R.id.etBirthday);
        etAddress = findViewById(R.id.etAddress);
        smsButt = findViewById(R.id.smsButt);
        callButt = findViewById(R.id.callButt);
        mail_Icon = findViewById(R.id.mailIcon);
        www_Icon = findViewById(R.id.wwwIcon);
        save_Icon = findViewById(R.id.saveIcon);
        remove_Icon = findViewById(R.id.removeIcon);
        calendar_Icon = findViewById(R.id.calendar_Icon);
    }

    private void setGUI() {
        etName.setText(friend.getName());
        etPhone.setText(friend.getPhone());
        etEmail.setText(friend.getEmail());
        etURL.setText(friend.getURL());
        etBirthday.setText(friend.getBirthday());
        etAddress.setText(friend.getAddress());
        setPicture(friend.getThumbnailFilePath());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
            File photoFile = createImageFile();
            try (FileOutputStream fos = new FileOutputStream(photoFile)) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
        }
    }
    private void setPicture(String filePath) {
        if(filePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            picture.setImageBitmap(bitmap);
        }
    }
    private File createImageFile() {
        // Checks whether the SD card is mounted or not
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // Creates the storage directory if it does not exist
        if (!mediaStorageDir.exists())
            if (!mediaStorageDir.mkdir())
                return null;
        // Create an media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + imageFileName);

        // Save a xml: path for use with ACTION_VIEW intents
        friend.setThumbnailFilePath(mediaFile.getPath());
        return mediaFile;
    }

}
