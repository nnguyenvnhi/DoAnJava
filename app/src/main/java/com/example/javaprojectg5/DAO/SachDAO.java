package com.example.javaprojectg5.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.javaprojectg5.database.Dbhelper;
import com.example.javaprojectg5.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase db;
    public SachDAO(Context context){
        Dbhelper dbhelper =new Dbhelper(context);
        db= dbhelper.getWritableDatabase();
    }
    public long insert(Sach obj) {
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
      values.put("maLoai", obj.getMaLoai());
        values.put("Nhacungcap",obj.getNhacungcap());
        return db.insert("Sach", null, values);
    }
    public int update(Sach obj){
        ContentValues values = new ContentValues();
        values.put("tenSach",obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
       values.put("Nhacungcap",obj.getNhacungcap());
        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(obj.getMaSach())});
    }
    public int detele(String id){
        return db.delete("Sach","maSach=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();
            obj.setNhacungcap(c.getString(c.getColumnIndex("Nhacungcap")));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            obj.setTenSach(c.getString(c.getColumnIndex("tenSach")));
            obj.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex("giaThue"))));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));

            list.add(obj);
        }
        return list;
    }
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    public Sach getID(String id)   {
        String sql = "SELECT * FROM   Sach WHERE maSach=?" ;
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }
}
