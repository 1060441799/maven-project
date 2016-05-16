define(['app.login', 'directive/RepeatDoneDirective'], function (app, RepeatDoneDirective) {
    'use strict';
    $(function () {
        layer.close(WX.index);
    });
    app.controller('FormDemoCtrl', ['$scope', 'FileUploader', '$http', function ($scope, FileUploader, $http) {
        $scope.user = {
            signatureFile: '',
            departmentId: '',
            officePhone: '',
            mobilePhone: '',
            userEmail: '',
            signature: '',
            realName: '',
            userName: '',
            password: '',
            province: '',
            city: ''
        };

        $scope.error = {
            userNameError: '',
            passwordError: '',
            password1Error: '',
            mobilePhoneError: '',
            officePhoneError: '',
            departmentError: '',
            checkCodeError: ''
        };
        // $scope.userNameError = '';
        // $scope.passwordError = '';
        // $scope.password1Error = '';
        // $scope.mobilePhoneError = '';
        // $scope.officePhoneError = '';
        // $scope.departmentError = '';
        // $scope.checkCodeError = '';
        $scope.provinces = [
            {id: '1', name: '北京'},
            {id: '2', name: '上海'},
            {id: '3', name: '天津'},
            {id: '4', name: '重庆'},
            {id: '5', name: '浙江'},
            {id: '6', name: '江苏'},
            {id: '7', name: '广东'},
            {id: '8', name: '福建'},
            {id: '9', name: '湖南'},
            {id: '10', name: '湖北'},
            {id: '11', name: '辽宁'},
            {id: '12', name: '吉林'},
            {id: '13', name: '黑龙江'},
            {id: '14', name: '河北'},
            {id: '15', name: '河南'},
            {id: '16', name: '山东'},
            {id: '17', name: '陕西'},
            {id: '18', name: '甘肃'},
            {id: '19', name: '新疆'},
            {id: '20', name: '青海'},
            {id: '21', name: '山西'},
            {id: '22', name: '四川'},
            {id: '23', name: '贵州'},
            {id: '24', name: '安徽'},
            {id: '25', name: '江西'},
            {id: '26', name: '云南'},
            {id: '27', name: '内蒙古'},
            {id: '28', name: '西藏'},
            {id: '29', name: '广西'},
            {id: '30', name: '宁夏'},
            {id: '31', name: '海南'},
            {id: '32', name: '香港'},
            {id: '33', name: '澳门'},
            {id: '34', name: '台湾'}
        ];

        var scope = angular.element(root).scope();
        WX.checkField([{
            scope: scope,
            rootElement: $('#userPassword'),
            validate: {
                isNull: {valid: true, errorMessage: "不能为空"},
                reg: {content: /^[0-9A-Za-z]{6,12}$/, errorMessage: "格式错误"}
            },
            callback : function(message){
                $scope.error.passwordError = message;
            }.bind(this)
        }, {
            scope: scope,
            rootElement: $('#mobilePhone'),
            validate: {
                isNull: {valid: false},
                reg: {content: /^[0-9A-Za-z]{6,12}$/, errorMessage: "格式错误"}
            },
            callback : function(message){
                $scope.error.mobilePhoneError = message;
            }.bind(this)
        }, {
            scope: scope,
            rootElement: $('#officePhone'),
            validate: {
                isNull: {valid: false},
                reg: {content: /^[0-9A-Za-z]{6,12}$/, errorMessage: "格式错误"}
            },
            callback : function(message){
                $scope.error.officePhoneError = message;
            }.bind(this)
        }, {
            scope: scope,
            rootElement: $('#department'),
            validate: {
                isNull: {valid: true, errorMessage: "不能为空"}
            },
            callback : function(message){
                $scope.error.departmentError = message;
            }.bind(this)
        }, {
            scope: scope,
            $http: $http,
            checkUrl: WX.constants.path + "login/checkUserName?userName=",
            rootElement: $('#userAccount'),
            validate: {
                isNull: {valid: true, errorMessage: "不能为空"},
                reg: {content: /^[0-9A-Za-z]{4,12}$/, errorMessage: "格式错误"}
            },
            callback : function(message){
                $scope.error.userNameError = message;
            }.bind(this),
            type: 'signup'
        }], function (result) {
            console.info('result', result);
        });


        // var territoriyChange1 = function () {
        //     var id = $(this).val();
        //     $http.get(WX.constants.path + 'login/getTerritory?id=' + (id - 1)).success(function (result) {
        //         $scope.items1 = result.obj;
        //     });
        // }
        // var territoriyChange2 = function () {
        //     var id = $(this).val();
        //     $http.get(WX.constants.path + 'login/getTerritory?id=' + (id - 1)).success(function (result) {
        //         $scope.items2 = result.obj;
        //     });
        // }

        var city = {
            "1": ["东城区", "西城区", "海淀区", "朝阳区", "丰台区", "石景山区", "通州区", "顺义区", "房山区", "大兴区", "昌平区", "怀柔区", "平谷区", "门头沟区", "延庆县", "密云县"],
            "2": ["浦东新区", "徐汇区", "长宁区", "普陀区", "闸北区", "虹口区", "杨浦区", "黄浦区", "卢湾区", "静安区", "宝山区", "闵行区", "嘉定区", "金山区", "松江区", "青浦区", "南汇区", "奉贤区", "崇明县"],
            "3": ["河东", "南开", "河西", "河北", "和平", "红桥", "东丽", "津南", "西青", "北辰", "塘沽", "汉沽", "大港", "蓟县", "宝坻", "宁河", "静海", "武清"],
            "4": ["渝中区", "大渡口区", "江北区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "万盛区", "双桥区", "渝北区", "巴南区", "万州区", "涪陵区", "黔江区", "长寿区", "江津区", "合川区", "永川区", "南川区"],
            "6": ["南京", "无锡", "常州", "徐州", "苏州", "南通", "连云港", "淮安", "扬州", "盐城", "镇江", "泰州", "宿迁"],
            "5": ["杭州", "宁波", "温州", "嘉兴", "湖州", "绍兴", "金华", "衢州", "舟山", "台州", "利水"],
            "7": ["广州", "韶关", "深圳", "珠海", "汕头", "佛山", "江门", "湛江", "茂名", "肇庆", "惠州", "梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳"],
            "8": ["福州", "厦门", "莆田", "三明", "泉州", "漳州", "南平", "龙岩", "宁德"],
            "9": ["长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "益阳", "郴州", "永州", "怀化", "娄底", "湘西土家苗族自治区"],
            "10": ["武汉", "襄阳", "黄石", "十堰", "宜昌", "鄂州", "荆门", "孝感", "荆州", "黄冈", "咸宁", "随州", "恩施土家族苗族自治州"],
            "11": ["沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛"],
            "12": ["长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延边朝鲜族自治区"],
            "13": ["哈尔滨", "齐齐哈尔", "鸡西", "牡丹江", "佳木斯", "大庆", "伊春", "黑河", "大兴安岭"],
            "14": ["石家庄", "保定", "唐山", "邯郸", "承德", "廊坊", "衡水", "秦皇岛", "张家口"],
            "15": ["郑州", "洛阳", "商丘", "安阳", "南阳", "开封", "平顶山", "焦作", "新乡", "鹤壁", "许昌", "漯河", "三门峡", "信阳", "周口", "驻马店", "济源"],
            "16": ["济南", "青岛", "菏泽", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "滨州", "德州", "聊城", "临沂"],
            "17": ["西安", "宝鸡", "咸阳", "渭南", "铜川", "延安", "榆林", "汉中", "安康", "商洛"],
            "18": ["兰州", "嘉峪关", "金昌", "金川", "白银", "天水", "武威", "张掖", "酒泉", "平凉", "庆阳", "定西", "陇南", "临夏", "合作"],
            "20": ["西宁", "海东地区", "海北藏族自治州", "黄南藏族自治州", "海南藏族自治州", "果洛藏族自治州", "玉树藏族自治州", "海西蒙古族藏族自治州"],
            "19": ["乌鲁木齐", "奎屯", "石河子", "昌吉", "吐鲁番", "库尔勒", "阿克苏", "喀什", "伊宁", "克拉玛依", "塔城", "哈密", "和田", "阿勒泰", "阿图什", "博乐"],
            "21": ["太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁"],
            "22": ["成都", "自贡", "攀枝花", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充", "眉山", "宜宾", "广安", "达州", "雅安", "巴中", "资阳", "阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州"],
            "23": ["贵阳", "六盘水", "遵义", "安顺", "黔南布依族苗族自治州", "黔西南布依族苗族自治州", "黔东南苗族侗族自治州", "铜仁", "毕节"],
            "24": ["合肥", "芜湖", "安庆", "马鞍山", "阜阳", "六安", "滁州", "宿州", "蚌埠", "巢湖", "淮南", "宣城", "亳州", "淮北", "铜陵", "黄山", "池州"],
            "25": ["南昌", "九江", "景德镇", "萍乡", "新余", "鹰潭", "赣州", "宜春", "上饶", "吉安", "抚州"],
            "26": ["昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "普洱", "临沧", "楚雄彝族自治州", "大理白族自治州", "红河哈尼族彝族自治州", "文山壮族苗族自治州", "西双版纳傣族自治州", "德宏傣族景颇族自治州", "怒江傈僳族自治州", "迪庆藏族自治州"],
            "27": ["呼和浩特", "包头", "乌海", "赤峰", "通辽", "鄂尔多斯", "呼伦贝尔", "巴彦淖尔", "乌兰察布"],
            "29": ["南宁", "柳州", "桂林", "梧州", "北海", "防城港", "钦州", "贵港", "玉林", "百色", "贺州", "河池", "崇左"],
            "28": ["拉萨", "昌都地区", "林芝地区", "山南地区", "日喀则地区", "那曲地区", "阿里地区"],
            "30": ["银川", "石嘴山", "吴忠", "固原", "中卫"],
            "31": ["海口", "三亚"],
            "32": ["中西区", "湾仔区", "东区", "南区", "九龙城区", "油尖旺区", "观塘区", "黄大仙区", "深水埗区", "北区", "大埔区", "沙田区", "西贡区", "元朗区", "屯门区", "荃湾区", "葵青区", "离岛区"],
            "34": ["台北", "高雄", "基隆", "台中", "台南", "新竹", "嘉义"],
            "33": ["澳门半岛", "氹仔岛", "路环岛"]
        };
        // $scope.city = [];
        $("#province").change(function () {
            var id = $(this).val();
            if (id) {
                var cities = city[id];
                var options = _.map(cities, function (city) {
                    return '<option value="' + city + '" selected>' + city + '</option>'
                });
                // options.unshift('<option value="">---所在城市---</option>')
                $('#city').children().not('option[value=""]').remove();
                $('#city').append(options.join('')).trigger("chosen:updated");
            }
        });

        // $scope.finish = function () {
        //     $('#city').trigger("chosen:updated");
        // }

        $scope.myImage = '';
        // $scope.myCroppedImage = '';
        $scope.cropType = "circle";

        // var uploader = $scope.uploader = new FileUploader({
        //     url: WX.constants.path + 'login/saveUser',
        //     data: {
        //         user: $scope.user
        //     },
        // });
        //
        // console.info('uploader', uploader);
        // uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
        //     console.info('onWhenAddingFileFailed', item, filter, options);
        // };
        // uploader.onAfterAddingFile = function (fileItem) {
        //     console.info('onAfterAddingFile', fileItem);
        // };
        // uploader.onAfterAddingAll = function (addedFileItems) {
        //     console.info('onAfterAddingAll', addedFileItems);
        // };
        // uploader.onBeforeUploadItem = function (item) {
        //     console.info('onBeforeUploadItem', item);
        // };
        // uploader.onProgressItem = function (fileItem, progress) {
        //     console.info('onProgressItem', fileItem, progress);
        // };
        // uploader.onProgressAll = function (progress) {
        //     console.info('onProgressAll', progress);
        // };
        // uploader.onSuccessItem = function (fileItem, response, status, headers) {
        //     console.info('onSuccessItem', fileItem, response, status, headers);
        // };
        // uploader.onErrorItem = function (fileItem, response, status, headers) {
        //     console.info('onErrorItem', fileItem, response, status, headers);
        // };
        // uploader.onCancelItem = function (fileItem, response, status, headers) {
        //     console.info('onCancelItem', fileItem, response, status, headers);
        // };
        // uploader.onCompleteItem = function (fileItem, response, status, headers) {
        //     console.info('onCompleteItem', fileItem, response, status, headers);
        // };
        // uploader.onCompleteAll = function () {
        //     console.info('onCompleteAll');
        // };

        var handleFileSelect = function (evt) {
            var file = evt.currentTarget.files[0];
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function ($scope) {
                    $scope.myImage = evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change', handleFileSelect);
        $scope.submit = function () {
            // uploader.uploadAll();
            $http.post(WX.constants.path + 'login/saveUser', $scope.user).success(function (result) {
                console.info('result', result);
            });
        }
        $scope.reset = function () {
            $scope.user = null;
        }
    }]);
});