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