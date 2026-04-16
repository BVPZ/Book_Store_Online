<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<main class="main-layout container mt-4">
    <section class="content-wrapper w-100">
        <div class="product-section">
            <h2 class="section-title mb-4">Kết quả tìm kiếm cho: '${searchKeyword}'</h2>

            <c:if test="${empty searchResults}">
                <p class="text-danger">Không tìm thấy sách nào!</p>
            </c:if>

            <div class="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-4">
                <c:forEach var="book" items="${searchResults}">
                    <div class="col">
                        <div class="product-card">
                            <div class="product-img-wrap">
                                <img src="${book.imageUrl}" class="product-img" alt="Book">
                            </div>
                            <div class="product-info">
                                <a href="book-detail?id=${book.bookId}" class="product-title">${book.title}</a>
                                <span class="product-price">
                                   <fmt:setLocale value="vi_VN"/>
                                   <fmt:formatNumber value="${book.price}" type="currency"/>
                               </span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>