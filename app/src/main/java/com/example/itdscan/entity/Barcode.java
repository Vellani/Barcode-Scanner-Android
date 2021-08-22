package com.example.itdscan.entity;

import com.example.itdscan.R;

import static com.example.itdscan.constants.Constants.*;
import static com.example.itdscan.constants.Exceptions.BARCODE_NOT_VALID;

public class Barcode {

    private static final int DEFAULT_PICTURE = R.drawable.ic_wait;

    private final String warehouseBarcode;
    private String productBarcode;
    private int picture;
    private boolean called;

    public Barcode(String warehouseBarcode,String productBarcode) {
        setProductBarcode(productBarcode);
        this.warehouseBarcode = warehouseBarcode;
        this.picture = DEFAULT_PICTURE;
        this.called = false;
    }

    private void setProductBarcode(String productBarcode) {
        if (productBarcode.length() != PRODUCT_LENGTH_ONE &&
                productBarcode.length() != PRODUCT_LENGTH_TWO &&
                productBarcode.length() != PRODUCT_LENGTH_THREE) throw new IllegalArgumentException(BARCODE_NOT_VALID);
        this.productBarcode = productBarcode;
    }

    public String getWarehouseBarcode() {
        return warehouseBarcode;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(boolean result) {
        this.picture = result ? R.drawable.ic_check : R.drawable.ic_fail;
    }

    public boolean hasCalled() {
        return this.called;
    }

    public void called() {
        this.called = true;
    }
}
