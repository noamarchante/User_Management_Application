//LAS ENTIDADES SE TRABAJAN DESDE LOS REPOSITORIOS
//implements Serializable: proporciona a la clase la capacidad de compartir sus objetos con todas las demas clases. Solo funciona con datos simples
//@Table: indica la tabla en la BD
//@Entity: indica que esto es una clase persistente tipo POJO perteneciente a la BD
//@EmbeddedId: hace referencia a la clave compuesta de la tabla
//@Column: características de una columna simple
//@ManyToOne: Hace referencia al lado N de una relación (1:N)
//@MapsId: hace referencia a cada atributo que forma la clave mapeando con el atributo que le representa en la clase de la que lo coge
//@JoinColumn: referencia a una columna en otra tabla (indica cual es la FK)
//@ColumnDefault: valor por defecto de una columna

package conexiona.pratices.noapractices.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.io.Serializable;

@Table(name="UserGroupUser")
@Entity(name="UserGroupUser")
public class UserGroupUser implements Serializable {

    //TABLA N:M CON CLAVE COMPUESTA EN CLASE EXTERNA USERGROUPUSERID Y MÁS ATRIBUTOS EN ESTA CLASE
    @EmbeddedId
    private UserGroupUserId userGroupUserId;

    //HACE REFERENCIA A LA MITAD DE LA CLAVE DE ESTA TABLA REFERENTE A USER SIENDO USERID EL CAMPO DE LA CLASE USERGROUPUSERID

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", nullable = false, columnDefinition = "varchar(36)")
    private User user;

    //HACE REFERENCIA A LA MITAD DE LA CLAVE DE ESTA TABLA REFERENTE A USERGROUP SIENDO USERID EL CAMPO DE LA CLASE USERGROUPUSERID

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userGroupId")
    @JoinColumn(name = "userGroupId", nullable = false, columnDefinition = "varchar(36)")
    private UserGroup userGroup;

    @Column(name = "userAdmin", nullable = false, length = 4)
    @ColumnDefault(value = "0")
    private byte userAdmin;


    //CON ESTE CONSTRUCTOR TRABAJA HIBERNATE
    public UserGroupUser(){}

    public UserGroupUser (UserGroupUserId userGroupUserId, byte userAdmin){
        this.userGroupUserId = userGroupUserId;
        this.userAdmin = userAdmin;
    }


    public User getUser() {
        return user;
    }

    public UserGroupUserId getUserGroupUserId() {
        return userGroupUserId;
    }

    public byte getUserAdmin() {
        return userAdmin;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }


    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserAdmin(byte userAdmin) {
        this.userAdmin = userAdmin;
    }

    public void setUserGroupUserId(UserGroupUserId userGroupUserId) {
        this.userGroupUserId = userGroupUserId;
    }
}
