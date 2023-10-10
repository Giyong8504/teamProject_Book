const rolLength = 6; // 해당 룰렛 콘텐츠 갯수
let setNum; // 랜덤숫자 담을 변수
const hiddenInput = document.createElement("input");
hiddenInput.className = "hidden-input";

//랜덤숫자부여
const rRandom = () => {
  const min = Math.ceil(0);
  const max = Math.floor(rolLength - 1);
  return Math.floor(Math.random() * (max - min)) + min;
};

const rRotate = () => {
  const panel = document.querySelector(".rouletter-wacu");
  const btn = document.querySelector(".rouletter-btn");
  const deg = [];

  // 룰렛 각도 설정(rolLength = 6)
  for (let i = 1, len = rolLength; i <= len; i++) {
    deg.push((360 / len) * i);
  }

  // 애니설정
  let num = 0;
  document.body.append(hiddenInput);
  setNum = rRandom();
  hiddenInput.value = setNum;

  const ani = setInterval(() => {
    num++;
    panel.style.transform = "rotate(" + 360 * num + "deg)";
    btn.disabled = true; //button, input
    btn.style.pointerEvents = "none"; //a 태그

    // 총 50에 다달했을때, 즉 마지막 바퀴를 돌고나서
    if (num === 50) {
      clearInterval(ani);
      panel.style.transform = `rotate(${deg[setNum]}deg)`;

    }
  }, 50);
};

// 정해진 alert띄우기, custom modal등
const rLayerPopup = (num) => {
  switch (num) {
    case 1:
      alert("당첨!! 스타벅스 아메리카노");
      break;
    case 3:
      alert("당첨!! 햄버거 세트 교환권");
      break;
    case 5:
      alert("당첨!! CU 3,000원 상품권");
      break;
    default:
      alert("꽝! 다음기회에");
  }
};

// reset
const rReset = (ele) => {
  setTimeout(() => {
    ele.disabled = false;
    ele.style.pointerEvents = "auto";
    rLayerPopup(setNum);
    hiddenInput.remove();
  }, 5500);
};

// 룰렛 이벤트 클릭 버튼
document.addEventListener("click", function (e) {
  const target = e.target;
  if (target.tagName === "BUTTON") {
    rRotate();
    rReset(target);
  }
});