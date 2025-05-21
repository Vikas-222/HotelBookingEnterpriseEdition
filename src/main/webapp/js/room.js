/* ---------- page‑specific ---------- */
function pageReady() {
    fetchRooms();
}

function fetchRooms() {
    const tbody = document.getElementById("roomTableBody");

    fetch("http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/get-all-room",
        {method: "GET", credentials: "include"})
        .then(r => r.json())
        .then(({data}) => {
            tbody.innerHTML = "";
            if (!Array.isArray(data) || data.length === 0) {
                tbody.innerHTML = `<tr><td colspan="9">No data found</td></tr>`;
                return;
            }

            data.forEach(rm => {
                const switchId = `statusSwitch_${rm.roomId}`;
                const row = document.createElement("tr");
                row.innerHTML = `
                <td>${rm.roomId}</td>
                <td>${rm.roomNumber}</td>
                <td>${rm.roomType}</td>
                <td>${rm.capacity}</td>
                <td>${rm.pricePerNight}</td>
                <td>${rm.roomStatus}</td>
                <td>
                    <label class="switch">
                        <input type="checkbox" id="${switchId}"
                               ${rm.roomStatus === "AVAILABLE" ? "checked" : ""}>
                            <span class="slider round"></span>
                    </label>
                </td>
                <td>
                    <button class="action-btn btn-update"
                            onclick="editRoom(${rm.roomId})">Update
                    </button>
                </td>
            `;
                tbody.appendChild(row);

                // attach listener
                document.getElementById(switchId)
                    .addEventListener("change",
                        e => toggleRoomStatus(rm.roomId, e.target.checked));
            });
        })
        .catch(err => {
                console.error("Fetch error:", err);
                tbody.innerHTML = `
                    <tr>
                        <td colspan="9"> Error
                            fetching
                            data
                        </td>
                    </tr>`;
            }
        );
}

function toggleRoomStatus(roomId, checked) {
    // Map checkbox state to desired enum
    const newStatus = checked ? "AVAILABLE" : "UNDER_MAINTENANCE";

    fetch(
        `http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/update-room-status?roomId=${roomId}`,
        {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            credentials: "include",
            body: JSON.stringify({roomStatus: newStatus})
        }
    )
        .then(r => {
            if (!r.ok) throw new Error(`HTTP ${r.status}`);
            return r.json();
        })
        .then(({message}) => {
            toastr.success(message || "Room status updated");
            // Update status text in the same row
            const cell = document.querySelector(
                `#statusSwitch_${roomId}`            // input
            ).closest("tr").children[5];          // 6th <td> is status text
            cell.textContent = newStatus;
        })
        .catch(err => {
            console.error("Status update failed:", err);
            // Revert toggle
            const cb = document.getElementById(`statusSwitch_${roomId}`);
            if (cb) cb.checked = !checked;
            toastr.error("Could not update status – please try again.");
        });
}

function editRoom(roomId) {
    localStorage.setItem("editRoomId", roomId);
    window.location.href = "../room/Room_Update.html";
}

function addRoom() {
    window.location.href = "../room/Room_Add.html";
}