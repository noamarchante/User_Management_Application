//LAS ENTIDADES SE TRABAJAN DESDE LOS REPOSITORIOS
//implements Serializable: proporciona a la clase la capacidad de compartir sus objetos con todas las demas clases. Solo funciona con datos simples
//@Embeddable: Proporciona a la clase la capacidad de ser embebida por otra clase

package conexiona.pratices.noapractices.entity;

import javax.persistence.*;
import java.io.Serializable;

//TABLA N:M CON CLAVE COMPUESTA EN CLASE EXTERNA Y MÁS ATRIBUTOS
@Embeddable
public class UserGroupUserId implements Serializable {

    private String userId;

    private String userGroupId;

    //CON ESTE CONSTRUCTOR TRABAJA HIBERNATE
    public UserGroupUserId(){}

    public UserGroupUserId (String userId, String userGroupId){
        this.userId = userId;
        this.userGroupId = userGroupId;
    }


    public String getUserGroupId() {
        return userGroupId;
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

}
