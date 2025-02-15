package Controller.adminlogin;

import dbconnection.DBConnection;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private static LoginController loginController;

    private LoginController(){
    }

    public static LoginController getInstance(){
        if (loginController==null){
            loginController=new LoginController();
        }
        return loginController;
    }
    public boolean setLogin(String email, String password)  {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("0169");

        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from admin where email = ?");
            stm.setObject(1,email);
            ResultSet res = stm.executeQuery();
            if(res.next()) {
                return password.equals(basicTextEncryptor.decrypt(res.getString(4)));
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
