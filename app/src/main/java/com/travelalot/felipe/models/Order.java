package com.travelalot.felipe.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by felipe on 25/01/17.
 */

@DatabaseTable(tableName = "order")
public class Order {

    @DatabaseField(columnName = "id_order", generatedId = true)
    private int id;

    @DatabaseField(columnName = "total")
    private Double total;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "id_package")
    private VacationPackage _package;

    public Order(){}

    public Order(Double total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public VacationPackage getPackage() {
        return _package;
    }

    public void setPackage(VacationPackage _package) {
        this._package = _package;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        return _package != null ? _package.equals(order._package) : order._package == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (_package != null ? _package.hashCode() : 0);
        return result;
    }
}

