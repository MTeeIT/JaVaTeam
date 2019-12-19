/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancansu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * @author MTee
 */
public class Frm_Covan extends javax.swing.JFrame {

    private boolean clickTable = false;
    private String userName = "root";//datausername
    private String password = "";//datapassuser
    private String url = "jdbc:mysql://127.0.0.1:3306/quanlybcs?useUnicode=true&characterEncoding=utf-8";	
    private Connection conn = null;
    private Statement state;
    private ResultSet rs;
    public Frm_Covan()
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//Center postion
        setResizable(false);
        hienThiDuLieu();
        cbbItemCoVanFrm();
        setSizeTable();
    }
    private void setSizeTable()
    {
        tableCoVan.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=tableCoVan.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(50);    
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(250);    
        colModel.getColumn(3).setPreferredWidth(150);
        colModel.getColumn(4).setPreferredWidth(150);    
        colModel.getColumn(5).setPreferredWidth(150);
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
        DefaultTableModel model = (DefaultTableModel) tableCoVan.getModel();
        lamRongTable(model);
        
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from covanhoctap, bomon where covanhoctap.MaBoMon = bomon.MaBoMon" ;
        this.connect();
        
        //---------Truy Vấn---------\\
        try {
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                int i = 1;
                while(rs.next())
                {
                    model.addRow(new Object[]{ i, rs.getString("MaCV"), rs.getString("HoTen_CV"), rs.getString("SDT_CV"), rs.getString("Email_CV"), rs.getString("TenBoMon") });
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

    public boolean tonTaiMaCoVan(String maCV)
    {
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from covanhoctap where MaCV='" + maCV + "'" ;

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
    
    private void cbbItemCoVanFrm()
    {
        String sql = "select * from bomon";
        this.connect();
        try{
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while(rs.next()){
                String TenBM = rs.getString("TenBoMon");
                String MaBM = rs.getString("MaBoMon");
                cbTenBoMon.addItem(TenBM);
                
            }
        } catch(Exception e){JOptionPane.showMessageDialog(null,"Failed to Item-List..!"); e.printStackTrace(); return;}
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
            }
        }
    }
    public String getMaFromMySQL(String tenBang, String tenTruong, String value, String tenTruongCanLay)
    {
        String sql = "select * from " + tenBang + " where "+ tenTruong +" = '" + value + "'";
        this.connect();
        try{
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while(rs.next()){
                return rs.getString(tenTruongCanLay);
            }
        } catch(Exception e){ JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");}
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Lỗi kết nối CSDL");
            }
        }
        return "";
    }
    
    public boolean themMoi()
    {
        if(tonTaiMaCoVan(tfMaCoVan.getText()))
        {
            return false;
        }
        else
        {
             //---------Lệnh Truy Vấn---------\\
//            String sql = "insert into covanhoctap values('" + tfMaCoVan.getText() + "', N'" + tfTenCoVan.getText() 
//                    + "', '" + cbTenBoMon.getSelectedItem().toString() +"', '" + tfSDT.getText() + "', '" + tfEmail.getText() + "')" ;
            String sql = "insert into covanhoctap (MaCV, HoTen_CV, SDT_CV, Email_CV, MaBoMon)" + " value(?,?,?,?,?)";
            String tenBoMon = cbTenBoMon.getSelectedItem().toString();
            this.connect();

            //---------Truy Vấn---------\\
            try {
                PreparedStatement preStmt = conn.prepareStatement(sql);
                preStmt.setString (1, tfMaCoVan.getText());
                preStmt.setString (2, tfTenCoVan.getText());
                preStmt.setString (3, tfSDT.getText());
                preStmt.setString (4, tfEmail.getText());
                preStmt.setString (5, this.getMaFromMySQL("bomon", "TenBoMon", tenBoMon, "MaBoMon"));
                int kq  = preStmt.executeUpdate();
                    if(kq > 0)
                        return true;
                    else return false;
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(rootPane, e);
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
        tableCoVan = new javax.swing.JTable();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        tfEmail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbTenBoMon = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        tfMaCoVan = new javax.swing.JTextField();
        tfSDT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfTenCoVan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(243, 156, 18));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel35.setText("Thông tin cố vấn");

        jPanel7.setBackground(new java.awt.Color(44, 62, 80));

        tableCoVan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableCoVan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã cố vấn", "Tên cố vấn", "SĐT", "Email", "Tên bộ môn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCoVan.setRowHeight(22);
        tableCoVan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCoVanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCoVan);

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
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION), "", javax.swing.border.TitledBorder.TRAILING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51)), "Nhập thông tin cố vấn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 22), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tên bộ môn");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mã cố vấn:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SĐT:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Họ tên:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(tfMaCoVan))
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfTenCoVan, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTenBoMon, 0, 171, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfMaCoVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTenCoVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbTenBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addContainerGap(51, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(340, 340, 340))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(29, 29, 29)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        tfMaCoVan.enable(true);
        tfMaCoVan.setEditable(true);
        tfMaCoVan.setText("");
        tfTenCoVan.setText("");
        tfSDT.setText("");
        tfEmail.setText("");
        clickTable = false;        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(!this.clickTable)
        {
            if(tfMaCoVan.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống mã cố vấn!");
            }
            else if(tfTenCoVan.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống tên cố vấn!");
            }
            else if(tfSDT.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống sô điện thoại!");
            }
            else if(tfEmail.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống email!");
            }
            else if(tfMaCoVan.getText().toString().length()>10)
            {
                JOptionPane.showMessageDialog(rootPane, "Mã cố vấn không được quá 10 kí tự!");
            }
            else if(tfTenCoVan.getText().toString().length()>30)
            {
                JOptionPane.showMessageDialog(rootPane, "Tên cố vấn không được quá 30 kí tự!");
            }
            else if(tfSDT.getText().toString().length()>10)
            {
                JOptionPane.showMessageDialog(rootPane, "Số điện thoại không được quá 10 kí tự!");
            }
            else if(tfEmail.getText().toString().length()>30)
            {
                JOptionPane.showMessageDialog(rootPane, "Email không được quá 10 kí tự!");
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
                    JOptionPane.showMessageDialog(rootPane, "Mã cố vấn đã tồn tại!");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Cần nhấn làm mới để thêm cố vấn!");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableCoVanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCoVanMouseClicked
        clickTable = true;
        tfMaCoVan.setEditable(false);
        tfMaCoVan.enable(false);
        
        int row_index = tableCoVan.getSelectedRow();
        String maCoVan = (String) tableCoVan.getModel().getValueAt(row_index, 1);
        String TenCoVan = (String) tableCoVan.getModel().getValueAt(row_index, 2);
        String SDT = (String) tableCoVan.getModel().getValueAt(row_index, 3);
        String Email = (String) tableCoVan.getModel().getValueAt(row_index, 4);
        String tenBoMon =  (String) tableCoVan.getModel().getValueAt(row_index, 5);
        cbTenBoMon.setSelectedItem(tenBoMon);

        
        tfMaCoVan.setText(maCoVan);
        tfTenCoVan.setText(TenCoVan);
        
        tfSDT.setText(SDT);
        tfEmail.setText(Email);// TODO add your handling code here:
    }//GEN-LAST:event_tableCoVanMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(clickTable)
        {
            String maBoMon = getMaFromMySQL("bomon", "TenBoMon", cbTenBoMon.getSelectedItem().toString(), "MaBoMon");
            String sql = "update covanhoctap set HoTen_CV=N'"+ tfTenCoVan.getText() +"', SDT_CV='" + tfSDT.getText() + 
                    "', Email_CV='" + tfEmail.getText() +"', MaBoMon='" + maBoMon + "' where MaCV = '" + tfMaCoVan.getText() + "'";
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
                // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if(clickTable)
        {
            String sql = "delete from covanhoctap where MaCV='" + tfMaCoVan.getText() + "'";
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
            tfMaCoVan.enable(true);
            tfMaCoVan.setEditable(true);
            tfMaCoVan.setText("");
            tfTenCoVan.setText("");
            tfSDT.setText("");
            tfEmail.setText("");
            clickTable = false; 
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Chọn dòng cần xóa!");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Frm_Covan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Frm_Covan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Frm_Covan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Frm_Covan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new Frm_Covan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbTenBoMon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCoVan;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfMaCoVan;
    private javax.swing.JTextField tfSDT;
    private javax.swing.JTextField tfTenCoVan;
    // End of variables declaration//GEN-END:variables
}
