package com.h.baghban.loancalculator;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    TextView version_name = findViewById(R.id.version_name);
    Button btn_loan = findViewById(R.id.btn_loan);
    Button btn_deposit = findViewById(R.id.btn_deposit);
    Button btn_gold = findViewById(R.id.btn_gold);
    Button btn_estate_buy = findViewById(R.id.btn_estate_buy);
    Button btn_estate_rent = findViewById(R.id.btn_estate_rent);

    btn_loan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LoanActivity.class);
        startActivity(intent);
      }
    });

    btn_deposit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DepositProfitActivity.class);
        startActivity(intent);
      }
    });
    btn_gold.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, GoldActivity.class);
        startActivity(intent);
      }
    });
    btn_estate_buy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, EstateBuyActivity.class);
        startActivity(intent);
      }
    });
    btn_estate_rent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, EstateRentActivity.class);
        startActivity(intent);
      }
    });

    PackageInfo pinfo = null;
    try {
      pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    int versionNumber = pinfo.versionCode;
    version_name.setText("نسخه  " + pinfo.versionName);
  }
}