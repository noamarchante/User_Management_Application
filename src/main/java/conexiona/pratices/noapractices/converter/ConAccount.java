//CONVIERTE LAS ENTIDADES A MODELOS PARA SER UTILIZADOS
//@Component: identifica la clase como un componente

package conexiona.pratices.noapractices.converter;

import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.model.MAccount;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConAccount")
public class ConAccount {

    public List<MAccount> conAccountList(List<Account> accountList){
        List<MAccount> mAccountList = new ArrayList<>();
        for (Account account: accountList) {
            mAccountList.add(new MAccount(account));
        }
        return mAccountList;
    }

    public MAccount conAccount (Account account){
        MAccount mAccount = new MAccount(account);
        return mAccount;
    }

    public Account conMAccount (MAccount mAccount){
        Account account = new Account(mAccount.getAccountId(), mAccount.getAccountName(), mAccount.getCompany(), mAccount.getAddress(), mAccount.getEmailAddress(), mAccount.getDicomId(), mAccount.getEnabled());
        return account;
    }
}
