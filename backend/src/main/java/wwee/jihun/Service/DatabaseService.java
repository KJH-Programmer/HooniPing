package wwee.jihun.Service;

import org.springframework.stereotype.Service;
import wwee.jihun.Entity.CampaignEntity;
import wwee.jihun.Entity.UserEntity;
import wwee.jihun.Repository.CampaignRepository;
import wwee.jihun.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService {
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;

    public DatabaseService(UserRepository userRepository, CampaignRepository campaignRepository) {
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
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

    //전체 캠페인 조회
    public List<CampaignEntity> getAllCampaigns(){
        return campaignRepository.findAll();
    }

    //userId에 해당하는 모든 캠페인 조회
    public List<CampaignEntity> getUserAllCampaigns(String userId){
        return campaignRepository.findAllByUserId(userId);
    }

    //캠페인 id 확인
    public Optional<CampaignEntity> findCampaignByUserIdAndCampaignId(String userId) {
        return campaignRepository.findByUserId(userId);
    }
}
