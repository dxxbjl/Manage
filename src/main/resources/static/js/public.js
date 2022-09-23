let fun = {
    /**
     * 对象转数组ids
     * @param obj
     */
    objToArrIds: function (obj) {
        return obj.map(x => {return x.id});
    },
    /**
     * 预览图片
     * @param that 图片对象
     */
    preview : function(that) {
        layer.photos({ photos: {"data": [{"src": that.src}]} ,closeBtn:true});
    },
    //#region 数组操作
    // 判断是否为数组
    isArray: function (arr) {
        return Object.prototype.toString.call(arr) === '[object Array]';
    },
    // 数组去重，只考虑数组中元素为数字或者字符串
    newarr: function (arr) {
        var arrs = [];
        for (var i = 0; i < arr.length; i++) {
            if (arrs.indexOf(arr[i]) == -1) {
                arrs.push(arr[i])
            }
        }
        return arrs;
    },
    // 数组去重
    removeRepeatArray: function (arr) {
        return Array.from(new Set(arr))
    },
    // 数组filter（搜索功能）
    filterItems: function (arr, str) {
        return arr.filter(function (el) {
            return el.toLowerCase().indexOf(str.toLowerCase()) > -1;
        })
    },
    // 判断数组是否包含某个元素
    contains: function (arr,val) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                return true;
            }
        }
        return false;
    },
    //  返回数组（字符串）一个元素出现的次数
    getEleCount(arr, str) {
        var num = 0;
        for (var i = 0, len = arr.length; i < len; i++) {
            if (str == arr[i]) {
                num++;
            }
        }
        return num;
    },
    //#endregion
    //#region 对象
    // 清空对象为空的数据
    filterParams: function (obj) {
        let _newPar = {};
        for (let key in obj) {
            if ((obj[key] === 0 || obj[key]) && obj[key].toString().replace(/(^\s*)|(\s*$)/g, '') !== '') {
                _newPar[key] = obj[key];
            }
        }
        return _newPar;
    },
    // 判断对象是否含有为空的数据
    filterParams2: function (obj) {
        let _newPar = {};
        for (let key in obj) {
            if ((obj[key] === 0 || obj[key]) && obj[key].toString().replace(/(^\s*)|(\s*$)/g, '') !== '') {
                _newPar[key] = obj[key];
            }
        }
        return _newPar;
    },
    //#endregion
    //#region 字符串操作
    // 去除字符串空格(type 1-所有空格  2-前后空格  3-前空格 4-后空格)
    trim: function (str) {
        switch (type) {
            case 1: return str.replace(/\s+/g, "");
            case 2: return str.replace(/(^\s*)|(\s*$)/g, "");
            case 3: return str.replace(/(^\s*)/g, "");
            case 4: return str.replace(/(\s*$)/g, "");
            default: return str;
        }
    },
    // 字母大小写切换(type: 1:首字母大写   2：首页母小写 3：大小写转换 4：全部大写 5：全部小写)
    changeCase: function (str, type) {
        function ToggleCase(str) {
            var itemText = ""
            str.split("").forEach(
                function (item) {
                    if (/^([a-z]+)/.test(item)) {
                        itemText += item.toUpperCase();
                    }
                    else if (/^([A-Z]+)/.test(item)) {
                        itemText += item.toLowerCase();
                    }
                    else {
                        itemText += item;
                    }
                });
            return itemText;
        }
        switch (type) {
            case 1:
                return str.replace(/^(\w)(\w+)/, function (v, v1, v2) {
                    return v1.toUpperCase() + v2.toLowerCase();
                });
            case 2:
                return str.replace(/^(\w)(\w+)/, function (v, v1, v2) {
                    return v1.toLowerCase() + v2.toUpperCase();
                });
            case 3:
                return ToggleCase(str);
            case 4:
                return str.toUpperCase();
            case 5:
                return str.toLowerCase();
            default:
                return str;
        }
    },
    // 字符串循环复制
    repeatStr: function (str, count) {
        var text = '';
        for (var i = 0; i < count; i++) {
            text += str;
        }
        return text;
    },
    //字符串替换(str:字符串,AFindText:要替换的字符,ARepText:替换成什么)
    replaceAll: function (str, AFindText, ARepText) {
        raRegExp = new RegExp(AFindText, "g");
        return str.replace(raRegExp, ARepText);
    },
    // 随机码
    randomNumber: function (count) {
        return Math.random().toString(count).substring(2);
    },
    //#endregion
    //#region 时间
    // 倒计时 -》"剩余时间6天 2小时 28 分钟20 秒"
    getEndTime: function (endTime) {
        var startDate = new Date();  //开始时间，当前时间
        var endDate = new Date(endTime); //结束时间，需传入时间参数
        var t = endDate.getTime() - startDate.getTime();  //时间差的毫秒数
        var d = 0, h = 0, m = 0, s = 0;
        if (t >= 0) {
            d = Math.floor(t / 1000 / 3600 / 24);
            h = Math.floor(t / 1000 / 60 / 60 % 24);
            m = Math.floor(t / 1000 / 60 % 60);
            s = Math.floor(t / 1000 % 60);
        }
        return "剩余时间" + d + "天 " + h + "小时 " + m + " 分钟" + s + " 秒";
    },
    // 时间戳转换时间格式(YY-MM-DD hh:mm:ss) 2021-10-10 10:23:56
    timestampToTim: function (timestamp) {
        var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() + ' ';
        h = date.getHours() + ':';
        m = date.getMinutes() + ':';
        s = date.getSeconds();
        return Y + M + D + h + m + s;
    },
    //  获取当月的天数
    getDateCount:function () {
        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const days = new Date(year, month, 0);
        return days.getDate()
    },
    // 获取本月所有日期
    getDate:function() {
        let now = new Date();
        let current_month_num = mGetDate();
        let current_month = [];
        for (let i = 1; i <= current_month_num; i++) {
            let day = now.setDate(i);
            let everyDay = formatMonth(day);
            current_month.push(everyDay);
        }
        return current_month;
    },
    //#endregion
    //#region 格式验证
    /**格式验证
     * identity:身份证
     * email=邮箱
     * phone：手机号
     * tel:电话号，
     * number：数字
     * english：字母
     * chinese：中文
     * lower：小写字母
     * upper：大写
     * IPv4地址:ip地址
     */
    checkType: function (str, type) {
        switch (type) {
            case 'identity':
                return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(str);
            case 'email':
                return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(str);
            case 'phone':
                return /^1[3|4|5|7|8][0-9]{9}$/.test(str);
            case 'tel':
                return /^(0\d{2,3}-\d{7,8})(-\d{1,4})?$/.test(str);
            case 'number':
                return /^[0-9]$/.test(str);
            case 'english':
                return /^[a-zA-Z]+$/.test(str);
            case 'chinese':
                return /^[\u4E00-\u9FA5]+$/.test(str);
            case 'lower':
                return /^[a-z]+$/.test(str);
            case 'upper':
                return /^[A-Z]+$/.test(str);
            case 'ip':
                return /((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}/.test(str);
            default:
                return true;
        }
    },
    // 密码强度(1、2、3)
    checkPwd: function (str) {
        var nowLv = 0;
        if (str.length < 6) {
            return nowLv
        }
        ;
        if (/[0-9]/.test(str)) {
            nowLv++
        }
        ;
        if (/[a-z]/.test(str)) {
            nowLv++
        }
        ;
        if (/[A-Z]/.test(str)) {
            nowLv++
        }
        ;
        if (/[\.|-|_]/.test(str)) {
            nowLv++
        }
        ;
        return nowLv;
    },
    //#endregion
    //#region 适配 rem
    getFontSize: function () {
        var doc = document, win = window;
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                //如果屏幕大于750（750是根据我效果图设置的，具体数值参考效果图），就设置clientWidth=750，防止font-size会超过100px
                if (clientWidth > 750) { clientWidth = 750 }
                //设置根元素font-size大小
                docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
            };
        //屏幕大小改变，或者横竖屏切换时，触发函数
        win.addEventListener(resizeEvt, recalc, false);
        //文档加载完成时，触发函数
        doc.addEventListener('DOMContentLoaded', recalc, false);
    },
    //使用方式很简单，比如效果图上，有张图片。宽高都是100px;
    //样式写法就是
    // img{
    //         width:1rem;
    // height: 1rem;
    // }
    //这样的设置，比如在屏幕宽度大于等于750px设备上，1rem=100px；图片显示就是宽高都是100px
    //比如在iphone6(屏幕宽度：375)上，375/750*100=50px;就是1rem=50px;图片显示就是宽高都是50px;
    //#endregion
    //#region 文件操作类
    // 获取文件名
    getFileName: function (fileName) {
        var pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            return fileName;
        } else {
            return fileName.substring(pos, fileName.length);
        }
    },
    // 获取文件后缀名
    getExt: function (fileName) {
        if (fileName.lastIndexOf(".") == -1)
            return fileName;
        var pos = fileName.lastIndexOf(".") + 1;
        return fileName.substring(pos, fileName.length).toLowerCase();
    },
    // 验证是否为图片
    tmCheckImage: function (fileName) {
        return /(gif|jpg|jpeg|png|GIF|JPG|PNG)$/ig.test(fileName);
    },
    // 验证是否为视频
    tmCheckVideo: function (fileName) {
        return /(mp4|mp3|flv|wav)$/ig.test(fileName);
    },
    /*验证是否为文档*/
    tmCheckDocument: function (fileName) {
        return /(doc|docx|xls|xlsx|pdf|txt|ppt|pptx|rar|zip|html|jsp|sql|htm|shtml|xml)$/ig.test(fileName);
    },
    //#endregion
    //#region 网络请求
    // 将 url 中的参数解析为一个对象
    getUrlPrmt: function (url) {
        var str = url.split('?')[1];
        var result = {};
        var temp = str.split('&');
        for (var i = 0; i < temp.length; i++) {
            var temp2 = temp[i].split('=');
            result[temp2[0]] = temp2[1];
        }
        return result;
    },

    // 将 对象 设置成url格式 {'a':1,'b':2}  -》  a=1&b=2
    setUrlPrmt: function (obj) {
        let _rs = [];
        for (let p in obj) {
            if (obj[p] != null && obj[p] != '') {
                _rs.push(p + '=' + obj[p])
            }
        }
        return _rs.join('&');
    }
    //#endregion
}