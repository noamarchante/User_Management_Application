//LAS ENTIDADES SE TRABAJAN DESDE LOS REPOSITORIOS
//implements Serializable: proporciona a la clase la capacidad de compartir sus objetos con todas las demas clases. Solo funciona con datos simples
//@Table: indica la tabla en la BD
//@Entity: indica que esto es una clase persistente tipo POJO perteneciente a la BD
//@Id: identifica la PRIMARY KEY (UNIQUE + NOT NULL)
//@Column: características de una columna simple
//@ColumnDefault: valor por defecto de una columna
//@OneToMany: Hace referencia al lado 1 de una relación (1:N)
//@Transient: valor transaccional, no se guarda en la BD
//@Cascade:
    //CascadeType.PERSIST: significa que las operaciones save() o persist() caen en cascada a entidades relacionadas.
    //CascadeType.MERGE: significa que las entidades relacionadas se fusionan cuando se fusiona la entidad propietaria.
    //CascadeType.REFRESH: hace lo mismo que la operación de refresco.
    //CascadeType.REMOVE: elimina todas las entidades relacionadas que se asocian con este ajuste cuando se elimina la entidad propietaria.
    //CascadeType.DETACH: desprende todas las entidades relacionadas si se produce un "desprendimiento manual".
    //CascadeType.ALL: es una abreviatura para todas las operaciones de cascada mencionadas.
//@GeneratedValue: implica que se genera un valor para una columna id
//@GenericGenerator: define como será el generador personalizado

package conexiona.pratices.noapractices.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name="Account")
@Entity(name="Account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="accountId", length = 36, updatable = false)
    private String accountId;

    @Column(name="accountName", nullable = false, length = 200, unique = true)
    private String accountName;

    @Column(name="company", length = 300)
    private String company;

    @Column(name="address", length = 300)
    private String address;

    @Column(name="emailAddress", length = 45, unique = true)
    private String emailAddress;

    @Column(name="dicomId", length = 45, unique = true)
    private String dicomId;

    @Column(name="enabled", nullable = false, length = 4)
    @ColumnDefault(value = "1")
    private byte enabled;

    //RELACIÓN 1:N  CON USER
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Transient
    private Set<User> userSet = new HashSet<>();

    //RELACIÓN 1:N CON USERGROUP
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Transient
    private Set<UserGroup> userGroupSet = new HashSet<>();

    public Account(){}

    public Account (String accountId, String accountName, String company, String address, String emailAddress, String dicomId, byte enabled){
        this.accountId = accountId;
        this.accountName = accountName;
        this.company = company;
        this.address = address;
        this.emailAddress = emailAddress;
        this.dicomId = dicomId;
        this.enabled = enabled;
    }

    public Account (String accountId){
        this.accountId = accountId;
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

    public Set<User> getUserSet() {
        return userSet;
    }

    public Set<UserGroup> getUserGroupSet() {
        return userGroupSet;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public void setUserGroupSet(Set<UserGroup> userGroupSet) {
        this.userGroupSet = userGroupSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }


    public void addUserGroupSet(UserGroup userGroup){
        userGroupSet.add(userGroup);
    }

    public void addUserSet(User user){
        userSet.add(user);
    }


    public void deleteUserGroupSet(UserGroup userGroup){
        userGroupSet.remove(userGroup);
    }

    public void deleteUserSet(User user){
        userSet.remove(user);
    }
}
