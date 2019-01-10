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

public class GoldActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Adad.initialize(getApplicationContext());
    setContentView(R.layout.activity_gold);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    Button btn_calculate = findViewById(R.id.btn_calculate);
    Button btn_delete = findViewById(R.id.btn_delete);

    final EditText gold_weight = findViewById(R.id.weight_gold);
    final EditText gold_price = findViewById(R.id.gold_price);
    final EditText make_price = findViewById(R.id.make_price);
    final EditText seller_percent = findViewById(R.id.seller_percent);
    final EditText vat_percent = findViewById(R.id.vat_percent);

    final TextView total_gold_price = findViewById(R.id.total_gold_price);
    final TextView gram_gold_price = findViewById(R.id.gram_gold_price);

    //تعریف فیلتر برای محدود کردن اعداد وارد شده توسط کاربر
    gold_weight.setFilters(new InputFilter[]{new InputFilterNumber("1", "100000")});
    seller_percent.setFilters(new InputFilter[]{new InputFilterPercent("1", "100")});
    vat_percent.setFilters(new InputFilter[]{new InputFilterPercent("1", "100")});

    // اگر می خواهید در جواب محاسبات دو رقم اعشار نیز نشان داده شود از فیلتر زیر استفاده کنید
    // DecimalFormat decimalFormat = new DecimalFormat("#,###" + ".##") for Example: 524,221.11
    final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    btn_calculate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (gold_weight.length() > 0 && gold_price.length() > 0 && make_price.length() > 0 && seller_percent.length() > 0 && vat_percent.length() > 0) {

          double goldweight = Double.parseDouble(String.valueOf(gold_weight.getText()).replace(",", ""));
          double goldprice = Double.parseDouble(String.valueOf(gold_price.getText()).replace(",", ""));
          double makeprice = Double.parseDouble(String.valueOf(make_price.getText()).replace(",", ""));
          double sellerpercent = Double.parseDouble(String.valueOf(seller_percent.getText()));
          double vatpercent = Double.parseDouble(String.valueOf(vat_percent.getText()));

          // محاسبه مبلغ کل طلا
          double totalgoldprice = G.getGoldPrice(goldweight, goldprice, makeprice, sellerpercent, vatpercent);
          String a = decimalFormat.format(totalgoldprice) + " " + "تومان";
          total_gold_price.setText(a);

          // محاسبه مبلغ هر گرم طلا
          if (totalgoldprice > 0) {
            double gramgoldprice = (totalgoldprice / goldweight);
            String b = decimalFormat.format(gramgoldprice) + " " + "تومان";
            gram_gold_price.setText(b);
          }


        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(GoldActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }
      }
    });

    // دکمه حذف محتوای تمام فیلدها
    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        gold_weight.setText("");
        gold_price.setText("");
        make_price.setText("");
        seller_percent.setText("");
        vat_percent.setText("");

        total_gold_price.setText("");
        gram_gold_price.setText("");
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    gold_price.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        gold_price.removeTextChangedListener(this);

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
          gold_price.setText(formattedString);
          gold_price.setSelection(gold_price.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        gold_price.addTextChangedListener(this);
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    make_price.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        make_price.removeTextChangedListener(this);

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
          make_price.setText(formattedString);
          make_price.setSelection(make_price.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        make_price.addTextChangedListener(this);
      }
    });

  }
}
