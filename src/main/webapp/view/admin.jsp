<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homestyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_style.css">
</head>
<body>

<div class="topbar d-flex justify-content-between">
    <div class="col-md-3 d-flex align-items-center">
        <div class="bg-success text-white rounded p-2 me-2">
            <i class="bi bi-book"></i>
    </div>
        <div>
            <div class="logo-text">Nhà Sách Tri Thức</div>
            <div class="logo-sub">Đọc sách - Mở tương lai</div>
        </div>
    </div>
    <h4>Admin Dashboard</h4>
    <div>
        Xin chào, Admin
        <a href="logout" class="btn btn-danger btn-sm">Đăng xuất</a>
    </div>

</div>

<div class="container mt-4">

    <!-- TAB -->
    <div class="nav-tabs-custom mb-4">
        <div class="nav-item-custom active" onclick="showTab('dashboard', this)">Dashboard</div>
        <div class="nav-item-custom" onclick="showTab('users', this)">Người dùng</div>
        <div class="nav-item-custom" onclick="showTab('books', this)">Sách</div>
        <div class="nav-item-custom" onclick="showTab('orders', this)">Đơn hàng</div>
    </div>

    <!-- DASHBOARD -->
    <div id="dashboard" class="tab-content">
        <div class="row">
            <div class="col-md-3">
                <div class="card-box">
                    <h6>Doanh thu</h6>
                    <h3 class="text-success">${cardTotalRevenue != null ? cardTotalRevenue : '0'}</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Đơn hàng</h6>
                    <h3 class="text-primary">${cardTotalOrders != null ? cardTotalOrders : '0'}</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Người dùng</h6>
                    <h3 class="text-purple">${cardTotalUsers != null ? cardTotalUsers : '0'}</h3>
                </div>
            </div>

            <div class="col-md-3">
                <div class="card-box">
                    <h6>Sản phẩm</h6>
                    <h3 class="text-warning">${cardTotalBooks != null ? cardTotalBooks : '0'}</h3>
                </div>
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-md-6" >

                <canvas id="revenueChart" height="300"></canvas>
            </div>

            <div class="col-md-6">
                <canvas id="categoryChart" height="80"></canvas>
            </div>
        </div>

        <div class="mt-4">
            <canvas id="orderChart" width="400"></canvas>
        </div>
    </div>

    <!-- USERS -->
    <div id="users" class="tab-content" style="display:none">
        <div class="card-box">
            <h5>Quản lý người dùng</h5>
            <table class="table">
                <tr>
                    <th>ID</th><th>UserName</th><th>FullName</th><th>Email</th>

                    <th>Xóa người dùng</th>
                   <th>
                       <button class="btn-add" onclick="openAddModal()">Thêm người dùng</button>
                   </th>
                </tr>

                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.userId}</td>
                        <td>${u.username}</td>
                        <td>${u.fullName}</td>
                        <td>${u.email}</td>
                        <td>
                            <td>
                                <button type="button" class="btn btn-sm btn-primary"
                                        onclick="openEditModal('${u.userId}', '${u.username}', '${u.fullName}', '${u.email}', '${u.address}' , '${u.phone}')">
                                    Chỉnh sửa
                                </button>
                            </td>
                        </td>
                        <td>
                            <a href="delete-user?id=${u.userId}">
                                <button type="button" class="btn-delete" style="border: solid ;background-color: red">
                                    Xóa
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>

    <!-- BOOKS -->
   <div id="books" class="tab-content" style="display:none">
           <c:catch var="bookError">
               <div class="card-box">
                   <div class="d-flex justify-content-between align-items-center mb-4">
                       <h4 class="m-0 font-weight-bold">Quản lý sách</h4>
                      <div class="d-flex gap-2 flex-wrap justify-content-end">
                         <form action="${pageContext.request.contextPath}/admin" method="get" class="m-0">
                                 <input type="hidden" name="showTab" value="books">

                                 <div class="input-group" style="width: 250px;">
                                     <button type="submit" class="input-group-text bg-light border-0" style="cursor: pointer;">
                                         <i class="bi bi-search"></i>
                                     </button>
                                     <input type="text" name="searchQuery" value="${param.searchQuery}" class="form-control bg-light border-0" placeholder="Tìm kiếm sách...">
                                 </div>
                         </form>

                          <button class="btn btn-outline-primary text-nowrap d-flex align-items-center gap-2" onclick="openAddAuthorModal()">
                              <i class="bi bi-person-plus"></i> Thêm tác giả
                          </button>

                          <button class="btn btn-outline-info text-nowrap d-flex align-items-center gap-2" onclick="openAddCategoryModal()">
                              <i class="bi bi-tags"></i> Thêm thể loại
                          </button>

                          <button class="btn btn-success text-nowrap d-flex align-items-center gap-2" onclick="openAddBookModal()">
                              <i class="bi bi-plus-lg"></i> Thêm sách
                          </button>
                      </div>
                   </div>

                   <div class="table-responsive">
                       <table class="table table-hover align-middle">
                           <thead class="table-light">
                               <tr>
                                   <th>Hình ảnh</th>
                                   <th>Tên sách</th>
                                   <th>Tác giả</th>
                                   <th>Danh mục</th>
                                   <th>Giá</th>
                                   <!-- <th>Đánh giá</th> -->
                                   <th class="text-center">Thao tác</th>
                               </tr>
                           </thead>
                           <tbody>
                               <c:forEach var="b" items="${books}">
                                   <tr>
                                       <td>
                                           <c:choose>
                                               <%-- Link mạng bắt đầu bằng chữ http --%>
                                               <c:when test="${fn:startsWith(b.imageUrl, 'http')}">
                                                   <img src="${b.imageUrl}" alt="Book" class="rounded shadow-sm" style="width: 40px; height: 60px; object-fit: cover;">
                                               </c:when>

                                               <%-- Nếu là ảnh tự upload --%>
                                               <c:otherwise>
                                                   <img src="${pageContext.request.contextPath}/${b.imageUrl}" alt="Book" class="rounded shadow-sm" style="width: 40px; height: 60px; object-fit: cover;">
                                               </c:otherwise>
                                           </c:choose>
                                       </td>
                                       <td class="fw-bold text-dark">${b.title}</td>
                                       <td class="text-muted">${b.authorName}</td>
                                       <td>
                                           <span class="badge bg-light text-secondary border">
                                               ${not empty b.genres ? b.genres[0] : 'Chưa phân loại'}
                                           </span>
                                       </td>
                                       <td class="fw-bold">${b.price}₫</td>
                                       <!--
                                       <td>
                                           <span class="text-warning"><i class="bi bi-star-fill"></i> ${b.rating}</span>
                                           <span class="text-muted small">(${b.reviewCount})</span>
                                       </td>
                                       -->
                                       <td class="text-center">
                                           <div class="d-flex justify-content-center gap-2">

                                               <button class="btn btn-sm btn-light text-primary" title="Sửa"
                                                       onclick="openEditBookModal('${b.bookId}', `${b.title}`, `${b.authorName}`, `${not empty b.genres ? b.genres[0] : ''}`, `${b.price}`, `${b.stock}`, `${b.description}`, `${b.imageUrl}`)">
                                                   <i class="bi bi-pencil-square"></i>
                                               </button>

                                               <a href="${pageContext.request.contextPath}/delete-book?id=${b.bookId}" class="btn btn-sm btn-light text-danger" title="Xóa" onclick="return confirm('Bạn có chắc chắn muốn xóa sách này?')">
                                                   <i class="bi bi-trash"></i>
                                               </a>
                                           </div>
                                       </td>
                                   </tr>
                               </c:forEach>
                           </tbody>
                       </table>
                   </div>
               </div>
           </c:catch>

           <c:if test="${not empty bookError}">
               <div class="alert alert-danger mt-3">
                   <strong>Hệ thống phát hiện lỗi Java (Exception):</strong> ${bookError.message}
                   <hr>
                   <p class="mb-0"><i>Gợi ý: Hãy kiểm tra file <b>Book.java</b>, khả năng cao bạn đang thiếu hàm Getter cho một biến nào đó (như getStock, getDescription, getImage...)</i></p>
               </div>
           </c:if>
       </div>

    <!-- ORDERS -->
    <div id="orders" class="tab-content" style="display:none">
        <div class="card-box">
            <h5>Quản lý đơn hàng</h5>
            <table class="table">
                <tr>
                    <th>Mã đơn hàng</th><th>Thời gian</th>
                    <th>Tổng</th><th>Thông tin</th> <th>Trạng thái</th>
                </tr>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.orderId}</td>
                        <td>${o.orderDate}</td>
                        <td>${o.totalPrice}</td>

                        <td><a href = "order-detail?orderId=${o.orderId}"><button type="button">Thông tin đơn hàng</button></a></td>
                        <td>
                            <form action="update-orderStatus" method="post">

                                <!-- hidden orderId -->
                                <input type="hidden" name="orderId" value="${o.orderId}" />
                                <input type="hidden" name="action" value="adminAction">
                                <select name="status"
                                        class="status-select
                        ${o.orderStatus == 'Pending' ? 'yellow' : ''}
                        ${o.orderStatus == 'Processing' ? 'light-green' : ''}
                        ${o.orderStatus == 'Completed' ? 'dark-green' : ''}
                        ${o.orderStatus == 'Cancelled' ? 'red' : ''}"

                                    ${o.orderStatus == 'Cancelled' ? 'disabled' : ''}
                                        onchange="handleStatusChange(this)">
                                    <option value="Pending" ${o.orderStatus == 'Pending' ? 'selected' : ''}>Chờ xác nhận</option>
                                    <option value="Processing" ${o.orderStatus == 'Processing' ? 'selected' : ''}>Đang xử lý</option>
                                    <option value="Completed" ${o.orderStatus == 'Completed' ? 'selected' : ''}>Hoàn thành</option>
                                    <option value="Cancelled" ${o.orderStatus == 'Cancelled' ? 'selected' : ''}>Hủy</option>

                                </select>

                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</div>
<div id="userModal" class="modal" style="display: none;">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle">Thông tin người dùng</h2>
        <form action="edit-user" method="post">
            <input type="hidden" id="action" name="action" value="add">
            <input type="hidden" id="userId" name="id">
            <div id="authFields" style="display: none;">
                <div class="form-group">
                    <label>Username:</label>
                    <input type="text" id="userName" name="userName">
                </div>
                <div class="form-group">
                    <label>Password:</label>
                    <input type="password" id="password" name="password">
                </div>
            </div>
            <div class="form-group">
                <label>FullName:</label>
                <input type="text" id="fullName" name="fullName">
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" id="email" name="email">
            </div>
            <div class="form-group">
                <label>Phone number:</label>
                <input type="text" id="phoneNumber" name="phoneNumber">
            </div>
            <div class="form-group">
                <label>Address:</label>
                <input type="text" id="address" name="address">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn-save">Xác nhận</button>
                <button type="button" onclick="closeModal()">Hủy</button>
            </div>
        </form>
    </div>
</div>

<div id="bookModal" class="modal" style="display: none; background-color: rgba(0,0,0,0.5); position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: 1050; align-items: center; justify-content: center;">
    <div class="modal-content bg-white p-4 rounded shadow-lg" style="width: 100%; max-width: 600px; max-height: 90vh; overflow-y: auto;">

        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
            <h4 id="bookModalTitle" class="m-0 font-weight-bold">Thêm sách mới</h4>
            <span class="close-btn fs-4 text-secondary" style="cursor: pointer;" onclick="closeBookModal()">&times;</span>
        </div>

       <form action="${pageContext.request.contextPath}/save-book" method="post" enctype="multipart/form-data">
            <input type="hidden" id="bookAction" name="action" value="add">
            <input type="hidden" id="bookId" name="bookId">

            <div class="row">
                <div class="col-md-12 mb-3">
                    <label class="form-label fw-bold">Tên sách:</label>
                    <input type="text" class="form-control" id="bookTitle" name="title" required>
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label fw-bold">Tác giả:</label>
                    <input list="authorList" class="form-control" id="bookAuthor" name="authorName" placeholder="Chọn hoặc nhập mới..." required>
                    <datalist id="authorList">
                        <c:forEach var="author" items="${allAuthors}">
                            <option value="${author.authorName}"></option>
                        </c:forEach>
                    </datalist>
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label fw-bold">Danh mục (Thể loại):</label>
                    <input list="genreList" class="form-control" id="bookGenre" name="genre" placeholder="Chọn hoặc nhập mới..." required>
                    <datalist id="genreList">
                        <c:forEach var="genre" items="${allGenres}">
                            <option value="${genre.categoryName}"></option>
                        </c:forEach>
                    </datalist>
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label fw-bold">Giá (VNĐ):</label>
                    <input type="number" class="form-control" id="bookPrice" name="price" required min="0" step="1000">
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label fw-bold">Số lượng (Stock):</label>
                    <input type="number" class="form-control" id="bookStock" name="stock" required min="0">
                </div>

                <div class="col-md-12 mb-3">
                    <label class="form-label fw-bold">Mô tả sách:</label>
                    <textarea class="form-control" id="bookDescription" name="description" rows="3"></textarea>
                </div>

                <div class="col-md-12 mb-4">
                    <label class="form-label fw-bold">Hình ảnh sách:</label>
                    <input type="file" class="form-control" id="bookImage" name="imageFile" accept="image/*">
                    <small class="text-muted" id="imageHint">Để trống nếu không muốn thay đổi ảnh (khi sửa).</small>
                </div>
            </div>

            <div class="d-flex justify-content-end gap-2 mt-2 pt-3 border-top">
                <button type="button" class="btn btn-secondary" onclick="closeBookModal()">Hủy</button>
                <button type="submit" class="btn btn-success px-4">Lưu thông tin</button>
            </div>
        </form>
    </div>
</div>

<div id="authorModal" class="modal" style="display: none; background-color: rgba(0,0,0,0.5); position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: 1060; align-items: center; justify-content: center;">
    <div class="modal-content bg-white p-4 rounded shadow-lg" style="width: 100%; max-width: 400px;">
        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
            <h5 class="m-0 font-weight-bold">Thêm tác giả mới</h5>
            <span class="close-btn fs-4 text-secondary" style="cursor: pointer;" onclick="closeAuthorModal()">&times;</span>
        </div>
       <form action="${pageContext.request.contextPath}/save-author" method="post">
            <div class="mb-3">
                <label class="form-label fw-bold">Tên tác giả:</label>
                <input type="text" class="form-control" name="authorName" placeholder="Nhập tên tác giả..." required>
            </div>
            <div class="d-flex justify-content-end gap-2 mt-2 pt-3 border-top">
                <button type="button" class="btn btn-secondary" onclick="closeAuthorModal()">Hủy</button>
                <button type="submit" class="btn btn-primary px-4">Lưu tác giả</button>
            </div>
        </form>
    </div>
</div>

<div id="categoryModal" class="modal" style="display: none; background-color: rgba(0,0,0,0.5); position: fixed; top: 0; left: 0; width: 100%; height: 100%; z-index: 1060; align-items: center; justify-content: center;">
    <div class="modal-content bg-white p-4 rounded shadow-lg" style="width: 100%; max-width: 400px;">
        <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
            <h5 class="m-0 font-weight-bold">Thêm thể loại mới</h5>
            <span class="close-btn fs-4 text-secondary" style="cursor: pointer;" onclick="closeCategoryModal()">&times;</span>
        </div>
       <form action="${pageContext.request.contextPath}/save-category" method="post">
            <div class="mb-3">
                <label class="form-label fw-bold">Tên thể loại:</label>
                <input type="text" class="form-control" name="categoryName" placeholder="Nhập tên thể loại..." required>
            </div>
            <div class="d-flex justify-content-end gap-2 mt-2 pt-3 border-top">
                <button type="button" class="btn btn-secondary" onclick="closeCategoryModal()">Hủy</button>
                <button type="submit" class="btn btn-info text-white px-4">Lưu thể loại</button>
            </div>
        </form>
    </div>
</div>

<c:if test="${not empty status}">
    <script>alert('${status}')</script>
    <c:remove var="status" scope="session"/>
</c:if>

<c:if test="${not empty showTab or not empty param.showTab}">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // 1. Lấy tên tab (ví dụ: 'books')
            const tabId = "${not empty showTab ? showTab : param.showTab}";

            // 2. Tìm thẻ div chứa tab đó
            const tabBtn = document.querySelector(".nav-item-custom[onclick*='" + tabId + "']");

            // 3. Gọi thẳng hàm showTab của bạn thay vì giả lập click
            if (tabBtn && typeof showTab === "function") {
                showTab(tabId, tabBtn);
            }
        });
    </script>
    <c:remove var="showTab" scope="session"/>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
<script>
    window.dashboardData = {
        revenueLabels: ${not empty revenueLabels ? revenueLabels : '[]'},
        revenues: ${not empty revenueData ? revenueData : '[]'},
        orderLabels: ${not empty orderLabels ? orderLabels : '[]'},
        orders: ${not empty orderData ? orderData : '[]'},
        catLabels: ${not empty catLabels ? catLabels : '[]'},
        catValues: ${not empty catValues ? catValues : '[]'}
    };
</script>

<script src="${pageContext.request.contextPath}/js/admin.js?v=<%= System.currentTimeMillis() %>"></script>

</body>
</html>