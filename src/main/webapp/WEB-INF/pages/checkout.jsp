<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <table>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Price</td>
            <td>Quantity</td>
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
                    <td>${item.quantity}</td>
                </tr>
            </c:forEach>
        </p>
    </table>
    <tfoot>
    <tr>
        <td colspan="2">Subtotal cost:</td>
        <td>${order.subtotal}</td>
        <br>
    </tr>
    <tr>
        <td colspan="2">Delivery cost:</td>
        <td>${order.deliveryCost}</td>
        <br>
    </tr>
    <tr>
        <td colspan="2">Total cost:</td>
        <td>${order.totalPrice}</td>
    </tr>
    </tfoot>
    <form action="${pageContext.servletContext.contextPath}/checkout" method="post">
        <table>
            <tags:field label="First name" name="firstname" errorMap="${errorMap}"></tags:field>
            <tags:field label="Last name" name="lastname" errorMap="${errorMap}"></tags:field>
            <tags:field label="Phone" name="phone" errorMap="${errorMap}"></tags:field>
            <tags:field label="Delivery date" name="date" errorMap="${errorMap}"></tags:field>
            <tags:field label="Delivery address" name="address" errorMap="${errorMap}"></tags:field>
            <tr>
                <td>Payment method:</td>
                <td>
                    <input type="radio" name="payment" value="money" checked/>money
                </td>
                <td>
                    <input type="radio" name="payment" value="creditCard"/>credit card
                </td>
            </tr>
        </table>
        <button>To order</button>
    </form>
</tags:master>