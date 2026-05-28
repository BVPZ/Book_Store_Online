// ═══════════════════════════════════════════════════════════
// INITIALIZATION & EVENT LISTENERS
// ═══════════════════════════════════════════════════════════

document.addEventListener("DOMContentLoaded", function () {
    initializeChatbot();
    setupSearchAutocomplete();
    animateOnScroll();
    setupSmoothScroll();
    updateCartCount();
});

// ═══════════════════════════════════════════════════════════
// CHATBOT INITIALIZATION
// ═══════════════════════════════════════════════════════════

function initializeChatbot() {
    const toggleBtn = document.getElementById("chatbot-toggle");
    const chatBox = document.getElementById("chatbot-box");
    const closeBtn = document.getElementById("chatbot-close");
    const chatInput = document.getElementById("chat-input");
    const sendBtn = document.getElementById("chat-send");

    if (toggleBtn && chatBox && closeBtn) {
        // Open chatbot with initialization
        toggleBtn.onclick = async () => {
            chatBox.style.display = "flex";
            toggleBtn.style.display = "none";

            const chat = document.getElementById("chatbot-messages");
            if (chat) {
                chat.innerHTML = `<div class="chatbot-system"><div class="bubble">Đang khởi động chat bot...</div></div>`;
            }
            if (chatInput) chatInput.disabled = true;
            if (sendBtn) sendBtn.disabled = true;

            try {
                const resp = await fetch('chatbot/init');
                const data = await resp.json();
                if (data.status === 'ready' && chat) {
                    chat.innerHTML += `<div class="chatbot-system"><div class="bubble">Chatbot sẵn sàng. Tôi có thể:
                        <ul class="chatbot-features-list">
                          <li>Tìm kiếm sách theo yêu cầu</li>
                          <li>Tìm thông tin sách có sẵn</li>
                          <li>Tìm tác giả, thể loại</li>
                          <li>Tư vấn mua sách</li>
                        </ul></div></div>`;
                }
            } catch (e) {
                if (chat) chat.innerHTML += `<div class="chatbot-system"><div class="bubble">Lỗi khởi động, thử lại sau.</div></div>`;
            } finally {
                if (chatInput) chatInput.disabled = false;
                if (sendBtn) sendBtn.disabled = false;
                if (chatInput) chatInput.focus();
            }
        };

        // Close chatbot
        closeBtn.onclick = () => {
            chatBox.style.display = "none";
            toggleBtn.style.display = "flex";
        };

        // Handle Enter key in chat input
        if (chatInput) {
            chatInput.addEventListener("keypress", function(event) {
                if (event.key === "Enter") {
                    event.preventDefault();
                    sendMessage();
                }
            });
        }

        // Handle send button click
        if (sendBtn) {
            sendBtn.addEventListener("click", sendMessage);
        }
    }
}

// ═══════════════════════════════════════════════════════════
// CHAT MESSAGE HANDLING
// ═══════════════════════════════════════════════════════════

async function sendMessage() {
    const input = document.getElementById("chat-input");
    const sendBtn = document.getElementById("chat-send");
    const chat = document.getElementById("chatbot-messages");

    if (!input || !chat) return;

    const msg = input.value.trim();
    if (msg === "") return;

    input.disabled = true;
    if (sendBtn) sendBtn.disabled = true;

    // Display user message
    chat.innerHTML += `<div class="chatbot-user">
        <div class="bubble">${escapeHtml(msg)}</div>
    </div>`;
    chat.scrollTop = chat.scrollHeight;
    input.value = "";

    // Show loading indicator
    const loadingElement = document.createElement("div");
    loadingElement.className = "chatbot-bot";
    loadingElement.innerHTML = `<div class="bubble">
        <span class="loading-dots">
            <span class="dot">.</span><span class="dot">.</span><span class="dot">.</span>
        </span>
    </div>`;
    chat.appendChild(loadingElement);
    chat.scrollTop = chat.scrollHeight;

    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 120000); // 120 seconds

    try {
        const response = await fetch("chatbot", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" },
            body: `message=${encodeURIComponent(msg)}`,
            signal: controller.signal
        });
        clearTimeout(timeoutId);

        const result = await response.json();
        loadingElement.remove();

        if (!response.ok || result.error) {
            const errorMsg = result.error || response.statusText || "Lỗi không xác định";
            chat.innerHTML += `<div class="chatbot-bot"><div class="bubble error-msg">Xin lỗi, lỗi: ${escapeHtml(errorMsg)}</div></div>`;
        } else {
            chat.innerHTML += `<div class="chatbot-bot"><div class="bubble">${escapeHtml(result.reply)}</div></div>`;
        }
    } catch (error) {
        clearTimeout(timeoutId);
        loadingElement.remove();
        const isTimeout = error.name === "AbortError";
        const errMsg = isTimeout
            ? "Chatbot đang xử lý quá lâu, vui lòng thử lại."
            : "Không thể kết nối tới chatbot.";
        chat.innerHTML += `<div class="chatbot-bot"><div class="bubble error-msg">${errMsg}</div></div>`;
    } finally {
        input.disabled = false;
        if (sendBtn) sendBtn.disabled = false;
        input.focus();
        chat.scrollTop = chat.scrollHeight;
    }
}

// ═══════════════════════════════════════════════════════════
// PRODUCT INTERACTIONS
// ═══════════════════════════════════════════════════════════

/**
 * Add book to shopping cart
 * @param {number} bookId - The book ID to add
 */
function addToCart(bookId) {
    if (!bookId) return;
    
    console.log(`[addToCart] Adding book ID: ${bookId}`);
    
    // Send to cart endpoint
    fetch(`addtocart?bookId=${bookId}&ajax=true`, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            showNotification(`Sách đã được thêm vào giỏ hàng!`, 'success');
            updateCartCount();
        } else {
            showNotification(data.message || 'Không thể thêm vào giỏ hàng', 'error');
        }
    })
    .catch(error => {
        console.error('Error adding to cart:', error);
        showNotification('Lỗi kết nối, thử lại sau', 'error');
    });
}

/**
 * Add/Remove book from wishlist
 * @param {number} bookId - The book ID to toggle
 */
function addToWishlist(bookId) {
    if (!bookId) return;
    
    console.log(`[addToWishlist] Toggling wishlist for book ID: ${bookId}`);
    
    const button = event?.target?.closest('.btn-wishlist');
    if (!button) return;
    
    fetch(`wishlist/toggle?bookId=${bookId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Toggle heart icon
            const icon = button.querySelector('i');
            if (data.added) {
                button.classList.add('in-wishlist');
                icon?.classList.remove('bi-heart');
                icon?.classList.add('bi-heart-fill');
                showNotification('Sách đã được thêm vào danh sách yêu thích', 'success');
            } else {
                button.classList.remove('in-wishlist');
                icon?.classList.remove('bi-heart-fill');
                icon?.classList.add('bi-heart');
                showNotification('Sách đã được xóa khỏi danh sách yêu thích', 'info');
            }
        } else {
            showNotification(data.message || 'Không thể cập nhật wishlist', 'error');
        }
    })
    .catch(error => {
        console.error('Error toggling wishlist:', error);
        showNotification('Lỗi kết nối, thử lại sau', 'error');
    });
}

/**
 * Load and display quick view modal for a book
 * @param {number} bookId - The book ID to display
 */
function loadQuickView(bookId) {
    if (!bookId) return;
    
    console.log(`[loadQuickView] Loading book ID: ${bookId}`);
    
    // Fetch book details
    fetch(`book-detail?id=${bookId}`, {
        method: 'GET'
    })
    .then(response => response.text())
    .then(html => {
        // Parse and extract book info
        const parser = new DOMParser();
        const doc = parser.parseFromString(html, 'text/html');
        
        const bookData = {
            id: bookId,
            title: doc.querySelector('[data-book-title]')?.textContent || 'Unknown',
            price: doc.querySelector('[data-book-price]')?.textContent || '0',
            image: doc.querySelector('[data-book-image]')?.src || '',
            description: doc.querySelector('[data-book-description]')?.textContent || '',
            author: doc.querySelector('[data-book-author]')?.textContent || 'Unknown'
        };
        
        // Display modal
        showQuickViewModal(bookData);
    })
    .catch(error => {
        console.error('Error loading quick view:', error);
        showNotification('Không thể tải thông tin sách', 'error');
    });
}

// ═══════════════════════════════════════════════════════════
// QUICK VIEW MODAL
// ═══════════════════════════════════════════════════════════

/**
 * Display book quick view modal
 * @param {Object} bookData - Book information object
 */
function showQuickViewModal(bookData) {
    const modal = document.getElementById('quickViewModal');
    if (!modal) {
        console.warn('Quick view modal not found in DOM');
        return;
    }
    
    // Update modal content
    const modalBody = modal.querySelector('.modal-body');
    if (modalBody) {
        modalBody.innerHTML = `
            <div class="row">
                <div class="col-md-6">
                    <img src="${escapeHtml(bookData.image)}" class="img-fluid" alt="${escapeHtml(bookData.title)}">
                </div>
                <div class="col-md-6">
                    <h3>${escapeHtml(bookData.title)}</h3>
                    <p class="text-muted">${escapeHtml(bookData.author)}</p>
                    <h4 class="text-success fw-bold">${escapeHtml(bookData.price)}</h4>
                    <p>${escapeHtml(bookData.description).substring(0, 200)}...</p>
                    <div class="d-flex gap-2">
                        <button class="btn btn-success" onclick="addToCart(${bookData.id}); bootstrap.Modal.getOrCreateInstance(document.getElementById('quickViewModal')).hide();">
                            <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                        </button>
                        <button class="btn btn-outline-primary" onclick="addToWishlist(${bookData.id})">
                            <i class="bi bi-heart"></i> Yêu thích
                        </button>
                    </div>
                </div>
            </div>
        `;
    }
    
    // Wire up footer modal button
    const modalFooterBtn = modal.querySelector('#modalAddToCart');
    if (modalFooterBtn) {
        modalFooterBtn.onclick = () => {
            addToCart(bookData.id);
            const bsModal = bootstrap.Modal.getOrCreateInstance(modal);
            bsModal.hide();
        };
    }
    
    // Show modal using Bootstrap
    const bsModal = bootstrap.Modal.getOrCreateInstance(modal);
    bsModal.show();
}

// ═══════════════════════════════════════════════════════════
// NOTIFICATIONS
// ═══════════════════════════════════════════════════════════

/**
 * Show toast notification
 * @param {string} message - The message to display
 * @param {string} type - Type: 'success', 'error', 'info', 'warning'
 */
function showNotification(message, type = 'info') {
    const alertClass = {
        'success': 'alert-success',
        'error': 'alert-danger',
        'info': 'alert-info',
        'warning': 'alert-warning'
    };
    
    const container = document.getElementById('notification-container') || createNotificationContainer();
    
    const alert = document.createElement('div');
    alert.className = `alert ${alertClass[type] || 'alert-info'} alert-dismissible fade show`;
    alert.role = 'alert';
    alert.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    container.appendChild(alert);
    
    // Auto-remove after 4 seconds
    setTimeout(() => {
        alert.remove();
    }, 4000);
}

/**
 * Create notification container if it doesn't exist
 */
function createNotificationContainer() {
    const container = document.createElement('div');
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
    return container;
}

/**
 * Update shopping cart count
 */
function updateCartCount() {
    // Fetch and update cart count badge
    fetch('cart/count')
        .then(r => r.json())
        .then(data => {
            const badge = document.querySelector('[data-cart-count]');
            if (badge) {
                badge.textContent = data.count || 0;
                if (data.count > 0) {
                    badge.style.display = 'inline-block';
                } else {
                    badge.style.display = 'none';
                }
            }
        })
        .catch(err => console.error('Error updating cart count:', err));
}

// ═══════════════════════════════════════════════════════════
// ANIMATION & SCROLL EFFECTS
// ═══════════════════════════════════════════════════════════

/**
 * Animate elements on scroll
 */
function animateOnScroll() {
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -100px 0px'
    };

    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    document.querySelectorAll('.product-card').forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'all 0.5s ease';
        observer.observe(card);
    });
}

/**
 * Setup smooth scroll for anchor links
 */
function setupSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
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

// ═══════════════════════════════════════════════════════════
// SEARCH & AUTOCOMPLETE
// ═══════════════════════════════════════════════════════════

/**
 * Setup search autocomplete
 */
function setupSearchAutocomplete() {
    const searchInput = document.querySelector('input[name="keyword"]');
    if (!searchInput) return;
    
    let autocompleteTimeout;
    
    searchInput.addEventListener('input', function(e) {
        clearTimeout(autocompleteTimeout);
        
        const query = e.target.value.trim();
        if (query.length < 2) {
            hideAutocompleteSuggestions();
            return;
        }
        
        autocompleteTimeout = setTimeout(() => {
            fetchAutocompleteSuggestions(query);
        }, 300); // Debounce
    });
    
    // Hide suggestions when clicking outside
    document.addEventListener('click', function(e) {
        if (e.target !== searchInput) {
            hideAutocompleteSuggestions();
        }
    });
}

/**
 * Fetch autocomplete suggestions
 * @param {string} query - Search query
 */
function fetchAutocompleteSuggestions(query) {
    fetch(`search/autocomplete?q=${encodeURIComponent(query)}`)
        .then(r => r.json())
        .then(suggestions => {
            displayAutocompleteSuggestions(suggestions);
        })
        .catch(err => console.error('Error fetching suggestions:', err));
}

/**
 * Display autocomplete suggestions
 * @param {Array} suggestions - Array of suggestion strings
 */
function displayAutocompleteSuggestions(suggestions) {
    let container = document.getElementById('autocomplete-suggestions');
    
    if (!container) {
        container = document.createElement('div');
        container.id = 'autocomplete-suggestions';
        container.className = 'autocomplete-suggestions';
        const searchInput = document.querySelector('input[name="keyword"]');
        searchInput?.parentElement?.appendChild(container);
    }
    
    if (suggestions.length === 0) {
        hideAutocompleteSuggestions();
        return;
    }
    
    container.innerHTML = suggestions
        .slice(0, 8)
        .map(s => `<div class="suggestion-item" onclick="selectSuggestion('${escapeHtml(s)}')">
            <i class="bi bi-search"></i> ${escapeHtml(s)}
        </div>`)
        .join('');
    
    container.style.display = 'block';
}

/**
 * Hide autocomplete suggestions
 */
function hideAutocompleteSuggestions() {
    const container = document.getElementById('autocomplete-suggestions');
    if (container) {
        container.style.display = 'none';
    }
}

/**
 * Select autocomplete suggestion
 * @param {string} suggestion - The selected suggestion
 */
function selectSuggestion(suggestion) {
    const searchInput = document.querySelector('input[name="keyword"]');
    if (searchInput) {
        searchInput.value = suggestion;
        hideAutocompleteSuggestions();
        // Optionally submit the form
        searchInput.form?.submit();
    }
}

// ═══════════════════════════════════════════════════════════
// UTILITY FUNCTIONS
// ═══════════════════════════════════════════════════════════

/**
 * Escape HTML to prevent XSS
 * @param {string} text - Text to escape
 * @returns {string} Escaped text
 */
function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

/**
 * Format currency
 * @param {number} amount - Amount to format
 * @returns {string} Formatted currency string
 */
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}
