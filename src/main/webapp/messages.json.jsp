<%@ page language="java" pageEncoding="utf-8" contentType="application/json; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

[
<c:forEach items="${requestScope.messages}" var="message" varStatus="status">
    <c:if test="${status.index != 0}">,</c:if>
    {
        "from": "${message.from}",
        "content": "${message.content}"
    }
</c:forEach>
]
