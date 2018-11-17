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

import ir.adad.client.Adad;

public class LoanActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Adad.initialize(getApplicationContext());
    setContentView(R.layout.activity_loan);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    Button btn_calculate = findViewById(R.id.btn_calculate);
    Button btn_delete = findViewById(R.id.btn_delete);

    final EditText loan_principal = findViewById(R.id.weight_gold);
    final EditText duration_month = findViewById(R.id.gold_price);
    final EditText percent_year = findViewById(R.id.make_price);

    final TextView amountInstallment = findViewById(R.id.amountInstallment);
    final TextView annualProfit = findViewById(R.id.annualProfit);
    final TextView monthlyProfit = findViewById(R.id.monthlyProfit);
    final TextView totalRefund = findViewById(R.id.totalRefund);
    final TextView totalInterest = findViewById(R.id.totalInterest);

    //تعریف فیلتر برای محدود کردن اعداد وارد شده توسط کاربر
    duration_month.setFilters(new InputFilter[]{new InputFilterNumber("1", "99999")});
    percent_year.setFilters(new InputFilter[]{new InputFilterPercent("1", "100")});

    // اگر می خواهید در جواب محاسبات دو رقم اعشار نیز نشان داده شود از فیلتر زیر استفاده کنید
    // DecimalFormat decimalFormat = new DecimalFormat("#,###" + ".##") for Example: 524,221.11
    final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    btn_calculate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (loan_principal.length() > 0 && duration_month.length() > 0 && percent_year.length() > 0) {

          double principal = Double.parseDouble(String.valueOf(loan_principal.getText()).replace(",", ""));
          int duratin = Integer.parseInt(String.valueOf(duration_month.getText()));
          double annualinterest = Double.parseDouble(String.valueOf(percent_year.getText()));

          // محاسبه مبلغ هر قسط
          double monthlypayment = G.getLoanMonthlyPerment(principal, duratin, annualinterest);
          String a = decimalFormat.format(monthlypayment) + " " + "تومان";
          amountInstallment.setText(a);

          // محاسبه کل بازپرداختی
          String b = decimalFormat.format(monthlypayment * duratin) + " " + "تومان";
          totalRefund.setText(b);

          // محاسبه بهره کل
          double c = (monthlypayment * duratin) - principal;
          String totalinterest = (decimalFormat.format(c) + " " + "تومان");
          totalInterest.setText(totalinterest);

          // محاسبه سود قسط ماهانه
          double d = c / duratin;
          String monthlyprofit = (decimalFormat.format(d) + " " + "تومان");
          monthlyProfit.setText(monthlyprofit);

          // محاسبه سود قسط سالانه
          double e = d * 12;
          String annualprofit = (decimalFormat.format(e) + " " + "تومان");
          annualProfit.setText(annualprofit);

        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(LoanActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }
      }
    });

    // دکمه حذف محتوای تمام فیلدها
    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loan_principal.setText("");
        duration_month.setText("");
        percent_year.setText("");

        amountInstallment.setText("");
        annualProfit.setText("");
        monthlyProfit.setText("");
        totalRefund.setText("");
        totalInterest.setText("");
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    loan_principal.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        loan_principal.removeTextChangedListener(this);
        String s = loan_principal.getText().toString();
        s = s.replace(",", "");
        if (s.length() > 0) {
          DecimalFormat sdd = new DecimalFormat("#,###");
          Double doubleNumber = Double.parseDouble(s);
          String format = sdd.format(doubleNumber);
          loan_principal.setText(format);
          loan_principal.setSelection(format.length());
        }
        loan_principal.addTextChangedListener(this);
      }
    });
  }
}