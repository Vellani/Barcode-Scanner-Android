package com.example.itdscan.web;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.itdscan.adapter.TalkbackInterface;
import com.example.itdscan.entity.Barcode;

import static com.example.itdscan.constants.Constants.URL_TEMPLATE;

public class Request {

    public static void call(Context context, Barcode barcode, TalkbackInterface listener) {

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // TODO Construct URL from Barcode
        //String url = String.format(URL_TEMPLATE, barcode.getWarehouseBarcode(), barcode.getProductBarcode());
        String url = String.format(URL_TEMPLATE);

        StringRequest stringRequest =
                new StringRequest(url,
                        listener::respondGood,
                        listener::respondBad
                );

        queue.add(stringRequest);
    }
}
