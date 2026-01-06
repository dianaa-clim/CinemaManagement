package common;

import java.io.Serializable;

public class Product implements Serializable {
    private int idProduct;
    private String nameProduct;
    private float priceProduct;
    private String category;

    public Product(String nameProduct, float priceProduct, String category) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.category = category;
    }
    public Product(int idProduct, String nameProduct, float priceProduct, String category) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.category = category;
    }

    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    public String getNameProduct() {
        return nameProduct;
    }
    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
    public float getPriceProduct() {
        return priceProduct;
    }
    public void setPriceProduct(float priceProduct) {
        this.priceProduct = priceProduct;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}

