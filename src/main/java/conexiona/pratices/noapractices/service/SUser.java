//PLANTILLA DE COMO VA A SER EL SERVICIO
//@Service: identifica la clase como un servicio
//@Autowired: permite la inyección de propiedades
//@Qualifier: permite establecer un nombre

package conexiona.pratices.noapractices.service;

import conexiona.pratices.noapractices.converter.ConUser;
import conexiona.pratices.noapractices.converter.ConUserGroupUser;
import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.entity.User;
import conexiona.pratices.noapractices.entity.UserGroupUser;
import conexiona.pratices.noapractices.model.MUser;
import conexiona.pratices.noapractices.model.MUserGroupUser;
import conexiona.pratices.noapractices.repository.RAccount;
import conexiona.pratices.noapractices.repository.RUser;
import conexiona.pratices.noapractices.repository.RUserGroup;
import conexiona.pratices.noapractices.repository.RUserGroupUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("SUser")
public class SUser implements UserDetailsService {

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    @Qualifier("RAccount")
    private RAccount rAccount;

    @Autowired
    @Qualifier("RUserGroupUser")
    private RUserGroupUser rUserGroupUser;

    @Autowired
    @Qualifier("ConUserGroupUser")
    private ConUserGroupUser conUserGroupUser;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    private  static final Log logger = LogFactory.getLog(SUser.class);

    //JWT: ESTE MÉTODO BUSCAR UN USUARIO CON EL NOMBRE PASADO POR PARÁMETRO Y DEVUELVE UN OBJETO DE TIPO USERDETAILS CON NOMBRE + CONTRASEÑA + LISTA DE PERMISOS VACÍA
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = rUser.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else {
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthorities(user));
        }
    }

    protected Collection<? extends GrantedAuthority> getAuthorities (User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        rUserGroupUser.findByUserGroupUserId_UserId(user.getUserId()).forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+rUserGroup.findByUserGroupId(p.getUserGroupUserId().getUserGroupId()).getUserGroupName());
            authorities.add(authority);
        });
        return authorities;
    }

    public synchronized boolean insertUser (MUser mUser){
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findByUserId(user.getUserId());
        Account existingAccount = rAccount.findByAccountId(user.getAccount().getAccountId());
        if (existingUser != null || existingAccount == null){
            logger.error("USER EXISTS OR ACCOUNT NOT EXISTS");
            return false;
        }else {
            //user.setPassword(bcryptEncoder.encode(user.getPassword()));
            rUser.save(user);
            logger.info("USER SAVED");
            return true;
        }
    }

    public synchronized boolean updateUser (MUser mUser){
        User user = conUser.conMUser(mUser);
        User existingUser = rUser.findById(user.getUserId()).get();
        Account existingAccount = rAccount.findById(user.getAccount().getAccountId()).get();
        if(existingUser != null && existingAccount != null ) {
            //user.setPassword(bcryptEncoder.encode(user.getPassword()));
            rUser.save(user);
            logger.info("USER UPDATED");
            return true;
        }else {
            logger.error("USER OR ACCOUNT NOT EXISTS");
            return false;
        }
    }

    public synchronized boolean deleteUser (String userId){
        User existingUser = rUser.findById(userId).get();
        if(existingUser != null) {
            rUser.delete(existingUser);
            logger.info("USER DELETED");
            return true;
        }else {
            logger.error("USER NOT EXISTS");
            return false;
        }
    }

    public synchronized List<MUser> selectAllUser (){
        List<User> userList = new ArrayList<>();
        rUser.findAll().forEach(e -> userList.add(e));
        return conUser.conUserList(userList);
    }

    public synchronized List<MUser> selectPageableUser (Pageable pageable){
        return conUser.conUserList(rUser.findAll(pageable).getContent());
    }

    public synchronized MUser selectUserByUserId (String userId){
        return conUser.conUser(rUser.findById(userId).get());
    }

    public synchronized List<MUserGroupUser> selectUserGroupUserbyUserId (String userId){
        List<UserGroupUser> userGroupUserList = new ArrayList<>();
        for (UserGroupUser userGroupUser : rUserGroupUser.findByUser_UserId(userId)) {
            userGroupUserList.add(userGroupUser);
        }
        return conUserGroupUser.conUserGroupUserList(userGroupUserList);
    }
}
