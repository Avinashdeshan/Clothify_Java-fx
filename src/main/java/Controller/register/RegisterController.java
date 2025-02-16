package Controller.register;

import dbconnection.DBConnection;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {
    private static RegisterController registerController;

    private RegisterController() {

    }

    public static RegisterController getInstance() {
        if (registerController == null) {
            registerController = new RegisterController();
        }
        return registerController;
    }
    public boolean setUsers(String username, String email, String password, String confirmedPassword) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("0169");

        try {
            if (password.equals(confirmedPassword)) {
                PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select * from admin where email = ?");
                stm.setObject(1, email);
                ResultSet res = stm.executeQuery();
                if (!res.next()) {
                    PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement("Insert into admin (username,email,password) values (?,?,?)");
                    psTm.setObject(1, username);
                    psTm.setObject(2, email);
                    psTm.setObject(3, basicTextEncryptor.encrypt(password));
                    return psTm.executeUpdate() > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
