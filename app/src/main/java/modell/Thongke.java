package modell;

import java.io.Serializable;

public class Thongke implements Serializable {
    public int ID;
    public String Madonhang;
    public int Masanpham;
    public String Tensanpham;
    public Integer Giasanpham;
    public Integer Soluongsanpham;
    public String Ngaydathang;

    public Thongke(final int ID, final String madonhang, final int masanpham, final String tensanpham, final Integer giasanpham, final Integer soluongsanpham, final String ngaydathang) {
        this.ID = ID;
        this.Madonhang = madonhang;
        this.Masanpham = masanpham;
        this.Tensanpham = tensanpham;
        this.Giasanpham = giasanpham;
        this.Soluongsanpham = soluongsanpham;
        this.Ngaydathang = ngaydathang;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }

    public String getMadonhang() {
        return this.Madonhang;
    }

    public void setMadonhang(final String madonhang) {
        this.Madonhang = madonhang;
    }

    public int getMasanpham() {
        return this.Masanpham;
    }

    public void setMasanpham(final int masanpham) {
        this.Masanpham = masanpham;
    }

    public String getTensanpham() {
        return this.Tensanpham;
    }

    public void setTensanpham(final String tensanpham) {
        this.Tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return this.Giasanpham;
    }

    public void setGiasanpham(final Integer giasanpham) {
        this.Giasanpham = giasanpham;
    }

    public Integer getSoluongsanpham() {
        return this.Soluongsanpham;
    }

    public void setSoluongsanpham(final Integer soluongsanpham) {
        this.Soluongsanpham = soluongsanpham;
    }

    public String getNgaydathang() {
        return this.Ngaydathang;
    }

    public void setNgaydathang(final String ngaydathang) {
        this.Ngaydathang = ngaydathang;
    }
}
