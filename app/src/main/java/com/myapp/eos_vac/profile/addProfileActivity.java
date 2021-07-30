package com.myapp.eos_vac.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.myapp.eos_vac.DialActivity;
import com.myapp.eos_vac.R;
import com.myapp.eos_vac.data.DummyData;
import com.myapp.eos_vac.data.Profile;

public class addProfileActivity extends AppCompatActivity {

    private ImageView profileImg;
    private EditText phoneNum_et, email_et;
    private TextInputLayout name_til;

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Add Profile");
        }

        phoneNum_et = (EditText) findViewById(R.id.add_et_phonenum);
        email_et = (EditText) findViewById(R.id.add_et_email);
        name_til = (TextInputLayout) findViewById(R.id.add_til_name);

        String phonenum_extra = getIntent().getStringExtra("phone_num");
        phoneNum_et.setText(phonenum_extra);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_data:
                String newName, newPhoneNum, newEmail;
                newPhoneNum = phoneNum_et.getText().toString();
                newEmail = email_et.getText().toString();
                newName = name_til.getEditText().getText().toString();

                Profile newProfile = new Profile(newName, DialActivity.changeToDial(newPhoneNum), newEmail);

                DummyData.dummyList.add(newProfile);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
