<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>Quản lý bài hát</title>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap-jquery-pagination.css"/>
		<style type="text/css">
			.btn-group {
				margin-bottom: 10px;
			}
		</style>
	</head>
	<body>
		<c:if test="${requestScope.flag==true }">
			<script type="text/javascript">
				alert('${requestScope.message}');
			</script>
		</c:if>
		<div class="container">
			<div class="form">
				<h2 class="theme-color">Quản lý bài hát</h2>
				<div class="row">
					<div class="col-md-12">
						<form action="<%=contextPath %>/SongServlet?info=find&pageNum=1" method="post" id="formSub">
							<div class="btn-group">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalAdd"><span class="fa fa-plus"></span>&nbsp;Thêm bài hát</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalDel"><span class="fa fa-minus"></span>&nbsp;Xóa bài hát</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalUpdate"><span class="fa fa-gear"></span>&nbsp;Chỉnh sửa bài hát</button>
								<button type="submit" class="btn btn-default" id="btnSubmit"><span class="fa fa-refresh"></span>&nbsp;Làm mới</button>
							</div>
						</form>
						<table class="table table-striped">
							<tr>
								<th>ID bài hát</th>
								<th>Tên bài hát</th>
								<th>ID ca sĩ</th>
							</tr>
							<tbody id="list-content">
								<c:forEach items="${result.dataList }" var="song">
									<tr>
										<td><c:out value="${song.songId }"></c:out> </td>
										<td><c:out value="${song.songTitle }"></c:out> </td>
										<td><c:out value="${song.singerId }"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<ul class="pagination">
											<li><a href="#" onclick="firstPage();">Trang đầu</a></li>
											<li><a href="#" onclick="previousPage();">Trang trước</a></li>
											<li><a href="#" onclick="nextPage();">Trang tiếp theo</a></li>
											<li><a href="#" onclick="lastPage();">Trang cuối</a></li>
										</ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="modalAdd" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalAddLabel">Thêm bài hát</h4>
					</div>
					<form action="<%=contextPath %>/SongServlet?info=add" method="post" enctype="multipart/form-data" >
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Tên bài hát" name="songTitle" required />
							</div>
							<div class="form-group">
								<label for="" class="">Ca sĩ</label>
								<select name="singerId" class="form-control">
									<option value="0" selected>Không ai</option>
									<c:forEach items="${singers }" var="singer">
										<option value="${singer.singerId }"><c:out value="${singer.singerName }"></c:out></option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="">Album</label>
								<select name="albumId" class="form-control">
									<option value="0" selected>Không ai</option>
									<c:forEach items="${albums }" var="album">
										<option value="${album.albumId }"><c:out value="${album.albumTitle }"></c:out></option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Tập các bài hát</label>
								<input type="file" name="filename" required/>
								<p class="help-block">Chỉ hỗ trợ định dạng .mp3</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Huỷ bỏ</button>
	        				<button type="submit" class="btn btn-primary">Thêm vào</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="modalDel" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalDelLabel">Xóa bài hát</h4>
					</div>
					<form action="<%=contextPath %>/SongServlet?info=del" method="post">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="ID bài hát" name="songId" required />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
	        				<button type="submit" class="btn btn-primary">Xoá bỏ</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal fade" id="modalUpdate" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="modalUpdateLabel">Chỉnh sửa bài hát</h4>
					</div>
					<form action="<%=contextPath %>/SongServlet?info=update" method="post" enctype="multipart/form-data">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="ID bài hát" name="songId" required />
							</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Tên bài hát" name="songTitle" required />
							</div>
							<div class="form-group">
								<label for="" class="">Ca sĩ</label>
								<select name="singerId" class="form-control">
									<option value="0" selected>Không ai</option>
									<c:forEach items="${singers }" var="singer">
										<option value="${singer.singerId }"><c:out value="${singer.singerName }"></c:out></option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label for="">Album</label>
								<select name="albumId" class="form-control">
									<option value="0" selected>Không ai</option>
									<c:forEach items="${albums }" var="album">
										<option value="${album.albumId }"><c:out value="${album.albumTitle }"></c:out></option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Tập các bài hát</label>
								<input type="file" name="filename" required/>
								<p class="help-block">Chỉ hỗ trợ định dạng .mp3</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
	        				<button type="submit" class="btn btn-primary">Ôn lại</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
	<script src="<%=contextPath %>/js/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=contextPath %>/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		
		var currentPage = ${result.currentPage};
		
		// tổng số trang
		var totalPage = ${result.totalPage};
		
		function submitForm(actionUrl){
			var formElement = document.getElementById("formSub");
			formElement.action = actionUrl;
			formElement.submit();
		}
		function nextPage(){
			if(currentPage == totalPage){
				alert("Đã là trang dữ liệu cuối cùng");
				return false;
			}else{
				submitForm("<%=contextPath %>/SongServlet?info=find&pageNum=" + (currentPage+1));
				return true;
			}
		}
		
		function previousPage(){
			if(currentPage == 1){
				alert("Đã là trang đầu tiên của dữ liệu");
				return false;
			}else{
				submitForm("<%=contextPath %>/SongServlet?info=find&pageNum=" + (currentPage-1));
				return true;
			}
		}
		// trang đầu tiên
		function firstPage(){
			if(currentPage == 1){
				alert("Đã là trang đầu tiên của dữ liệu");
				return false;
			}else{
				submitForm("<%=contextPath %>/SongServlet?info=find&pageNum=1");
				return true;
			}
		}
		// trang cuối
		function lastPage(){
			if(currentPage == totalPage){
				alert("Đã là trang dữ liệu cuối cùng");
				return false;
			}else{
				submitForm("<%=contextPath %>/SongServlet?info=find&pageNum=${result.totalPage}");
				return true;
			}
		}
	</script>
</html>