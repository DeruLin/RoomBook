<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>center</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Free HTML5 Template by FreeHTML5.co"/>
    <meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive"/>

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/style.css">


    <script src="${pageContext.request.contextPath}/web/js/modernizr-2.6.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/respond.min.js"></script>
    <style>
        body{
            font-weight: 400
        }
    </style>
</head>
<body class="style-3">

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <ul class="menu">
                <li><a href="${pageContext.request.contextPath}/center">USER-CENTER</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/book?submitType=create">BOOK-ROOM</a></li>
                <li style="float: right"><a><span class="glyphicon glyphicon-user" aria-hidden="true"></span>${user.name}</a><a href="${pageContext.request.contextPath}/logout">LOGOUT</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <form class="fh5co-form animate-box" data-animate-effect="fadeInRight">
                <h2>BOOK-INFO</h2>
                <ul class="nav nav-tabs nav-justified" role="tablist">
                <c:forEach var="dateBookInfoList" items="${bookInfoMap}" varStatus="status">
                    <li role="presentation" <c:if test="${status.count==1}">class="active"</c:if>>
                        <a href="#${dateBookInfoList.key}" aria-controls="${dateBookInfoList.key}" role="tab" data-toggle="tab">${dateBookInfoList.key}</a>
                    </li>
                </c:forEach>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <c:if test="${bookInfoMap.size()==0}">
                    <div class="col-md-8 col-md-offset-2">
                        <img class="img-responsive center-block" style="max-height: 300px" src="${pageContext.request.contextPath}/web/images/sad.png">
                        <h4 style="text-align: center">还没有预定信息，快去预定吧</h4>
                    </div>
                    </c:if>

                    <c:forEach var="dateBookInfoList" items="${bookInfoMap}" varStatus="status">
                        <div role="tabpanel" class="tab-pane fade <c:if test="${status.count==1}">in active</c:if>" id="${dateBookInfoList.key}">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>房间名</th>
                                    <th>开始</th>
                                    <th>结束</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="bookInfo" items="${dateBookInfoList.value}" varStatus="childStatus">
                                    <tr>
                                        <td>${childStatus.count}</td>
                                        <td>${bookInfo.roomName}</td>
                                        <td>${bookInfo.startTime}</td>
                                        <td>${bookInfo.endTime}</td>
                                        <td>${bookInfo.status}</td>
                                        <td>
                                            <a
                                            <c:choose>
                                                    <c:when test="${bookInfo.realStatus!=0}">
                                                        style="color: gray" data-toggle="tooltip" data-placement="bottom" title="预订已执行"
                                                        href="#"
                                                    </c:when>
                                            <c:otherwise>
                                                    data-toggle="tooltip" data-placement="bottom" title="修改"
                                                    href="${pageContext.request.contextPath}/book?submitType=modify&bookInfoId=${bookInfo.id}"
                                            </c:otherwise>
                                            </c:choose>
                                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                            </a>
                                            <a href="#" data-toggle="tooltip" data-placement="bottom" title="删除" data-id="${bookInfo.id}" name="deleteBtn"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:forEach>
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
<script src="${pageContext.request.contextPath}/web/plugin/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/web/js/main.js"></script>
<script src="${pageContext.request.contextPath}/web/js/common.js"></script>

<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();

        $("a[name='deleteBtn']").click(function () {
            var that=this;
            layer.msg('确定删除吗？', {
                time: 0 //不自动关闭
                ,btn: ['确定','取消']
                ,yes: function(index){
                    layer.close(index);
                    var bookInfoId=$(that).data('id');
                    openLoading();
                    $.ajax({
                        type: "get",
                        async: true,
                        dataType: "json",
                        data: {
                            bookInfoId: bookInfoId
                        },
                        url: "${pageContext.request.contextPath}/deleteUserBookInfo",
                        cache: false,
                        success: function (data) {
                            if (data.retcode == "0000") {
                                msg("删除成功");
                                $(that).parents('tr').remove();
                            } else {
                                msg(data.errorMsg);
                            }
                            closeLoading();
                        }
                    });
                }
            });
        });
    });

</script>

</body>
</html>
