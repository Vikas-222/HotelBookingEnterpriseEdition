window.onload = () => {
    // 1️⃣  inject header, then sidebar
    includeHTML("header", "../components/Admin_Header.html", () => {
        includeHTML("sidebar", "../components/Admin_Sidebar.html", () => {
            // 2️⃣  once both are ready, bind logout & let page‑specific code run
            bindLogoutEvent(".logoutBtn");

            // 3️⃣  give pages a hook for their own on‑ready logic
            if (typeof pageReady === "function") {
                pageReady();          // <- defined per page
            }
        });
    });
};

/*  -------- helpers ---------- */
function bindLogoutEvent(selector) {
    document.querySelectorAll(selector).forEach(btn =>
        btn.addEventListener("click", e => {
            e.preventDefault();
            fetch(
                "http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/logout",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    credentials: "include"
                }
            )
                .then(r => r.json())
                .then(({ message }) => {
                    if (message === "Logout successful") {
                        alert("Logged out successfully!");
                        window.location.href = "../Login.html";
                    } else {
                        alert("Logout failed!");
                    }
                })
                .catch(err => {
                    console.error("Logout error:", err);
                    alert("Something went wrong during logout.");
                });
        })
    );
}

function includeHTML(id, url, cb) {
    fetch(url)
        .then(r => r.text())
        .then(html => {
            document.getElementById(id).innerHTML = html;
            if (cb) cb();
        })
        .catch(err => console.error(`includeHTML ${url} →`, err));
}
