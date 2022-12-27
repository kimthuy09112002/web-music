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
		<title>Theo dõi bài hát</title>
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
				<h2 class="theme-color">Theo dõi bài hát</h2>
				<section class="table-responsive">
					<table class="table table-striped">
						<tr>
							<th class="list-num"></th>
							<th>Tên bài hát</th>
							<th>Ca sĩ</th>
							<th>Năm</th>
						</tr>
						<c:forEach items="${list }" var="normalUserSong" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td><a href="<%=contextPath %>/SongServlet?info=play&playId=${normalUserSong.song.songId}" target="_blank"><c:out value="${normalUserSong.song.songTitle }"></c:out> </a></td>
								<td><a href="<%=contextPath %>/SingerServlet?info=play&playId=${normalUserSong.song.singerId}"><c:out value="${normalUserSong.song.singer.singerName }"></c:out></a></td>
								<td><a href="<%=contextPath %>/SongServlet?info=removeFollow&songId=${normalUserSong.song.songId}">Đăng xuất</a></td>
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