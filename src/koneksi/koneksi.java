
package koneksi;
import java.sql.*;
public class koneksi {
public static Connection koneksi;
public Connection connect(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
                System.out.println("_________________________");
        System.out.println("berhasil connect");
    }
    catch(ClassNotFoundException ex){
        System.out.println("gagal connect"+ex);
    }
    String url= "jdbc:mysql://localhost:3306/service_gadget";
    try{
        koneksi=DriverManager.getConnection(url,"root","");
        System.out.println("berhasil koneksi database");
        System.out.println("_________________________");
    }
    catch (SQLException ex){
        System.out.println("gagal koneksi database"+ex);
    }
    return koneksi;
}
}
