package com.example.javaprojectg5.phieumuon;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.javaprojectg5.Adapter.PhieuMuonAdapter;
import com.example.javaprojectg5.Adapter.SachPsinerAdapter;
import com.example.javaprojectg5.Adapter.ThanhVienSpinerAdapter;
import com.example.javaprojectg5.DAO.PhieuMuonDAO;
import com.example.javaprojectg5.DAO.SachDAO;
import com.example.javaprojectg5.DAO.ThanhVienDAO;
import com.example.javaprojectg5.R;
import com.example.javaprojectg5.model.PhieuMuon;
import com.example.javaprojectg5.model.Sach;
import com.example.javaprojectg5.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PhieuMuonFragment extends Fragment {
    ListView lvPhieuMuon;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV,spSach;
    TextView tvNgay,tvTienThue;
    CheckBox chkTraSach;
    Button btnSave,btnCancel;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ThanhVienSpinerAdapter thanhVienSpinerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;

    SachPsinerAdapter sachPsinerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach,tienThue;
    int positionTV,positionSach;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lvPhieuMuon = v.findViewById(R.id.lvPhieuMuon);
        fab = v.findViewById(R.id.fabPM);
        dao = new PhieuMuonDAO(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lvPhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1); // update
                return false;
            }
        });
        capNhatLv();

        return v;
    }
    void capNhatLv(){
        list = (ArrayList<PhieuMuon>) dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(),this,list);
        lvPhieuMuon.setAdapter(adapter);
    }
    protected void openDialog(final Context context,final int type){
        //custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spMaTV);
        spSach = dialog.findViewById(R.id.spMaSach);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvTienThue= dialog.findViewById(R.id.tvTienThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnCancel = dialog.findViewById(R.id.btnCancelPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);
        //set ngay thue,mac dinh ngay hien hanh

        tvNgay.setText("Ngày Thuê:"+sdf.format(new Date()));
        edMaPM.setEnabled(false);
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienSpinerAdapter = new ThanhVienSpinerAdapter(context,listThanhVien);
        spTV.setAdapter(thanhVienSpinerAdapter);
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();
                // Toast.makeText(context, "Chọn"+listThanhVien.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDAO = new SachDAO(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        sachPsinerAdapter = new SachPsinerAdapter(context,listSach);
        spSach.setAdapter(sachPsinerAdapter);
        // lay maLoaiSach
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThue = listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền Thuê:" +tienThue);
                //  Toast.makeText(context, "Chọn"+listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //edti set data nen form
        if (type !=0){
            edMaPM.setText(String.valueOf(item.getMaPM()));
            for (int i = 0; i < listThanhVien.size(); i++)
                if (item.getMaTV()==(listThanhVien.get(i).getMaTV())){
                    positionTV = i;
                }
            spTV.setSelection(positionTV);

            for (int i = 0; i < listSach.size(); i++)
                if (item.getMaSach()==(listSach.get(i).getMaSach())){
                    positionSach = i;
                }
            spSach.setSelection(positionSach);
            tvNgay.setText("Ngày Thuê:"+sdf.format(item.getNgay()));
            tvTienThue.setText("Tiền Thuê:"+item.getTienThue());
            if (item.getTraSach()==1){
                chkTraSach.setChecked(true);
            }else{
                chkTraSach.setChecked(false);
            }
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new PhieuMuon();
                item.setMaSach(maSach);
                item.setMaTV(maThanhVien);
                item.setNgay(new Date());
                item.setTienThue(tienThue);
                if (chkTraSach.isChecked()){
                    item.setTraSach(1);
                }else{
                    item.setTraSach(0);
                }
                if (type ==0){
                    //type = 0(ínert)
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    //type = 1(update)
                    item.setMaPM((Integer.parseInt(edMaPM.getText().toString())));
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "sửa Thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa(final String Id){
        //Sử dụng Alẻt
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.detele(Id);
                        Toast.makeText(getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        capNhatLv();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("No"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        builder.show();
    }
}