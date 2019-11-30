<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<a class="nav-link" href="${pageContext.servletContext.contextPath}/cart">Cart: $ ${cart.totalPrice != null ? cart.totalPrice : 0} <span class="sr-only"></span></a>