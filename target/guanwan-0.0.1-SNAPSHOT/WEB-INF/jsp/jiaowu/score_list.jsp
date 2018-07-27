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
                            <table style="margin-top:5px;">
                                <tr>
                                    <td style="vertical-align:top;padding-left:2px;">
                                        <select class="chosen-select form-control" name="xueqi" id="xueqi" data-placeholder="请选择学期" style="vertical-align:top;width: 100px;">
                                            <option value=""></option>
                                            <option value="">全部</option>
                                            <option value="1">第一学期</option>
                                            <option value="2">第二学期</option>
                                            <option value="3">第三学期</option>
                                            <option value="4">第四学期</option>
                                            <option value="5">第五学期</option>
                                            <option value="6">第六学期</option>
                                            <option value="7">第七学期</option>
                                            <option value="8">第八学期</option>
                                        </select>
                                    </td>
                                    <td style="vertical-align:top;padding-left:2px;"><a  class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                                </tr>
                            </table>
                            <!-- 检索  -->


                            <!-- 绩点和排名 -->
                            <table id="jd-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center">平均绩点</th>
                                    <th class="center">平均成绩</th>
                                    <th class="center">绩点班级排名</th>
                                    <th class="center">绩点专业排名</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${pd.jiDianList}" var="j" >
                                        <tr>
                                            <td class="center">${j.avgGPA }</td>
                                            <td class="center">${j.avgScore }</td>
                                            <td class="center">${j.GPARankClass }</td>
                                            <td class="center">${j.GPARankSpecialty }</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <!--成绩 1:序号 2:学期 3:课程名字 4：成绩 5:学分 6：总学时 7:考核方式 8:课程属性 9:课程性质 -->
                            <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center" style="width:50px;">序号</th>
                                    <th class="center" style="width:200px;">学期</th>
                                    <th class="center">课程名字</th>
                                    <th class="center">成绩</th>
                                    <th class="center">学分</th>
                                    <th class="center">总学时</th>
                                    <th class="center">考核方式</th>
                                    <th class="center">课程属性</th>
                                    <th class="center">课程性质</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pd.scoreList}" var="s" >
                                    <tr>
                                        <td class="center">${s.id }</td>
                                        <td class="center">${s.semester }</td>
                                        <td class="center">${s.courseName }</td>
                                        <td class="center">${s.score }</td>
                                        <td class="center">${s.credit }</td>
                                        <td class="center">${s.totalHours }</td>
                                        <td class="center">${s.method }</td>
                                        <td class="center">${s.attribute }</td>
                                        <td class="center">${s.type }</td>
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
