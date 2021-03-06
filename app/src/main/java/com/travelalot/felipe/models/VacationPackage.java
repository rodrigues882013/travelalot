package com.travelalot.felipe.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by felipe on 23/01/17.
 */

@DatabaseTable(tableName = "vacation_package")
public class VacationPackage {

    @DatabaseField(columnName = "id_package", generatedId = true)
    private int id;

    @DatabaseField
    private String packageName;

    @DatabaseField
    private Double price;

    @DatabaseField
    private String description;

    @DatabaseField
    private String local;

    @DatabaseField
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VacationPackage(String packageName,
                           Double price,
                           String description,
                           String image,
                           String local) {
        this.packageName = packageName;
        this.price = price;
        this.description = description;
        this.local = local;
        this.image = image;
    }

    public VacationPackage(){}

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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
        result = 31 * result + local.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "VacationPackage{" +
                "packageName='" + packageName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
