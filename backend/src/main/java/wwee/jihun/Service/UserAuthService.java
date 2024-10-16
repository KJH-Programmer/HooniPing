package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserAuthService {
    private final DatabaseService databaseService;

    public UserAuthService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    //db에 있는 모든 유저를 반환
    public List<UserEntity> getAllUsers() {
        return databaseService.getAllUsers();
    }

    //회원가입(이미 있는 아이디인지 검증 기능 추가 해야함)
    public String CreateUser(UserEntity userEntity) {
        String userVerification = databaseService.getUserName(userEntity.getUserId());
        if(userVerification.equals("Available")){
            return databaseService.CreateUser(userEntity);
        }else {
            return "Unavailable";
        }
    }
}
