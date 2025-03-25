function validateForm() {
	let memId = document.getElementById("username").value;
	let memPassword = document.getElementById("password").value;
	let memName = document.getElementById("email").value;
	let memNickname = document.getElementById("email").value;
	let memIdRegex = /^[a-zA-Z0-9]{5,20}$/;
	let memPasswordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;
	let memNameRegex = /^[a-zA-Z가-힣]{2,20}$/;
	let memNicknameRegex = /^[a-zA-Z0-9가-힣]{4,8}$/;

	if (!memIdRegex.test(memId)) {
		alert("아이디는 5자 이상 20자 이하이며, 영문과 숫자만 입력 가능합니다.");
		return false;
	}
	if (!memPasswordRegex.test(memPassword)) {
		alert("비밀번호는 8~20자이며, 영문과 숫자를 포함해야 합니다.");
		return false;
	}
	if (!memNameRegex.test(memName)) {
		alert("이름은 한글, 영어만 입력 가능하며, 2~20자여야 합니다.");
		return false;
	}
	if (!memNicknameRegex.test(memNickname)) {
		alert("닉네임은 한글, 영어, 숫자만 입력 가능하며, 4~8자여야 합니다.");
		return false;
	}
	return true;
}

// 아이디 중복 검사
document.getElementById('memId').addEventListener('keyup', function() {
	let memId = this.value;
	if (memId.length > 0) {
		fetch(`/checkMemId?memId=${memId}`)
			.then(response => response.json)
			.then(data => {
				const idError = document.getElementById('idError');
				if (data.exists) {
					idError.textContent = "이미 존재하는 아이디입니다.";
				} else {
					idError.textContent = ""; // 오류 메시지 지우기
				}
			});
	}
});

// 닉네임 중복 검사
document.getElementById('memNickname').addEventListener('keyup', function() {
	let memNickname = this.value;
	if (memNickname.length > 0) {
		fetch(`/checkMemNickname?memNickname=${memNickname}`)
			.then(response => response.json)
			.then(data => {
				const nicknameError = document.getElementById('nicknameError');
				if (data.exists) {
					nicknameError.textContent = "이미 존재하는 닉네임입니다.";
				} else {
					idError.textContent = ""; // 오류 메시지 지우기
				}
			});
	}
});

