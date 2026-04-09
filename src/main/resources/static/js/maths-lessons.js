const mathsLesson1Questions = [
 {
  id: 1,
  left: "../assets/math-q1.png",
  right: "../assets/math-q1.png",
  options: [
    { count: 1 },
    { count: 2 },
    { count: 3 }
  ],
  correct: 2
},
 {
  id: 2,
  img: "../assets/math-q2.png",
  leftCount: 3,
  rightCount: 1,
  options: [
    { count: 3 },
    { count: 2 },
    { count: 4 }
  ],
  correct: 3
},
  {
  id: 3,
  img: "../assets/math-q3.png",
  leftCount: 4,
  rightCount: 2,
  options: [
    { count: 3 },
    { count: 6 }, // ✅ correct
    { count: 1 }
  ],
  correct: 2
},
  {
  id: 4,
  img: "../assets/math-q4.png",
  leftCount: 5,
  rightCount: 3,
  options: [
    { count: 5 },
    { count: 8 }, // ✅ correct
    { count: 4 }
  ],
  correct: 2
},
 {
  id: 5,
  img: "../assets/math-q5.png",
  leftCount: 5,
  rightCount: 4,
  options: [
    { count: 7 },
    { count: 4 },
    { count: 9 } // ✅ correct
  ],
  correct: 3
},
  {
    id: 6,
    left: "../assets/math-q6.png",
    right: "../assets/math-q6.png",
    options: [
      "../assets/math-q6.png",
      "../assets/math-q7.png",
      "../assets/math-q8.png"
    ],
    correct: 2
  },
  {
    id: 7,
    left: "../assets/math-q7.png",
    right: "../assets/math-q7.png",
    options: [
      "../assets/math-q7.png",
      "../assets/math-q8.png",
      "../assets/math-q9.png"
    ],
    correct: 2
  },
  {
    id: 8,
    left: "../assets/math-q8.png",
    right: "../assets/math-q8.png",
    options: [
      "../assets/math-q8.png",
      "../assets/math-q9.png",
      "../assets/math-q10.png"
    ],
    correct: 1
  },
  {
    id: 9,
    left: "../assets/math-q9.png",
    right: "../assets/math-q9.png",
    options: [
      "../assets/math-q9.png",
      "../assets/math-q10.png",
      "../assets/math-q8.png"
    ],
    correct: 1
  },
  {
    id: 10,
    left: "../assets/math-q10.png",
    right: "../assets/math-q10.png",
    options: [
      "../assets/math-q10.png",
      "../assets/math-q9.png",
      "../assets/math-q8.png"
    ],
    correct: 1
  }
];

let currentQuestionIndex = 0;
let attemptsForCurrentQuestion = 0;
let totalScore = 0;
let countdownTimer = null;

function renderMathsQuestion() {
  attemptsForCurrentQuestion = 0;
  const q = mathsLesson1Questions[currentQuestionIndex];

  const equationBox = document.getElementById("equationBox");
  const optionsBox = document.getElementById("optionsBox");

  equationBox.innerHTML = `
    <div class="equation-row">
      <img src="${q.left}" class="equation-img">
      <div>+</div>
      <img src="${q.right}" class="equation-img">
      <div>=</div>
      <div>?</div>
    </div>
  `;

  optionsBox.innerHTML = "";

  /* ✅ QUESTION 1 — MINI IMAGE OPTIONS */
  if (q.id === 1) {
    q.options.forEach(opt => {
      const btn = document.createElement("button");
      btn.className = "option-btn";

      for (let i = 0; i < opt.count; i++) {
        const img = document.createElement("img");
        img.src = "../assets/math-q1.png";
        img.style.width = "40px";   // mini apple
        img.style.margin = "0 4px";
        btn.appendChild(img);
      }

      optionsBox.appendChild(btn);
    });
  }

  else if (q.id === 2) {

  equationBox.innerHTML = "";
  optionsBox.innerHTML = "";

  /* ===== LEFT SIDE (3 CAKES — TRIANGLE) ===== */
  const leftWrapper = document.createElement("div");
  leftWrapper.style.display = "grid";
  leftWrapper.style.gridTemplateColumns = "repeat(2, auto)";
  leftWrapper.style.columnGap = "12px";
  leftWrapper.style.rowGap = "12px";
  leftWrapper.style.justifyItems = "center";

  for (let i = 0; i < 3; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "90px";
    leftWrapper.appendChild(img);
  }

  // center the 3rd cake
  leftWrapper.children[2].style.gridColumn = "1 / span 2";

  /* ===== PLUS ===== */
  const plus = document.createElement("div");
  plus.innerText = "+";
  plus.style.fontSize = "48px";
  plus.style.margin = "0 30px";

  /* ===== RIGHT SIDE (1 CAKE) ===== */
  const rightImg = document.createElement("img");
  rightImg.src = q.img;
  rightImg.style.width = "90px";

  /* ===== = ? ===== */
  const equal = document.createElement("div");
  equal.innerText = "= ?";
  equal.style.fontSize = "48px";
  equal.style.marginLeft = "30px";

  /* ===== FINAL EQUATION ROW ===== */
  const row = document.createElement("div");
  row.style.display = "flex";
  row.style.alignItems = "center";
  row.appendChild(leftWrapper);
  row.appendChild(plus);
  row.appendChild(rightImg);
  row.appendChild(equal);

  equationBox.appendChild(row);

  /* ===== OPTIONS (MINI CAKES) ===== */
  q.options.forEach(opt => {
    const btn = document.createElement("button");
    btn.className = "option-btn";

    for (let i = 0; i < opt.count; i++) {
      const mini = document.createElement("img");
      mini.src = q.img;
      mini.style.width = "35px";
      mini.style.margin = "0 4px";
      btn.appendChild(mini);
    }

    optionsBox.appendChild(btn);
  });
}
else if (q.id === 3) {

  equationBox.innerHTML = "";
  optionsBox.innerHTML = "";

  /* ===== LEFT SIDE (4 FOOTBALLS — 2x2 GRID) ===== */
  const leftWrapper = document.createElement("div");
  leftWrapper.style.display = "grid";
  leftWrapper.style.gridTemplateColumns = "repeat(2, auto)";
  leftWrapper.style.gap = "12px";

  for (let i = 0; i < 4; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "90px";
    leftWrapper.appendChild(img);
  }

  /* ===== PLUS ===== */
  const plus = document.createElement("div");
  plus.innerText = "+";
  plus.style.fontSize = "48px";
  plus.style.margin = "0 30px";

  /* ===== RIGHT SIDE (2 FOOTBALLS) ===== */
  const rightWrapper = document.createElement("div");
  rightWrapper.style.display = "flex";
  rightWrapper.style.gap = "12px";

  for (let i = 0; i < 2; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "90px";
    rightWrapper.appendChild(img);
  }

  /* ===== = ? ===== */
  const equal = document.createElement("div");
  equal.innerText = "= ?";
  equal.style.fontSize = "48px";
  equal.style.marginLeft = "30px";

  /* ===== FINAL ROW ===== */
  const row = document.createElement("div");
  row.style.display = "flex";
  row.style.alignItems = "center";
  row.appendChild(leftWrapper);
  row.appendChild(plus);
  row.appendChild(rightWrapper);
  row.appendChild(equal);

  equationBox.appendChild(row);

  /* ===== OPTIONS (MINI FOOTBALLS) ===== */
  q.options.forEach(opt => {
    const btn = document.createElement("button");
    btn.className = "option-btn";

    for (let i = 0; i < opt.count; i++) {
      const mini = document.createElement("img");
      mini.src = q.img;
      mini.style.width = "35px";
      mini.style.margin = "0 4px";
      btn.appendChild(mini);
    }

    optionsBox.appendChild(btn);
  });
}
else if (q.id === 4) {

  equationBox.innerHTML = "";
  optionsBox.innerHTML = "";

  /* ===== LEFT SIDE (5 PENCILS) ===== */
  const leftWrapper = document.createElement("div");
  leftWrapper.style.display = "flex";
  leftWrapper.style.gap = "12px";

  for (let i = 0; i < q.leftCount; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "35px";
    leftWrapper.appendChild(img);
  }

  /* ===== PLUS ===== */
  const plus = document.createElement("div");
  plus.innerText = "+";
  plus.style.fontSize = "48px";
  plus.style.margin = "0 30px";

  /* ===== RIGHT SIDE (3 PENCILS) ===== */
  const rightWrapper = document.createElement("div");
  rightWrapper.style.display = "flex";
  rightWrapper.style.gap = "12px";

  for (let i = 0; i < q.rightCount; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "35px";
    rightWrapper.appendChild(img);
  }

  /* ===== = ? ===== */
  const equal = document.createElement("div");
  equal.innerText = "= ?";
  equal.style.fontSize = "48px";
  equal.style.marginLeft = "30px";

  /* ===== FINAL EQUATION ROW ===== */
  const row = document.createElement("div");
  row.style.display = "flex";
  row.style.alignItems = "center";
  row.appendChild(leftWrapper);
  row.appendChild(plus);
  row.appendChild(rightWrapper);
  row.appendChild(equal);

  equationBox.appendChild(row);

  /* ===== OPTIONS (MINI PENCILS) ===== */
 /* ===== OPTIONS (SMALLER MINI PENCILS) ===== */
/* ===== OPTIONS (SMALLER MINI PENCILS) ===== */
q.options.forEach(opt => {
  const btn = document.createElement("button");
  btn.className = "option-btn";

  for (let i = 0; i < opt.count; i++) {
    const mini = document.createElement("img");
    mini.src = q.img;

    // 🔽 SMALLER SIZE (MATCHES OTHER OPTIONS)
    mini.style.width = "16px";
    mini.style.margin = "0 2px";

    btn.appendChild(mini);
  }

  optionsBox.appendChild(btn);
});
}
else if (q.id === 5) {

  equationBox.innerHTML = "";
  optionsBox.innerHTML = "";

  /* ===== LEFT SIDE (5 COINS — 3 TOP, 2 BOTTOM) ===== */
  const leftWrapper = document.createElement("div");
  leftWrapper.style.display = "grid";
  leftWrapper.style.gridTemplateColumns = "repeat(3, auto)";
  leftWrapper.style.gap = "12px";
  leftWrapper.style.justifyItems = "center";

  for (let i = 0; i < q.leftCount; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "60px";
    leftWrapper.appendChild(img);
  }

  /* ===== PLUS ===== */
  const plus = document.createElement("div");
  plus.innerText = "+";
  plus.style.fontSize = "48px";
  plus.style.margin = "0 30px";

  /* ===== RIGHT SIDE (4 COINS — 2x2 GRID) ===== */
  const rightWrapper = document.createElement("div");
  rightWrapper.style.display = "grid";
  rightWrapper.style.gridTemplateColumns = "repeat(2, auto)";
  rightWrapper.style.gap = "12px";

  for (let i = 0; i < q.rightCount; i++) {
    const img = document.createElement("img");
    img.src = q.img;
    img.style.width = "60px";
    rightWrapper.appendChild(img);
  }

  /* ===== = ? ===== */
  const equal = document.createElement("div");
  equal.innerText = "= ?";
  equal.style.fontSize = "48px";
  equal.style.marginLeft = "30px";

  /* ===== FINAL ROW ===== */
  const row = document.createElement("div");
  row.style.display = "flex";
  row.style.alignItems = "center";
  row.appendChild(leftWrapper);
  row.appendChild(plus);
  row.appendChild(rightWrapper);
  row.appendChild(equal);

  equationBox.appendChild(row);

  /* ===== OPTIONS (MINI COINS) ===== */
 /* ===== OPTIONS (BIGGER MINI COINS) ===== */
q.options.forEach(opt => {
  const btn = document.createElement("button");
  btn.className = "option-btn";

  for (let i = 0; i < opt.count; i++) {
    const mini = document.createElement("img");
    mini.src = q.img;

    // 🔼 INCREASED SIZE (CLEAR & READABLE)
    mini.style.width = "26px";
    mini.style.margin = "0 3px";

    btn.appendChild(mini);
  }

  optionsBox.appendChild(btn);
});
}
  /* OTHER QUESTIONS — UNCHANGED */
  else {
    q.options.forEach(opt => {
      const btn = document.createElement("button");
      btn.className = "option-btn";
      btn.innerHTML = `<img src="${opt}" class="option-img">`;
      optionsBox.appendChild(btn);
    });
  }

  document.getElementById("answerResult").innerText = "";
  document.getElementById("timerDisplay").innerText = "Time: -";
}

function startAnswerCountdown() {
  let timeLeft = 10;
  const timerEl = document.getElementById("timerDisplay");

  if (countdownTimer) clearInterval(countdownTimer);
  timerEl.innerText = "Time: " + timeLeft;

  countdownTimer = setInterval(() => {
    timeLeft--;
    timerEl.innerText = "Time: " + timeLeft;

    if (timeLeft <= 0) {
      clearInterval(countdownTimer);
      evaluateMathGestureAnswer();
    }
  }, 1000);
}

function evaluateMathGestureAnswer() {
  const q = mathsLesson1Questions[currentQuestionIndex];
  const fingers = window.detectedGestureValue || 0;
  const resultEl = document.getElementById("answerResult");

  if (fingers < 1 || fingers > 3) {
    resultEl.innerText = "Show 1, 2 or 3 fingers";
    return;
  }

  attemptsForCurrentQuestion++;

  if (fingers === q.correct) {
    let points = 10;
    if (attemptsForCurrentQuestion === 2) points = 8;
    else if (attemptsForCurrentQuestion >= 3) points = 6;

    totalScore += points;
    resultEl.innerText = `Correct 🎉 +${points} points`;

    currentQuestionIndex++;

    if (currentQuestionIndex >= mathsLesson1Questions.length) {
      resultEl.innerText = `Lesson Completed 🎉 Score: ${totalScore}/100`;
      return;
    }

    setTimeout(() => {
      renderMathsQuestion();
      startAnswerCountdown();
    }, 1200);
  } else {
    resultEl.innerText = "Wrong ❌ Try again";
  }
}

window.addEventListener("load", () => {
  renderMathsQuestion();
  startAnswerCountdown();
});