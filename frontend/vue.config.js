const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000,  // 사용하고 싶은 포트를 지정하세요
  },
})
