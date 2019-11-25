package bancansu;
/**
 * @(#)DBase.java
 *
 *
 * @author 
 * @version 1.00 2019/6/25
 */

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DataBase {

    private String userName = "root";//datausername
    private String password = "";//datapassuser
    private String url = "jdbc:mysql://127.0.0.1:3306/quanlybcs";	
    private Connection conn = null;
    private Statement state;
    private ResultSet rs;
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
                        System.out.print("Loi: " + e.getMessage());;
                }

        }
        catch(Exception e)
        {
                System.out.print("Khong load duoc Driver");
        }
    }  
    
    public ResultSet queryUser(String sql)
    {
        
        return rs;
    }
}
