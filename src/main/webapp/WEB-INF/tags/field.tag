<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="errorMap" required="true" type="java.util.Map" %>

<tr>
    <td>${label}:</td>
    <td>
        <input name="${name}" value="${param[name]}"/>
        <c:if test="${not empty errorMap[name]}">
            <span class="message-red">${errorMap[name]}</span>
        </c:if>
    </td>
</tr>