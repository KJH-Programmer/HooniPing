package wwee.jihun.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwee.jihun.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // ID와 Password로 사용자 조회
}
