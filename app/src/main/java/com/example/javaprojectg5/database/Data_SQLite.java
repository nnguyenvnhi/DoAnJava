package com.example.javaprojectg5.database;

public class Data_SQLite {
    public static  final String INSERT_THUTHU = "insert into ThuThu(maTT,hoTen,matKhau) values" +
            "('admin', 'nguyen admin', 'admin')," +
            "('namvn', 'nguyen van nam', '123456')," +
            "('teonv', 'nguyen van teo', '123456')";
    public static  final String INSERT_THANHVIEN = "insert into ThanhVien(hoTen,namSinh) values" +
            "('Pham Le Ngan','2002')," +
            "('Vu Thi Yen Nhi','2002')," +
            "('Bui Thu Thao','2002')," +
            "('Nguyen Van Nam','2002')," +
            "('Luu Thi Trinh','2002')";
    public static final String INSERT_LOAISACH = "insert into LoaiSach(tenLoai) values" +
            "('Tieng anh')," +
            "('Sách Khoa Học')," +
            "('Sách Lập Trình')," +
            "('Sách Dạy Con'),"+
            "('Sách Kinh Doanh'),"+
             "('Văn Học'),"+
            "('Thám Tử'),"+
            "('Phiêu Lưu'),"+
            "('Sách Kinh Doanh'),"+
            "('Sách Trí Tuệ')";


    public static final String INSERT_SACH = "insert into Sach(TenSach,GiaThue,MaLoai,Nhacungcap) values" +
            "('Học Lập Trình Android','1000','1','dan tri')," +
            "('Đắc Nhân Tâm','2000','2','Tuoi Tre')," +
            "('Thám tử lừng danh','3000','3','Sach viet')," +
            "('Dạy con làm giàu','4000','4','Sach online')";
    public static final String INSERT_PHIEUMUON = "insert into PhieuMuon(maTT,maTV,maSach,tienThue,ngay,traSach) values" +
            "('admin','1','1','2000','2021/02/02',1)," +
            "('admin','2','2','2500','2021/02/02',0)," +
            "('admin','3','3','3000','2021/02/02',1)" ;


}
