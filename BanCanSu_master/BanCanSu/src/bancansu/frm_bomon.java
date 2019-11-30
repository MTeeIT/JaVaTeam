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


public final class frm_bomon extends javax.swing.JFrame {

    private boolean clickTable = false;
    private String userName = "root";//datausername
    private String password = "";//datapassuser
    private String url = "jdbc:mysql://127.0.0.1:3306/quanlybcs?useUnicode=true&characterEncoding=utf-8";	
    private Connection conn = null;
    private Statement state;
    private ResultSet rs;

    
    public frm_bomon()
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//Center postion
        setResizable(false);
        hienThiDuLieu();
        setSizeTable();
    }
    private void setSizeTable()
    {
        tableBoMon.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=tableBoMon.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(25);    
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(200);    
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
    
    public void lamRongTable(DefaultTableModel model)
    {
        int rows = model.getRowCount(); 
        for(int i = rows - 1; i >=0; i--)
        {
           model.removeRow(i); 
        }
    }
    
    public void hienThiDuLieu()
    {
        DefaultTableModel model = (DefaultTableModel) tableBoMon.getModel();
        lamRongTable(model);
        
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from bomon" ;
        this.connect();
        
        //---------Truy Vấn---------\\
        try {
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                int i = 1;
                while(rs.next())
                {
                    model.addRow(new Object[]{ i, rs.getString("MaBoMon"), rs.getString("TenBoMon") });
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
    
    public boolean tonTaiMaBoMon(String maBoMon)
    {
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from bomon where MaBoMon='" + maBoMon + "'" ;

        this.connect();
        
        //---------Truy Vấn---------\\
        try {
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                rs.last();
                if(rs.getRow() > 0)
                    return true;
                else return false;
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
        } finally {
            try {
                    conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");

            }
        }
        return false;
    }
    
    public boolean themMoi()
    {
        if(tonTaiMaBoMon(tfMaBoMon.getText()))
        {
            return false;
        }
        else
        {
             //---------Lệnh Truy Vấn---------\\
            String sql = "insert into bomon values('" + tfMaBoMon.getText() + "', N'" + tfTenBoMon.getText() + "')" ;
            this.connect();

            //---------Truy Vấn---------\\
            try {
                    state = conn.createStatement();
                    int kq  = state.executeUpdate(sql);
                    if(kq > 0)
                        return true;
                    else return false;
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
        return false;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBoMon = new javax.swing.JTable();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfMaBoMon = new javax.swing.JTextField();
        tfTenBoMon = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(243, 156, 18));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel35.setText("Thông tin bộ môn");

        jPanel7.setBackground(new java.awt.Color(44, 62, 80));

        tableBoMon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tableBoMon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableBoMon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã bộ môn", "Tên bộ môn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBoMon.setRowHeight(22);
        tableBoMon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBoMonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBoMon);

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(44, 62, 80));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION), "", javax.swing.border.TitledBorder.TRAILING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51)), "Nhập thông tin bộ môn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 22), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mã bộ môn:");

        tfMaBoMon.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfMaBoMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMaBoMonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên bộ môn:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfMaBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfTenBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfMaBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tfTenBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)))
                .addGap(25, 58, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 597, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfMaBoMonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tfMaBoMonActionPerformed
    {//GEN-HEADEREND:event_tfMaBoMonActionPerformed
        
    }//GEN-LAST:event_tfMaBoMonActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        tfMaBoMon.enable(true);
        tfMaBoMon.setEditable(true);
        tfMaBoMon.setText("");
        tfTenBoMon.setText("");
        clickTable = false;
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        
        if(!this.clickTable)
        {
            if(tfMaBoMon.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống mã bộ môn!");
            }
            else if(tfTenBoMon.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống tên bộ môn!");
            }
            else
            {
                if(themMoi())
                {
                    JOptionPane.showMessageDialog(rootPane, "Thêm mới thành công!");
                    hienThiDuLieu();
                }
                else
                {
                    JOptionPane.showMessageDialog(rootPane, "Mã bộ môn đã tồn tại!");
                }
            }
            
            
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Cần nhấn làm mới để thêm bộ môn!");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableBoMonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBoMonMouseClicked
        clickTable = true;
        tfMaBoMon.setEditable(false);
        tfMaBoMon.enable(false);
        
        int row_index = tableBoMon.getSelectedRow();
        String maBoMon = (String) tableBoMon.getModel().getValueAt(row_index, 1);
        String TenBoMon = (String) tableBoMon.getModel().getValueAt(row_index, 2);
        
        tfMaBoMon.setText(maBoMon);
        tfTenBoMon.setText(TenBoMon);
    }//GEN-LAST:event_tableBoMonMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(clickTable)
        {
            String sql = "update bomon set TenBoMon=N'"+ tfTenBoMon.getText() +"' where MaBoMon = '" + tfMaBoMon.getText() + "'";
            System.out.println(sql);
            this.connect();
            //---------Update---------\\
                try {
                        state = conn.createStatement();
                        int kq  = state.executeUpdate(sql);
                        if(kq > 0)
                            JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công!");
                        else JOptionPane.showMessageDialog(rootPane, "Cập nhật không thành công!!");
                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
                } finally {
                    try {
                            conn.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");

                    }
                }

            hienThiDuLieu();
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Chọn dòng cần sửa!");
        }
        
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if(clickTable)
        {
            String sql = "delete from bomon where MaBoMon='" + tfMaBoMon.getText() + "'";
            this.connect();
            //---------Delete---------\\
                try {
                        state = conn.createStatement();
                        int kq  = state.executeUpdate(sql);
                        if(kq > 0)
                            JOptionPane.showMessageDialog(rootPane, "Xóa thành công!");
                        else JOptionPane.showMessageDialog(rootPane, "Xóa không thành công!!");
                } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
                } finally {
                    try {
                            conn.close();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");

                    }
                }
            hienThiDuLieu();
            tfMaBoMon.enable(true);
            tfMaBoMon.setEditable(true);
            tfMaBoMon.setText("");
            tfTenBoMon.setText("");
            clickTable = false;
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Chọn dòng cần xóa!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed


    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(frm_bomon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(frm_bomon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(frm_bomon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(frm_bomon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new frm_bomon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableBoMon;
    private javax.swing.JTextField tfMaBoMon;
    private javax.swing.JTextField tfTenBoMon;
    // End of variables declaration//GEN-END:variables
}
