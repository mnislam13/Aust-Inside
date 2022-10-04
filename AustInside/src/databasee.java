/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
import java.awt.Image;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class databasee {

    String stst;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public databasee() {
        connect();

    }
    

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "");
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
public static String dname, dpassword,demail,did,dgender,dbday,ddept,dyear,dsem,dcontact;
    public boolean getData(String $$email,String $$password) {
        //boolean flag=false;
        
        try {
            rs = stmt.executeQuery("select * from userinfo");
            while (rs.next()) {
                demail=rs.getString("email");
                dpassword=rs.getString("password");
                if($$email.equals(demail)&&$$password.equals(dpassword))
                {
                did=rs.getString("id");
                dname = rs.getString("name");
                dgender = rs.getString("gender");
                dbday=rs.getString("bday");
                ddept=rs.getString("dept");
                dyear=rs.getString("year");
                dsem=rs.getString("sem");
                dcontact=rs.getString("contact");
                System.out.println(dname + "  " + dpassword);
                return true;
                
                }
                
                //stst = rs.getString("email");
                

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
}

    public boolean getsData(String $$email,String $$id) {
        //boolean flag=false;
        
        try {
            rs = stmt.executeQuery("select * from userinfo");
            while (rs.next()) {
                demail=rs.getString("email");
                did=rs.getString("id");
                if($$email.equals(demail) || $$id.equals(did))
                {     
                return false; 
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
}

    public void setData(String $email, String $id, String $password,
            String $name, String $gender,String $bday, String $dept, String $year, String $sem, String $contact) {
        try {
            String sql = "insert into userinfo values('" + $email + "', '" + $id
                    + "', '" + $password + "', '" + $name + "', '" + $gender + "','" +
                    $bday + "',  '" + $dept+ "', '" + $sem + "', '" + $year + "', '" + $contact + "')";
            stmt.executeUpdate(sql);
            //getData();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setImageIntoLabel(String $$id, JLabel label){
        try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/user","root","");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from userimages where db_ID = '"+$$id+"'");
                if(rs.next()){
                    byte[] img = rs.getBytes("db_image");
                   
                    //Resize The ImageIcon
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    label.setIcon(newImage);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }
    
    public static void setImageIntoButton(String $$id, JButton button){
        try{
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/user","root","");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from userimages where db_ID = '"+$$id+"'");
                if(rs.next()){
                    byte[] img = rs.getBytes("db_image");
                   
                    //Resize The ImageIcon
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(button.getWidth(), button.getHeight(),Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    button.setIcon(newImage);
                    
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }
           
    
    public static void main(String args[]) {
        new databasee();
    }
}
