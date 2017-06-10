function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function addDate(date, days) {
  var d = new Date(date);
  d.setDate(d.getDate() + days);
  var m = d.getMonth() + 1;
  return d.getFullYear() + '-' + m + '-' + d.getDate();
} 

module.exports = {
  formatTime: formatTime,
  addDate: addDate
}
