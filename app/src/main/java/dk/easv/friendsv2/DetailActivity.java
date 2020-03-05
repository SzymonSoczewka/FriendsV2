package dk.easv.friendsv2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import dk.easv.friendsv2.Model.BEFriend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;
    EditText etName,etPhone,etEmail,etURL;
    Button smsButt,callButt,mailButt,wwwButt;
    CheckBox cbFavorite;
    BEFriend f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");
        f = (BEFriend) getIntent().getSerializableExtra("friend");
        initializeViews();
        setButtonsFunctionality();
        setGUI();
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
        wwwButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBrowser();
            }
        });
        mailButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }
    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + f.getPhone()));
        startActivity(intent);
    }
    private void startBrowser()
    {
        String url = f.getURL();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] receivers = { f.getEmail() };
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Hej, Hope that it is ok, Best Regards android...;-)");
        startActivity(emailIntent);
    }
    static int PERMISSION_TO_SMS_CODE = 1;
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
        m.sendTextMessage(f.getPhone(), null, text, null, null);
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
                m.sendTextMessage(f.getPhone(), null, text, null, null);
            }
        }
        else
            Log.d(TAG, "Unknown permission request code: " + requestCode);

    }
    private void startSMSActivity()
    {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + f.getPhone()));
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
    private void initializeViews() {
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);
        etURL = findViewById(R.id.etURL);
        etEmail = findViewById(R.id.etEmail);
        smsButt = findViewById(R.id.smsButt);
        callButt = findViewById(R.id.callButt);
        mailButt = findViewById(R.id.mailButt);
        wwwButt = findViewById(R.id.wwwButt);
    }

    private void setGUI()
    {
        etName.setText(f.getName());
        etPhone.setText(f.getPhone());
        cbFavorite.setChecked(f.isFavorite());
        etEmail.setText(f.getEmail());
        etURL.setText(f.getURL());
    }
}
