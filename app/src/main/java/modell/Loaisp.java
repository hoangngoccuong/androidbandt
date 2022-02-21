package modell;

public class Loaisp {
    public int Id;
    public String Tenloaisp;
    public String Hinhanhloaisp;

    public Loaisp(final int id, final String tenloaisp, final String hinhanhloaisp) {
        this.Id = id;
        this.Tenloaisp = tenloaisp;
        this.Hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(final int id) {
        this.Id = id;
    }

    public String getTenloaisp() {
        return this.Tenloaisp;
    }

    public void setTenloaisp(final String tenloaisp) {
        this.Tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return this.Hinhanhloaisp;
    }

    public void setHinhanhloaisp(final String hinhanhloaisp) {
        this.Hinhanhloaisp = hinhanhloaisp;
    }
}
