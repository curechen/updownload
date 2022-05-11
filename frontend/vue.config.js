module.exports = {
  configureWebpack: {
    resolve: {
      alias: {
        'assets': '@/assets',
        'common': '@/common',
        'components': '@/components',
        'network': '@/network',
        'views': '@/views',
        'plugins': '@/plugins'
      }
    }
  },
  devServer: {
    // 项目启动时自动使用浏览器打开
    open: true,
    proxy: { // 解决跨域问题
      '/api': {
        // 只要访问的 url 中带有 /api/xx 就会把地址/api前面替换为 http://localhost:3000/
        target: 'http://localhost:8082/',
        // 上述注释想要实现，在cli2中还要加上下面这句话
        // changeOrigin: true,
        ws: true,
        pathRewrite: {
          // 重写路径，目的是将 /api 从路径中删去
          '^/api': ''
        }
      }
    }
  }
}