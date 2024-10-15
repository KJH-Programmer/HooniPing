package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.UserEntity;

import java.util.List;

@Service
public class UserAuthService {
    private final DatabaseService databaseService;

    public UserAuthService(DatabaseService databaseService){
        this.databaseService = databaseService;
    }

    public String GetUserName(UserEntity userEntity){
        return databaseService.getUserName(userEntity.getUserId(), userEntity.getUserPassword());
    }
    
    public List<UserEntity> getAllUsers(){
        return databaseService.getAllUsers();
    }

}
