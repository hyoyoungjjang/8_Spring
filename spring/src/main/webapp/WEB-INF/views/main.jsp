<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body onload="init()">
	<jsp:include page="common/header.jsp" />
	<div class="content">
		<br><br>
		
		<div class="innerOuter">
			<h4>게시글 Top 5</h4>
			<br>
			<table id="boardList" class="table table-hover" align="center">
				<thead>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>첨부파일</th>					
					</tr>
				</thead>
				<tbody>
					<!-- 형재 조회수가 가장 높은 상위 5개의 게시글을 조회해서 그리기(ajax) -->
					
				</tbody>
			</table>
		</div>
	</div>
	
	<script>
		function init(){
			//서버로부터 조회수가 높은 게시글 5개 조회해서 가져오기(ajax)
			//tbody 요소로써 추가해주기
			getTopBoardList(function(list){
				drawTopListBody(list);
			})
		}

		function drawTopListBody(list){
			const TopBody = document.querySelector("#boardList > tbody");

			for(let b of list){
				const tr = document.createElement("tr");
				tr.innerHTML += "<tr>"
						+ "<td>" + b.boardNo + "</td>"
						+ "<td>" + b.boardTitle + "</td>"
						+ "<td>" + b.boardWriter + "</td>"
						+ "<td>" + b.count + "</td>"
						+ "<td>" + b.createDate + "</td>"
						+ "<td>" + (b.originName != null ? "★" : "") + "</td>"
						+ "</tr>"
					
				tr.onclick = function(){
					location.href = 'detail.bo?bno=' + b.boardNo
				}	

				topBody.appendChild(tr);		
			}
			
		}

		//서버로부터 조회수가 높은 게시글 5개 조회해서 가져오는 함수
		function getTopBoardList(callback){
			$.ajax({
				url : "topList.bo",
				success : callback,
				error : function(){
					console.log("top5 ajax실패")
				}
			})
		}
	</script>
	
	<jsp:include page="common/footer.jsp" />
</body>
</html>