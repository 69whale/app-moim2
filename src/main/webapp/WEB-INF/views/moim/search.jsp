<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>M.O.I.M</title>
<link rel="stylesheet" href="/resource/css/style.css?${millis }" />
</head>
<body>
	<div class="root">
		<%-- 최상단 / 로그인 상태 출력 뷰--%>
		<%@ include file="/WEB-INF/views/common/top.jsp"%>
		<c:set var="joinedCate" value="${fn:join(paramValues.cate, '&') }" />
		<div style="flex: 1; margin-top: 20px; width: 100%;" class="block">
			<%-- 검색창 렌더링 영역 --%>
			<form class="block-row" style="align-items: center; gap: 5px;"
				action="/moim/search">
				<div style="flex: 1" class="block-row">
					<c:forTokens items="스포츠,문화,학습,봉사,사교,게임,기타" delims="," var="one"
						varStatus="vs">
						<div>
							<input name="cate" type="checkbox" value="${one }"
								id="cate${vs.count }"
								${fn:contains(joinedCate, one ) ? 'checked' :'' } /><label
								for="cate${vs.count }">${one }</label>
						</div>
					</c:forTokens>
				</div>
				<div style="" class="block">
					<input type="text" style="flex: 1; width: 200px" />
				</div>
				<div style="" class="block">
					<button style="flex: 1; padding: 4px 8px">검색</button>
				</div>
			</form>
			<%-- 모임 정보 렌더링 영역 --%>
			<div class="block-row" style="margin-top: 10px;">
				<c:forEach items="${list }" var="moim">
					<div class="moim-detail-card block"
						onclick="location.href='/moim/detail?id=${moim.id}'">
						<div>
							<span style="color: hotpink">[ ${moim.type eq 'public' ? '공개':'비공개' }
								/ ${moim.cate }]</span>&nbsp;
							<c:choose>
								<c:when test="${fn:length(moim.event) gt 16}">
									${fn:substring(moim.event, 0,16) }...
								</c:when>
								<c:otherwise>
									${moim.event }
								</c:otherwise>
							</c:choose>
							(${moim.currentPerson }/${moim.maxPerson })
						</div>
						<div style="text-align: left">
							# 일정 : <span><fmt:formatDate value="${moim.beginDate }"
									pattern="yyyy.MM.dd HH:mm" /></span> ~ <span><fmt:formatDate
									value="${moim.endDate }" pattern="HH:mm" /></span>

						</div>
						<div style="text-align: left">
							# 회비 : <span> <fmt:formatNumber value="${moim.finalCost }"
									pattern="#,###" />
							</span>
						</div>
						<div style="text-align: left; overflow: hidden">
							# 소개 : <span>${moim.description }</span>
						</div>
					</div>
				</c:forEach>
			</div>
			<%-- 페이지 링크 뷰 영역 --%>
			<c:set var="currentPage" value="${empty param.page ? 1: param.page }"/>
			<div>
				<c:if test="${existPrev }">
					<c:url value="/moim/search" var="target">
						<c:forEach items="${paramValues.cate }" var="c">
							<c:param name="cate" value="${c }" />
						</c:forEach>
						<c:param name="page" value="${start-1 }" />
					</c:url>
					<a href="${target}">←</a>
				</c:if>
				<c:forEach var="p" begin="${start }" end="${last }">
					<c:url value="/moim/search" var="target">
						<c:forEach items="${paramValues.cate }" var="c">
							<c:param name="cate" value="${c }" />
						</c:forEach>
						<c:param name="page" value="${p }" />
					</c:url>
					<c:choose>
						<c:when test="${p eq currentPage }">
							<b style="color: green">${p }</b>
						</c:when>
						<c:otherwise>
							<a href="${target }">${p }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${existNext }">
					<c:url value="/moim/search" var="target">
						<c:forEach items="${paramValues.cate }" var="c">
							<c:param name="cate" value="${c }" />
						</c:forEach>
						<c:param name="page" value="${last + 1 }" />
					</c:url>
					<a href="${target }">→</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>