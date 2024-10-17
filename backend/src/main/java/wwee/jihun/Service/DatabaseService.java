package wwee.jihun.Service;

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

    //모든 유저 조회
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    //유저 회원가입
    public String CreateUser(UserEntity userEntity){
        try{
            userRepository.save(userEntity);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    //이미 사용중인 id 인지 검증
    public String getUserName(String user_Id){
        Optional<UserEntity> userEntity = userRepository.findByUserId(user_Id);
        return userEntity.map(UserEntity::getUserName).orElse("Available");
    }

    //유저 로그인을 위한 유저 정보
    public Optional<UserEntity> Login(String userId, String userPassword){
        return userRepository.findByUserIdAndUserPassword(userId, userPassword);
    }

}
