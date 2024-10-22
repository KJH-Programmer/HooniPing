
const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {

    port: 8081, // 원하는 포트로 변경
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot 백엔드 주소
        changeOrigin: true,
      },
    },
  },
});
