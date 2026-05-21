document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("chatbot-toggle");
    const chatBox = document.getElementById("chatbot-box");
    const closeBtn = document.getElementById("chatbot-close");
    const chatInput = document.getElementById("chat-input");

    if (toggleBtn && chatBox && closeBtn) {
        toggleBtn.onclick = () => {
            chatBox.style.display = "flex";
            toggleBtn.style.display = "none";
        };

        closeBtn.onclick = () => {
            chatBox.style.display = "none";
            toggleBtn.style.display = "flex";
        };
    }

    if (chatInput) {
        chatInput.addEventListener("keypress", function(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                sendMessage();
            }
        });
    }
});

async function sendMessage() {
    const input  = document.getElementById("chat-input");
    const sendBtn = document.getElementById("chat-send");
    const chat   = document.getElementById("chatbot-messages");

    if (!input || !chat) return;

    const msg = input.value.trim();
    if (msg === "") return;

    // Disable input trong lúc chờ để tránh gửi nhiều request chồng nhau
    input.disabled = true;
    if (sendBtn) sendBtn.disabled = true;

    chat.innerHTML += `<div class="chatbot-user">
        <div class="bubble">${escapeHtml(msg)}</div>
    </div>`;
    chat.scrollTop = chat.scrollHeight;
    input.value = "";

    const loadingElement = document.createElement("div");
    loadingElement.className = "chatbot-bot";
    loadingElement.innerHTML = `<div class="bubble">Đang trả lời...</div>`;
    chat.appendChild(loadingElement);
    chat.scrollTop = chat.scrollHeight;

    // Timeout 90 giây — khớp với thời gian chờ tối đa phía Java
    const controller = new AbortController();
    const timeoutId  = setTimeout(() => controller.abort(), 120_000);

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
            chat.innerHTML += `<div class="chatbot-bot"><div class="bubble">Xin lỗi, có lỗi xảy ra: ${escapeHtml(result.error || response.statusText)}</div></div>`;
        } else {
            chat.innerHTML += `<div class="chatbot-bot"><div class="bubble">${escapeHtml(result.reply)}</div></div>`;
        }
    } catch (error) {
        clearTimeout(timeoutId);
        loadingElement.remove();
        const isTimeout = error.name === "AbortError";
        const errMsg = isTimeout
            ? "Chatbot đang xử lý quá lâu, vui lòng thử lại sau."
            : "Xin lỗi, không thể kết nối tới chatbot.";
        chat.innerHTML += `<div class="chatbot-bot"><div class="bubble">${errMsg}</div></div>`;
    } finally {
        // Luôn re-enable input sau khi xử lý xong
        input.disabled = false;
        if (sendBtn) sendBtn.disabled = false;
        input.focus();
        chat.scrollTop = chat.scrollHeight;
    }
}

function escapeHtml(text) {
    return text
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/\"/g, "&quot;")
        .replace(/'/g, "&#039;");
}
