/* dashboard.js
   Handles sidebar toggle, dashboard navigation, user info, etc.
*/

// ===============================
//  SIDEBAR TOGGLE
// ===============================
const sidebar = document.querySelector(".sidebar");
const menuBtn = document.querySelector(".menu-btn");

if (menuBtn) {
    menuBtn.addEventListener("click", () => {
        sidebar.classList.toggle("open");
    });
}

// ===============================
//  LOGOUT BUTTON
// ===============================
const logoutBtn = document.querySelector(".btn-logout");

if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        window.location.href = "../login.html";
    });
}

// ===============================
//  DASHBOARD CARD CLICK HANDLERS
// ===============================
const cards = document.querySelectorAll(".card");

cards.forEach(card => {
    card.addEventListener("click", () => {
        const page = card.getAttribute("data-go");

        if (!page) return;

        // student-dashboard → student pages
        // parent-dashboard → parent pages
        // admin-dashboard → admin pages
        window.location.href = page;
    });
});

// ===============================
//  LOAD USER NAME FROM LOCAL STORAGE
// ===============================

// For example: set during login using login.js
// localStorage.setItem("username", "student");

const usernameDisplay = document.querySelector(".username-text");

if (usernameDisplay) {
    const user = localStorage.getItem("username") || "User";
    usernameDisplay.innerText = user;
}

// ===============================
//  SIMPLE ENTRY ANIMATION
// ===============================
window.addEventListener("load", () => {
    const panels = document.querySelectorAll(".card, .course, .reward");
    panels.forEach(el => {
        el.style.opacity = "0";
        el.style.transform = "translateY(10px)";
        setTimeout(() => {
            el.style.transition = "0.4s ease";
            el.style.opacity = "1";
            el.style.transform = "translateY(0px)";
        }, 150);
    });
});

// ===============================
//  RESPONSIVE BEHAVIOR
// ===============================
function closeSidebarOnSmallScreens() {
    if (window.innerWidth < 860) {
        sidebar.classList.remove("open");
    }
}

window.addEventListener("resize", closeSidebarOnSmallScreens);
closeSidebarOnSmallScreens();

