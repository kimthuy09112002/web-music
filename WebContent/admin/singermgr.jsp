<%@page import="music.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%String contextPath = request.getContextPath();
int male = Constant.SEX_MALE;
int female = Constant.SEX_FEMALE;
int sexDefault = Constant.SEX_DEFAULT;
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>Quản lý ca sĩ</title>
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
				<h2 class="theme-color">Quản lý ca sĩ</h2>
				<div class="row">
					<div class="col-md-12">
						<form action="<%=contextPath %>/SingerServlet?info=find&pageNum=1" method="post" id="formSub">
							<div class="btn-group">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalAdd"><span class="fa fa-plus"></span>&nbsp;Thêm ca sĩ</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalDel"><span class="fa fa-minus"></span>&nbsp;Xóa ca sĩ</button>
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#modalUpdate"><span class="fa fa-gear"></span>&nbsp;Sửa đổi ca sĩ</button>
								<button type="submit" class="btn btn-default" id="btnSubmit"><span class="fa fa-refresh"></span>&nbsp;Làm mới</button>
							</div>
						</form>
						<table class="table table-striped">
							<tr>
								<th>ID ca sĩ</th>
								<th>Tên ca sĩ</th>
								<th>Giới tính</th>
							</tr>
							<tbody id="list-content">
								<c:forEach items="${result.dataList }" var="singer">
									<tr>
										<td><c:out value="${singer.singerId }"></c:out> </td>
										<td><c:out value="${singer.singerName }"></c:out> </td>
										<td>
											<c:if test="${singer.singerSex eq 0 }">
												<c:out value="Khác"></c:out>
											</c:if>
											<c:if test="${singer.singerSex eq 1 }">
												<c:out value="Nam"></c:out>
											</c:if>
											<c:if test="${singer.singerSex eq 2 }">
												<c:out value="Nữ"></c:out>
											</c:if>
										 </td>
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
						<h4 class="modal-title" id="modalAddLabel">Thêm ca sĩ</h4>
					</div>
					
					<form action="<%=contextPath %>/SingerServlet?info=add" method="post" enctype="multipart/form-data">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Tên ca sĩ" name="singerName" required />
							</div>
							<div class="form-group">
								<label>Giới tính</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio1" value="<%=male%>"> Nam
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio2" value="<%=female%>"> Nữ
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio3" value="<%=sexDefault%>" checked > Khác
								</label>
							</div>
							<div class="form-group">
								<label for="">Giới thiệu ca sĩ</label>
								<textarea class="form-control" name="singerIntroduction" rows="4" maxlength="255" required></textarea>
							</div>
							<div class="form-group">
								<label>Hình ảnh ca sĩ</label>
								<input type="file" name="filename" required/>
								<p class="help-block">Chỉ hỗ trợ định dạng .jpg hoặc .png</p>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
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
						<h4 class="modal-title" id="modalDelLabel">Xóa ca sĩ</h4>
					</div>
					<form action="<%=contextPath %>/SingerServlet?info=del" method="post">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="ID ca sĩ" name="singerId" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
	        				<button type="submit" class="btn btn-primary">Xóa bỏ</button>
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
						<h4 class="modal-title" id="modalUpdateLabel">Sửa đổi ca sĩ</h4>
					</div>
					<form action="<%=contextPath %>/SingerServlet?info=update" method="post" enctype="multipart/form-data">
						<div class="modal-body">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="ID ca sĩ" name="singerId" />
							</div>
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Tên ca sĩ" name="singerName" />
							</div>
							<div class="form-group">
								<label>Giới tính</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio1" value="<%=male%>" /> Nam
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio2" value="<%=female%>" /> Nữ
								</label>
								<label class="radio-inline">
									<input type="radio" name="sex" id="inlineRadio3" value="<%=sexDefault%>" checked /> Khác
								</label>
							</div>
							<div class="form-group">
								<label for="">Giới thiệu ca sĩ</label>
								<textarea class="form-control" rows="4" maxlength="255" name="singerIntroduction"></textarea>
							</div>
							<div class="form-group">
								<label>Hình ảnh ca sĩ</label>
								<input type="file" name=filename required/>
								<p class="help-block">Chỉ hỗ trợ định dạng .jpg hoặc .png</p>
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
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=" + (currentPage+1));
				return true;
			}
		}
		
		function previousPage(){
			if(currentPage == 1){
				alert("Đã là trang đầu tiên của dữ liệu");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=" + (currentPage-1));
				return true;
			}
		}
		// trang đầu tiên
		function firstPage(){
			if(currentPage == 1){
				alert("Đã là trang đầu tiên của dữ liệu");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=1");
				return true;
			}
		}
		// trang cuối
		function lastPage(){
			if(currentPage == totalPage){
				alert("Đã là trang dữ liệu cuối cùng");
				return false;
			}else{
				submitForm("<%=contextPath %>/SingerServlet?info=find&pageNum=${result.totalPage}");
				return true;
			}
		}
	</script>
</html>