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
import java.text.NumberFormat;
import java.util.Locale;

import ir.adad.client.Adad;

public class EstateBuyActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Adad.initialize(getApplicationContext());
    setContentView(R.layout.activity_estate_buy);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    Button btn_calculate = findViewById(R.id.btn_calculate);
    Button btn_delete = findViewById(R.id.btn_delete);

    final EditText total_transaction = findViewById(R.id.weight_gold);
    final EditText vat_percent = findViewById(R.id.make_price);

    final TextView commission_person = findViewById(R.id.commission_person);
    final TextView vat_person = findViewById(R.id.vat_person);
    final TextView commission_total = findViewById(R.id.commission_total);

    //تعریف فیلتر برای محدود کردن اعداد وارد شده توسط کاربر
    vat_percent.setFilters(new InputFilter[]{new InputFilterPercent("1", "100")});

    // اگر می خواهید در جواب محاسبات دو رقم اعشار نیز نشان داده شود از فیلتر زیر استفاده کنید
    // DecimalFormat decimalFormat = new DecimalFormat("#,###" + ".##") for Example: 524,221.11
    final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    btn_calculate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (total_transaction.length() > 0 && vat_percent.length() > 0) {

          double totaltransaction = Double.parseDouble(String.valueOf(total_transaction.getText()).replace(",", ""));
          double vatpercent = Double.parseDouble(String.valueOf(vat_percent.getText()));

          // محاسبه مبلغ کمیسیون هر یک طرف
          double commissionPerson = G.getBuyCommisionPerson(totaltransaction);
          String a = decimalFormat.format(commissionPerson) + " " + "تومان";
          commission_person.setText(a);

          // محاسبه مالیات ارزش افزوده هر یک طرف
          double vatPerson = G.getBuyVatPerson(totaltransaction, vatpercent);
          String b = decimalFormat.format(vatPerson) + " " + "تومان";
          vat_person.setText(b);

          // محاسبه مبلغ کل پرداختی هر طرف
          double commissionTotal = G.getBuyCommisionTotal(totaltransaction, vatPerson);
          String c = (decimalFormat.format(commissionTotal) + " " + "تومان");
          commission_total.setText(c);

        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(EstateBuyActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }
      }
    });

    // دکمه حذف محتوای تمام فیلدها
    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        total_transaction.setText("");
        vat_percent.setText("");

        commission_person.setText("");
        vat_person.setText("");
        commission_total.setText("");
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    total_transaction.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        total_transaction.removeTextChangedListener(this);

        try {
          String originalString = editable.toString();

          Long longval;
          if (originalString.contains(",")) {
            originalString = originalString.replaceAll(",", "");
          }
          longval = Long.parseLong(originalString);

          DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
          formatter.applyPattern("#,###,###,###");
          String formattedString = formatter.format(longval);

          //setting text after format to EditText
          total_transaction.setText(formattedString);
          total_transaction.setSelection(total_transaction.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        total_transaction.addTextChangedListener(this);
      }
    });
  }
}