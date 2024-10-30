package wwee.jihun.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.annotation.processing.Generated;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_campaign")
public class CampaignEntity {

    @Id
    @Column(name = "campaign_id", updatable = true)
    private Long campaignId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "product")
    private String product;

    @Column(name = "keyword1")
    private String keyword1;

    @Column(name = "keyword2")
    private String keyword2;

    @Column(name = "keyword3")
    private String keyword3;

    @Column(name = "brand")
    private String brand;

    @Column(name = "tone")
    private String tone;

    @Column(name = "prompt_for_ad_text")
    private String prompt_for_ad_text;

    @Column(name = "prompt_for_ad_image")
    private String prompt_for_ad_image;

    @Column(name = "ad_text")
    private String ad_text;

    @Column(name = "ad_image")
    private String ad_image;

    @Column(name = "ad_format")
    private String ad_format;

}

