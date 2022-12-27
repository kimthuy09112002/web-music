<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Quản lý album</title>
		<script type="text/javascript"> 
			window.location.href="<%=contextPath %>/AlbumServlet?info=find&pageNum=1";
		</script>
	</head>
	<body>
		Đang tải...
	</body>
</html>