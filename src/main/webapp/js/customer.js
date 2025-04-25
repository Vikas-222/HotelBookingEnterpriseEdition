/* ---------- page‑specific ---------- */
function pageReady() {
    fetchUsers();
}

// ===== modified fetchUsers =====
function fetchUsers() {
    const tableBody = document.getElementById("userTableBody");

    fetch("http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/get-all-user", {
        method: "GET", credentials: "include"
    })
        .then(r => r.json())
        .then(({data}) => {
            tableBody.innerHTML = "";
            if (!Array.isArray(data) || data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="9">No data found</td></tr>`;
                return;
            }

            data.forEach(u => {
                const row = document.createElement("tr");
                // Build a unique id for the switch input
                const switchId = `activeSwitch_${u.userId}`;
                row.innerHTML = `<td>${u.userId}</td>
                <td>${u.firstName}</td>
                <td>${u.lastName}</td>
                <td>${u.email}</td>
                <td>${u.contactNumber}</td>
                <td>${u.gender}</td>
                <td>${u.role}</td>
                <td>
                    <!-- toggle switch -->
                    <label class="switch">
                        <input type="checkbox" id="${switchId}" ${u.isActive ? "checked" : ""}>
                            <span class="slider round"></span>
                    </label>
                </td>
                <td>
                    <button class="action-btn btn-update" onclick="updateUser(${u.userId})">Update</button>
                </td>
                `;
                tableBody.appendChild(row);
                // attach change listener AFTER row is in DOM
                document.getElementById(switchId)
                    .addEventListener("change", e => toggleStatus(u.userId, e.target.checked));
            });
        })
        .catch(err => {
                console.error("Fetch error:", err);
                tableBody.innerHTML = `<tr><td colspan="9">Error fetching data</td></tr>`;
            }
        );
}


function toggleStatus(userId, newState) {
    fetch("http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/update-user-status", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ userId, isActive: newState })
    })
        .then(r => {
            if (!r.ok) throw new Error(`HTTP ${r.status}`);
            return r.json();
        })
        .then(({ message }) => {          // ← destructure message
            console.log(
                `User ${userId} set to ${newState ? "Active" : "Inactive"}`
            );
            toastr.success(message);        // ← show API message
        })
        .catch(err => {
            console.error("Status update failed:", err);
            const checkbox = document.getElementById(`activeSwitch_${userId}`);
            if (checkbox) checkbox.checked = !newState;
            toastr.error("Something went wrong. Please try again.");
        });
}


function updateUser(userId) {
    localStorage.setItem("editUserId", userId);
    window.location.href = '../customer/Customer_Update.html';
}

function deleteUser(userId) {
    if (confirm("Are you sure you want to delete user " + userId + "?")) {
        alert("Deleted user: " + userId);
        // Call delete API here if needed
    }
}

function addUser() {
    alert("Add user functionality here.");
}


