module.exports = {
	baseUrl: './',
	assetsDir: 'static',
	productionSourceMap: false,
    devServer: {
	    proxy: {
	        '/api':{
                //target:'http://192.168.123.86:8088',
                //target:'http://47.112.192.40:8088',
				target:'http://192.168.2.107:8088',
	            changeOrigin:true,
	            pathRewrite:{
	                '^/api':''
	            }
            }
	    }
	}
}
