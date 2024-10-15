package wwee.jihun.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    //front에서 받은 id password 가지고 유저 이름 출력
    @PostMapping
    public String getUserName(@RequestBody UserEntity userEntity){
        return userAuthService.GetUserName(userEntity);
    }
    //모든 user 조회
    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userAuthService.getAllUsers();
    }
}
