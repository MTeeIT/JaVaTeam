/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancansu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Administrator
 */
public class frm_admin extends javax.swing.JFrame {
    private String userName = "root";//datausername
    private String password = "";//datapassuser
    private String url = "jdbc:mysql://127.0.0.1:3306/quanlybcs";	
    private Connection conn = null;
    private Statement state;
    private ResultSet rs;
    private dangNhap form;
    /**
     * Creates new form frm_admin
     */
    public frm_admin(dangNhap form) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//Center postion
        this.form = form;
        hienDuLieu();
    }
    public frm_admin() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hienDuLieu();
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=jTable1.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(25);    
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(100);    
        colModel.getColumn(3).setPreferredWidth(200);
        colModel.getColumn(4).setPreferredWidth(120);    
        colModel.getColumn(5).setPreferredWidth(220);
    }
    
    @SuppressWarnings("deprecation")
    public void connect()
    {
        try
        {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                try
                {
                    conn = DriverManager.getConnection (url,userName, password);
                }catch(Exception e)
                {
                        JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
                }

        }
        catch(Exception e)
        {
                JOptionPane.showMessageDialog(rootPane, "Không load được Driver!");
        }
    }  
    
    public void hienDuLieu()
    {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from sinhvien, lop, covanhoctap, bomon where sinhvien.MaLop = lop.MaLop and sinhvien.MaCV = covanhoctap.MaCV and lop.MaBoMon = bomon.MaBoMon " ;
        System.out.println(sql);
        this.connect();
        
        //---------Truy Vấn---------\\
        try {
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                int i = 1;
                while(rs.next())
                {
                    model.addRow(new Object[]{ i, rs.getString("MaLop"), rs.getString("MaSV"), rs.getString("HoTen_SV"), rs.getString("ChucVu_SV") , rs.getString("Email_SV")  });
                    i++;
                }
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
        } finally {
            try {
                    conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");

            }
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuAddBoMon = new javax.swing.JMenuItem();
        MenuAddLop = new javax.swing.JMenuItem();
        MenuAddCVHT = new javax.swing.JMenuItem();
        MenuAddSV = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuFillterBoMon = new javax.swing.JMenuItem();
        MenuFillterLop = new javax.swing.JMenuItem();
        MenuFillterCVHT = new javax.swing.JMenuItem();
        MenuFillterSV = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

        jMenuItem1.setText("jMenuItem1");

        jMenu9.setText("File");
        jMenuBar4.add(jMenu9);

        jMenu10.setText("Edit");
        jMenuBar4.add(jMenu10);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(243, 156, 18));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Hệ thống quản lý ban cán sự");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(176, 176, 176))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã lớp", "Mã sinh viên", "Tên ban cán sự", "Chức vụ", "Email sinh viên"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(22);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jMenu1.setText("Quản lý");

        MenuAddBoMon.setText("Bộ môn");
        MenuAddBoMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAddBoMonActionPerformed(evt);
            }
        });
        jMenu1.add(MenuAddBoMon);

        MenuAddLop.setText("Lớp");
        MenuAddLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAddLopActionPerformed(evt);
            }
        });
        jMenu1.add(MenuAddLop);

        MenuAddCVHT.setText("Cố vấn học tập");
        MenuAddCVHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAddCVHTActionPerformed(evt);
            }
        });
        jMenu1.add(MenuAddCVHT);

        MenuAddSV.setText("Sinh Viên");
        MenuAddSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAddSVActionPerformed(evt);
            }
        });
        jMenu1.add(MenuAddSV);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Lọc danh sách");

        MenuFillterBoMon.setText("Bộ môn");
        MenuFillterBoMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFillterBoMonActionPerformed(evt);
            }
        });
        jMenu2.add(MenuFillterBoMon);

        MenuFillterLop.setText("Lớp");
        MenuFillterLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFillterLopActionPerformed(evt);
            }
        });
        jMenu2.add(MenuFillterLop);

        MenuFillterCVHT.setText("Cố vấn học tập");
        MenuFillterCVHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFillterCVHTActionPerformed(evt);
            }
        });
        jMenu2.add(MenuFillterCVHT);

        MenuFillterSV.setText("Khóa học");
        MenuFillterSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFillterSVActionPerformed(evt);
            }
        });
        jMenu2.add(MenuFillterSV);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Đổi mật khẩu");
        jMenu7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu7MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu7);

        jMenu8.setText("Đăng xuất");
        jMenu8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu8MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuAddLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAddLopActionPerformed
        this.setVisible(true);
        new fmr_lop().setVisible(true);
        
    }//GEN-LAST:event_MenuAddLopActionPerformed

    private void MenuAddBoMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAddBoMonActionPerformed
        this.setVisible(true);
        new frm_bomon().setVisible(true);
    }//GEN-LAST:event_MenuAddBoMonActionPerformed

    private void MenuAddCVHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAddCVHTActionPerformed
        this.setVisible(true);
        new Frm_Covan().setVisible(true);
    }//GEN-LAST:event_MenuAddCVHTActionPerformed

    private void MenuAddSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAddSVActionPerformed
        this.setVisible(true);
        new frm_SinhVien().setVisible(true);
    }//GEN-LAST:event_MenuAddSVActionPerformed

    private void jMenu8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu8MouseClicked
        this.form.setDangXuat();
        this.form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenu8MouseClicked

    private void MenuFillterBoMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFillterBoMonActionPerformed
        this.setVisible(true);
        new frm_locBoMon().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_MenuFillterBoMonActionPerformed

    private void MenuFillterLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFillterLopActionPerformed
        this.setVisible(true);
        new frm_locLop().setVisible(true);
    }//GEN-LAST:event_MenuFillterLopActionPerformed

    private void MenuFillterCVHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFillterCVHTActionPerformed
        this.setVisible(true);
        new frm_locCoVan().setVisible(true);
    }//GEN-LAST:event_MenuFillterCVHTActionPerformed

    private void MenuFillterSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFillterSVActionPerformed
        this.setVisible(true);
        new frm_locKhoaHoc().setVisible(true);
    }//GEN-LAST:event_MenuFillterSVActionPerformed

    private void jMenu7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu7MouseClicked
        frm_doimatkhau fr = new frm_doimatkhau(this);
        fr.setVisible(true);
    }//GEN-LAST:event_jMenu7MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row_index = jTable1.getSelectedRow();
        String maSV = (String) jTable1.getModel().getValueAt(row_index, 2);
        frm_hienThiTTSV fr = new frm_hienThiTTSV(this, maSV);
        fr.setVisible(true);
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(frm_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frm_admin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuAddBoMon;
    private javax.swing.JMenuItem MenuAddCVHT;
    private javax.swing.JMenuItem MenuAddLop;
    private javax.swing.JMenuItem MenuAddSV;
    private javax.swing.JMenuItem MenuFillterBoMon;
    private javax.swing.JMenuItem MenuFillterCVHT;
    private javax.swing.JMenuItem MenuFillterLop;
    private javax.swing.JMenuItem MenuFillterSV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}