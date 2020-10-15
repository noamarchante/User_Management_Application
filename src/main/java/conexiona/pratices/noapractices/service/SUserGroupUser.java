//PLANTILLA DE COMO VA A SER EL SERVICIO
//@Service: identifica la clase como un servicio
//@Autowired: permite la inyección de propiedades
//@Qualifier: permite establecer un nombre

package conexiona.pratices.noapractices.service;

import conexiona.pratices.noapractices.converter.ConUserGroupUser;
import conexiona.pratices.noapractices.entity.*;
import conexiona.pratices.noapractices.model.MUserGroupUser;
import conexiona.pratices.noapractices.repository.RUser;
import conexiona.pratices.noapractices.repository.RUserGroup;
import conexiona.pratices.noapractices.repository.RUserGroupUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("SUserGroupUser")
public class SUserGroupUser {

    @Autowired
    @Qualifier("RUserGroupUser")
    private RUserGroupUser rUserGroupUser;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    @Qualifier("ConUserGroupUser")
    private ConUserGroupUser conUserGroupUser;

    private  static final Log logger = LogFactory.getLog(SUserGroupUser.class);

    public synchronized boolean insertUserGroupUser (MUserGroupUser mUserGroupUser){
        UserGroupUser userGroupUser = conUserGroupUser.conMUserGroupUser(mUserGroupUser);
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        User existingUser = rUser.findById(userGroupUser.getUserGroupUserId().getUserId()).get();
        UserGroup existingUserGroup = rUserGroup.findById(userGroupUser.getUserGroupUserId().getUserGroupId()).get();
        if (existingUserGroupUser != null || existingUser == null || existingUserGroup == null){
            logger.error("USERGROUPUSER EXISTS OR USER OR USERGROUP NOT EXISTS");
            return false;
        }else {
            userGroupUser.setUser(existingUser);
            userGroupUser.setUserGroup(existingUserGroup);
            rUserGroupUser.save(userGroupUser);
            logger.info("USERGROUPUSER SAVED");
            return true;
        }
    }

    public synchronized boolean updateUserGroupUser (MUserGroupUser mUserGroupUser){
        UserGroupUser userGroupUser = conUserGroupUser.conMUserGroupUser(mUserGroupUser);
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUser.getUserGroupUserId());
        User existingUser = rUser.findById(userGroupUser.getUserGroupUserId().getUserId()).get();
        UserGroup existingUserGroup = rUserGroup.findById(userGroupUser.getUserGroupUserId().getUserGroupId()).get();
        if(existingUserGroupUser != null && existingUser != null && existingUserGroup != null){
            rUserGroupUser.save(userGroupUser);
            logger.info("USERGROUPUSER UPDATED");
            return true;
        }else {
            logger.error("USERGROUPUSER OR USER OR USERGROUP NOT EXISTS");
            return false;
        }
    }

    public synchronized boolean deleteUserGroupUser (UserGroupUserId userGroupUserId){
        UserGroupUser existingUserGroupUser = rUserGroupUser.findByUserGroupUserId(userGroupUserId);
        if(existingUserGroupUser != null){
            rUserGroupUser.delete(existingUserGroupUser);
            logger.info("USERGROUPUSER DELETED");
            return true;
        }else {
            logger.error("USERGROUPUSER NOT EXISTS");
            return false;
        }
    }

    public synchronized List<MUserGroupUser> selectAllUserGroupUser (){
        List<UserGroupUser> userGroupUserList = new ArrayList<>();
        rUserGroupUser.findAll().forEach(e -> userGroupUserList.add(e));
        return conUserGroupUser.conUserGroupUserList(userGroupUserList);
    }

    public synchronized List<MUserGroupUser> selectPageableUserGroupUser (Pageable pageable){
        return conUserGroupUser.conUserGroupUserList(rUserGroupUser.findAll(pageable).getContent());
    }

    public synchronized MUserGroupUser selectUserGroupUserByUserGroupUserId (UserGroupUserId userGroupUserId){
        return conUserGroupUser.conUserGroupUser(rUserGroupUser.findByUserGroupUserId(userGroupUserId));
    }


}
