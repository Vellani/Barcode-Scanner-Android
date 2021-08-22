package com.example.itdscan.adapter;

import com.android.volley.VolleyError;

public interface TalkbackInterface {
    void respondGood(String response);
    void respondBad(VolleyError error);
}
