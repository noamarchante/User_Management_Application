//LAS ENTIDADES SE TRABAJAN DESDE LOS REPOSITORIOS
//implements Serializable: proporciona a la clase la capacidad de compartir sus objetos con todas las demas clases. Solo funciona con datos simples
//@Table: indica la tabla en la BD
//@Entity: indica que esto es una clase persistente tipo POJO perteneciente a la BD
//@Id: identifica la PRIMARY KEY (UNIQUE + NOT NULL)
//@Column: características de una columna simple
//@ManyToOne: Hace referencia al lado N de una relación (1:N)
//@JoinColumn: referencia a una columna en otra tabla (indica cual es la FK)
//@ManyToMany: hace referencia a una tabla N:M
//@JoinTable: crea una tabla utilizando atributos de otras tablas. El atributo joinColumns añade a la nueva tabla atributos de la clase en la que está. El atributo inverseJoinColumn añade los atributos de la otra clase que forma la tabla
//@GeneratedValue: implica que se genera un valor para una columna id
//@GenericGenerator: define como será el generador personalizado
//@ColumnDefault: valor por defecto de una columna
//@Transient: valor transaccional, no se guarda en la BD
//@Temporal(): campo de tipo fecha
//@Cascade:
    //CascadeType.PERSIST: significa que las operaciones save() o persist() caen en cascada a entidades relacionadas.
    //CascadeType.MERGE: significa que las entidades relacionadas se fusionan cuando se fusiona la entidad propietaria.
    //CascadeType.REFRESH: hace lo mismo que la operación de refresco.
    //CascadeType.REMOVE: elimina todas las entidades relacionadas que se asocian con este ajuste cuando se elimina la entidad propietaria.
    //CascadeType.DETACH: desprende todas las entidades relacionadas si se produce un "desprendimiento manual".
    //CascadeType.ALL: es una abreviatura para todas las operaciones de cascada mencionadas.

package conexiona.pratices.noapractices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Table(name="User")
@Entity(name="User")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="userId", length = 36)
    private String userId;

    //RELACIÓN N:1 CON ACCOUNT
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId", nullable = false)
    private Account account;

    @Column(name="userName", nullable = false, length = 200, unique = true)
    private String userName;

    @Column(name="emailAddress", nullable = false, length = 100, unique = true)
    private String emailAddress;

    @Column(name="password", nullable= false, length = 100)
    @JsonIgnore
    private String password;

    @Column(name="enabled", nullable = false, length = 4)
    @ColumnDefault(value = "1")
    private byte enabled;

    @Column(name="lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    //TABLA N:M SOLO CON LA CLAVE COMPUESTA Y SIN MÁS ATRIBUTOS
    /*@ManyToMany
    @JoinTable(
            name = "UserGroupUser",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "userGroupId")}
    )
    Set<UserGroup> userGroupSet;*/

    //TABLA N:M CON CLAVE COMPUESTA EN CLASE EXTERNA USERGROUPUSERID Y MÁS ATRIBUTOS EN USERGROUPUSER
    //mappedBy: hace referencia al objeto user en la clase UserGroupUser
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserGroupUser> userGroupUserSet = new HashSet<>();

    public User (){}

    public User (String userId, Account account, String userName, String emailAddress, String password, byte enabled, Date lastLogin){
        this.userId = userId;
        this.account = account;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password =password;
        this.enabled = enabled;
        this.lastLogin = lastLogin;

    }

    public String getUserId() {
        return userId;
    }

    public Account getAccount() {
        return account;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public byte getEnabled() {
        return enabled;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public Set<UserGroupUser> getUserGroupUserSet() {
        return userGroupUserSet;
    }


    public void setAccount(Account account) {
        this.account = account;
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
        this.password =password;
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
