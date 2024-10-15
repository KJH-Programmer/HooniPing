package wwee.jihun.Service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService {
    private final UserRepository userRepository;

    public DatabaseService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String getUserName(String user_Id, String user_Password){
        Optional<UserEntity> userEntity = userRepository.findByUserIdAndUserPassword(user_Id, user_Password);
        return userEntity.map(UserEntity::getUserName).orElse("존재하지 않는 회원입니다.");
    }

    //모든 유저 조회
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
}
