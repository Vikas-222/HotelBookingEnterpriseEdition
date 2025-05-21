// Run when DOM is ready
document.addEventListener("DOMContentLoaded", function () {

    // Handle form submission
    document.getElementById("signupForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent page refresh

        // Get form values
        const firstName = document.getElementById("first-name").value;
        const lastName = document.getElementById("last-name").value;
        const email = document.getElementById("email").value;
        const contactNumber = document.getElementById("contact-number").value;
        const password = document.getElementById("password").value;

        // Create payload
        const payload = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password,
            contactNumber: contactNumber
        };

        console.log("Sending Payload:", payload);

        // Send AJAX request using fetch API
        fetch('http://192.168.1.102:8081/HotelBookingEnterpriseEdition_war_exploded/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.json())
            .then(data => {
                console.log("Success:", data);
                if (data.message === "Account created successfully") {
                    toastr.success(data.message);
                    window.location.href = "login.html";
                } else {
                    toastr.info(data.message);
                }
            })
            .catch(error => {
                console.error("Error:", error);
                toastr.error("Something went wrong. Please try again.");
            });
    });
});