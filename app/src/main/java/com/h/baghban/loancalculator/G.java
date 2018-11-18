package com.h.baghban.loancalculator;

public final class G {

  // تابع محاسبه اقساط وام بانکی
  public static Double getLoanMonthlyPerment(double principal, int duration, double annualInterest) {
    annualInterest = annualInterest / 12;
    double j = annualInterest / 100;
    return principal * j / (1 - Math.pow(1 + j, -duration));
  }

  // تابع محاسبه سود سپرده بانکی
  public static double getTotalProfit(double depositProfit, double profitPercent, int depositPeriod) {
    return (depositProfit * profitPercent * depositPeriod * 30) / 36500;
  }

  // تابع محاسبه قیمت طلای
  public static double getGoldPrice(double gold_weight, double gold_price, double make_price, int seller_percent, int vat_percent) {
    double totalGoldPrice = (gold_price + make_price) * gold_weight;
    double percent = seller_percent + vat_percent;
    return (totalGoldPrice + (totalGoldPrice * percent) / 100);
  }

  // تابع محاسبه کمیسیون خرید و فروش ملک
  // کمیسیون هر یک طرف
  public static double getBuyCommisionPerson(double total_transaction) {
    double commision = 0;
    // اگر مبلغ معامله کمتر یا مساوی پانصد میلیون بود کمیسیون نیم درصد حساب می شود
    if (total_transaction <= 500000000) {
      commision = (total_transaction * 0.005);
    }
    // اگر مبلغ معامله بیش از پانصد میلیون بود تا پانصد میلیون نیم درصد و مابقی آن 25 صدم درصد حساب می شوشد
    if (total_transaction > 500000000) {
      commision = ((500000000 * 0.005) + ((total_transaction - 500000000) * 0.0025));
    }
    return commision;
  }

  // مالیات ارزش افزوده هر یک طرف
  public static double getBuyVatPerson(double total_transaction, double vat_percent) {
    return ((getBuyCommisionPerson(total_transaction) * vat_percent) / 100);
  }

  // مبلغ کل پرداختی هر طرف
  public static double getBuyCommisionTotal(double total_transaction, double vat_percent) {
    return (getBuyCommisionPerson(total_transaction) + vat_percent);
  }

  // تابع محاسبه کمیسیون رهن و اجاره ملک
  // کمیسیون هر یک طرف
  public static double getRentCommisionPerson(double mortgagePrice, double rentPrice) {
    return ((mortgagePrice * 0.03) + rentPrice) * 0.25;
  }

  // مالیات ارزش افزوده هر یک طرف
  public static long getRentVatPerson(double mortgagePrice, double rentPrice, int vatrentpercent) {
    return (long) ((getRentCommisionPerson(mortgagePrice, rentPrice) * vatrentpercent) / 100);
  }

  // مبلغ کل پرداختی هر طرف
  public static double getRentCommisionTotal(double mortgagePrice, double rentPrice, int vatrentpercent) {
    return getRentCommisionPerson(mortgagePrice, rentPrice) + getRentVatPerson(mortgagePrice, rentPrice, vatrentpercent);
  }

}