// boundinfo.js
Page({
  data: {
    loading: false
  },
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value);
    var that=this;
    that.setData({
      loading: true
    });
    wx.request({
      url: 'https://www.030ml.com/roombook/setStudentInfo',
      data: {
        userId: wx.getStorageSync('session'),
        id: e.detail.value.id,
        pwd: e.detail.value.pwd
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.retcode == '0000') {
          wx.redirectTo({
            url: '../index/index'
          });
        }
        else {
          wx.showToast({
            image: '../../image/information.png',
            title: '绑定失败，请确认账号密码',
            duration: 2000
          });
          that.setData({
            loading: false
          });
        }
      }
    })
  },
})