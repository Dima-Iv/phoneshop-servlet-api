<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <div class="container"></div>

    <h1 class="mt-5 mb-5">Catalog page</h1>

    <li class="list-group">
        <ul class="list-group-item">
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="query"
                       value="${param.query}">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            Sort by description
            <tags:sort query="${param.query}" sort="description" order="${order}"></tags:sort>
            Sort by price
            <tags:sort query="${param.query}" sort="price" order="${order}"></tags:sort>
        </ul>
    </li>

    <div class="row">
        <div class="col-11">
            <div class="row">
                <c:forEach var="product" items="${products}">
                    <div class="card col-3">
                        <tr>
                            <td>
                                <img class="card-img-top"
                                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                            </td>

                            <div class="card-body">
                                <a class="card-title" href="products/${product.id}">${product.description}</a>
                                <td class="card-text">
                                    <a href="products/prices/${product.id}">
                                        <fmt:formatNumber value="${product.price}" type="currency"
                                                          currencySymbol="${product.currency.symbol}"/>
                                    </a>
                                </td>
                            </div>

                        </tr>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <div class="row">
        <c:forEach var="product" items="${lastViewed}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <div>
                    <td>
                        <a href="products/${product.id}">${product.description}</a>
                    </td>
                    <br>
                    <td class="price">
                        <a href="products/prices/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                </div>

            </tr>
        </c:forEach>
    </div>
</tags:master>