package com.example.javaprojectg5.themuser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.javaprojectg5.DAO.ThuThuDAO;
import com.example.javaprojectg5.model.ThuThu;
import com.example.javaprojectg5.DAO.ThuThuDAO;
import com.example.javaprojectg5.R;
import com.example.javaprojectg5.model.ThuThu;

import java.util.regex.Pattern;

public class ThemUserFragment extends Fragment {
    private ImageView imageView4;
    private EditText edUser;
    private EditText edHoTen;
    private EditText edPass;
    private EditText edRePass;
    private Button btnSaveUser;
    private Button btnCancelUser;
    public static Pattern USER_NAME = Pattern.compile("^[A-Z]+\\w*");

    ThuThu thuThu;
    ThuThuDAO dao;


    public ThemUserFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_them_user, container, false);

        imageView4 =  v.findViewById(R.id.imageView4);
        edUser = (EditText) v.findViewById(R.id.edUser);
        edHoTen = (EditText) v.findViewById(R.id.edHoTen);
        edPass = (EditText) v.findViewById(R.id.edPass);
        edRePass = (EditText) v.findViewById(R.id.edRePass);
        btnSaveUser = (Button) v.findViewById(R.id.btnSaveUser);
        btnCancelUser = (Button) v.findViewById(R.id.btnCancelUser);
        dao = new ThuThuDAO(getActivity());
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(edUser.getText().toString());
                thuThu.setHoTen(edHoTen.getText().toString());
                thuThu.setMatKhau(edPass.getText().toString());
                if (valide()>0){
                    if (dao.insert(thuThu)>0){
                        edUser.setText("");
                        edHoTen.setText("");
                        edPass.setText("");
                        edRePass.setText("");
                        Toast.makeText(getActivity(), "L??u Th??nh C??ng", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getActivity(), "L??u Th???t b???i", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        return v;
    }
    public int valide(){
        int check= 1;


        String hoten = edHoTen.getText().toString().trim();
        if (! USER_NAME.matcher(hoten).matches()){
            Toast.makeText(getContext(), "Chu cai dau tien phai viet hoa", Toast.LENGTH_SHORT).show();
            check = -1;
        }


        if (edHoTen.getText().length()==0 && edUser.getText().length()==0 && edPass.getText().length()==0 && edPass.getText().length()==0){
            edHoTen.setError("Kh??ng ??c ????? tr???ng");
            edPass.setError("Kh??ng ??c ????? tr???ng");
            edRePass.setError("Kh??ng ??c ????? tr???ng");
            edUser.setError("Kh??ng ??c ????? tr???ng");
            check = -1;
        }

        else  if (edHoTen.getText().length()<5){
            Toast.makeText(getContext(), "H??? t??n ph???i l???n h??n 5 k?? t???", Toast.LENGTH_SHORT).show();
        }
        else  if  (edHoTen.getText().length()>15){
            edHoTen.setError("H??? t??n kh??ng ???????c d??i h??n 15 k?? t???");


        }//check r???ng


        return check=1;
    }

}