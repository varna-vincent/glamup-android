package com.coen499.glamup.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product implements Parcelable, Serializable {

    private String id;
    private String shadeName;
    private String shadeSubtitle;
    private String productName;
    private String productSubtitle;
    private String description;
    private String modelNumber;
    private String productType;
    private String productOwner;
    private String createdBy;
    private String productImage;
    private Double price;

    public Product() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShadeName() {
        return shadeName;
    }

    public void setShadeName(String shadeName) {
        this.shadeName = shadeName;
    }

    public String getShadeSubtitle() {
        return shadeSubtitle;
    }

    public void setShadeSubtitle(String shadeSubtitle) {
        this.shadeSubtitle = shadeSubtitle;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {

        System.out.println("\n\nProduct Name : " + getProductName()
                + "\nProduct Subtitle : " + getProductSubtitle()
                + "\nProduct Type : " + getProductType()
                + "\nShade Name : " + getShadeName()
                + "\nShade Subtitle : " + getShadeSubtitle()
                + "\nProduct Id : " + getId()
                + "\nProduct Owner : " + getProductOwner()
                + "\nProduct Price : " + getPrice()
                + "\nDescription : " + getDescription()
                + "\nImage : " + getProductImage());

        return "\n\nProduct Name : " + getProductName()
                + "\nProduct Subtitle : " + getProductSubtitle()
                + "\nProduct Type : " + getProductType()
                + "\nShade Name : " + getShadeName()
                + "\nShade Subtitle : " + getShadeSubtitle()
                + "\nProduct Id : " + getId()
                + "\nProduct Owner : " + getProductOwner()
                + "\nProduct Price : " + getPrice()
                + "\nDescription : " + getDescription()
                + "\nImage : " + getProductImage();
    }

    protected Product(Parcel in) {
        id = in.readString();
        shadeName = in.readString();
        shadeSubtitle = in.readString();
        productName = in.readString();
        productSubtitle = in.readString();
        description = in.readString();
        modelNumber = in.readString();
        productType = in.readString();
        productOwner = in.readString();
        createdBy = in.readString();
        productImage = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shadeName);
        dest.writeString(shadeSubtitle);
        dest.writeString(productName);
        dest.writeString(productSubtitle);
        dest.writeString(description);
        dest.writeString(modelNumber);
        dest.writeString(productType);
        dest.writeString(productOwner);
        dest.writeString(createdBy);
        dest.writeString(productImage);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
    }
}
