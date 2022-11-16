package com.example.javaprojectg5.thanhvien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.javaprojectg5.Adapter.ThanhVienAdapter;
import com.example.javaprojectg5.DAO.ThanhVienDAO;
import com.example.javaprojectg5.R;
import com.example.javaprojectg5.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaTV,edTenTV,ednamSinh;
    Button btnsave,btncancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_thanh_vien, container, false);

        lvThanhVien=v.findViewById(R.id.lvThanhVien);
        fab=v.findViewById(R.id.fabTV);
        dao =new ThanhVienDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);//bang = thi insert
            }
        });
        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                item= list.get(position);
                openDialog(getActivity(),1);//=1 thi update

                return false;
            }
        });
        return v;
    }
    void capNhatLv(){
        list=(ArrayList<ThanhVien>) dao.getAll();
        adapter=new ThanhVienAdapter(getActivity(),this,list);
        lvThanhVien.setAdapter(adapter);
    }
    public void xoa(final String id ){
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("bạn có muốn xóa không ?");
        builder.setCancelable(true);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                dao.detele(id);
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog =builder.create();
        builder.show();

    }
    protected void openDialog(final Context context,final int type){
        dialog =new Dialog(context);
        dialog.setContentView(R.layout.thanh_vien_dialog);

        edMaTV=dialog.findViewById(R.id.edMaTV);
        edTenTV=dialog.findViewById(R.id.edTenTV);
        ednamSinh=dialog.findViewById(R.id.edNamSinh);
        btncancel=dialog.findViewById(R.id.btnCancelTV);
        btnsave=dialog.findViewById(R.id.btnSaveTV);

        edMaTV.setEnabled(false);
        if (type !=0){
            //cap nhat
            edMaTV.setText(String.valueOf(item.getMaTV()));
            edTenTV.setText(String.valueOf(item.getHoTen()));
            ednamSinh.setText(String.valueOf(item.getNamSinh()));
        }
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item =new ThanhVien();
                item.setHoTen(edTenTV.getText().toString());
                item.setNamSinh(ednamSinh.getText().toString());

                if (validate()>0){
                    if (type==0){
                        //==- thi them nguoi dung
                        if (dao.insert(item)>0){
                            Toast.makeText(getContext(),"them thanh cong",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),"them that bai",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //khac kohn thi update data
                        item.setMaTV(Integer.parseInt(edMaTV.getText().toString()));
                        if (dao.update(item)>0){
                            Toast.makeText(getContext(),"sua thanh cong",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),"sua that bai",Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public int validate(){
        int check=1;
        if (edTenTV.getText().length()==0||ednamSinh.getText().length()==0){
            Toast.makeText(getContext(),"ban phia nhap day du thong tin",Toast.LENGTH_SHORT).show();
            check=-1;
        }
        return check=1;
    }
}