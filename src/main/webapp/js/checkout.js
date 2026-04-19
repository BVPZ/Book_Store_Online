document.addEventListener('DOMContentLoaded', function() {
    const paymentMethods = document.querySelectorAll('input[name="paymentMethod"]');
    const noteText = document.querySelector('#payment-note span');

    if (paymentMethods && noteText) {
        paymentMethods.forEach((input) => {
            input.addEventListener('change', function() {
                switch (this.value) {
                    case 'MOMO':
                        noteText.innerText = 'Lưu ý: Bạn sẽ được chuyển đến trang thanh toán của MoMo.';
                        break;
                    case 'ZALOPAY':
                        noteText.innerText = 'Lưu ý: Bạn sẽ được chuyển đến trang thanh toán của ZaloPay.';
                        break;
                    case 'COD':
                        noteText.innerText = 'Lưu ý: Bạn sẽ được kiểm tra hàng trước khi thanh toán.';
                        break;
                    case 'QRCODE':
                        noteText.innerText = 'Lưu ý: Bạn sẽ quét mã QR ngân hàng để thanh toán.';
                        break;
                    default:
                        noteText.innerText = 'Lưu ý: Vui lòng chọn phương thức thanh toán.';
                }
            });
        });
    }
});
document.addEventListener('DOMContentLoaded', function() {
    const voucherSelect = document.getElementById("voucherSelect");
    const discountText = document.getElementById("discountAmount");
    const totalText = document.getElementById("finalTotal");
    const finalInput = document.getElementById("finalPriceInput");

    // lấy total từ input hidden hoặc từ server
    let total = parseFloat(finalInput.value);

    function formatVND(number){
        return number.toLocaleString('vi-VN') + "₫";
    }

    if(voucherSelect){
        voucherSelect.addEventListener("change", function(){
            let percent = parseInt(this.value);

            let discount = total * percent / 100;
            let finalPrice = total - discount;

            // update UI
            discountText.innerText = formatVND(discount);
            totalText.innerText = formatVND(finalPrice);

            // update hidden để gửi về servlet
            finalInput.value = finalPrice;
        });
    }
});
voucherSelect.addEventListener("change", function(){
    let percent = parseInt(this.value);

    // chưa đăng nhập mà chọn voucher
    if(!isLoggedIn && percent > 0){
        alert("Voucher chỉ áp dụng cho người dùng đã đăng nhập");

        this.value = "0"; // reset về không dùng

        discountText.innerText = "0₫";
        totalText.childNodes[0].nodeValue = formatVND(total);
        finalInput.value = total;

        return;
    }

    let discount = total * percent / 100;
    let finalPrice = total - discount;

    discountText.innerText = formatVND(discount);
    totalText.childNodes[0].nodeValue = formatVND(finalPrice);
    finalInput.value = finalPrice;
});