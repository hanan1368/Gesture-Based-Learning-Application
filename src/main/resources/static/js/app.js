// app.js – handles role-based login screen

function getRoleFromUrl() {
  const params = new URLSearchParams(location.search);
  const role = (params.get("role") || "student").toLowerCase();
  if (role !== "student" && role !== "parent" && role !== "admin") {
    return "student";
  }
  return role;
}

function setupLoginScreen() {
  const role = getRoleFromUrl();

  const title = document.getElementById("login-title");
  const userLabel = document.getElementById("user-label");
  const studentIdBlock = document.getElementById("student-id-block");
  const illustration = document.getElementById("login-illustration-img");
  const loginBtn = document.getElementById("loginButton");

  if (!title || !userLabel || !studentIdBlock || !illustration || !loginBtn) {
    return;
  }

  if (role === "student") {
    title.textContent = "Student Login!";
    userLabel.textContent = "Student ID";
    studentIdBlock.style.display = "none";
    illustration.src = "assets/student-login-illustration.png";
  } else if (role === "parent") {
    title.textContent = "Parent Login!";
    userLabel.textContent = "Username";
    studentIdBlock.style.display = "block";
    illustration.src = "assets/parent-login-illustration.png";
  } else if (role === "admin") {
    title.textContent = "Admin Login!";
    userLabel.textContent = "Admin ID";
    studentIdBlock.style.display = "none";
    illustration.src = "assets/admin-login-illustration.png";
  }

  // On click, just navigate to correct dashboard (you can later replace with real auth)
  loginBtn.onclick = () => {
    if (role === "student") {
      location.href = "student/student-dashboard.html";
    } else if (role === "parent") {
      location.href = "parent/parent-dashboard.html";
    } else if (role === "admin") {
      location.href = "admin/admin-dashboard.html";
    }
  };
}

document.addEventListener("DOMContentLoaded", setupLoginScreen);
