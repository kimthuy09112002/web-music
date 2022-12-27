<%@page import="music.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String contextPath = request.getContextPath();
request.setAttribute("login_success", Constant.LOGIN_SUCCESS);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>Quản lý</title>
	<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<style type="text/css">
		.main {
			padding-top: 20px;
		}
	</style>
</head>
<body>
	<c:if test="${sessionScope.admin_login_flag!=login_success}">
		<%
		
		response.sendRedirect("admin.jsp");
		%>
	</c:if>
	<jsp:include page="adminheader.jsp"></jsp:include>
	<section class="main">
		<div class="sidebar col-md-2">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-success">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a href="#collapseUser" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseUser">Quản lý người dùng&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<div id="collapseUser" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						<div class="list-group">
							<a href="admin/ban.jsp" target="iframe" class="list-group-item active">Người dùng bị cấm</a>
							<a href="admin/newAdmin.jsp" target="iframe" class="list-group-item">Thêm quản trị viên</a>
						</div>
					</div>
				</div>
				<div class="panel panel-success">
					<div class="panel-heading" role="tab" id="headingTwo">
						<h4 class="panel-title">
							<a href="#collapseSite" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseSite">Quản lý trang Web&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<div id="collapseSite" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
						<div class="list-group">
							<a href="admin/recommendForward.jsp" target="iframe" class="list-group-item">Đề xuất bài hát</a>
							<a href="admin/newSongForward.jsp" target="iframe" class="list-group-item">Bài hát mới </a>
							<a href="admin/singermgrForward.jsp" target="iframe" class="list-group-item">Quản lý ca sĩ</a>
							<a href="admin/albummgrForward.jsp" target="iframe" class="list-group-item">Quản lý album</a>
							<a href="admin/songmgrForward.jsp" target="iframe" class="list-group-item">Quản lý bài hát</a>
						</div>
					</div>
				</div>
				<div class="panel panel-success">
					<div class="panel-heading" role="tab" id="headingThree">
						<h4 class="panel-title">
							<a href="#collapseAccount" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseAccount">Quản lý tài khoản&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
						</h4>
					</div>
					<div id="collapseAccount" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
						<div class="list-group">
							<a href="admin/psw.jsp" target="iframe" class="list-group-item">Đổi mật khẩu</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content col-md-10">
			<div class="iframe">
				<div class="embed-responsive embed-responsive-16by9">
					<iframe class="embed-responsive-item" name="iframe" src="admin/ban.jsp">Phiên bản trình duyệt của bạn quá thấp, vui lòng nâng cấp trình duyệt của bạn lên phiên bản mới!</iframe>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
	<script src="js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var taga = $(".list-group a");
		taga.on('click', function (){
			taga.removeClass("active");
			$(this).addClass("active");
		});
	</script>
</html>