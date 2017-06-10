<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>book</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Free HTML5 Template by FreeHTML5.co"/>
    <meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive"/>

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/web/plugin/datetimepicker/css/bootstrap-material-datetimepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/animate.css">

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.5.10/css/bootstrap-material-design.min.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-material-design/0.5.10/css/ripples.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/style.css">

    <script src="${pageContext.request.contextPath}/web/js/modernizr-2.6.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/respond.min.js"></script>

    <style>
        body {
            font-weight: 400
        }
    </style>
</head>
<body class="style-3">


<div class="container">
    <div class="row">
        <div class="col-md-12">
            <ul class="menu">
                <li class="active"><a href="${pageContext.request.contextPath}/center">USER-CENTER</a></li>
                <li><a href="${pageContext.request.contextPath}/book?submitType=create">BOOK-ROOM</a></li>
                <li style="float: right"><a><span class="glyphicon glyphicon-user"
                                                  aria-hidden="true"></span>${user.name}</a><a
                        href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <form id="book-form" class="fh5co-form animate-box" data-animate-effect="fadeInRight"
                  action="${pageContext.request.contextPath}/submitBookInfo" method="post">

                <input type="hidden" id="submitType" name="submitType" value="${submitType}">
                <input type="hidden" id="bookInfoId" name="bookInfoId" value="${bookInfo.id}">

                <div class="form-group">
                    <label for="date">请选择预订日期</label>
                    <input type="text" class="form-control" name="date" id="date" placeholder="请选择">
                </div>
                <div class="form-group">
                    <label for="roomId">请选择预订房间</label>
                    <select name="roomId" id="roomId" class="form-control">
                        <c:forEach var="roomInfoWithTime" items="${roomInfoWithTimeList}">
                            <option value="${roomInfoWithTime.id}"
                                    <c:if test="${bookInfo.roomId==roomInfoWithTime.id}">selected</c:if> >${roomInfoWithTime.roomName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>可选时间段</label>
                    <div id="remainTime">
                        <c:forEach var="roomInfoWithTime" items="${roomInfoWithTimeList}" varStatus="status">
                            <p style="font-size: 16px;font-weight: 400" class="${roomInfoWithTime.id} hidden ">
                                <c:forEach var="remainTime" items="${roomInfoWithTime.remainTime}" varStatus="childStatus">
                                    ${remainTime.startTime}~${remainTime.endTime}
                                    <c:if test="${childStatus.count!=roomInfoWithTime.remainTime.size()}">|</c:if>
                                </c:forEach>
                            </p>
                        </c:forEach>
                        <c:if test="${roomInfoWithTimeList.size()==0}">
                            <img style="max-height: 40px;max-width: 40px"
                                 src="${pageContext.request.contextPath}/web/images/cry.png">
                            <p style="font-size: 16px;font-weight: 400">这个房间被预定完了</p>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label for="startTime">请选择开始时间</label>
                    <input type="text" class="form-control" id="startTime" placeholder="请选择">
                    <input type="hidden" name="startTime" value="${bookInfo.startTime}">
                </div>
                <div class="form-group">
                    <label for="endTime">请选择结束时间</label>
                    <input type="text" class="form-control" id="endTime" placeholder="请选择">
                    <input type="hidden" name="endTime" value="${bookInfo.endTime}">
                </div>
                <div class="form-group" style="margin-bottom: 35px">
                    <input type="submit" class="btn btn-primary" style="color: white;width: 30%;float: right"
                           value="提交">
                </div>
            </form>
        </div>
    </div>
    <div class="row" style="padding-top: 60px; clear: both;">
        <div class="col-md-12 text-center">
            <p>
                <small>&copy;Lin All Rights Reserved.</small>
            </p>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/web/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/web/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/web/js/jquery.placeholder.min.js"></script>
<script src="${pageContext.request.contextPath}/web/js/jquery.waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/web/plugin/datetimepicker/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/web/plugin/datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/web/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/web/js/main.js"></script>
<script src="${pageContext.request.contextPath}/web/js/common.js"></script>

<script>
    $(function () {

        init();

        $('#date').change(function () {
            updateRemainTime();
        });

        $('#roomId').change(function () {
            chooseRemainTime();
        });

        $('#book-form').submit(function (e) {
            openLoading();
            e.preventDefault();
            $.ajax({
                url: e.currentTarget.action,
                type: 'post',
                async: true,
                dataType: "json",
                data: $("#book-form").serialize(),
                success: function (data) {
                    if (data.retcode == "0000") {
                        msg('提交成功');
                        if ($('#submitType').val() == "modify") {
                            location.href = '${pageContext.request.contextPath}/center'
                        }
                        reset();
                    } else {
                        msg(data.errorMsg);
                    }
                    closeLoading();
                }
            });
        });

    });

    function init() {
        $('#date').bootstrapMaterialDatePicker
        ({
            time: false,
            minDate: addDate(new Date(), 1),
            maxDate: addDate(new Date(), 5),
            currentDate: "${bookInfo.date}"
        });
        $('#startTime').bootstrapMaterialDatePicker
        ({
            date: false,
            shortTime: false,
            minDate: "08:00",
            maxDate: "22:00",
            currentDate: "${bookInfo.startTime}",
            format: 'HH:mm'
        }).on('change', function (e, date) {
            $(this).next().val(date.format('HH:mm:ss'));
        });
        $('#endTime').bootstrapMaterialDatePicker
        ({
            date: false,
            shortTime: false,
            minDate: "08:00",
            maxDate: "22:00",
            currentDate: "${bookInfo.endTime}",
            format: 'HH:mm'
        }).on('change', function (e, date) {
            $(this).next().val(date.format('HH:mm:ss'));
        });

        chooseRemainTime();
    }

    function chooseRemainTime() {
        var id = $('#roomId').val();
        $('#remainTime').find('p').addClass('hidden');
        $('.' + id).removeClass('hidden');
    }

    function updateRemainTime() {
        openLoading();
        var date = $('#date').val();
        var submitType = $('#submitType').val();
        var bookInfoId = $('#bookInfoId').val();
        $.ajax({
            type: "get",
            async: true,
            dataType: "json",
            data: {
                date: date,
                submitType: submitType,
                bookInfoId: bookInfoId
            },
            url: "${pageContext.request.contextPath}/getRoomInfo",
            cache: false,
            success: function (data) {
                if (data.retcode == "0000") {
                    $('#remainTime').empty();
                    data.data.forEach(function (e) {
                        if (e.remainTime.length == 0) {
                            $('#remainTime').append("<p class='" + e.id + "' style='font-size: 16px;font-weight: 400'>这个房间被预定完了</p>");
                        } else {
                            var remainTime = "<p style='font-size: 16px;font-weight: 400' class='" + e.id + " hidden'>";
                            e.remainTime.forEach(function (ele) {
                                remainTime += ele.startTime + "~" + ele.endTime + "/";
                            });
                            remainTime = remainTime.substring(0, remainTime.length - 1)
                            remainTime += "</p>";
                            $('#remainTime').append(remainTime);
                        }

                    });


                    chooseRemainTime();
                } else {
                    msg(data.errorMsg);
                }
                closeLoading();
            }
        });
    }

    function reset() {
        $('#book-form').trigger("reset");
        $('#date').bootstrapMaterialDatePicker('setDate', addDate(new Date(), 1));
        $('#startTime').bootstrapMaterialDatePicker('setDate', "12:00:00");
        $('#endTime').bootstrapMaterialDatePicker('setDate', "12:00:00");
        $("input[name='startTime']").val("12:00:00");
        $("input[name='endTime']").val("12:00:00");
    }

    function addDate(date, days) {
        var d = new Date(date);
        d.setDate(d.getDate() + days);
        var m = d.getMonth() + 1;
        return d.getFullYear() + '-' + m + '-' + d.getDate();
    }

</script>

</body>
</html>
