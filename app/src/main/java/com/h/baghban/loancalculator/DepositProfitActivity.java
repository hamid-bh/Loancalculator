package com.h.baghban.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DepositProfitActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_deposit_profit);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    Button btn_calculate = findViewById(R.id.btn_calculate);
    Button btn_delete = findViewById(R.id.btn_delete);

    final EditText deposit_profit = findViewById(R.id.weight_gold);
    final EditText percent_year = findViewById(R.id.gold_price);
    final EditText deposit_period = findViewById(R.id.make_price);

    final TextView total_profit = findViewById(R.id.total_profit);
    final TextView monthly_profit = findViewById(R.id.monthly_profit);
    final TextView daily_profit = findViewById(R.id.daily_profit);

    //تعریف فیلتر برای محدود کردن اعداد وارد شده توسط کاربر
    percent_year.setFilters(new InputFilter[]{new InputFilterNumber("1", "100")});
    deposit_period.setFilters(new InputFilter[]{new InputFilterPercent("1", "99999")});

    // اگر می خواهید در جواب محاسبات دو رقم اعشار نیز نشان داده شود از فیلتر زیر استفاده کنید
    // DecimalFormat decimalFormat = new DecimalFormat("#,###" + ".##") for Example: 524,221.11
    final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    btn_calculate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (deposit_profit.length() > 0 && deposit_period.length() > 0 && percent_year.length() > 0) {

          double depositprofit = Double.parseDouble(String.valueOf(deposit_profit.getText()).replace(",", ""));
          double percentyear = Double.parseDouble(String.valueOf(percent_year.getText()));
          int depositperiod = Integer.parseInt(String.valueOf(deposit_period.getText()));

          // محاسبه کل سود سپرده گذاری
          double totalprofit = G.getTotalProfit(depositprofit, percentyear, depositperiod);
          String a = decimalFormat.format(totalprofit) + " " + "تومان";
          total_profit.setText(a);

          // محاسبه متوسط سود ماهانه
          String b = decimalFormat.format(totalprofit / depositperiod) + " " + "تومان";
          monthly_profit.setText(b);

          // محاسبه متوسط سود روزانه
          double c = ((totalprofit / depositperiod) / 30);
          String totalinterest = (decimalFormat.format(c) + " " + "تومان");
          daily_profit.setText(totalinterest);

        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(DepositProfitActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }
      }
    });

    // دکمه حذف محتوای تمام فیلدها
    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deposit_profit.setText("");
        deposit_period.setText("");
        percent_year.setText("");

        total_profit.setText("");
        monthly_profit.setText("");
        daily_profit.setText("");
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    deposit_profit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        deposit_profit.removeTextChangedListener(this);
        String s = deposit_profit.getText().toString();
        s = s.replace(",", "");
        if (s.length() > 0) {
          DecimalFormat sdd = new DecimalFormat("#,###");
          Double doubleNumber = Double.parseDouble(s);
          String format = sdd.format(doubleNumber);
          deposit_profit.setText(format);
          deposit_profit.setSelection(format.length());
        }
        deposit_profit.addTextChangedListener(this);
      }
    });

  }

}