package com.example.productolemas.entity;

public class ProductoRequestEntity {
    private String op;
    private ProductoEntity data;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ProductoEntity getData() {
        return data;
    }

    public void setData(ProductoEntity data) {
        this.data = data;
    }
}
