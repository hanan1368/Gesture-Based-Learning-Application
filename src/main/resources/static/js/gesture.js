console.log("gesture.js loaded");

// global
let lastCount = 0;
window.detectedGestureValue = 0;
let detector = null;
let video = null;

/*  LOAD MODEL — with wake-up FPS settings  */
async function loadModel() {
    detector = await handPoseDetection.createDetector(
        handPoseDetection.SupportedModels.MediaPipeHands,
        {
            runtime: "mediapipe",
            modelType: "lite",
            maxHands: 1,
            solutionPath: "https://cdn.jsdelivr.net/npm/@mediapipe/hands",

            smoothLandmarks: false,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
        }
    );
    console.log("Mediapipe Hands loaded.");
}

/*  CAMERA START — added video.play() keep-alive  */
async function startCamera() {
    video = document.getElementById("gestureCam");
    const stream = await navigator.mediaDevices.getUserMedia({ video: true });
    video.srcObject = stream;

    return new Promise(resolve => {
        video.onloadedmetadata = () => {
            video.play();  
            resolve();
        };
    });
}

/*    FINGER COUNT (UNCHANGED EXCEPT FOR EPSILON SAFETY)    */
function countFingers(landmarks) {
    if (!landmarks) return 0;

    let count = 0;

    const tips = [8, 12, 16, 20];
    const epsilon = 0.02;

    tips.forEach(tip => {
        const pip = tip - 2;
        if (landmarks[tip].y < landmarks[pip].y - epsilon) {
            count++;
        }
    });

    const thumbEps = 0.02;
    if (landmarks[4].x > landmarks[3].x + thumbEps) {
        count++;
    }

    return Math.max(0, Math.min(count, 4));
}

/*    FPS WAKE-UP BOOST — prevents long idle freezing  */
function keepDetectorAwake() {
    setInterval(() => {
        if (detector && video) {
            detector.reset();   
            video.play();       
            console.log("Detector wake-up refresh");
        }
    }, 10000); 
}

/*   MAIN LOOP (UNCHANGED)  */
async function startGestureCapture() {

    console.log("Starting gesture capture…");

    await loadModel();
    await startCamera();
    keepDetectorAwake();  

    async function detect() {
        const hands = await detector.estimateHands(video);

        if (hands.length > 0) {
            const lm = hands[0].keypoints3D || hands[0].keypoints;
            const fingers = countFingers(lm);

            if (fingers !== lastCount) {
                lastCount = fingers;
                window.detectedGestureValue = fingers;
                document.getElementById("gestureOutput").innerText =
                    "Detected Fingers: " + fingers;
            }
        }

        requestAnimationFrame(detect);
    }

    detect();
}
