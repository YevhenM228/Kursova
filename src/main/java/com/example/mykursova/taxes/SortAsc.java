package com.example.mykursova.taxes;

import java.util.Comparator;

class SortAsc implements Comparator<Income> {
    @Override
    public int compare(Income o1, Income o2) {
        return (int)(o1.tax - o2.tax);
    }
}