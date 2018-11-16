
var BaseUtil = new Vue({
   el:"default-msg",
    data:function(){
       return {needLoadingRequestCount:0,loading:null};
    },
    methods:{
        startLoading: function() {
            console.log('startLoading =============');
            this.loading = this.$loading({
                lock: true,
                text: '加载中……'
            });
        },
        endLoading: function() {
            console.log('endLoading==========');
            this.loading.close();
        },
        tryCloseLoading : function() {
            if (this.needLoadingRequestCount === 0) {
                this.endLoading();
            }
        },
        showFullScreenLoading: function() {
            if (this.needLoadingRequestCount === 0) {
                this.startLoading();
            }
            this.needLoadingRequestCount++;
        },
        tryHideFullScreenLoading: function() {
            if (this.needLoadingRequestCount <= 0) {
                return
            }
            this.needLoadingRequestCount--;
            if (this.needLoadingRequestCount === 0) {
                this.tryCloseLoading();
            }
        },
        downFile:function(url){
            $("#downFileIframe").remove();
            $("body").append("<iframe style='display: none;' id='downFileIframe' name='downFileIframe' src='"+url+"'></iframe>");
        },
        isNotBlank :function(cs){
            return !this.isBlank(cs);
        },
        isBlank :function(cs){
            return cs == null || cs+'' == '';
        },
        trim : function(str) {
            if(typeof str != 'string'){
                return str;
            }
            return str.replace(/(^\s*)|(\s*$)/g, '');
        },
        dateFormat: function (date,format) {
            if(date == null || date == ''){return "";}

            var _this = new Date(date);
            var o = {
                "M+": _this.getMonth() + 1, //month
                "d+": _this.getDate(), //day
                "h+": _this.getHours(), //hour
                "m+": _this.getMinutes(), //minute
                "s+": _this.getSeconds(), //second
                "q+": Math.floor((_this.getMonth() + 3) / 3), //quarter
                "S": _this.getMilliseconds() //millisecond
            }
            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (_this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        }
    }
});


// 请求拦截器
axios.interceptors.request.use(function(config)  {
    BaseUtil.showFullScreenLoading();
    return config;
}, function(error)  {
    return Promise.reject(error);
});

// 响应拦截器
axios.interceptors.response.use(function(response) {
    BaseUtil.tryHideFullScreenLoading();
    return response;
}, function(error) {
    return Promise.reject(error);
});
(function(root, $http) {
    root.httpGet = function(url, func1, func2) {
        $http.get(url).then(function(response) {
            if(func1) func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    root.httpPost = function(url,data, func1, func2) {
        $http.post(url,data).then(function(response) {
            if(func1) func1(response.data);
        }, function(response) {
            if(func2) func2(response);
        });
    };
    root.YES_NO = {YES:1,NO:0};
})(window, axios);
