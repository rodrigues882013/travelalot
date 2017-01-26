package com.travelalot.felipe.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.travelalot.felipe.models.Order;
import com.travelalot.felipe.models.VacationPackage;

import java.sql.SQLException;

/**
 * Created by felipe on 25/01/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "travelalot.db";
    private static final int DATABASE_VERSION = 4;

    private Dao<VacationPackage, Integer> packageDao = null;
    private Dao<Order, Integer> orderDao = null;


    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, VacationPackage.class);
            TableUtils.createTable(connectionSource, Order.class);

        } catch (SQLException e) {
            Log.e("DATABSE", "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, VacationPackage.class, true);
            TableUtils.createTable(connectionSource, VacationPackage.class);

            TableUtils.dropTable(connectionSource, Order.class, true);
            TableUtils.createTable(connectionSource, Order.class);

        } catch (SQLException e) {
            Log.e("SIGEL", "Unable to create databases", e);
        }
    }

    public Dao<VacationPackage, Integer> getPackagesDao() throws SQLException {
        if (packageDao == null) {
            packageDao = getDao(VacationPackage.class);
        }
        return packageDao;
    }

    public Dao<Order, Integer> getOrderDao() throws SQLException {
        if (orderDao == null) {
            orderDao = getDao(Order.class);
        }
        return orderDao;
    }


    @Override
    public void close() {
        super.close();
        packageDao = null;
        orderDao = null;
    }

}
