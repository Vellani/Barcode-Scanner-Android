package com.example.itdscan.repository;

import com.example.itdscan.entity.Barcode;

import java.util.ArrayList;
import java.util.List;

import static com.example.itdscan.constants.Constants.*;
import static com.example.itdscan.constants.Exceptions.*;

public class BarcodeRepository {

    private String warehouseBarcode;
    private final List<Barcode> barcodeList;

    public BarcodeRepository(String wareBarcode) {
        this.barcodeList = new ArrayList<>();
        setWareBarcode(wareBarcode);
    }

    public void setWareBarcode(String wareBarcode) {
        if (wareBarcode.length() != WAREHOUSE_CODE_LENGTH) throw new IllegalArgumentException(BARCODE_NOT_VALID);
        this.warehouseBarcode = wareBarcode;
    }

    public void addBarcode(Barcode barcode) {
        this.barcodeList.add(0, barcode);
    }

    public List<Barcode> getBarcodeList() {
        return barcodeList;
    }

    @Override
    public String toString() {
        return warehouseBarcode;
    }
}
