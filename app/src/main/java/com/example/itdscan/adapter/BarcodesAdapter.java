package com.example.itdscan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.itdscan.R;
import com.example.itdscan.entity.Barcode;
import com.example.itdscan.web.Request;

import java.util.List;

import androidx.annotation.NonNull;

import static com.example.itdscan.constants.Constants.SERVER_RESPONSE_GOOD;

public class BarcodesAdapter extends ArrayAdapter<Barcode> {

    private final Context context;
    private CountingInterface cListener;

    public BarcodesAdapter(@NonNull Context context, int layoutID, List<Barcode> listOfBarcodes, CountingInterface cListener) {
        super(context, layoutID, listOfBarcodes);
        this.context = context;
        this.cListener = cListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = (convertView == null ? inflater.inflate(R.layout.card_barcode, parent, false) : convertView);

        Barcode barcode = getItem(position);

        TextView product = convertView.findViewById(R.id.card_t_product);
        product.setText(barcode.getProductBarcode());

        ImageView imageView = convertView.findViewById(R.id.card_image);
        imageView.setImageResource(barcode.getPicture());

        if (!barcode.hasCalled()) {
            barcode.called();

            Request.call(context, barcode, new TalkbackInterface() {
                @Override
                public void respondGood(String response) {
                    setPicture(response.equals(SERVER_RESPONSE_GOOD));
                }

                @Override
                public void respondBad(VolleyError error) {
                    setPicture(false);
                }

                private void setPicture(boolean result) {
                    barcode.setPicture(result);
                    imageView.setImageResource(barcode.getPicture());
                    if (result) cListener.increaseCountOfGoodResponses();
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
