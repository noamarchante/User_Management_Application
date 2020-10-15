package conexiona.pratices.noapractices.model;

import java.io.Serializable;

//DATOS JSON QUE SE PASA A LA PETICIÓN
public class MJwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String userName;
    private String password;

    public MJwtRequest() {
    }

    public MJwtRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
}
