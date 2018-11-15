package com.h.baghban.loancalculator;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterPercent implements InputFilter {

  private double min, max;

  public InputFilterPercent(double min, double max) {
    this.min = min;
    this.max = max;
  }

  public InputFilterPercent(String min, String max) {
    this.min = Double.parseDouble(min);
    this.max = Double.parseDouble(max);
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
    try {
      double input = Double.parseDouble(dest.toString() + source.toString());
      if (isInRange(min, max, input)) return null;
    } catch (NumberFormatException nfe) {
      return null;
    }
    return "";
  }

  private boolean isInRange(double a, double b, double c) {
    return b > a ? c >= a && c <= b : c >= b && c <= a;
  }
}