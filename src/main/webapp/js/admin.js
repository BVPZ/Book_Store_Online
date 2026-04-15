
const modal = document.getElementById("userModal");

    // Hàm mở cho nút "Thêm người dùng"
function openAddModal() {
    document.getElementById("modalTitle").innerText = "Thêm người dùng mới";
    document.getElementById("action").value = "add";

    const form = document.querySelector("#userModal form");
    if(form) form.reset();

    document.getElementById("authFields").style.display = "block";
    modal.style.display = "block";
}

    // Hàm mở cho nút "Chỉnh sửa" - Nhận dữ liệu từ JSTL truyền sang
function openEditModal(id, username, fullName, email, address, phoneNumber) {
    document.getElementById("modalTitle").innerText = "Chỉnh sửa người dùng";
    document.getElementById("action").value = "update";

    // Gán giá trị vào các ô input
    document.getElementById("userId").value = id;
    document.getElementById("fullName").value = fullName;
    document.getElementById("email").value = email;
    document.getElementById("address").value = address;
    document.getElementById("phoneNumber").value = phoneNumber;

    document.getElementById("authFields").style.display = "none";

    modal.style.display = "block";
}

function closeModal() {
    modal.style.display = "none";
}

function showTab(tab) {
    document.querySelectorAll(".tab-content").forEach(el => el.style.display = "none");
    document.getElementById(tab).style.display = "block";

    document.querySelectorAll(".nav-item-custom").forEach(el => el.classList.remove("active"));
    event.target.classList.add("active");
}
const ctx1 = document.getElementById('revenueChart');

new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: ['T1', 'T2', 'T3', 'T4'],
            datasets: [
                {
                    label: 'Doanh thu',
                    data: [12000000, 15000000, 18000000, 10000000]
                }
            ]
        },
        options: {
            maintainAspectRatio: false
        }
    });
const ctx2 = document.getElementById('orderChart');

new Chart(ctx2, {
    type: 'line',
    data: {
    labels: ['T1', 'T2', 'T3', 'T4'],
    datasets: [{
    label: 'Đơn hàng',
    data: [150, 180, 240, 130]
}]
},
    options: {
    maintainAspectRatio: false
}
});

const ctx = document.getElementById('categoryChart');

new Chart(ctx, {
    type: 'pie',
    data: {
    labels: ['Văn học', 'Kinh doanh', 'Khoa học', 'Giả tưởng', 'Khác'],
    datasets: [{
    data: [35, 25, 20, 15, 5],
    backgroundColor: [
    '#10b981',
    '#3b82f6',
    '#f59e0b',
    '#8b5cf6',
    '#9ca3af'
    ]
}]
},
    options: {
    maintainAspectRatio: false,
    plugins: {
    legend: {
    display: false // ẩn legend nếu bạn muốn giống hình
},
    datalabels: {
    color: function(context) {
    return context.dataset.backgroundColor[context.dataIndex];
},
    formatter: function(value, context) {
    let label = context.chart.data.labels[context.dataIndex];
    return label + ': ' + value + '%';
},
    font: {
    weight: 'bold',
    size: 14
},
    anchor: 'end',
    align: 'end',
    offset: 10
}
}
},
    plugins: [ChartDataLabels]
});
