let cIndex = 0;
let countdownTimer = null;

/* ================= PROGRESS LOGIC (ADDED) ================= */
let attemptsForCurrentQuestion = 0;
let totalScore = 0;
/* ========================================================== */

const codingQuestions = [
  { seq:["star","circle","star","circle"], options:[["star"],["circle"],["star","star"]], correct:1 },
  { seq:["tri","sq","tri","sq","tri"], options:[["tri"],["tri","sq"],["sq"]], correct:3 },
  { seq:["dia","pen","dia","pen","dia"], options:[["dia","pen"],["pen","pen"],["pen","dia"]], correct:3 },
  {
    seq:["hex","circle","hex","circle","hex"],
    options:[["circle","circle"],["circle","hex"],["hex","circle"]],
    correct:2
  },
  { seq:["rect","oct","star","rect","oct"], options:[["star","oct","rect"],["rect","rect","star"],["star","rect","oct"]], correct:3 },
  { seq:["red","gray","orange","red","gray"], options:[["red","gray","orange"],["orange","red","gray"],["red","orange","gray"]], correct:2 },
  {
    seq:["pencil.png","glue.png","pencil.png","glue.png","pencil.png"],
    options:[["glue.png","glue.png"],["pencil.png","glue.png"],["glue.png","pencil.png"]],
    correct:3
  },
  {
    seq:["hat.png","shirt.png","shoes.png","hat.png","shirt.png"],
    options:[["hat.png","shirt.png"],["shoes.png","hat.png"],["hat.png","hat.png"]],
    correct:2
  },
  {
    seq:["flowers1.png","flowers2.png","flowers3.png","flowers1.png","flowers2.png"],
    options:[
      ["flowers1.png","flowers2.png","flowers3.png"],
      ["flowers2.png","flowers3.png","flowers1.png"],
      ["flowers3.png","flowers1.png","flowers2.png"]
    ],
    correct:3
  },
  {
    seq:["sunglasses.png","bag.png","socks.png","sunglasses.png","bag.png"],
    options:[["sunglasses.png","bag.png"],["bag.png","socks.png"],["socks.png","sunglasses.png"]],
    correct:3
  }
];

function updateGestureLabel(v){
  document.getElementById("gestureOutput").innerText = "Detected Fingers: " + v;
}

function renderCodingQuestion(){
  attemptsForCurrentQuestion = 0;

  const q = codingQuestions[cIndex];

  let html = `<div class="pattern-row">`;

  q.seq.forEach(x=>{
    html += isShape(x)
      ? `<div class="shape ${x}"></div>`
      : `<img src="/assets/${x}" class="pattern-img">`;
  });

  html += `<span class="blank"></span></div>`;
  html += `<div id="optionsBox">`;

  q.options.forEach(opt=>{
    html += `<div class="option-box">`;
    opt.forEach(o=>{
      html += isShape(o)
        ? `<div class="shape ${o} small"></div>`
        : `<img src="/assets/${o}" class="option-img">`;
    });
    html += `</div>`;
  });

  html += `</div>`;
  document.getElementById("lessonImage").innerHTML = html;
}

/* ---------------- TIMER + ANSWER LOGIC ---------------- */

function evaluateAnswer(){
  const fingers = window.detectedGestureValue || 0;
  updateGestureLabel(fingers);

  if(fingers < 1 || fingers > 3){
    document.getElementById("answerResult").innerText = "Show 1, 2 or 3 fingers";
    return;
  }

  if(fingers === codingQuestions[cIndex].correct){

    attemptsForCurrentQuestion++;

    let pointsForThisQuestion = 10;
    if(attemptsForCurrentQuestion === 2) pointsForThisQuestion = 8;
    else if(attemptsForCurrentQuestion >= 3) pointsForThisQuestion = 6;

    totalScore += pointsForThisQuestion;

    document.getElementById("answerResult").innerText =
      "Correct 🎉 +" + pointsForThisQuestion + " points";

    cIndex++;

    if(cIndex >= codingQuestions.length){
      document.getElementById("answerResult").innerText =
        "Lesson Completed 🎉 Score: " + totalScore + "/100";

      saveLessonProgress(totalScore);
      return;
    }

    setTimeout(()=>{
      renderCodingQuestion();
      startAnswerCountdown();
    }, 1000);

  } else {
    attemptsForCurrentQuestion++;
    document.getElementById("answerResult").innerText = "Wrong ❌ Try again";
  }
}

function startAnswerCountdown(){
  if(countdownTimer) clearInterval(countdownTimer);

  let t = 8;
  document.getElementById("timerDisplay").innerText = "Time: " + t;

  countdownTimer = setInterval(()=>{
    t--;
    document.getElementById("timerDisplay").innerText = "Time: " + t;

    if(t <= 0){
      clearInterval(countdownTimer);
      countdownTimer = null;
      evaluateAnswer();
    }
  }, 1000);
}

function isShape(x){
  return [
    "star","circle","tri","sq","dia","pen","hex","rect","oct",
    "red","gray","orange","pink"
  ].includes(x);
}

/* ================= SAVE PROGRESS (FIXED) ================= */
function saveLessonProgress(score){

  fetch("/progress/save", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: new URLSearchParams({
      subject: "CODING",
      score: score
    })
  })
  .then(res => res.text())
  .then(msg => console.log("Progress response:", msg))
  .catch(err => console.error("Progress save failed", err));
}


window.onload = renderCodingQuestion;
