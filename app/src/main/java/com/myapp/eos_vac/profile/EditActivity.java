package com.myapp.eos_vac.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.myapp.eos_vac.DialActivity;
import com.myapp.eos_vac.R;
import com.myapp.eos_vac.data.DummyData;
import com.myapp.eos_vac.data.Profile;

public class EditActivity extends AppCompatActivity {

    private ImageView editImg;
    private TextInputLayout editNameTil;
    private EditText editPhoneNumEt, editEmailEt;



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Edit Profile");
        }

        editPhoneNumEt = (EditText) findViewById(R.id.edit_et_phonenum);
        editEmailEt = (EditText) findViewById(R.id.edit_et_email);
        editNameTil = (TextInputLayout) findViewById(R.id.edit_til_name);

        String name_extra, phonenum_extra, email_extra;
        name_extra = DummyData.dummyList.get(DummyData.getPage()).getName();
        phonenum_extra = DummyData.dummyList.get(DummyData.getPage()).getPhoneNum();
        email_extra = DummyData.dummyList.get(DummyData.getPage()).getEmail();

        editNameTil.getEditText().setText(name_extra);
        editPhoneNumEt.setText(phonenum_extra);
        editEmailEt.setText(email_extra);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

                return true;
            case R.id.save_data:
                String newName, newPhoneNum, newEmail;
                newPhoneNum = editPhoneNumEt.getText().toString();
                newEmail = editEmailEt.getText().toString();
                newName = editNameTil.getEditText().getText().toString();

                DummyData.dummyList.get(DummyData.getPage()).setName(newName);
                DummyData.dummyList.get(DummyData.getPage()).setPhoneNum(DialActivity.changeToDial(newPhoneNum));
                DummyData.dummyList.get(DummyData.getPage()).setEmail(newEmail);

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
