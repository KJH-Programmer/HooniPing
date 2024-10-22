package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.JwtToken.TokenProvider;
import wwee.jihun.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService {
    private final wwee.jihun.Service.DatabaseService databaseService;
    private final TokenProvider tokenProvider;


    public UserAuthService(DatabaseService databaseService, TokenProvider tokenProvider) {
        this.databaseService = databaseService;
        this.tokenProvider = tokenProvider;
    }

    //db에 있는 모든 유저를 반환
    public List<UserEntity> getAllUsers() {
        return databaseService.getAllUsers();
    }

    //회원가입
    public String CreateUser(UserEntity userEntity) {
        String userVerification = databaseService.getUserName(userEntity.getUserId());
        //System.out.println(userVerification);
        if(userVerification.equals("Available")){
            return databaseService.CreateUser(userEntity);
        }else {
            return "Unavailable";
        }
    }

    //로그인
    public String Login(String userId, String userPassword){
        Optional<UserEntity> userEntity = databaseService.Login(userId,userPassword);
        String tokenId = userEntity.map(UserEntity::getUserId).orElse(null);
        String tokenName = userEntity.map(UserEntity::getUserName).orElse(null);
        if(tokenId == null || tokenName == null){
            return "Not User";
        }else{
            return tokenProvider.CreateAccessToken(tokenId,tokenName);
        }
    }
}
