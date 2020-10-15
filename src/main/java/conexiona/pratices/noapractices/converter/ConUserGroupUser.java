//CONVIERTE LAS ENTIDADES A MODELOS PARA SER UTILIZADOS
//@Component: identifica la clase como un componente

package conexiona.pratices.noapractices.converter;

import conexiona.pratices.noapractices.entity.UserGroupUser;
import conexiona.pratices.noapractices.entity.UserGroupUserId;
import conexiona.pratices.noapractices.model.MUserGroupUser;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserGroupUser")
public class ConUserGroupUser {
    public List<MUserGroupUser> conUserGroupUserList(List<UserGroupUser> userGroupUserList){
        List<MUserGroupUser> mUserGroupUserList = new ArrayList<>();
        for (UserGroupUser userGroupUser: userGroupUserList) {
            mUserGroupUserList.add(new MUserGroupUser(userGroupUser));
        }
        return mUserGroupUserList;
    }

    public MUserGroupUser conUserGroupUser (UserGroupUser userGroupUser){
        MUserGroupUser mUserGroupUser = new MUserGroupUser(userGroupUser);
        return mUserGroupUser;
    }

    public UserGroupUser conMUserGroupUser (MUserGroupUser mUserGroupUser){
        UserGroupUser userGroupUser = new UserGroupUser(new UserGroupUserId(mUserGroupUser.getUserId(), mUserGroupUser.getUserGroupId()),mUserGroupUser.getUserAdmin());
        return userGroupUser;
    }
}
