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
		<title>Theo dõi ca sĩ</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<style type="text/css">
			.form {
				width: 600px;
				margin: 0 auto;
			}
			h2 {
				margin-bottom: 25px;
			}
			.list-num {
				width: 10px;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div class="form">
				<h2 class="theme-color">Theo dõi ca sĩ</h2>
				<section class="table-responsive">
					<table class="table table-striped">
						<tr>
							<th class="list-num"></th>
							<th>Ca Sĩ</th>
							<th>Giới tính</th>
							<th>Năm sinh</th>
						</tr>
						<c:forEach items="${list }" var="normalUserSinger" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td><a href="<%=contextPath %>/SingerServlet?info=play&playId=${normalUserSinger.singer.singerId}" target="_blank"><c:out value="${normalUserSinger.singer.singerName }"></c:out> </a></td>
								<td>
									<c:if test="${normalUserSinger.singer.singerSex==0 }">
										Khác
									</c:if>
									<c:if test="${normalUserSinger.singer.singerSex==1 }">
										Nam
									</c:if>
									<c:if test="${normalUserSinger.singer.singerSex==2 }">
										Nữ
									</c:if>
								</td>
								<td><a href="<%=contextPath %>/SingerServlet?info=removeFollow&singerId=${normalUserSinger.singer.singerId}">Đăng xuất</a></td>
							</tr>
						</c:forEach>
					</table>
				</section>
			</div>
		</div>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>