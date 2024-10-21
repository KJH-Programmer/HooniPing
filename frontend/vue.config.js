// <<<<<<< HEAD
// const { defineConfig } = require('@vue/cli-service')

// module.exports = defineConfig({
//   transpileDependencies: true,
//   devServer: {
//     port: 3000,  // 사용하고 싶은 포트를 지정하세요
//   },
//   configureWebpack: {
//     devtool: 'source-map'
//   }
// })
// =======
const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    // port: 8081, // 원하는 포트로 변경
    // proxy: {
    //   '/api': {
    //     target: 'http://localhost:8080', // Spring Boot 백엔드 주소
    //     changeOrigin: true,
    //   },
    // },
    port: 8081,  // 사용하고 싶은 포트를 지정하세요
  },
});
// >>>>>>> main
