//CONVIERTE LAS ENTIDADES A MODELOS PARA SER UTILIZADOS
//@Component: identifica la clase como un componente

package conexiona.pratices.noapractices.converter;

import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.entity.User;
import conexiona.pratices.noapractices.model.MUser;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUser")
public class ConUser {
    public List<MUser> conUserList(List<User> userList){
        List<MUser> mUserList = new ArrayList<>();
        for (User user: userList) {
            mUserList.add(new MUser(user));
        }
        return mUserList;
    }

    public MUser conUser (User user){
        MUser mUser = new MUser(user);
        return mUser;
    }

    public User conMUser (MUser mUser){
        User user = new User(mUser.getUserId(),new Account(mUser.getAccountId()), mUser.getUserName(),mUser.getEmailAddress(),mUser.getPassword(),mUser.getEnabled(),mUser.getLastLogin());
        return user;
    }
}
