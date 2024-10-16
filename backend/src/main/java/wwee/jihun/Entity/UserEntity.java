package wwee.jihun.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="tb_user")
public class UserEntity {
    @Id

    @Column(name="id")
    private String userId;

    @Column(name="password")
    private String userPw;

    @Column(name="name")
    private String userName;
}
