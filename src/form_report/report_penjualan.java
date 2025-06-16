/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form_report;

import form.UserID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ahmad Nur Latif P
 */
public class report_penjualan extends javax.swing.JFrame {
private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;

    /**
     * Creates new form report_penjualan
     */
    public report_penjualan() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        tanggal_mulai.setFormats(new String[]{"yyyy-MM-dd"});
        tanggal_selesai.setFormats(new String[]{"yyyy-MM-dd"});
        initTable();
    }
    
    protected void initTable() {
        Object[] Baris = {"ID Nota", "Tanggal", "ID Pelanggan", "Nama Pelanggan", "ID Item", "Nama Item", "Harga", "Kuantitas", "Subtotal"};
        tabmode = new DefaultTableModel(null, Baris);
        tbl_penjualan_detail.setModel(tabmode);
    }
    
    protected void clearTable() {
        tabmode.setRowCount(0);
    }
    
    protected void DataTransaksi(Date startDate, Date endDate) {
        clearTable();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strStartDate = sdf.format(startDate);
        String strEndDate = sdf.format(endDate);

        try {
            String sql = "SELECT "
                       + "n.id_nota, "
                       + "n.tanggal, "
                       + "n.id_pelanggan, "
                       + "p.nama_pelanggan, "
                       + "d.id_item, "
                       + "d.nama_item, "
                       + "d.harga_jual, "
                       + "d.harga_beli, "
                       + "d.kuantitas, "
                       + "(CAST(d.harga_jual AS SIGNED) * CAST(d.kuantitas AS SIGNED)) AS subtotal, "
                       + "((CAST(d.harga_jual AS SIGNED) - CAST(d.harga_beli AS SIGNED)) * CAST(d.kuantitas AS SIGNED)) AS total_pendapatan_bersih " 
                       + "FROM tb_nota_detail d "
                       + "JOIN tb_nota n ON d.id_nota = n.id_nota "
                       + "LEFT JOIN tb_pelanggan p ON n.id_pelanggan = p.id_pelanggan "
                       + "WHERE n.tanggal BETWEEN ? AND ? "
                       + "ORDER BY n.tanggal ASC, n.id_nota ASC, d.id_item ASC";

            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, strStartDate);
            stat.setString(2, strEndDate);
            ResultSet rs = stat.executeQuery();

            long totalPendapatanKeseluruhan = 0;
            long totalPendapatanBersih = 0;

            while (rs.next()) {
                String idNota = rs.getString("id_nota");
                String tanggal = rs.getString("tanggal");
                String idPelanggan = rs.getString("id_pelanggan");
                String namaPelanggan = rs.getString("nama_pelanggan");
                String idItem = rs.getString("id_item");
                String namaItem = rs.getString("nama_item");
                int hargaJual = rs.getInt("harga_jual");
                int kuantitas = rs.getInt("kuantitas");
                int subtotal = rs.getInt("subtotal");
                int subtotalbersih = rs.getInt("total_pendapatan_bersih");

                Object[] row = {idNota, tanggal, idPelanggan, namaPelanggan, idItem, namaItem, hargaJual, kuantitas, subtotal};
                tabmode.addRow(row);

                totalPendapatanKeseluruhan += subtotal;
                totalPendapatanBersih += subtotalbersih;
            }
           label_total_pendapatan.setText(String.format("Rp "+"%,d", totalPendapatanKeseluruhan));
           label_total_pendapatan_bersih.setText(String.format("Rp "+"%,d", totalPendapatanBersih));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data penjualan: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void DiagramBatang(Date tglMulai, Date tglSelesai) {
        try {
            String loginId = UserID.getIdTeknisi();
            String loginTeknisi = "Tidak Diketahui";

            try (PreparedStatement teknama = conn.prepareStatement("SELECT nama FROM tb_login WHERE id_teknisi = ?")) {
                teknama.setString(1, loginId);
                try (ResultSet rsNama = teknama.executeQuery()) {
                    if (rsNama.next()) {
                        loginTeknisi = rsNama.getString("nama");
                    }
                }
            }
            String path = "./src/report/penjualan_ChartBar.jasper";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("TEKNISI", loginTeknisi);
            parameters.put("TANGGAL_MULAI", new java.sql.Date(tglMulai.getTime()));
            parameters.put("TANGGAL_SELESAI", new java.sql.Date(tglSelesai.getTime()));

            JasperPrint print = JasperFillManager.fillReport(path, parameters, conn);

            JasperViewer.viewReport(print, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,
                    "Error saat membuat grafik batang: " + ex.getMessage(),
                    "Kesalahan Laporan",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tanggal_mulai = new org.jdesktop.swingx.JXDatePicker();
        tanggal_selesai = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        bcari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_penjualan_detail = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        label_total_pendapatan = new javax.swing.JLabel();
        bcetak_chart = new javax.swing.JButton();
        label_total_pendapatan_bersih = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Diagram Batang Pendapatan Penjualan");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 2, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Awal :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tanggal Akhir :");

        bcari.setText("CARI DATA");
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));

        tbl_penjualan_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_penjualan_detail);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total Pendapatan");

        label_total_pendapatan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_total_pendapatan.setForeground(new java.awt.Color(255, 255, 255));
        label_total_pendapatan.setText("---------------------------------------");

        bcetak_chart.setText("CETAK");
        bcetak_chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetak_chartActionPerformed(evt);
            }
        });

        label_total_pendapatan_bersih.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_total_pendapatan_bersih.setForeground(new java.awt.Color(255, 255, 255));
        label_total_pendapatan_bersih.setText("---------------------------------------");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Keuntungan Bersih");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(":");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(":");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_total_pendapatan_bersih, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(label_total_pendapatan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bcetak_chart)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_total_pendapatan)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bcetak_chart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_total_pendapatan_bersih)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tanggal_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tanggal_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                        .addComponent(bcari)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(tanggal_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tanggal_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bcari)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcetak_chartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetak_chartActionPerformed

        Date tglMulai = tanggal_mulai.getDate();
        Date tglSelesai = tanggal_selesai.getDate();

        if (tglMulai == null || tglSelesai == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih tanggal mulai dan tanggal selesai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (tglMulai.after(tglSelesai)) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai tidak boleh lebih dari tanggal selesai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {

            DiagramBatang(tglMulai, tglSelesai);
        }
    }//GEN-LAST:event_bcetak_chartActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        Date tglMulai = tanggal_mulai.getDate();
        Date tglSelesai = tanggal_selesai.getDate();

        if (tglMulai == null || tglSelesai == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih tanggal mulai dan tanggal selesai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (tglMulai.after(tglSelesai)) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai tidak boleh lebih dari tanggal selesai.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            DataTransaksi(tglMulai, tglSelesai);
        }
    }//GEN-LAST:event_bcariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(report_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(report_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(report_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(report_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new report_penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcetak_chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_total_pendapatan;
    private javax.swing.JLabel label_total_pendapatan_bersih;
    private org.jdesktop.swingx.JXDatePicker tanggal_mulai;
    private org.jdesktop.swingx.JXDatePicker tanggal_selesai;
    private javax.swing.JTable tbl_penjualan_detail;
    // End of variables declaration//GEN-END:variables
}
