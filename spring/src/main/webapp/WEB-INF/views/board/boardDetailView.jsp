<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<style>
	table * {margin:5px;}
	table {width:100%;}
</style> 
</head>
<body>
	<jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
						<c:choose>
							<c:when test="${not empty b.originName }">
								<!-- case1 -->
                        		<a href="${b.changeName }" download="${b.originName }">${b.originName }</a>
                        	</c:when>
                        	<c:otherwise>
								<!-- case2 -->
								첨부파일이 없습니다.
							</c:otherwise>
						</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${b.boardContent }</p></td>
                </tr>
            </table>
            <br>

   			
   			<!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
            <div align="center">
            	<c:if test="${loginUser.userId eq b.boardWriter}">
	                <a class="btn btn-primary" onclick="">수정하기</a>
	                <a class="btn btn-danger" onclick="">삭제하기</a>
                </c:if>
            </div>
            <br><br>
           
            
             <form action="" method="post" id="postForm">
           		<input type="hidden" name=bno value="7">
           		<input type="hidden" name="filePath" value="이미지.jpg">
             </form>
            
          
 
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${empty loginUser }">
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" readonly cols="55" rows="2" style="resize:none; width:100%;">로그인 후 이용 가능합니다.</textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary disabled">등록하기</button></th>
		                    </tr>
	                	</c:when>
	                    <c:otherwise>
		                    <tr> 
		                        <th colspan="2">
		                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
		                    </tr>
	                    </c:otherwise>
                    </c:choose>
                
                    
                    
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
    
                </thead>
                <tbody>
                   <!--
                   	<tr>
                        <th>admin</th>
                        <td>댓글남깁니다</td>
                        <td>2022-05-10</td>
                    </tr>
                   -->
                </tbody>
            </table>
        </div>
        <br><br>
        <script>
            $(function(){
                getReplyList({bno : ${b.boardNo}}, function(reulst){
                    // reulst = reulst.map(r => {
                    //     return {
                    //         ...r,
                    //         cNo : 1
                    //     }
                    // })

                    // reulst.push({
                    //     cNo : 2,
                    //     createDate : "2022-10-30",
                    //     refBno : 0,
                    //     replyContent : "안녕하세요",
                    //     replyNo: 5,
                    //     replyWriter: "admin"
                    // });

                    // const rList = {
                        
                    // }
                    // for (let r of reulst) {
                    //     if (rList[r.cNo]) {
                    //         rList[r.cNo].push(r);
                    //     } else {
                    //         rList[r.cNo] = [r];
                    //     }
                    // }
                    // console.log(rList)

                    const replyBody = document.querySelector("#replyArea tbody");
                    const list = [];
                    for (let r of reulst) {
                        list.push({
                            tdData1: r.replyWriter,
                            tdData2: r.replyContent,
                            tdData3: r.createDate,
                            rowEvent: function(){
                                console.log("클릭됨")
                            }
                        })
                    }

                    drawTableList(reulst, replyBody);
                })
            })
            

            // 댓글 목록 가져오기
            function getReplyList(data, callback){
                $.ajax({
                    url: 'rlist.bo',
                    data : data,
                    success: function(reulst){
                        callback(reulst)
                    },
                    error: function(item){
                        console.log(item);
                        console.log("댓글요청 ajax 실패");
                    }
                })
            }

            function drawTableList(itemList, parent){

               
                //단순하게 보여주기위한 view를 작성할때  
                // let str = "";                
                // for (let reply of replyList) {
                //     str += `<tr>
                //                 <th>` + reply.replyWriter + `</th>
                //                 <td>` + reply.replyContent + `</td>
                //                 <td>` + reply.createDate + `</td>
                //             </tr>`;
                // }
                // replyBody.innerHTML = str;

                //이벤트를 넣는 뷰를 작성하고 싶을 때               
                for (let reply of itemList) {
                    const replyRow = document.createElement('tr');
                    replyRow.innerHTML = `<th>` + reply.replyWriter + `</th>
                                          <td>` + reply.replyContent + `</td>
                                          <td>` + reply.createDate + `</td>`;
                    parent.appendChild(replyRow);
                    
                    replyRow.onclick = function(){
                        console.log(reply);
                    }
                }

                //ui라이브러리형식으로 구성하기
                // for (let item of itemList) {
                //     const row = document.createElement('tr');
                //     row.innerHTML = `<th>` + item.tdData1 + `</th>
                //                           <td>` + item.tdData2 + `</td>
                //                           <td>` + item.tdData3 + `</td>`
                //     parent.appendChild(row);
                    
                //     row.onclick = function(){
                //         item.rowEvent(item);
                //     }
                // }
               
            }
        </script>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
</body>
</html>