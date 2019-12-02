<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <c:if test="${not empty error}">
        <p class="error" style="color: red">
                ${error}
        </p>
    </c:if>
    <c:if test="${not empty param.message}">
        <p style="color: green">
                ${param.message}
        </p>
    </c:if>
    <form method="Post">
        <table>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td>Price</td>
                <td>Quantity</td>
                <td>Action</td>
            </tr>
            <p>
                <c:forEach var="item" items="${cart.cartItemList}" varStatus="status">
                    <c:set var="product" value="${item.product}"/>
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>
                        <a href="products/${product.id}">${product.description}</a>
                    </td>
                    <td class="price">
                        <a href="products/prices/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty errorMap[product] ? paramValues.quantity[status.index] : item.quantity}"/>
                        <c:if test="${not empty errorMap[product]}"><p style="color: red">${errorMap[product]}</p></c:if>
            <input type="hidden" name="productId" value="${product.id}"/>
            </td>
            <td>
                <button form="deleteCartItem" name="productId" value="${product.id}">Delete</button>
            </td>
            </tr>
            </c:forEach>
            </p>
        </table>
        <button>Update</button>
    </form>
    <form id="deleteCartItem" action="${pageContext.servletContext.contextPath}/cart/deleteCartItem"
          method="post"></form>
    <form action="${pageContext.servletContext.contextPath}/checkout" method="get">
        <button>Checkout</button>
    </form>
    <p>
        <c:forEach var="product" items="${lastViewed}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="products/prices/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </p>
</tags:master>