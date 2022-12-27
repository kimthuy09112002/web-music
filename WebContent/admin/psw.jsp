<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String contextPath = request.getContextPath();  %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>Đổi mật khẩu</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<style type="text/css">
			.form {
				width: 400px;
				margin: 0 auto;
			}
			.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td {
			    border-top: none;
			}
			h2 {
				margin-bottom: 25px;
			}
		</style>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert("${requestScope.message}");
			</script>
		</c:if>
		<div class="container">
			<div class="form">
				<h2 class="theme-color">Đổi mật khẩu</h2>
			
				<form action="<%=contextPath %>/AdminServlet?info=psw" method="post" class="form-horizontal">
					<table class="table">
						<tr>
							<td><label class="control-label">Mật khẩu cũ</label></td>
							<td><input type="password" name="password" class="form-control" /></td>
						</tr>
						<tr>
							<td><label class="control-label">Mật khẩu mới</label></td>
							<td><input type="password" name="newPassword" class="form-control" /></td>
						</tr>
						<tr>
							<td colspan="2"><button type="submit" class="btn btn-primary">Xác nhận</button></td>
						</tr>
					</table>
					<input type="hidden" value="${sessionScope.admin.adminUsername }" name="username" />
				</form>
			</div>
		</div>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</html>