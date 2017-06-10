//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    studentId: '',
    studentName: '',
    userInfo: {},
    bookInfoList: []
  },
  startBook:function(){
    wx.navigateTo({
      url: '../book/book'
    });
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    wx.showLoading({
      title: '加载中',
      mask: true
    });
    var that = this;
    login(that);
    getAndSetData(that);
  },
  onPullDownRefresh: function () {
    getAndSetData(this);
    
  }
});

function login(that){
  wx.login({
    success: function (res) {
      if (res.code) {
        console.log("登陆成功");
        wx.request({
          url: 'https://www.030ml.com/roombook/login',
          data: {
            code: res.code
          },
          success: function (loginRes) {
            console.log(loginRes.data);
            wx.setStorageSync('session', loginRes.data.data);
            wx.getUserInfo({
              success: function (res) {
                that.setData({
                  userInfo: res.userInfo
                })
              }
            });
          },
          fail: function () {
            console.log("请求登陆失败");
          }
        })
      } else {
        console.log('获取用户登录态失败！' + res.errMsg)
      }
    }
  });
}

function getAndSetData(that){
  wx.request({
    url: 'https://www.030ml.com/roombook/getStudentInfo',
    data: {
      userId: wx.getStorageSync('session')
    },
    success: function (res) {
      if (res.data.retcode == '0000') {
        that.setData({
          studentId: res.data.data.studentId,
          studentName: res.data.data.name
        })
      }
      else {
        wx.redirectTo({
          url: '../boundinfo/boundinfo'
        });
      }
    }
  });
  wx.request({
    url: 'https://www.030ml.com/roombook/getUserBookInfo',
    data: {
      userId: wx.getStorageSync('session')
    },
    success: function (res) {
      if (res.data.retcode == '0000') {
        that.setData({
          bookInfoList: res.data.data,
        })
      }
      else {
        wx.redirectTo({
          url: '../boundinfo/boundinfo'
        });
      }
    },
    complete: function () {
      wx.hideLoading();
      wx.stopPullDownRefresh();
    }
  })
}
