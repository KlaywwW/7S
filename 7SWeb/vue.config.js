module.exports = {
	baseUrl: './',
	assetsDir: 'static',
	productionSourceMap: false,
    devServer: {
	    proxy: {
	        '/api':{
	            target:'http://192.168.123.86:8088',
	            changeOrigin:true,
	            pathRewrite:{
	                '^/api':''
	            }
	        }
	    }
	}
}
