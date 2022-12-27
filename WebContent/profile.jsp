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
		<title>Quản lý tài khoản</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/profile.css"/>
	</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<section class="main">
			<div class="sidebar col-md-2">
				<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-success">
						<div class="panel-heading" role="tab" id="headingOne">
							<h4 class="panel-title">
								<a href="#collapseSetting" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseSetting">Thông tin cá nhân&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
							</h4>
						</div>
						<div id="collapseSetting" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
							<div class="list-group">
								<a href="<%=contextPath %>/user/setting.jsp" target="iframe" class="list-group-item active">Cài đặt</a>
								<a href="<%=contextPath %>/user/avatar.jsp" target="iframe" class="list-group-item">Tải ảnh đại diện lên</a>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading" role="tab" id="headingTwo">
							<h4 class="panel-title">
								<a href="#collapseAccount" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseAccount">Quản lý tài khoản&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
							</h4>
						</div>
						<div id="collapseAccount" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
							<div class="list-group">
								<a href="user/psw.jsp" target="iframe" class="list-group-item">Đổi mật khẩu</a>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading" role="tab" id="headingThree">
							<h4 class="panel-title">
								<a href="#collapseFollow" role="button" data-toggle="collapse" data-parent="#accordion" aria-expanded="true" aria-controls="collapseFollow">Quản lý chung&nbsp;<span class="fa fa-angle-double-down pull-right"></span></a>
							</h4>
						</div>
						<div id="collapseFollow" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
							<div class="list-group">
								<a href="SongServlet?info=followmgr" target="iframe" class="list-group-item">Theo dõi bài hát</a>
								<a href="AlbumServlet?info=followmgr" target="iframe" class="list-group-item">Theo dõi album</a>
								<a href="SingerServlet?info=followmgr" target="iframe" class="list-group-item">Theo dõi ca sĩ</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="content col-md-10">
				<div class="iframe">
					<div class="embed-responsive embed-responsive-16by9">
						<iframe class="embed-responsive-item" name="iframe" src="user/setting.jsp">Phiên bản trình duyệt của bạn quá thấp, vui lòng nâng cấp trình duyệt của bạn lên phiên bản mới!</iframe>
					</div>
				</div>
			</div>
		</section>
		<jsp:include page="footer.jsp"></jsp:include>
		
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var taga = $(".list-group a");
		taga.on('click', function (){
			taga.removeClass("active");
			$(this).addClass("active");
		});
	</script>
</html>