package wwee.jihun.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GptEntity {
    @Id
    private Long id;
    private String prompt;
    private String keyword1;
    private String keyword2;
    private String keyword3;
}
