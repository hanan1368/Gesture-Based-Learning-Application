const englishLesson1Questions = [
  { id:1, title:"The children are ______", options:["Playing","Dancing","Singing"], correct:1, image:"../assets/english-q1.png", imageSize:"380px" },
  { id:2, title:"The girl is ______", options:["Standing","Crying","Sitting"], correct:3, image:"../assets/english-q2.png", imageSize:"260px" },
  { id:3, title:"She is ______", options:["Reading","Sleeping","Writing"], correct:2, image:"../assets/english-q3.png", imageSize:"380px" },
  { id:4, title:"He is ______", options:["Reading","Standing","Jumping"], correct:2, image:"../assets/english-q4.png", imageSize:"160px" },
  { id:5, title:"They are ______", options:["Crying","Standing","Studying"], correct:3, image:"../assets/english-q5.png", imageSize:"380px" },
  { id:6, title:"He is ______", options:["Jumping","Falling","Clapping"], correct:1, image:"../assets/english-q6.png", imageSize:"370px" },
  { id:7, title:"They are ______", options:["Sleeping","Dancing","Walking"], correct:2, image:"../assets/english-q7.png", imageSize:"600px" },
  { id:8, title:"He is ______", options:["Walking","Running","Dancing"], correct:1, image:"../assets/english-q8.png", imageSize:"220px" },
  { id:9, title:"He is ______", options:["Bathing","Punching","Running"], correct:3, image:"../assets/english-q9.png", imageSize:"240px" },
  { id:10, title:"She is ______", options:["Dancing","Laughing","Singing"], correct:3, image:"../assets/english-q10.png", imageSize:"200px" }
];

let currentQuestionIndex = 0;
let countdownTimer = null;

/* ===== SAME STRUCTURE AS CODING ===== */
let attemptsForCurrentQuestion = 0;
let totalScore = 0;
/* =================================== */

function renderEnglishLessonQuestion() {
  attemptsForCurrentQuestion = 0;

  const q = englishLesson1Questions[currentQuestionIndex];
  if (!q) return;

  document.getElementById("questionTitle").innerText = q.title;

  const img = document.getElementById("lessonImage");
  img.src = q.image;
  img.style.maxWidth = q.imageSize || "300px";

  const box = document.getElementById("optionsBox");
  box.innerHTML = "";

  const list = document.createElement("div");
  list.className = "option-list";

  q.options.forEach(opt => {
    const btn = document.createElement("button");
    btn.className = "option";
    btn.innerText = opt;
    list.appendChild(btn);
  });

  box.appendChild(list);

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
      evaluateEnglishGestureAnswer();
    }
  }, 1000);
}

function evaluateEnglishGestureAnswer() {
  const q = englishLesson1Questions[currentQuestionIndex];
  const resultEl = document.getElementById("answerResult");

  const fingers = window.detectedGestureValue || 0;

  /* ✅ FIX: DO NOT AUTO-VALIDATE 0 OR INVALID FINGERS */
  if (fingers < 1 || fingers > 3) {
    resultEl.innerText = "Show 1, 2 or 3 fingers";
    return;
  }

  const chosenOption = fingers;

  if (chosenOption === q.correct) {

    attemptsForCurrentQuestion++;

    let points = 10;
    if (attemptsForCurrentQuestion === 2) points = 8;
    else if (attemptsForCurrentQuestion >= 3) points = 6;

    totalScore += points;

    resultEl.innerText = "Correct 🎉 +" + points + " points";
    currentQuestionIndex++;

    if (currentQuestionIndex >= englishLesson1Questions.length) {
      resultEl.innerText = "Lesson Completed 🎉 Score: " + totalScore + "/100";
      saveEnglishProgress(totalScore);
      return;
    }

    setTimeout(() => {
      renderEnglishLessonQuestion();
      startAnswerCountdown();
    }, 1200);

  } else {
    attemptsForCurrentQuestion++;
    resultEl.innerText = `Wrong ❌ Show ${q.correct} fingers`;
  }
}

/* ===== SAVE ENGLISH PROGRESS (LIKE CODING) ===== */
function saveEnglishProgress(score) {
  fetch("/progress/save", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({
      subject: "ENGLISH",
      score: score
    })
  })
  .then(res => res.text())
  .then(msg => console.log("English progress saved:", msg))
  .catch(err => console.error("Failed to save English progress", err));
}
/* ============================================== */

window.addEventListener("load", () => {
  if (document.getElementById("lessonImage")) {
    currentQuestionIndex = 0;
    totalScore = 0;
    renderEnglishLessonQuestion();
    startAnswerCountdown();
  }
});
