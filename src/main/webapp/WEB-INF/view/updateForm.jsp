<%@ page import="cs.dit.board.BoardDao"%>
<%@ page import="cs.dit.board.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix ="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>  
<%@ page import = "java.util.List, java.sql.Date" %>
  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
 	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<title>게시판</title>
</head>

<body>  
<div class="container">
	<h2 class="text-center font-weight-bold">상세보기</h2>
	<br/>
	<form action="update.do" method="post">
		<input type="hidden" name="bcode" value="${dto.bcode}" id="bcode">
		<table class="table table-striped table-hover">
			<tr>
				<th>bcode</th><td>${dto.bcode}</td>
				<th>subject</th><td><input type="text" value="${dto.subject}" name="subject"></td>
			</tr>
			<tr>
				<th>content</th>
				<td colspan="3"><textarea rows="10" cols="80" name="content">${dto.content}</textarea></td>	
			</tr>
			<tr>
				<th>writer</th><td><input type="text" value="${dto.writer}" name="writer"></td>
				<th>regDate</th><td><input type="text" value="${dto.regDate}" name="regDate"></td>
			</tr>
			<tr>
				<th>filename</th><td><input type="text" value="${dto.filename}" name="filename"></td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="submit" value ="게시글 수정" >
					<input type="button" value ="게시글 삭제" onclick ="location.href='delete.do?bcode=${dto.bcode}'">
					<input type="button" value ="게시글 목록" onclick ="location.href='list.do'">
					<input type="button" value ="게시글 등록" onclick ="location.href='insertForm.do'">
				</td>
			</tr>
		</table><br><br>
	</form>
</div>
<!-- 댓글 달기 -->
	<script type="text/javascript">
	var bcode = $('#bcode').val();
	
	function cList(){
		$.ajax({
			url:'./cList.ct?bcode=' + bcode,
			type: 'post',
			async: true,
			dataType: 'json',
			success: function(data){
				var str = '';
				
				for(var i in data){
					str += '<tr><td>' + data[i].ccode + '<td>';
					str += '<td>' + data[i].content +'<td>';
					str += '<td>' + data[i].regdate +'<td><tr>';
				}
				$('#commentsTable').html(str);
			}
		});
	}
	
		function cInsert(){
			$.ajax({
				url: './cInsert.ct?bcode=' + bcode,
				type:'post',
				async: true,
				data: {"comments":$('#comments').val()},
				success: function (data){
					$('#comments').val('');
					cList();
				}
			});
		}
		
	window.onload = function(){cList();}
	
	</script>
	<div class="container">
		<table class="table" style="text-align:center; border: 1px solid #ddddddd">
				<tr>
					<td style="background-color:#fafafa; text-align:center">댓글 : </td>
					<td><input class="form-control" type="text" id="comments" size="80"></td>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="cInsert();">한줄 댓글 작성</button></td>
				</tr>
		</table>
		<table class="table" style="text-align:center; border:1px solid #dddddd">
			<tbody id ="commentsTable">
			</tbody>
		</table>
	</div>

</body>
</html>