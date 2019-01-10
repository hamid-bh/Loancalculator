package com.h.baghban.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import ir.adad.client.Adad;

public class EstateRentActivity extends AppCompatActivity {
  public double rentprice;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Adad.initialize(getApplicationContext());
    setContentView(R.layout.activity_estate_rent);
    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    final CheckBox chk_full_mortgage = findViewById(R.id.chk_full_mortgage);

    final EditText mortagage_price = findViewById(R.id.mortagage_price);
    final EditText rent_price = findViewById(R.id.rent_price);
    final EditText vat_percent = findViewById(R.id.vat_percent);

    final TextView vat_person = findViewById(R.id.vat_person);
    final TextView commission_person = findViewById(R.id.commission_person);
    final TextView total_person = findViewById(R.id.total_person);

    Button btn_calculate = findViewById(R.id.btn_calculate);
    Button btn_delete = findViewById(R.id.btn_delete);

    //تعریف فیلتر برای محدود کردن اعداد وارد شده توسط کاربر
    vat_percent.setFilters(new InputFilter[]{new InputFilterPercent("1", "100")});

    // اگر می خواهید در جواب محاسبات دو رقم اعشار نیز نشان داده شود از فیلتر زیر استفاده کنید
    // DecimalFormat decimalFormat = new DecimalFormat("#,###" + ".##") for Example: 524,221.11
    final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    // اگر تیک رهن کامل زده شود فیلد اجاره ماهیانه غیرفعال می شود
    chk_full_mortgage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (chk_full_mortgage.isChecked()) {
          rent_price.setEnabled(false);
        } else {
          rent_price.setEnabled(true);
        }
      }
    });

    // دکمه محاسبه
    btn_calculate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        if (chk_full_mortgage.isChecked()) {
          rentprice = 0;
        } else if (rent_price.length() > 0) {
          rentprice = Double.parseDouble(String.valueOf(rent_price.getText()).replace(",", ""));
        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(EstateRentActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }

        if (mortagage_price.length() > 0 && vat_percent.length() > 0) {

          double mortagageprice = Double.parseDouble(String.valueOf(mortagage_price.getText()).replace(",", ""));
          int vatpercent = Integer.parseInt(String.valueOf(vat_percent.getText()));

          // کمیسیون هر یک طرف
          double rentvatperson = G.getRentVatPerson(mortagageprice, rentprice, vatpercent);
          String a = decimalFormat.format(rentvatperson) + " " + "تومان";
          vat_person.setText(a);

          // مالیات ارزش افزوده هر یک طرف
          double rentcommisionperson = G.getRentCommisionPerson(mortagageprice, rentprice);
          String b = decimalFormat.format(rentcommisionperson) + " " + "تومان";
          commission_person.setText(b);

          // مبلغ کل پرداختی هر طرف
          double rentcommisiontotal = G.getRentCommisionTotal(mortagageprice, rentprice, vatpercent);
          String c = (decimalFormat.format(rentcommisiontotal) + " " + "تومان");
          total_person.setText(c);

        } else {
          // اگر فیلدها خالی باشد این پیام نمایش داده می شود
          Toast.makeText(EstateRentActivity.this, "لطفاً همه گزینه ها را پر کنید!", Toast.LENGTH_LONG).show();
        }

      }
    });

    // دکمه حذف محتوای تمام فیلدها
    btn_delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mortagage_price.setText("");
        rent_price.setText("");
        vat_percent.setText("");
        vat_person.setText("");
        commission_person.setText("");
        total_person.setText("");
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    mortagage_price.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        mortagage_price.removeTextChangedListener(this);

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
          mortagage_price.setText(formattedString);
          mortagage_price.setSelection(mortagage_price.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        mortagage_price.addTextChangedListener(this);
      }
    });

    // جدا کردن 3 رقم 3 رقم اعداد هنگام نوشتن در تکست باکس
    rent_price.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        rent_price.removeTextChangedListener(this);

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
          rent_price.setText(formattedString);
          rent_price.setSelection(rent_price.getText().length());
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }

        rent_price.addTextChangedListener(this);
      }
    });

  }
}