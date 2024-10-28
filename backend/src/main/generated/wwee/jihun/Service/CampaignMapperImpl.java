package wwee.jihun.Service;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import wwee.jihun.Entity.CampaignEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-25T09:27:09+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Ubuntu)"
)
@Component
public class CampaignMapperImpl implements CampaignMapper {

    @Override
    public void updateCampaign(CampaignEntity source, CampaignEntity target) {
        if ( source == null ) {
            return;
        }

        target.setCampaignId( source.getCampaignId() );
        target.setUserId( source.getUserId() );
        target.setProduct( source.getProduct() );
        target.setKeyword1( source.getKeyword1() );
        target.setKeyword2( source.getKeyword2() );
        target.setKeyword3( source.getKeyword3() );
        target.setBrand( source.getBrand() );
        target.setTone( source.getTone() );
        target.setPrompt_for_ad_text( source.getPrompt_for_ad_text() );
        target.setPrompt_for_ad_image( source.getPrompt_for_ad_image() );
        target.setAd_text( source.getAd_text() );
        target.setAd_image( source.getAd_image() );
    }
}
