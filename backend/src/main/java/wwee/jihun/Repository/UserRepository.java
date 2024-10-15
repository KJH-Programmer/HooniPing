package wwee.jihun.Repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import wwee.jihun.Entity.UserEntity;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserIdAndUserPassword(String userId, String userPassword);
}
