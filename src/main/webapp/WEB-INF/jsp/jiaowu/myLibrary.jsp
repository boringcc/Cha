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
%>
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
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <!-- 检索  -->
                        <form action="/jiaowu/chaScore" method="post" name="scoreForm" id="scoreForm">

                            <!-- 借书信息 -->
                            <table id="jd-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center">序号</th>
                                    <th class="center">书名</th>
                                    <th class="center">图书条码</th>
                                    <th class="center">存放位置</th>
                                    <th class="center">流通状态</th>
                                    <th class="center">应还日期</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pd.bookInfos}" var="book" >
                                    <tr>
                                        <td class="center">${book.id }</td>
                                        <td class="center">${book.bookName }</td>
                                        <td class="center">${book.barCode }</td>
                                        <td class="center">${book.place }</td>
                                        <td class="center">${book.statu }</td>
                                        <td class="center">${book.date }</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->

    <!-- 返回顶部 -->
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>

</div>
<!-- /.main-container -->

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
