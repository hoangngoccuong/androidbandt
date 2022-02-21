package modell;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int ID;
    public String Tensanpham;
    public Integer Giasp;
    public String Hinhanhsp;
    public String Motasp;
    public int IDSanpham;

    public Sanpham(final int ID, final String tensanpham, final Integer giasp, final String hinhanhsp, final String motasp, final int IDSanpham) {
        this.ID = ID;
        this.Tensanpham = tensanpham;
        this.Giasp = giasp;
        this.Hinhanhsp = hinhanhsp;
        this.Motasp = motasp;
        this.IDSanpham = IDSanpham;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(final int ID) {
        this.ID = ID;
    }

    public String getTensanpham() {
        return this.Tensanpham;
    }

    public void setTensanpham(final String tensanpham) {
        this.Tensanpham = tensanpham;
    }

    public Integer getGiasp() {
        return this.Giasp;
    }

    public void setGiasp(final Integer giasp) {
        this.Giasp = giasp;
    }

    public String getHinhanhsp() {
        return this.Hinhanhsp;
    }

    public void setHinhanhsp(final String hinhanhsp) {
        this.Hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return this.Motasp;
    }

    public void setMotasp(final String motasp) {
        this.Motasp = motasp;
    }

    public int getIDSanpham() {
        return this.IDSanpham;
    }

    public void setIDSanpham(final int IDSanpham) {
        this.IDSanpham = IDSanpham;
    }
}
