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
public class fmr_lop extends javax.swing.JFrame {

    private boolean clickTable = false;
    private String userName = "root";//datausername
    private String password = "";//datapassuser
    private String url = "jdbc:mysql://127.0.0.1:3306/quanlybcs?useUnicode=true&characterEncoding=utf-8";	
    private Connection conn = null;
    private Statement state;
    private ResultSet rs;
    public fmr_lop()
    {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);//Center postion
        setResizable(false);
        hienThiDuLieu();
        setSizeTable();
        cbbItemTenBM();
        cbbItemTenCV();
    }
    private void setSizeTable()
    {
    tableLop.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=tableLop.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(50);    
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(200);    
        colModel.getColumn(3).setPreferredWidth(200);
        colModel.getColumn(4).setPreferredWidth(200);
        colModel.getColumn(5).setPreferredWidth(200);
    }
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
        DefaultTableModel model = (DefaultTableModel) tableLop.getModel();
        lamRongTable(model);
        
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from lop" ;
        this.connect();
        
        //---------Truy Vấn---------\\
        try {
                state = conn.createStatement();
                rs = state.executeQuery(sql);
                int i = 1;
                while(rs.next())
                {
                    model.addRow(new Object[]{ i, rs.getString("MaLop"), rs.getString("TenLop"), rs.getString("EmailLop"), rs.getString("MaBoMon"), rs.getString("MaCV") });
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
    public boolean tonTaiMaLop(String maLop)
    {
         //---------Lệnh Truy Vấn---------\\
        String sql = "select * from lop where MaLop='" + maLop + "'" ;

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
    private void cbbItemTenBM()
    {
        String sql = "select * from bomon";
        this.connect();
        try{
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while(rs.next()){
                String TenBM = rs.getString("TenBoMon");
                String MaBM = rs.getString("MaBoMon");
                
                cbBoMon.addItem(MaBM);
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
    private void cbbItemTenCV()
    {
        String sql = "select * from covanhoctap";
        this.connect();
        try{
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while(rs.next()){
                String TenCV = rs.getString("HoTen_CV");
                String MaCV = rs.getString("MaCV");
                cbCoVan.addItem(MaCV);
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
    public boolean themMoi()
    {
        if(tonTaiMaLop(tfMaLop.getText()))
        {
            return false;
        }
        else
        {
             if(tonTaiMaLop(tfMaLop.getText()))
            {
                return false;
            }
            else
            {
                String tenBoMon = cbBoMon.getSelectedItem().toString();
                String tenCoVan = cbCoVan.getSelectedItem().toString();
                String maBoMon = this.getMaFromMySQL("bomon", "TenBoMon", tenBoMon, "MaBoMon");
                String maCoVan = this.getMaFromMySQL("covanhoctap", "HoTen_CV", tenCoVan, "MaCV");
                String malop = tfMaLop.getText();
                String tenLop = tfTenLop.getText();
                String emailLop = tfEmail.getText();
                 //---------Lệnh Truy Vấn---------\\
                String sql = "insert into lop " + " values('" + malop + "'," + "N'" + tenLop + "'," + "'";
                sql += emailLop + "'," + "'" + maBoMon + "',"+ "'" + maCoVan + "')";
                System.out.println(sql);
                this.connect();

                //---------Truy Vấn---------\\
                try {
    //                PreparedStatement preStmt = conn.prepareStatement(sql);
    //                preStmt.setString (1, tfMaLop.getText());
    //                preStmt.setString (2, tfTenLop.getText());
    //                preStmt.setString (3, tfEmail.getText());
    //                preStmt.setString (4, cbBoMon.getSelectedItem().toString());
    //                preStmt.setString (5, cbCoVan.getSelectedItem().toString());
                    state = conn.createStatement();
                    int kq  = state.executeUpdate(sql);
                    //int kq  = preStmt.executeUpdate();
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
        tableLop = new javax.swing.JTable();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfMaLop = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfTenLop = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbCoVan = new javax.swing.JComboBox<>();
        cbBoMon = new javax.swing.JComboBox<>();
        tfEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(243, 156, 18));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel35.setText("Thông tin lớp");

        jPanel7.setBackground(new java.awt.Color(44, 62, 80));

        tableLop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tableLop.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableLop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã lớp", "Tên lớp", "Email lớp", "Tên bộ môn", "Tên cố vấn"
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
        tableLop.setRowHeight(22);
        tableLop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLopMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableLop);

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
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION), "", javax.swing.border.TitledBorder.TRAILING, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51)), "Nhập thông tin lớp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 22), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mã lớp:");

        tfMaLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMaLopActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tên bộ môn:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên lớp:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tên cố vấn:");

        tfEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEmailActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Email lớp:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbBoMon, 0, 134, Short.MAX_VALUE)
                    .addComponent(tfEmail)
                    .addComponent(tfMaLop))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfTenLop)
                    .addComponent(cbCoVan, 0, 135, Short.MAX_VALUE))
                .addGap(0, 68, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfTenLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(cbCoVan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbBoMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
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
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnLamMoi)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(jLabel35)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
 @SuppressWarnings("unchecked")
    private void tfMaLopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tfMaLopActionPerformed
    {//GEN-HEADEREND:event_tfMaLopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMaLopActionPerformed

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        tfMaLop.enable(true);
        tfMaLop.setEditable(true);
        tfMaLop.setText("");
        tfTenLop.setText("");
        tfEmail.setText("");
        clickTable = false;          // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
if(!this.clickTable)
        {
            if(tfMaLop.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống mã lớp!");
            }
            else if(tfTenLop.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống tên lớp!");
            }
            else if(tfEmail.getText().equals(""))
            {
                JOptionPane.showMessageDialog(rootPane, "Không được để trống Email!");
            }
            else if(tfMaLop.getText().toString().length()>10)
            {
                JOptionPane.showMessageDialog(rootPane, "Mã lớp không được quá 10 kí tự!");
            }
            else if(tfTenLop.getText().toString().length()>30)
            {
                JOptionPane.showMessageDialog(rootPane, "Tên lớp không được quá 30 kí tự!");
            }
            else if(tfEmail.getText().toString().length()>30)
            {
                JOptionPane.showMessageDialog(rootPane, "Email lớp không được quá 30 kí tự!");
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
                    JOptionPane.showMessageDialog(rootPane, "Mã lớp đã tồn tại!");
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Cần nhấn làm mới để thêm Lớp!");
        }        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableLopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLopMouseClicked
        clickTable = true;
        tfMaLop.setEditable(false);
        tfMaLop.enable(false);
        
        int row_index = tableLop.getSelectedRow();
        String maLop = (String) tableLop.getModel().getValueAt(row_index, 1);
        String TenLop = (String) tableLop.getModel().getValueAt(row_index, 2);
        //COMBOBOX
        String Email = (String) tableLop.getModel().getValueAt(row_index, 3);
        
        tfMaLop.setText(maLop);
        tfTenLop.setText(TenLop);
        tfEmail.setText(Email);// TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_tableLopMouseClicked
    
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
    
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if(clickTable)
        {
            String tenBoMon = cbBoMon.getSelectedItem().toString();
            String tenCoVan = cbCoVan.getSelectedItem().toString();
            String maBoMon = this.getMaFromMySQL("bomon", "TenBoMon", tenBoMon, "MaBoMon");
            String maCoVan = this.getMaFromMySQL("covanhoctap", "HoTen_CV", tenCoVan, "MaCV");
            String sql = "update lop set TenLop=N'"+ tfTenLop.getText() +
                    "', EmailLop='" + tfEmail.getText() +"', MaBoMon ='" + maBoMon + "', MaCV='" + maCoVan + "'  where MaLop = '" + tfMaLop.getText() + "'";
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
if(clickTable)
        {
            String sql = "delete from lop where MaLop='" + tfMaLop.getText() + "'";
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
            java.util.logging.Logger.getLogger(fmr_lop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(fmr_lop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(fmr_lop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(fmr_lop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new fmr_lop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbBoMon;
    private javax.swing.JComboBox<String> cbCoVan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableLop;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfMaLop;
    private javax.swing.JTextField tfTenLop;
    // End of variables declaration//GEN-END:variables
}
