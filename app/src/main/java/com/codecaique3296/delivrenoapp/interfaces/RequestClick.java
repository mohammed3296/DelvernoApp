package com.codecaique3296.delivrenoapp.interfaces;

import android.view.View;

public interface RequestClick {

    void accept(View v, int position);

    void receipt(View v, int position);

    void delete(View v, int position);

    void deleive(View v, int position);

    void undeleive(View v, int position);

    void unaccept(View v, int position);

    void unreceipt(View v, int position);
}
