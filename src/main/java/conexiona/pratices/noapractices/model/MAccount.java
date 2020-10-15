//LOS MODELOS SE TRABAJAN EN LOS CONTROLADORES

package conexiona.pratices.noapractices.model;

import conexiona.pratices.noapractices.entity.Account;

public class MAccount {

    private String accountId;

    private String accountName;

    private String company;

    private String address;

    private String emailAddress;

    private String dicomId;

    private byte enabled;

    public MAccount (){

    }

    //CON ESTE CONSTRUCTOR SE RELACIONAN LA ENTIDAD Y EL MODELO
    public MAccount (Account account){
        this.accountId = account.getAccountId();
        this.accountName = account.getAccountName();
        this.company = account.getCompany();
        this.address = account.getAddress();
        this.emailAddress = account.getEmailAddress();
        this.dicomId = account.getDicomId();
        this.enabled = account.getEnabled();

    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDicomId() {
        return dicomId;
    }

    public byte getEnabled() {
        return enabled;
    }




    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDicomId(String dicomId) {
        this.dicomId = dicomId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
