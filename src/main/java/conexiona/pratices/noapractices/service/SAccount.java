//PLANTILLA DE COMO VA A SER EL SERVICIO
//@Service: identifica la clase como un servicio
//@Autowired: permite la inyección de propiedades
//@Qualifier: permite establecer un nombre

package conexiona.pratices.noapractices.service;

import conexiona.pratices.noapractices.converter.ConAccount;
import conexiona.pratices.noapractices.converter.ConUser;
import conexiona.pratices.noapractices.converter.ConUserGroup;
import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.entity.User;
import conexiona.pratices.noapractices.entity.UserGroup;
import conexiona.pratices.noapractices.model.MAccount;
import conexiona.pratices.noapractices.model.MUser;
import conexiona.pratices.noapractices.model.MUserGroup;
import conexiona.pratices.noapractices.repository.RAccount;
import conexiona.pratices.noapractices.repository.RUser;
import conexiona.pratices.noapractices.repository.RUserGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("SAccount")
public class SAccount {

    @Autowired
    @Qualifier("RAccount")
    private RAccount rAccount;

    @Autowired
    @Qualifier("RUser")
    private RUser rUser;

    @Autowired
    @Qualifier("RUserGroup")
    private RUserGroup rUserGroup;

    @Autowired
    @Qualifier("ConAccount")
    private ConAccount conAccount;

    @Autowired
    @Qualifier("ConUser")
    private ConUser conUser;

    @Autowired
    @Qualifier("ConUserGroup")
    private ConUserGroup conUserGroup;

    private  static final Log logger = LogFactory.getLog(SAccount.class);

    public synchronized boolean insertAccount (MAccount mAccount){
        Account account = conAccount.conMAccount(mAccount);
        Account existingAccount = rAccount.findByAccountId(account.getAccountId());
        if (existingAccount != null){
            logger.error("ACCOUNT EXISTS");
            return false;
        }else {
            rAccount.save(account);
            logger.info("ACCOUNT SAVED");
            return true;
        }
    }

    public synchronized boolean updateAccount (MAccount mAccount){
        Account account = conAccount.conMAccount(mAccount);
        Account existingAccount = rAccount.findById(account.getAccountId()).get();
        if(existingAccount != null){
            rAccount.save(account);
            logger.info("ACCOUNT UPDATED");
            return true;
        }else {
            logger.error("ACCOUNT NOT EXISTS");
            return false;
        }
    }

    public synchronized boolean deleteAccount (String accountId){
        Account existingAccount = rAccount.findById(accountId).get();
        if(existingAccount != null){
            rAccount.delete(existingAccount);
            logger.info("ACCOUNT DELETED");
            return true;
        }else {
            logger.error("ACCOUNT NOT EXISTS");
            return false;
        }
    }

    public synchronized List<MAccount> selectAllAccount (){
        List<Account> accountList = new ArrayList<>();
        rAccount.findAll().forEach(e -> accountList.add(e));
        return conAccount.conAccountList(accountList);
    }

    public synchronized List<MAccount> selectPageableAccount (Pageable pageable){
        return conAccount.conAccountList(rAccount.findAll(pageable).getContent());
    }

    public synchronized MAccount selectAccountByAccountId (String accountId){
        return conAccount.conAccount(rAccount.findById(accountId).get());
    }

    public synchronized List<MUser> selectUserbyAccountId (String accountId){
        List<User> userList = new ArrayList<>();
        for (User user : rUser.findByAccount_AccountId(accountId)) {
            userList.add(user);
        }
        return conUser.conUserList(userList);
    }

    public synchronized List<MUserGroup> selectUserGroupbyAccountId (String accountId){
        List<UserGroup> userGroupList = new ArrayList<>();
        for (UserGroup userGroup : rUserGroup.findByAccount_AccountId(accountId)) {
            userGroupList.add(userGroup);
        }
        return conUserGroup.conUserGroupList(userGroupList);
    }
}