/**
 * Footer functionality - Back to top, newsletter subscription
 */

document.addEventListener('DOMContentLoaded', function () {
    initBackToTopButton();
    initNewsletterSubscription();
    initSmoothScrolling();
});

/**
 * Initialize Back to Top button
 */
function initBackToTopButton() {
    const backToTopBtn = document.querySelector('.back-to-top');
    
    if (!backToTopBtn) return;
    
    // Show/hide button on scroll
    window.addEventListener('scroll', function () {
        if (window.scrollY > 300) {
            backToTopBtn.classList.add('show');
        } else {
            backToTopBtn.classList.remove('show');
        }
    });
    
    // Scroll to top on click
    backToTopBtn.addEventListener('click', function (e) {
        e.preventDefault();
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });
}

/**
 * Initialize newsletter subscription
 */
function initNewsletterSubscription() {
    const newsletterForm = document.querySelector('.newsletter-form');
    if (!newsletterForm) return;
    
    const emailInput = newsletterForm.querySelector('input[type="email"]');
    const submitBtn = newsletterForm.querySelector('.newsletter-btn');
    
    if (emailInput && submitBtn) {
        emailInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                submitNewsletter(emailInput.value);
            }
        });
        
        submitBtn.addEventListener('click', function (e) {
            e.preventDefault();
            submitNewsletter(emailInput.value);
        });
    }
}

/**
 * Submit newsletter subscription
 * @param {string} email - Email address
 */
function submitNewsletter(email) {
    if (!email || !isValidEmail(email)) {
        showNotification('Vui lòng nhập email hợp lệ', 'warning');
        return;
    }
    
    // Send subscription request
    fetch('newsletter/subscribe', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showNotification('Cảm ơn, bạn đã đăng ký nhận bản tin!', 'success');
            document.querySelector('.newsletter-form input').value = '';
        } else {
            showNotification(data.message || 'Đăng ký thất bại, thử lại sau', 'error');
        }
    })
    .catch(error => {
        console.error('Newsletter error:', error);
        showNotification('Lỗi kết nối, thử lại sau', 'error');
    });
}

/**
 * Validate email format
 * @param {string} email - Email to validate
 * @returns {boolean} True if valid
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Initialize smooth scrolling for footer links
 */
function initSmoothScrolling() {
    document.querySelectorAll('.footer-links a[href^="#"]').forEach(link => {
        link.addEventListener('click', function (e) {
            const href = this.getAttribute('href');
            if (href !== '#') {
                e.preventDefault();
                const target = document.querySelector(href);
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            }
        });
    });
}

/**
 * Show notification toast (reuse from home.js)
 * @param {string} message - Message to display
 * @param {string} type - Notification type
 */
function showNotification(message, type = 'info') {
    const alertClass = {
        'success': 'alert-success',
        'error': 'alert-danger',
        'info': 'alert-info',
        'warning': 'alert-warning'
    };
    
    let container = document.getElementById('notification-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'notification-container';
        container.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
            width: 350px;
            max-width: 90vw;
        `;
        document.body.appendChild(container);
    }
    
    const alert = document.createElement('div');
    alert.className = `alert ${alertClass[type] || 'alert-info'} alert-dismissible fade show`;
    alert.role = 'alert';
    alert.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    container.appendChild(alert);
    
    setTimeout(() => {
        alert.remove();
    }, 4000);
}
