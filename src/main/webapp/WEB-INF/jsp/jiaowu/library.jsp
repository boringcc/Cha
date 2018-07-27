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
                        <form action="/jiaowu/jdChaBook" method="post" name="bookForm" id="bookForm">

                            <table style="margin-top:5px;">
                                <tr>
                                    <td>
                                        <div class="nav-search">
									<span class="input-icon">
										<input class="nav-search-input" autocomplete="off" id="keywords" type="text" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
                                        </div>
                                    </td>
                                    <td style="vertical-align:top;padding-left:2px;"><a  class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                                </tr>
                            </table>
                            <!-- 借书信息 -->
                            <table id="jd-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <th class="center">序号</th>
                                    <th class="center">书名</th>
                                    <th class="center">作者</th>
                                    <th class="center">出版社</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${pd.jdBookInfos}" var="book" >
                                    <tr>
                                        <td class="center" >${book.id }</td>
                                        <td class="center"><a id="viewBook" onclick="viewBook('${book.barCode}')" style="cursor:pointer;">${book.bookName }</a></td>
                                        <td class="center">${book.author }</td>
                                        <td class="center">${book.pub }</td>
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
<script src="/static/layer-v3.1.1/layer/layer.js"></script>
</body>

<script type="text/javascript">

    //检索
    function searchs(){
        $("#bookForm").submit();
    }


    function viewBook(barCode){
        var BARCODE = barCode;
        $.ajax({
            type: "POST",
            url: '/jiaowu/viewBook',
            data: {BARCODE:BARCODE},
            dataType:'json',
            cache: false,
            success: function(data){
                var NUM = data.bookNum;
                var bookInfors = data.xxBookInfo;
                var bookInfo = bookInfors.split("!");
                var text = "";
                for(var i = 0;i<bookInfo.length;i++){
                    text = "<p align=\"center\">" + bookInfo[i] +"</p><br/>" + text;
                }
                layer.open({
                    type: 1,
                    title: '图书信息',
                    closeBtn: 0,
                    area: ['350px', '200px'],
                    shadeClose: true,
                    skin: 'demo-class',
                    content:text
                });
            }
        });
    }

</script>
</html>
