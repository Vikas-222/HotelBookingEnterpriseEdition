document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const payload = {
            email: email,
            password: password
        };

        fetch('http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify(payload)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(text => {
                        throw new Error(text);
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log("Login Success:", data);
                toastr.success(data.message);
                setTimeout(() => {
                    if (data.data.role === "ADMIN") {
                        window.location.href = "../templates/admin/Admin_Dashboard.html";
                    } else {
                        window.location.href = "home.html";
                    }
                }, 1500);
            })
            .catch(error => {
                console.error("Login Error:", error);
                try {
                    const parsed = JSON.parse(error.message);
                    toastr.error(parsed.message || "Login failed.");
                } catch (e) {
                    toastr.error("Server error. Please try again.");
                }
            });
    });
});