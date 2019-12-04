<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="lastViewed" type="java.util.LinkedList" scope="request"/>
<tags:master pageTitle="Product Details">
    <p>
            ${product.description}
    </p>
    <c:if test="${not empty error}">
        <p class="error" style="color: red">
                ${error}
        </p>
    </c:if>
    <p>
        <img class="product-tile"
             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        <br><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/><br>
        Current stock: ${product.stock}
    </p>
    <form method="POST" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
        <button>Add to cart</button>
        <c:if test="${not empty param.message}">
            <p style="color: green">
                    ${param.message}
            </p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="error" style="color: red">
                    ${error}
            </p>
        </c:if>
    </form>

    <form method="post" action="${pageContext.servletContext.contextPath}/products/addComment/${product.id}">
        <tags:field label="Name" name="name" errorMap="${errorMap}"></tags:field><br>
        <tags:field label="Rating" name="rating" errorMap="${errorMap}"></tags:field><br>
        <label>Comment:</label><input name="comment"/>
        <button>Add comment</button>
    </form>

    <table>
        <tr>
            <td>Name</td>
            <td>Comment</td>
        </tr>
        <p>
            <c:forEach var="comm" items="${product.comments}" varStatus="status">
                <tr>
                    <td>
                       ${comm.key}
                    </td>
                    <td>
                        ${comm.value}
                    </td>
                </tr>
            </c:forEach>
        </p>
    </table>

    <p>
        <c:forEach var="product" items="${lastViewed}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/prices/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </p>
</tags:master>