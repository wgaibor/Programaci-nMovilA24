package com.example.productos.entity;

public class ProductoRequestEntity {
    private String op;
    private ItemEntity data;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ItemEntity getData() {
        return data;
    }

    public void setData(ItemEntity data) {
        this.data = data;
    }
}
