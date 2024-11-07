package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Service.UserAuthService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://hooniping-bucket.s3-website.ap-northeast-2.amazonaws.com"})
public class UserController {
    private final UserAuthService userAuthService;

    @Autowired
    public UserController(UserAuthService userAuthService){
        this.userAuthService = userAuthService;
    }


    //모든 user 조회
    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userAuthService.getAllUsers();
    }

    //회원가입
    @PostMapping("/create")
    public String CreateUser(@RequestBody UserEntity userEntity){
        return userAuthService.CreateUser(userEntity);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity userEntity) {
        //토큰을 발급받아서 token 저장
        String token = userAuthService.Login(userEntity.getUserId(), userEntity.getUserPassword());
        //login api 요청에 대한 token 반환
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
