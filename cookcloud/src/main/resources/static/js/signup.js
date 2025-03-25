// keyup 이벤트로 비밀번호 확인 실시간 체크
document.getElementById("ckeckPassword").addEventListener("keyup", function() {
	let password = document.getElementById("password").value;
	let checkPassword = document.getElementById("ckeckPassword").value;

	// 비밀번호가 일치하지 않으면 오류 메시지 표시
	if (password !== checkPassword) {
		document.getElementById("error-message").textContent = "비밀번호가 일치하지 않습니다.";
		document.querySelector(".submit-btn").disabled = true; // 일치하지 않으면 가입하기 버튼 비활성화
	} else {
		document.getElementById("error-message").textContent = ""; // 일치하면 오류 메시지 지움
		document.querySelector(".submit-btn").disabled = false; // 일치하면 가입하기 버튼 활성화
	}
});

// 폼 제출 시 비밀번호가 일치하지 않으면 제출되지 않도록
document.getElementById("signupForm").addEventListener("submit", function(event) {
	let password = document.getElementById("password").value;
	let checkPassword = document.getElementById("ckeckPassword").value;

	if (password !== checkPassword) {
		event.preventDefault(); // 폼 제출 방지
		document.getElementById("error-message").textContent = "비밀번호가 일치하지 않습니다.";
	}
});