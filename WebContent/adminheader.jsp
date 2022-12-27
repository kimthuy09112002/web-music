<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String contextPath = request.getContextPath(); %>

<header class="theme-bg-color">
	<nav class="navbar navbar-default">
		<div class="container theme-bg-color">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand"><img src="img/logo_2.png" alt="music"/></a>
			</div><!-- /.navbar-header -->
				<div class="nav navbar-nav hidden-xs hidden-sm">
					<h1 class="white-color">Hệ thống quản lý nền tảng chia sẻ âm nhạc</h1>				
				</div>
			<div class="collapse navbar-collapse" id="navbar-collapse">
				<%-- <ul class="nav navbar-nav">
					<li><a href="index.jsp">Trang đầu</a></li>
					<li><a href="<%=contextPath %>/SingerServlet?info=list">Ca Sĩ</a></li>
					<li><a href="<%=contextPath %>/AlbumServlet?info=list">Album</a></li>
				</ul> --%>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${admin!=null}">
						<li><a href="#"><span class="fa fa-user"></span>&nbsp;${sessionScope.admin.adminUsername }</a></li>
						<li><a href="<%=contextPath %>/AdminServlet?info=logout">Đăng xuất</a></li>
					</c:if>
				</ul>
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container -->
	</nav>
</header>