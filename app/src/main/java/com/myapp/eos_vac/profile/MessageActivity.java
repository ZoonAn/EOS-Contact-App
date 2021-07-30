package com.myapp.eos_vac.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.eos_vac.R;
import com.myapp.eos_vac.data.DummyData;

public class MessageActivity extends AppCompatActivity {

    private TextView messageName, messagePhoneNum;
    private Button messageSend;
    private EditText messageContent;

    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Message");
        }

        messageName = (TextView) findViewById(R.id.message_tv_name);
        messagePhoneNum = (TextView) findViewById(R.id.message_tv_phonenum);
        messageSend = (Button) findViewById(R.id.message_btn_send);
        messageContent =(EditText) findViewById(R.id.message_et_content);

        messageName.setText(DummyData.dummyList.get(DummyData.getPage()).getName());
        messagePhoneNum.setText(DummyData.dummyList.get(DummyData.getPage()).getPhoneNum());

        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(
                            new String[]{Manifest.permission.SEND_SMS}, 2000);
                }
                else{
                    sendSMS();
                }
            }
        });
    }

    private void sendSMS(){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(messagePhoneNum.getText().toString(), null, messageContent.getText().toString(), null, null);

            Toast.makeText(MessageActivity.this, "Message Sent.", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS Failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                sendSMS();
            }
            else{
                Toast.makeText(this, "You have to grant the permission in order to send SMS", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
