package com.travelalot.felipe.models;

import java.io.InputStream;

/**
 * Created by felipe on 23/01/17.
 */

public class VacationPackage {
    private String packageName;
    private Double price;
    private String description;
    private InputStream image;

    public VacationPackage(String packageName, Double price, String description, InputStream image) {
        this.packageName = packageName;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VacationPackage that = (VacationPackage) o;

        if (!packageName.equals(that.packageName)) return false;
        if (!price.equals(that.price)) return false;
        if (!description.equals(that.description)) return false;
        return image.equals(that.image);

    }

    @Override
    public int hashCode() {
        int result = packageName.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + image.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VacationPackage{" +
                "packageName='" + packageName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}