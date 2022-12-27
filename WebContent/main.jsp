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
		<title>Nền tảng chia sẻ âm nhạc</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/index.css"/>
	</head>
	<body>
	
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
					<div class="collapse navbar-collapse" id="navbar-collapse">
						<ul class="nav navbar-nav">
							<li><a href="index.jsp"><span class="fa fa-home"></span>&nbsp;Trang đầu</a></li>
							<li><a href="<%=contextPath %>/SingerServlet?info=list"><span class="fa fa-microphone"></span>&nbsp;Ca sĩ</a></li>
							<li><a href="<%=contextPath %>/AlbumServlet?info=list"><span class="fa fa-th-list"></span>&nbsp;Album</a></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<c:if test="${sessionScope.user==null || sessionScope.user=='' }">
								<li><a href="login.jsp">Đăng nhập</a></li>
							</c:if>
							<c:if test="${sessionScope.user!=null && sessionScope.user!='' }">
	 							<li><a href="<%=contextPath %>/NormalUserServlet?info=manager"><span class="fa fa-user"></span>&nbsp;<c:out value="${sessionScope.user.userNickname }"></c:out> </a></li>
	 							<li><a href="<%=contextPath %>/NormalUserServlet?info=logout">Đăng xuất</a></li>
							</c:if>
						</ul>
					</div><!-- /.navbar-collapse -->
				</div><!-- /.container -->
			</nav>
			<div class="container text-center">
				<h1>Nền tảng chia sẻ âm nhạc</h1>
				<div class="row">
					<div class="col-sm-offset-3 col-sm-6 col-md-offset-3 col-md-6">
						<form action="<%=contextPath %>/SongServlet?info=search" method="post">
							<div class="form-group input-group">
								<input type="search" class="form-control" placeholder="Âm nhạc để tìm kiếm..." name="songTitle"/>
								<span class="input-group-btn">
									<button class="btn btn-default" type="submit"><span class="fa fa-search"></span></button>
								</span>
							</div>
						</form>	
					</div>
				</div>
			</div>
		</header>
		<section class="main">
			<div class="container main-content">
				<div class="row">
					<div class="col-md-7">
						<section>
							<h2 class="theme-color">Đề xuất</h2>
							<div class="row">
								<c:forEach items="${requestScope.recommends }" var="recommend">
									<div class="col-sm-6 music-item">
										<div class="media-list">
											<div class="media">
												<div class="media-left">
													<a href="<%=contextPath %>/SongServlet?info=play&playId=${recommend.song.songId}">
														<img class="media-object" src="<%=Constant.ALBUM_PATH %>/${recommend.album.albumPic }"/>
													</a>
												</div>
												<div class="media-body">
													<h4 class="media-heading"><a href="<%=contextPath %>/SongServlet?info=play&playId=${recommend.song.songId}"><c:out value="${recommend.song.songTitle }"></c:out></a></h4>
													<div class="singer"><a href="<%=contextPath %>/SingerServlet?info=play&playId=${recommend.singer.singerId}"><c:out value="${recommend.singer.singerName }"></c:out></a></div>
													<div class="album"><a href="<%=contextPath %>/AlbumServlet?info=play&playId=${recommend.album.albumId}"><c:out value="${recommend.album.albumTitle }"></c:out></a></div>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</section>
						<section>
							<h2 class="theme-color">Bài hát mới</h2>
							<div class="row">
								<c:forEach items="${requestScope.newSongs }" var="newSong">
									<div class="col-sm-6 music-item">
										<div class="media-list">
											<div class="media">
												<div class="media-left">
													<a href="<%=contextPath %>/SongServlet?info=play&playId=${newSong.song.songId}">
														<img class="media-object" src="<%=Constant.ALBUM_PATH %>/${newSong.album.albumPic }"/>
													</a>
												</div>
												<div class="media-body">
													<h4 class="media-heading"><a href="<%=contextPath %>/SongServlet?info=play&playId=${newSong.song.songId}"><c:out value="${newSong.song.songTitle }"></c:out></a></h4>
													<div class="singer"><a href="<%=contextPath %>/SingerServlet?info=play&playId=${newSong.singer.singerId}"><c:out value="${newSong.singer.singerName }"></c:out></a></div>
													<div class="album"><a href="<%=contextPath %>/AlbumServlet?info=play&playId=${newSong.album.albumId}"><c:out value="${newSong.album.albumTitle }"></c:out></a></div>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</section>
					</div>
					<div class="col-md-offset-1 col-md-4">
						<section>
							<h2 class="theme-color">Tải xuống</h2>
							<table class="table">
								<c:forEach items="${requestScope.dRank }" var="dsong" varStatus="status">
									<tr>
										<td><c:out value="${status.count }"></c:out>.</td>
										<td><a href="<%=contextPath %>/SongServlet?info=play&playId=${dsong.songId}"><c:out value="${dsong.songTitle }"></c:out></a></td>
										<td><span class="fa fa-download theme-color"></span>&nbsp;<small><c:out value="${dsong.songDldtimes }"></c:out>Hạng hai</small></td>
									</tr>
								</c:forEach>
							</table>
						</section>
					</div>
				</div>
			</div>
		</section>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>