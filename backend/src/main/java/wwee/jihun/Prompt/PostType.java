package wwee.jihun.Prompt;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PostType {
    private String blog = "";

    private String snsFeed = "";

    private String review = "";
}
