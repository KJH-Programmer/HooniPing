package wwee.jihun.Controller;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Service.UserAuthService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8081")
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
        String token = userAuthService.Login(userEntity.getUserId(), userEntity.getUserPassword());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
