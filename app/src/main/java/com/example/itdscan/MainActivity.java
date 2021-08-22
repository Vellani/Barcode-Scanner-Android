package com.example.itdscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.example.itdscan.adapter.BarcodesAdapter;
import com.example.itdscan.adapter.CountingInterface;
import com.example.itdscan.entity.Barcode;
import com.example.itdscan.repository.BarcodeRepository;

import static com.example.itdscan.constants.Constants.*;
import static com.example.itdscan.constants.Exceptions.*;
import static com.example.itdscan.util.ErrorHandler.logError;

public class MainActivity extends AppCompatActivity {

    private TextView warehouse, counter;
    private BarcodeRepository bRepo;
    private ListView listViewOfBarcodes;
    private BarcodesAdapter barcodesAdapter;
    private int goodServerResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) savedInstanceState = new Bundle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goodServerResponses = 0;
        setUpViews();
    }

    private void setUpViews() {
        warehouse = findViewById(R.id.t_warehouse_code);
        warehouse.setText(String.format(WAREHOUSE_CODE, ""));

        counter = findViewById(R.id.t_total_scanned);
        counter.setText(String.format("%d", goodServerResponses));

        listViewOfBarcodes = findViewById(R.id.list_barcodes);

        /*findViewById(R.id.b_debug).setOnClickListener(this);
        findViewById(R.id.b_debug_random).setOnClickListener(this);*/
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getAction() == KeyEvent.ACTION_MULTIPLE) sortBarcode(e.getCharacters());
        return super.dispatchKeyEvent(e);
    }

    private void sortBarcode(String barcode) {
        try {
            if (barcode.length() == WAREHOUSE_CODE_LENGTH) {
                // TODO figure more elegant reset
                goodServerResponses = 0;
                counter.setText(String.format("%d", goodServerResponses));
                listViewOfBarcodes.setAdapter(null);
                bRepo = new BarcodeRepository(barcode);
                warehouse.setText(String.format(WAREHOUSE_CODE, bRepo.toString()));
            } else {
                Barcode aBarcode = new Barcode(bRepo.toString(), barcode);
                collectBarcode(aBarcode);
            }
        } catch (Exception e) {
            logError(getApplicationContext(), e.getMessage());
        }
    }

    private void collectBarcode(Barcode barcode) {
        if (bRepo == null) {
            throw new IllegalArgumentException(NO_WAREHOUSE_SELECTED);
        } else {
            bRepo.addBarcode(barcode);
            updateList();
        }
    }

    private void updateList() {
        if (listViewOfBarcodes.getAdapter() == null) {
            barcodesAdapter = new BarcodesAdapter(getApplicationContext(),
                    R.layout.card_barcode,
                    bRepo.getBarcodeList(),
                    new CountingInterface() {
                        @Override
                        public void increaseCountOfGoodResponses() {
                            goodServerResponses++;
                            counter.setText(String.format("%d", goodServerResponses));
                        }
                    });

            listViewOfBarcodes.setAdapter(barcodesAdapter);
        } else {
            barcodesAdapter.notifyDataSetChanged();
        }

    }


}