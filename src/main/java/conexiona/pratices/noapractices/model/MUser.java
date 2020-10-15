//LOS MODELOS SE TRABAJAN EN LOS CONTROLADORES

package conexiona.pratices.noapractices.model;

import conexiona.pratices.noapractices.entity.User;
import java.util.Date;

public class MUser {

    private String userId;

    //RELACIÓN N:1 CON ACCOUNT
    private String accountId;

    private String userName;

    private String emailAddress;

    private String password;

    private byte enabled;

    private Date lastLogin;

    public MUser(){}

    //CON ESTE CONSTRUCTOR SE RELACIONAN LA ENTIDAD Y EL MODELO
    public MUser (User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.emailAddress = user.getEmailAddress();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
        this.accountId = user.getAccount().getAccountId();
        this.lastLogin = user.getLastLogin();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public byte getEnabled() {
        return enabled;
    }

    public Date getLastLogin() {
        return lastLogin;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
