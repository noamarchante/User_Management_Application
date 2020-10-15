//LOS MODELOS SE TRABAJAN EN LOS CONTROLADORES

package conexiona.pratices.noapractices.model;

import conexiona.pratices.noapractices.entity.UserGroupUser;

public class MUserGroupUser {

    //HACE REFERENCIA A LA MITAD DE LA CLAVE DE ESTA TABLA REFERENTE A USER SIENDO USERID EL CAMPO DE LA CLASE USERGROUPUSERID
    private String userId;

    //HACE REFERENCIA A LA MITAD DE LA CLAVE DE ESTA TABLA REFERENTE A USERGROUP SIENDO USERID EL CAMPO DE LA CLASE USERGROUPUSERID
    private String userGroupId;

    private byte userAdmin;

    public MUserGroupUser (){}

    //CON ESTE CONSTRUCTOR SE RELACIONAN LA ENTIDAD Y EL MODELO
    public MUserGroupUser (UserGroupUser userGroupUser){
        this.userGroupId = userGroupUser.getUserGroupUserId().getUserGroupId();
        this.userId = userGroupUser.getUserGroupUserId().getUserId();
        this.userAdmin = userGroupUser.getUserAdmin();
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public byte getUserAdmin() {
        return userAdmin;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserAdmin(byte userAdmin) {
        this.userAdmin = userAdmin;
    }
}
