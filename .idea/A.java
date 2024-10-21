try {
        // 로컬 스토리지에서 토큰 가져오기
        const token = localStorage.getItem('token');

        // getCampaign 함수 호출
        const campaign = await getCampaign(token, this.campaignId);
        this.campaignData = campaign;

        console.log('캠페인 정보 가져오기 성공:', campaign);
      } catch (error) {
        console.error('캠페인 정보 가져오기 중 오류:', error);
      }