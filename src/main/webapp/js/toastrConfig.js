// Custom Toastr styling via JS
const toastrStyle = document.createElement("style");
toastrStyle.innerHTML = `
    #toast-container {
        position: fixed;
        top: 70px;
        left: 50%;
        transform: translateX(-50%);
        z-index: 1055;
        pointer-events: none;
        max-width: 300px;
    }

    .toast {
        font-size: 12px;
        border-radius: 8px;
        padding: 10px 14px;
        box-shadow: 0 8px 10px rgba(0, 0, 0, 0.2);
        text-align: center;
    }

    .toast-success {
        background-color: #28a745 !important;
    }

    .toast-error {
        background-color: #dc3545 !important;
    }
`;
document.head.appendChild(toastrStyle);
