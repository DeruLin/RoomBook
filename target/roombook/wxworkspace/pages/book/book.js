var util = require('../../utils/util.js')
Page({
  data: {
    classArray: [
      {
        id: 3674968,
        name: '中北研究室（玻璃门）'
      },
      {
        id: 3675132,
        name: '中北研究室（木门）'
      }
    ],
    roomArray: [],
    classIndex: 0,
    roomIndex: 0,
    date: util.addDate(new Date(), 1),
    startDate: util.addDate(new Date(),1),
    endDate: util.addDate(new Date(), 6),
    startTime: '12:00',
    endTime: '12:00',
    loading: false
  },
  onLoad: function () {
    var that = this;
    getRoomInfo(that);
  },
  bindClassChange: function (e) {
    var that = this;
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      classIndex: e.detail.value,
      roomIndex: 0
    });
    getRoomInfo(that);
  },
  bindRoomChange: function (e) {
    this.setData({
      roomIndex: e.detail.value
    })
  },
  bindDateChange: function (e) {
    var that = this;
    this.setData({
      date: e.detail.value
    });
    getRoomInfo(that);
  },
  bindStartTimeChange: function (e) {
    this.setData({
      startTime: e.detail.value
    })
  },
  bindEndTimeChange: function (e) {
    this.setData({
      endTime: e.detail.value
    })
  },
  formSubmit: function (e) {
    var that=this;
    that.setData({
      loading: true
    });
    wx.request({
      url: 'https://www.030ml.com/roombook/submitBookInfo',
      data: {
        userId: wx.getStorageSync('session'),
        date: e.detail.value.date,
        roomId: e.detail.value.roomId,
        startTime: e.detail.value.startTime + ':00',
        endTime: e.detail.value.endTime + ':00'
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
            image: '../../images/information.png',
            title: res.data.errorMsg,
            duration: 2000
          });
          that.setData({
            loading: false
          });
        }
      }
    })
  },

});

function getRoomInfo(that){
  wx.showLoading({
    title: '加载中',
    mask: true
  });
  wx.request({
    url: 'https://www.030ml.com/roombook/getRoomInfo',
    data: {
      classId: that.data.classArray[that.data.classIndex].id,
      date: that.data.date
    },
    success: function (res) {
      if (res.data.retcode == '0000') {
        that.setData({
          roomArray: res.data.data
        })
      }
    },
    complete:function(){
      wx.hideLoading();
    }
  })
}