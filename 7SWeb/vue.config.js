module.exports = {
	baseUrl: './',
	assetsDir: 'static',
	productionSourceMap: false,
	//devServer: {
        //disableHostCheck: true
	//},
    devServer: {
		port: 8081,
		disableHostCheck: true,
	    proxy: {
	        '/api':{
                target:'http://47.112.192.40:8088',
			    // target:'http://192.168.123.86:8088',
	            changeOrigin:true,
	            pathRewrite:{
	                '^/api':''
	            }
            }
	    }
	}
}
