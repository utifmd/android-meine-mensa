package com.heinrichreimer.meinemensa.model;

import com.heinrichreimer.meinemensa.annotations.PriceCategory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import io.realm.RealmObject;

public class Price extends RealmObject {
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final DecimalFormat GERMAN_NUMBER_FORMAT = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);

    static {
        GERMAN_NUMBER_FORMAT.setMinimumFractionDigits(2);
        GERMAN_NUMBER_FORMAT.setMaximumFractionDigits(2);
    }

    static final String STUDENTS = "students";
    static final String EMPLOYEES = "employees";
    static final String GUESTS = "guests";

    private int students; //in Euro-cents
    private int employees; //in Euro-cents
    private int guests; //in Euro-cents

    public Price() {
    }

    private Price(Builder builder) {
        this.students = builder.students;
        this.employees = builder.employees;
        this.guests = builder.guests;
    }

    public String forCategory(@PriceCategory int category) {
        switch (category) {
            case PriceCategory.STUDENTS:
                return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(students)
                        .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
            case PriceCategory.EMPLOYEES:
                return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(employees)
                        .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
            case PriceCategory.GUESTS:
            default:
                return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(guests)
                        .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
        }
    }

    String forStudents() {
        return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(students)
                .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
    }

    String forEmployees() {
        return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(employees)
                .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
    }

    String forGuests() {
        return GERMAN_NUMBER_FORMAT.format(BigDecimal.valueOf(guests)
                .divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
    }

    public static class Builder {
        private int students;
        private int employees;
        private int guests;

        public Builder forCategory(String price, @PriceCategory int category) {
            try {
                BigDecimal decimal = new BigDecimal(GERMAN_NUMBER_FORMAT.parse(price).toString());
                decimal = decimal.multiply(HUNDRED);
                switch (category) {
                    case PriceCategory.STUDENTS:
                        this.students = decimal.intValue();
                        return this;
                    case PriceCategory.EMPLOYEES:
                        this.employees = decimal.intValue();
                        return this;
                    case PriceCategory.GUESTS:
                    default:
                        this.guests = decimal.intValue();
                        return this;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder forStudents(String price) {
            try {
                BigDecimal decimal = new BigDecimal(GERMAN_NUMBER_FORMAT.parse(price).toString());
                decimal = decimal.multiply(HUNDRED);
                this.students = decimal.intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder forEmployees(String price) {
            try {
                BigDecimal decimal = new BigDecimal(GERMAN_NUMBER_FORMAT.parse(price).toString());
                decimal = decimal.multiply(HUNDRED);
                this.employees = decimal.intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder forGuests(String price) {
            try {
                BigDecimal decimal = new BigDecimal(GERMAN_NUMBER_FORMAT.parse(price).toString());
                decimal = decimal.multiply(HUNDRED);
                this.guests = decimal.intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Price build() {
            return new Price(this);
        }
    }
}