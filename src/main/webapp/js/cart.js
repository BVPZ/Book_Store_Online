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
    checks.forEach(cb => cb.checked = this.checked);
    updateCart();
});

// quantity
document.querySelectorAll('.plus').forEach(btn => {
    btn.onclick = () => {
        let input = btn.parentElement.querySelector('.qty-input');
        input.value++;
        updateCart();
    };
});

document.querySelectorAll('.minus').forEach(btn => {
    btn.onclick = () => {
        let input = btn.parentElement.querySelector('.qty-input');
        if (input.value > 1) input.value--;
        updateCart();
    };
});