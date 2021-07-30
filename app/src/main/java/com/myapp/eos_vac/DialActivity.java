package com.myapp.eos_vac;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.eos_vac.data.DummyData;
import com.myapp.eos_vac.profile.addProfileActivity;
import com.myapp.eos_vac.profile.contactActivity;

public class DialActivity extends AppCompatActivity {
    private TextView enteredNumTv;
    private Button listBtn;
    private Button addBtn;
    private Button[] btn;
    private Button starBtn, sharpBtn, callBtn, plusBtn, delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteredNumTv = findViewById(R.id.enteredNum_tv);
        listBtn = findViewById(R.id.list_btn);
        addBtn = findViewById(R.id.add_btn);
        starBtn = findViewById(R.id.btnStar);
        sharpBtn = findViewById(R.id.btnSharp);
        callBtn = findViewById(R.id.btnCall);
        plusBtn = findViewById(R.id.btnPlus);
        delBtn = findViewById(R.id.btnDel);

        btn = new Button[10];
        for (int i=0; i<10; i++){
            btn[i] = findViewById(getResourceID("btn"+i, "id", this));
        }
        enteredNumTv.setText("");
        for(int i=0;i<10;i++){
            final int finalI=i;
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enteredNumTv.setText(changeToDial(enteredNumTv.getText().toString()+finalI));
                }
            });
        }

        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredNumTv.setText(changeToDial(enteredNumTv.getText().toString()+"*"));
            }
        });
        sharpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredNumTv.setText(changeToDial(enteredNumTv.getText().toString()+"#"));
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredNumTv.setText(changeToDial(enteredNumTv.getText().toString()+"+"));
            }
        });
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DummyData.dummyList.size() != 0){
                    Intent intent = new Intent(DialActivity.this, contactActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(DialActivity.this, "No one in the contact list.", Toast.LENGTH_LONG).show();
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialActivity.this, addProfileActivity.class);
                intent.putExtra("phone_num", enteredNumTv.getText().toString());
                startActivity(intent);
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enteredNumTv.getText().toString().length() != 0) {
                    enteredNumTv.setText(changeToDial(enteredNumTv.getText().toString().substring(0, enteredNumTv.getText().toString().length() - 1)));
                }
                else{
                    Toast.makeText(DialActivity.this, "there is no number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enteredNumTv.getText()));
                        startActivity(intent);
                    }
                } // 마시멜로우 미만의 버전일 때
                else {
                    // 즉시 실행
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enteredNumTv.getText()));
                    startActivity(intent);
                }

            }
        });
    }
    /**
     * requestPermission()함수 수행 이후 할 동작 지정.
     * requestPermission에서 ALLOW를 선택할 경우 onRequestPermissionsResult 함수를 호출한다.
     * intent에 들어온 빨간 줄은 무시해도 된다.
     * ACTION_CALL의 경우 퍼미션 체크를 해주라고 뜨는건데
     * 아래 함수에서 동작은 퍼미션 체크 이후 수행하는 것이기에 상관 없음.
     * 따라서 SuppressLint로 warning은 간단하게 무시해준다.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            //전달된 requestCode가 1000일 경우(임의로 지정 가능)
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 동의 및 로직 처리
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enteredNumTv.getText()));
                    startActivity(intent);
                } else {
                    // 동의 안함
                    Toast.makeText(DialActivity.this, "Need permission allowed to Call!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public int getResourceID(final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType, ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + resName);
        } else {
            return ResourceID;
        }
    }

    public static String changeToDial(String a){
        String b;
        if(a.contains("*") || a.contains("#") || a.contains("+") ){
            b = a.replace("-", "");
        }
        else if(a.length()>13){
            b = a.replace("-", "");
        }

        else if(a.length()>3 && a.length()<8){
            a = a.replace("-", "");
            b = a.substring(0,3)+"-"+a.substring(3);

        }
        else if(a.length()>8 && a.length()<13){
            a = a.replace("-", "");
            b = a.substring(0,3)+"-"+a.substring(3,7)+"-"+a.substring(7);
        }
/*
        else if(a.length() == 3 || a.length() == 8){
                b = a+ "-";
        }
*/
        else b = a;

        return b;
    }
}
