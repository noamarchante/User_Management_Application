//LOS MODELOS SE TRABAJAN EN LOS CONTROLADORES

package conexiona.pratices.noapractices.model;

import conexiona.pratices.noapractices.entity.UserGroup;

public class MUserGroup {

    private String userGroupId;

    //RELACIÓN N:1 CON ACCOUNT
    private String accountId;

    private String userGroupName;

    private byte enabled;

    public MUserGroup (){}

    //CON ESTE CONSTRUCTOR SE RELACIONAN LA ENTIDAD Y EL MODELO
    public MUserGroup (UserGroup userGroup){
        this.userGroupId = userGroup.getUserGroupId();
        this.userGroupName =userGroup.getUserGroupName();
        this.enabled = userGroup.getEnabled();
        this.accountId = userGroup.getAccount().getAccountId();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public byte getEnabled() {
        return enabled;
    }


    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }
}
