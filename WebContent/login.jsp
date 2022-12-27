<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String contextPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>Đăng Nhập</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/login.css"/>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert('${requestScope.message}');
			</script>
		</c:if>
		<jsp:include page="header.jsp"></jsp:include>
		<section class="main">
			<div class="container">
				<div class="row">
					<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6 col-md-offset-4 col-md-4">
						<div class="main text-center">
							
							<ul class="nav nav-tabs nav-justified" role="tablist">
								<li role="presentation" class="active"><a href="#login" aria-controls="login" role="tab" data-toggle="tab">Đăng Nhập</a></li>
								<li role="presentation"><a href="#signup" aria-controls="signup" role="tab" data-toggle="tab">Đăng Kí</a></li>
							</ul>
						
							<div class="tab-content">
							
								<div id="login" class="tab-pane fade in active" role="tabpanel">
									<form action="<%=contextPath %>/NormalUserServlet?info=login" method="post">
										<div class="form-group input-group">
											<span class="input-group-addon"><span class="fa fa-user fa-fw"></span></span>
											<input type="text" id="loginUsername" class="form-control" placeholder="Tên tài khoản" name="userName" required autofocus/>
										</div>
										<div class="form-group input-group">
											<span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span>
											<input type="password" id="loginPassword" class="form-control" placeholder="Mật khẩu" name="userPassword" required/>
										</div>
										<!-- <div class="form-group text-right">
											<a href="#">Quên mật khẩu?</a>
										</div> -->
										<div class="form-group">
											<button type="submit" class="btn btn-primary form-control">Đăng nhập</button>
										</div>
									</form>
								</div>
							
								<div id="signup" class="tab-pane fade" role="tabpanel">
									<form action="<%=contextPath %>/NormalUserServlet?info=signup" method="post">
										<div class="form-group input-group">
											<span class="input-group-addon"><span class="fa fa-user fa-fw"></span></span>
											<input type="text" id="signupUsername" class="form-control" placeholder="Tên tài khoản" name="userName" required autofocus/>
										</div>
										<div class="form-group input-group">
											<span class="input-group-addon"><span class="fa fa-key fa-fw"></span></span>
											<input type="password" id="signupPassword" class="form-control" placeholder="Mật khẩu" name="userPassword" required/>
										</div>
										<div class="form-group input-group">
											<span class="input-group-addon"><span class="fa fa-envelope-o fa-fw"></span></span>
											<input type="email" id="signupEmail" class="form-control" placeholder="Email" name="userEmail" required/>
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary form-control">Đăng Ký</button>
										</div>
									</form>
								</div>
							
							</div>
						
						</div><!-- /.main -->
					</div><!-- /.col-md-4 -->
				</div><!-- /.row -->
			</div><!-- /.container -->
		</section>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>