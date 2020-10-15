//CONVIERTE LAS ENTIDADES A MODELOS PARA SER UTILIZADOS
//@Component: identifica la clase como un componente

package conexiona.pratices.noapractices.converter;

import conexiona.pratices.noapractices.entity.Account;
import conexiona.pratices.noapractices.entity.UserGroup;
import conexiona.pratices.noapractices.model.MUserGroup;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("ConUserGroup")
public class ConUserGroup {
    public List<MUserGroup> conUserGroupList(List<UserGroup> userGroupList){
        List<MUserGroup> mUserGroupList = new ArrayList<>();
        for (UserGroup userGroup: userGroupList) {
            mUserGroupList.add(new MUserGroup(userGroup));
        }
        return mUserGroupList;
    }

    public MUserGroup conUserGroup (UserGroup userGroup){
        MUserGroup mUserGroup = new MUserGroup(userGroup);
        return mUserGroup;
    }

    public UserGroup conMUserGroup (MUserGroup mUserGroup){
        UserGroup userGroup = new UserGroup(mUserGroup.getUserGroupId(),new Account(mUserGroup.getAccountId()),mUserGroup.getUserGroupName(),mUserGroup.getEnabled());
        return userGroup;
    }
}
