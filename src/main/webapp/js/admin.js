
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
            labels: window.dashboardData.revenueLabels.length > 0 ? window.dashboardData.revenueLabels : ['Chưa có dữ liệu'],
            datasets: [
                {
                    label: 'Doanh thu',
                    data: window.dashboardData.revenues.length > 0 ? window.dashboardData.revenues : [0], // DYNAMIC DATA
                    backgroundColor: '#8bc34a'
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
    labels: window.dashboardData.orderLabels.length > 0 ? window.dashboardData.orderLabels : ['Chưa có dữ liệu'],
    datasets: [{
        label: 'Đơn hàng',
        data: window.dashboardData.orders.length > 0 ? window.dashboardData.orders : [0], // DYNAMIC DATA
        borderColor: '#03a9f4',
        backgroundColor: 'rgba(3, 169, 244, 0.2)',
        fill: true,
        tension: 0.3
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
    labels: window.dashboardData.catLabels.length > 0 ? window.dashboardData.catLabels : ['Chưa có dữ liệu'],
    datasets: [{
        data: window.dashboardData.catValues.length > 0 ? window.dashboardData.catValues : [1],
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
            if (label === 'Chưa có dữ liệu') return '0%';
            // Tính phần trăm
            let sum = context.chart.data.datasets[0].data.reduce((a, b) => a + b, 0);
            let percentage = sum > 0 ? ((value * 100) / sum).toFixed(1) + '%' : '0%';
            return label + ': ' + percentage;
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
