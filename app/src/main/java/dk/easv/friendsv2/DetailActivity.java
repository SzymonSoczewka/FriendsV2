package dk.easv.friendsv2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import dk.easv.friendsv2.Model.BEFriend;


public class DetailActivity extends AppCompatActivity {
    static final int PERMISSION_TO_SMS_CODE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int RESULT_DELETED = 2137;
    static final int RESULT_CREATED = 1234;
    EditText etName,etPhone,etEmail,etURL,etBirthday,etAddress;
    ImageView picture,mail_Icon,www_Icon,calendar_Icon;
    String TAG = MainActivity.TAG;
    DatePickerDialog picker;
    Button smsButt,callButt;
    boolean modeUpdate;
    BEFriend friend;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        friend = (BEFriend) getIntent().getSerializableExtra("friend");
        modeUpdate = (Boolean) getIntent().getSerializableExtra("modeUpdate");
        findViews();
        etName.setClickable(false);
        loadUserInformation();
        setButtonsFunctionality();
        hasBirthday();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    // This method is handling back arrow and menu bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.saveButt:
                save();
                break;
            case R.id.deleteButt:
                deleteFriend();
                break;
        }
        return true;
    }

    private void setButtonsFunctionality() {
        smsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSMSActivity();
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
        calendar_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
    }
    private void selectDate(){
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void hasBirthday(){
        String fBirthday = friend.getBirthday(); // Getting friend birthday - dd/MM/yyyy
        if(fBirthday != null) {
            LocalDate currentDate = LocalDate.now(); //Getting the current date value
            int currentDay = currentDate.getDayOfMonth(); //Getting the current day
            int currentMonth = currentDate.getMonthValue(); //Getting the current month
            int currentYear = currentDate.getYear(); //getting the current year

            int firstSlashIndex = fBirthday.indexOf('/');
            int lastSlashIndex = fBirthday.lastIndexOf('/');
            int day = Integer.parseInt(fBirthday.substring(0, firstSlashIndex)); //The day the user was born
            int month = Integer.parseInt(fBirthday.substring(firstSlashIndex+1, lastSlashIndex)); //The month the user was born
            int year = Integer.parseInt(fBirthday.substring(lastSlashIndex+1,fBirthday.length())); //The year the user was born

            if(currentDay == day && currentMonth == month && currentYear > year){
                findViewById(R.id.confetti).setVisibility(View.VISIBLE);
                findViewById(R.id.birthdayCake).setVisibility(View.VISIBLE);
                showToast("It's your friend birthday today!",Toast.LENGTH_LONG);
            }
        }

    }
    private void save() {
        Intent i = new Intent();
        friend.setBirthday(etBirthday.getText().toString());
        friend.setAddress(etAddress.getText().toString());
        friend.setEmail(etEmail.getText().toString());
        friend.setPhone(etPhone.getText().toString());
        friend.setName(etName.getText().toString());
        friend.setURL(etURL.getText().toString());
        i.putExtra("friend", friend);

        if(modeUpdate)
        setResult(Activity.RESULT_OK,i);
        else
        setResult(RESULT_CREATED,i);

        finish();
    }
    private void deleteFriend(){
        if(modeUpdate) {
            setResult(RESULT_DELETED, new Intent());
            finish();
        } else
            showToast("You can't delete a user before it's created",Toast.LENGTH_SHORT);
    }

    private void showToast(String message,int duration){
        Toast.makeText(this,message,duration).show();
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
        startActivity(emailIntent);
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
        sendIntent.putExtra("sms_body", "Greeting from Szymon and Mate :)");
        startActivity(sendIntent);
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
        calendar_Icon = findViewById(R.id.calendar_Icon);
    }

    private void loadUserInformation() {
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
                showToast("Something went wrong!",Toast.LENGTH_SHORT);
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
