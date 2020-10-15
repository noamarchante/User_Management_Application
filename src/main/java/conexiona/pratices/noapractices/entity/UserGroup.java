//LAS ENTIDADES SE TRABAJAN DESDE LOS REPOSITORIOS
//implements Serializable: proporciona a la clase la capacidad de compartir sus objetos con todas las demas clases. Solo funciona con datos simples
//@Table: indica la tabla en la BD
//@Entity: indica que esto es una clase persistente tipo POJO perteneciente a la BD
//@Id: identifica la PRIMARY KEY (UNIQUE + NOT NULL)
//@Column: características de una columna simple
//@ManyToOne: Hace referencia al lado N de una relación (1:N)
//@ManyToMany: hace referencia a una tabla N:M, el atributo mappedBy se refiere al objeto que está en la clase que forma la otra mitad de la relación
//@GeneratedValue: implica que se genera un valor para una columna id
//@GenericGenerator: define como será el generador personalizado
//@ColumnDefault: valor por defecto de una columna
//@Transient: valor transaccional, no se guarda en la BD
//@JoinColumn: referencia a una columna en otra tabla (indica cual es la FK)
//@Cascade:
//CascadeType.PERSIST: significa que las operaciones save() o persist() caen en cascada a entidades relacionadas.
//CascadeType.MERGE: significa que las entidades relacionadas se fusionan cuando se fusiona la entidad propietaria.
//CascadeType.REFRESH: hace lo mismo que la operación de refresco.
//CascadeType.REMOVE: elimina todas las entidades relacionadas que se asocian con este ajuste cuando se elimina la entidad propietaria.
//CascadeType.DETACH: desprende todas las entidades relacionadas si se produce un "desprendimiento manual".
//CascadeType.ALL: es una abreviatura para todas las operaciones de cascada mencionadas.

package conexiona.pratices.noapractices.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name="UserGroup")
@Entity(name="UserGroup")
public class UserGroup implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="userGroupId", length = 36)
    private String userGroupId;

    //RELACIÓN N:1 CON ACCOUNT
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", nullable = false)
    private Account account;

    @Column(name="userGroupName", length = 45, nullable = false, unique = true)
    private String userGroupName;

    @Column(name="enabled", length = 4, nullable = false)
    @ColumnDefault(value = "1")
    private byte enabled;

    //TABLA N:M CON CLAVE COMPUESTA SIN MÁS ATRIBUTOS
   /* @ManyToMany(mappedBy = "userGroupSet")
    Set<User> userSet;*/

    //TABLA N:M CON CLAVE COMPUESTA EN CLASE EXTERNA USERGROUPUSERID Y MÁS ATRIBUTOS EN USERGROUPUSER
    //mappedBy: hace referencia al objeto userGroup en la clase UserGroupUser
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    @Transient
    private Set<UserGroupUser> userGroupUserSet = new HashSet<>();

    public UserGroup (){}

    public UserGroup (String userGroupId, Account account, String userGroupName, byte enabled){
        this.userGroupId = userGroupId;
        this.account = account;
        this.userGroupName = userGroupName;
        this.enabled = enabled;
    }

    public Account getAccount() {
        return account;
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

    public Set<UserGroupUser> getUserGroupUserSet() {
        return userGroupUserSet;
    }


    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public void setUserGroupUserSet(Set<UserGroupUser> userGroupUserSet) {
        this.userGroupUserSet = userGroupUserSet;
    }


    public void addUserGroupUserSet(UserGroupUser userGroupUser){
        userGroupUserSet.add(userGroupUser);
    }


    public void deleteUserGroupUserSet(UserGroupUser userGroupUser){
        userGroupUserSet.remove(userGroupUser);
    }
}
