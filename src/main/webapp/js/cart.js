const checks = document.querySelectorAll('.item-check');
const checkAll = document.getElementById('checkAll');

function updateCart() {
    let total = 0;
    let count = 0;

    checks.forEach(cb => {
        const item = cb.closest('.cart-item');
        const qty = item.querySelector('.qty-input').value;

        if (cb.checked) {
            item.classList.add('active');

            total += cb.dataset.price * qty;
            count++;
        } else {
            item.classList.remove('active');
        }
    });

    document.getElementById('subtotal').innerText = total.toLocaleString() + "đ";
    document.getElementById('totalPrice').innerText = total.toLocaleString() + "đ";
    document.getElementById('selectedCount').innerText = count + " sản phẩm";
}

// checkbox
checks.forEach(cb => cb.addEventListener('change', updateCart));

checkAll.addEventListener('change', function() {
    checks.forEach(cb => {
        if (!cb.disabled) {
            cb.checked = this.checked;
        }
    });
    updateCart();
});

// quantity
document.querySelectorAll('.plus').forEach(btn => {
    btn.onclick = () => {
        let item = btn.closest('.cart-item');
        let input = item.querySelector('.qty-input');
        let stock = parseInt(input.getAttribute("max"));
        let value = parseInt(input.value);

        // lấy cartId từ checkbox
        let cartId = item.querySelector('.item-check').dataset.cartId;
        let bookId = item.querySelector('.item-check').dataset.cartBookId;
        if(value >= stock){
            alert("Số lượng sản phẩm vượt quá số hàng còn lại");
            return;
        }
        let newQty = value + 1;
        //  GỌI FETCH (update DB trước)
        fetch("update-cart-item", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "cartItemId=" + cartId + "&quantity=" + newQty + "&bookId=" + bookId
        })
            .then(res => res.text())
            .then(data => {
                console.log("Server:", data);

                // chỉ update UI khi server OK
                input.value = newQty;
                updateCart();
            })
            .catch(err => {
                console.error("Lỗi:", err);
            });
        console.log(stock);
        console.log(value);
    };
});
document.querySelectorAll('.minus').forEach(btn => {
    btn.onclick = () => {
        let item = btn.closest('.cart-item');
        let input = item.querySelector('.qty-input');
        let value = parseInt(input.value);

        //  lấy cartId
        let cartId = item.querySelector('.item-check').dataset.cartId;
        let bookId = item.querySelector('.item-check').dataset.cartBookId;
        //  không cho xuống dưới 1
        if (value <= 1){
            return;
        }

        let newQty = value - 1;

        // gọi API update DB
        fetch("update-cart-item", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "cartItemId=" + cartId + "&quantity=" + newQty + "&bookId=" + bookId
        })
            .then(res => res.text())
            .then(data => {
                console.log("Server:", data);

                //  update UI sau khi DB OK
                input.value = newQty;
                updateCart();
            })
            .catch(err => {
                console.error("Lỗi:", err);
            });
    };
});
const checks_select = document.querySelectorAll('.item-check');
const checkoutBtn = document.getElementById('checkoutBtn');

function toggleButton(){
    const checked = document.querySelectorAll('.item-check:checked');
    checkoutBtn.disabled = checked.length === 0;
}

checks_select.forEach(cb => cb.addEventListener('change', toggleButton));