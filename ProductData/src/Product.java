import java.util.Date;

public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private Date tanggalMasuk;

    public Product(String id, String nama, double harga, String kategori, Date tanggalMasuk) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.tanggalMasuk = tanggalMasuk;
        
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public Date getTanggalMasuk() {
        return this.tanggalMasuk;
    }
}