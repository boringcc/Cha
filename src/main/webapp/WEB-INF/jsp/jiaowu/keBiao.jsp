<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cc.admin.entity.KeBiao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String[] kebiao = {"第一二节","第三四节","第五六节","第七八节","第九十节" };
%>
<script type="text/javascript" src="/static/js/jquery-1.7.2.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        var kbs = "${pd.kbS}";
        var kb = kbs.split("!");
        for(var i=0;i<${pd.size};i++) {
            var kbInfor = kb[i].split(",");

                var h = kbInfor[0].substring(0,1);
                var l = kbInfor[0].substring(2,3);
                var id = '#tr_' + h + ' #td_' + l;
                var text = kbInfor[1]+"  " + kbInfor[2]  +"  " + kbInfor[3]+"  " + kbInfor[4];
                $(id).text(text);
        }
    })
</script>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <link rel="stylesheet" href="/static/ace/css/chosen.css" />
    <!-- jsp文件头和头部 -->
    <%@ include file="top.jsp"%>
    <!-- 日期框 -->
    <link rel="stylesheet" href="/static/ace/css/datepicker.css" />
</head>
<body class="no-skin">


<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
    <thead>
    <tr>
        <th class="center" style="width:50px;"></th>
        <th class="center" style="width: 200px">星期一</th>
        <th class="center" style="width: 200px">星期二</th>
        <th class="center" style="width: 200px">星期三</th>
        <th class="center" style="width: 200px">星期四</th>
        <th class="center" style="width: 200px">星期五</th>
        <th class="center" style="width: 100px">星期六</th>
        <th class="center" style="width: 100px">星期天</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="<%= kebiao %>" var="s" varStatus="status">
        <tr id="tr_${status.index+1}">
            <td class="center" id="td_0">${s}</td>
            <td class="center" id="td_1"></td>
            <td class="center" id="td_2"></td>
            <td class="center" id="td_3"></td>
            <td class="center" id="td_4"></td>
            <td class="center" id="td_5"></td>
            <td class="center" id="td_6"></td>
            <td class="center" id="td_7"></td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="foot.jsp"%>
<!-- 删除时确认窗口 -->
<script src="/static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="/static/ace/js/ace/ace.js"></script>
<!-- 日期框 -->
<script src="/static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!-- 下拉框 -->
<script src="/static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="/static/js/jquery.tips.js"></script>
</body>

<script type="text/javascript">

    //检索
    function searchs(){
        $("#scoreForm").submit();
    }


</script>
</html>
