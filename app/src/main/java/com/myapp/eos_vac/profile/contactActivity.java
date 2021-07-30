package com.myapp.eos_vac.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.eos_vac.R;
import com.myapp.eos_vac.data.DummyData;
import com.myapp.eos_vac.data.Profile;

public class contactActivity extends AppCompatActivity {

    private ImageView profileImg;
    private TextView profileName, profilePhoneNum, profileEmail;
    private ImageButton backBtn, nextBtn;
    private TextView page;
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Profile");
        }

        profileImg = (ImageView) findViewById(R.id.contact_iv_avatar);
        profilePhoneNum = (TextView) findViewById(R.id.contact_tv_rphonenum);
        profileName = (TextView) findViewById(R.id.contact_tv_rname);
        profileEmail = (TextView) findViewById(R.id.contact_tv_remail);

        backBtn = (ImageButton) findViewById(R.id.contact_ib_back);
        nextBtn = (ImageButton) findViewById(R.id.contact_ib_next);
        page = (TextView) findViewById(R.id.contact_tv_page);

        profileName.setText(DummyData.dummyList.get(DummyData.getPage()).getName());
        profilePhoneNum.setText(DummyData.dummyList.get(DummyData.getPage()).getPhoneNum());
        profileEmail.setText(DummyData.dummyList.get(DummyData.getPage()).getEmail());

        page.setText((DummyData.getPage()+1)+"/"+DummyData.dummyList.size());

        if(DummyData.getPage() == 0){
            backBtn.setVisibility(View.GONE);
        }
        if(DummyData.getPage() == DummyData.dummyList.size()-1){
            nextBtn.setVisibility(View.GONE);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyData.pageDown();
                Intent intent = new Intent(contactActivity.this, contactActivity.class);
                startActivity(intent);
                finish();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyData.pageUp();
                Intent intent = new Intent(contactActivity.this, contactActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile_call:
// 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /**
                     * 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다.
                     * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다. */
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    /**
                     * 패키지는 안드로이드 어플리케이션의 아이디이다.
                     * 현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다. */
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        /**
                         * shouldShowRequestPermissionRationale()는
                         * 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                         * 거부한적이 있으면 True를 리턴하고 * 거부한적이 없으면 False를 리턴한다.
                         * 이는 한번 이상 거부하면 왜 필요한지 알리는 메시지를 띄우기 위한 알고리즘이나,
                         * 현재는 필요없어 참고만 하라고 주석처리해두었다. */
//                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
//                            // (이미 한번 이상 권한요청을 거절했을 경우에 해당)
//                            AlertDialog.Builder dialog = new AlertDialog.Builder(DialActivity.this);
//                            dialog.setTitle("권한이 필요합니다.")
//                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
//                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            /**
//                                             * 새로운 인스턴스(onClickListener)를 생성했기 때문에
//                                             * 버전체크를 다시 해준다. */
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                // CALL_PHONE 권한을 Android OS에 요청한다.
//                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
//                                            }
//                                        }
//                                    })
//                                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Toast.makeText(DialActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .create()
//                                    .show();
//                        }
//                        else { // (최초로 권한요청하는 경우에 해당) CALL_PHONE 권한을 Android OS에 요청한다.
//                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
//                        }
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);


                    } // CALL_PHONE의 권한이 있을 때
                    else { // 즉시 실행
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + profilePhoneNum.getText()));
                        startActivity(intent);
                    }
                } // 마시멜로우 미만의 버전일 때
                else {
                    // 즉시 실행
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + profilePhoneNum.getText()));
                    startActivity(intent);
                }
                return true;
            case R.id.profile_message:
                Intent intent = new Intent(contactActivity.this, MessageActivity.class);
                startActivity(intent);

                return true;
            case R.id.profile_edit:
                Intent intent_e = new Intent(contactActivity.this, EditActivity.class);
                startActivity(intent_e);

                return true;
            case R.id.profile_delete:
                    DummyData.dummyList.remove(DummyData.getPage());
                    finish();



                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        profileImg = (ImageView) findViewById(R.id.contact_iv_avatar);
        profilePhoneNum = (TextView) findViewById(R.id.contact_tv_rphonenum);
        profileName = (TextView) findViewById(R.id.contact_tv_rname);
        profileEmail = (TextView) findViewById(R.id.contact_tv_remail);

        backBtn = (ImageButton) findViewById(R.id.contact_ib_back);
        nextBtn = (ImageButton) findViewById(R.id.contact_ib_next);
        page = (TextView) findViewById(R.id.contact_tv_page);

        profileName.setText(DummyData.dummyList.get(DummyData.getPage()).getName());
        profilePhoneNum.setText(DummyData.dummyList.get(DummyData.getPage()).getPhoneNum());
        profileEmail.setText(DummyData.dummyList.get(DummyData.getPage()).getEmail());

        page.setText((DummyData.getPage() + 1) + "/" + DummyData.dummyList.size());

        if (DummyData.getPage() == 0) {
            backBtn.setVisibility(View.GONE);
        }
        if (DummyData.getPage() == DummyData.dummyList.size() - 1) {
            nextBtn.setVisibility(View.GONE);
        }
    }
}
