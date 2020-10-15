//PLANTILLA DE COMO VA A SER EL SERVICIO
//@Service: identifica la clase como un servicio
//@Autowired: permite la inyección de propiedades
//@Qualifier: permite establecer un nombre

package conexiona.pratices.noapractices.service;

import conexiona.pratices.noapractices.converter.ConUserGroup;
import conexiona.pratices.noapractices.converter.ConUserGroupUser;
import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.entity.UserGroup;
import conexiona.pratices.noapractices.entity.UserGroupUser;
import conexiona.pratices.noapractices.model.MUserGroup;
import conexiona.pratices.noapractices.model.MUserGroupUser;
import conexiona.pratices.noapractices.repository.RAccount;
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

@Service("SUserGroup")
public class SUserGroup {

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    @Qualifier("RAccount")
    private RAccount rAccount;

    @Autowired
    @Qualifier("RUserGroupUser")
    private RUserGroupUser rUserGroupUser;

    @Autowired
    @Qualifier("ConUserGroup")
    private ConUserGroup conUserGroup;

    @Autowired
    @Qualifier("ConUserGroupUser")
    private ConUserGroupUser conUserGroupUser;

    private  static final Log logger = LogFactory.getLog(SUserGroup.class);

    public synchronized boolean insertUserGroup (MUserGroup mUserGroup){
        UserGroup userGroup = conUserGroup.conMUserGroup(mUserGroup);
        UserGroup existingUserGroup = rUserGroup.findByUserGroupId(userGroup.getUserGroupId());
        Account existingAccount = rAccount.findById(userGroup.getAccount().getAccountId()).get();
        if (existingUserGroup != null || existingAccount == null){
            logger.error("USERGROUP EXISTS OR ACCOUNT NOT EXISTS");
            return false;
        }else {
            rUserGroup.save(userGroup);
            logger.info("USERGROUP SAVED");
            return true;
        }
    }

    public synchronized boolean updateUserGroup (MUserGroup mUserGroup){
        UserGroup userGroup = conUserGroup.conMUserGroup(mUserGroup);
        UserGroup existingUserGroup = rUserGroup.findById(userGroup.getUserGroupId()).get();
        Account existingAccount = rAccount.findById(userGroup.getAccount().getAccountId()).get();
        if(existingUserGroup != null && existingAccount != null){
            rUserGroup.save(userGroup);
            logger.info("USERGROUP UPDATED");
            return true;
        }else {
            logger.error("USERGROUP OR ACCOUNT NOT EXISTS");
            return false;
        }
    }

    public synchronized boolean deleteUserGroup (String userGroupId){
        UserGroup existingUserGroup = rUserGroup.findById(userGroupId).get();
        if(existingUserGroup != null){
            rUserGroup.delete(existingUserGroup);
            logger.info("USERGROUP DELETED");
            return true;
        }else {
            logger.error("USERGROUP NOT EXISTS");
            return false;
        }
    }

    public synchronized List<MUserGroup> selectAllUserGroup (){
        List<UserGroup> userGroupList = new ArrayList<>();
        rUserGroup.findAll().forEach(e -> userGroupList.add(e));
        return conUserGroup.conUserGroupList(userGroupList);
    }

    public synchronized List<MUserGroup> selectPageableUserGroup (Pageable pageable){
        return conUserGroup.conUserGroupList(rUserGroup.findAll(pageable).getContent());
    }

    public synchronized MUserGroup selectUserGroupByUserGroupId(String userGroupId){
        return conUserGroup.conUserGroup(rUserGroup.findById(userGroupId).get());
    }

    public synchronized List<MUserGroupUser> selectUserGroupUserbyUserGroupId(String userGroupId){
        List<UserGroupUser> userGroupUserList = new ArrayList<>();
        for (UserGroupUser userGroupUser : rUserGroupUser.findByUserGroup_UserGroupId(userGroupId)) {
            userGroupUserList.add(userGroupUser);
        }
        return conUserGroupUser.conUserGroupUserList(userGroupUserList);
    }
}
