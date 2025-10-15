import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import  java.text.ParseException;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar1
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane((menu.mainPanel));

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private Database database;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JSpinner tanggalSpinner;
    private JLabel tanggalLabel;
    private String selectedId;

    // constructor
    public ProductMenu() {

        // buat objek database
        database = new Database();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Elektronik", "Makanan", "Minuman", "Pakaian", "Alat Tulis"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                }
                else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: tambahkan konfirmasi sebelum menghapus data
                int confirm = JOptionPane.showConfirmDialog(null, "Anda Ingin Hapus Data Ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Data Batal Dihapus");
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String cutKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(cutKategori);

                selectedId = curId;

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });

        // atur spinner tanggal masuk
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        tanggalSpinner.setModel(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(tanggalSpinner, "dd-MM-yyyy");
        tanggalSpinner.setEditor(dateEditor);
        ((JSpinner.DefaultEditor) tanggalSpinner.getEditor()).getTextField().setEditable(false);
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID Produk", "Nama", "Harga", "Kategori", "Tanggal Masuk" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");

            // isi tabel dengan hasil query
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[6];
                row[0] = i + 1;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("tanggalMasuk");
                tmp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tmp; // return juga harus diganti
    }

    public void insertData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String hargaText = hargaField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString().trim();
            Date tanggalMasuk = (Date) tanggalSpinner.getValue();

            // cek kalau ada data yang kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua data harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaText);

            // cek duplikat ID di database
            String checkQuery = "SELECT COUNT(*) AS jumlah FROM product WHERE id = '" + id + "'";
            ResultSet rs = database.selectQuery(checkQuery);
            if (rs.next() && rs.getInt("jumlah") > 0) {
                JOptionPane.showMessageDialog(null, "ID sudah digunakan! Gunakan ID lain.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ubah format tangga ke yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalFormatted = sdf.format(tanggalMasuk);

            // tambahkan data ke dalam database
            String sqlQuery = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', '" + harga + "', '" + kategori + "', '" + tanggalFormatted + "')";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            // ambil data dari form
            String id = idField.getText().trim();
            String nama = namaField.getText().trim();
            String hargaText = hargaField.getText().trim();
            String kategori = kategoriComboBox.getSelectedItem().toString().trim();
            Date tanggalMasuk = (Date) tanggalSpinner.getValue();

            // cek kalau ada data yang kosong
            if (id.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua data harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaText);

            // ambil id lama
            String idLama = selectedId;

            // cek duplikat ID baru kalau ID diganti
            if (!id.equals(idLama)) {
                String checkQuery = "SELECT COUNT(*) AS jumlah FROM product WHERE id = '" + id + "'";
                ResultSet rsCheck = database.selectQuery(checkQuery);
                if (rsCheck.next() && rsCheck.getInt("jumlah") > 0) {
                    JOptionPane.showMessageDialog(null, "ID sudah digunakan! Gunakan ID lain.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // ubah format tangga ke yyyy-MM-dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tanggalFormatted = sdf.format(tanggalMasuk);

            // ubah data produk dalam database
            String sql = "UPDATE product SET nama = '" + nama + "', harga = '" + harga + "', kategori = '" + kategori + "', tanggalMasuk = '" + tanggalFormatted + "' WHERE id = '" + idLama + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga Harus Berupa Angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // hapus data dari database
        try {
            String id = idField.getText();
            String sql = "DELETE FROM product WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete Berhasil");
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        tanggalSpinner.setValue(new Date());

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }


}