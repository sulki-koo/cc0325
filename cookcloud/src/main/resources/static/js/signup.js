document.getElementById("insertMember").addEventListener("click", function(event) {
	event.preventDefault();  // 기본 폼 제출을 방지

	let valid = true;

	// 아이디 유효성 검사
	const id = $('#id').val();
	const idPattern = /^[a-zA-Z0-9]+$/;
	if (id.length < 5 || id.length > 20 || !idPattern.test(id)) {
		valid = false;
		$('#idError').text('아이디는 영어와 숫자만 입력 가능하며, 6~20자여야 합니다.');

	} else {
		$('#idError').text('');
	}

	// 비밀번호 유효성 검사
	const password = $('#password').val();
	const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,20}$/;
	if (password.length < 8 || password.length > 20 || !passwordPattern.test(password)) {
		valid = false;
		$('#passwordError').text('비밀번호는 영어 대소문자, 숫자 포함해야 하며, 8~20자여야 합니다.');
	} else {
		$('#passwordError').text('');
	}

	// 비밀번호 확인 유효성 검사
	const checkPassword = $('#checkPassword').val();
	if (password !== checkPassword) {
		valid = false;
		$('#checkPasswordError').text('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
	} else {
		$('#checkPasswordError').text('');
	}

	// 이름 유효성 검사
	const name = $('#name').val();
	const namePattern = /^[a-zA-Z가-힣]+$/;
	if (name.length < 2 || name.length > 20 || !namePattern.test(name)) {
		valid = false;
		$('#nameError').text('이름은 한글, 영어만 입력 가능하며, 2~20자여야 합니다.');
	} else {
		$('#nameError').text('');
	}

	// 닉네임 유효성 검사
	const nickname = $('#nickname').val();
	const nicknamePattern = /^[a-zA-Z0-9가-힣]+$/;
	if (nickname.length < 4 || nickname.length > 8 || !nicknamePattern.test(nickname)) {
		valid = false;
		$('#nicknameError').text('닉네임은 한글, 영어, 숫자만 입력 가능하며, 4~8자여야 합니다.');
	} else {
		$('#nicknameError').text('');
	}

	// 이메일 유효성 검사
	const email = $('#email').val();
	const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (!emailPattern.test(email)) {
		valid = false;
		$('#emailError').text('유효한 이메일을 입력해주세요.');
	} else {
		$('#emailError').text('');
	}

	// 전화번호 유효성 검사
	const phone = $('#phone').val();
	const phonePattern = /^\d{10,11}$/; // 전화번호는 10~11자리 숫자만 허용
	if (!phonePattern.test(phone)) {
		valid = false;
		$('#phoneError').text('전화번호는 10~11자리 숫자만 입력 가능합니다.');
	} else {
		$('#phoneError').text('');
	}

	if (valid) {
		checkDuplicate(id, nickname).then(isDuplicate => {
			// 중복이 아니면 회원 등록
			if (!isDuplicate) {
				insertMember();
			} else {
				alert('아이디 또는 닉네임이 이미 존재합니다.');
			}
		}).catch(error => {
			console.error("중복 확인 오류:", error);
			alert('중복 확인 중 오류가 발생했습니다.');
		});
	}

});

function checkDuplicate(id, nickname) {
	return new Promise((resolve, reject) => {
		// 빈 값이 있을 경우 함수 종료
		if (!id.trim() || !nickname.trim()) {
			reject('아이디와 닉네임을 입력해야 합니다.');
			return;
		}

		const jsonStr = {
			memId: id,
			memNickname: nickname
		};

		$.ajax({
			url: "/signup/checkDuplicate",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(jsonStr),
			success: function(response) {
				// 응답 처리
				if (response.result === true) {
					// 중복된 경우 resolve(false)
					resolve(true);
				} else {
					// 중복되지 않은 경우 resolve(true)
					resolve(false);
				}
			},
			error: function(xhr, status, error) {
				// 오류 처리
				console.error("Error:", error);
				reject(error); // 에러 발생 시 reject
			}
		});
	});
}


function insertMember() {
	let password = $("#password").val();
	let checkPassword = $("#checkPassword").val();

	if (!checkPassword) {
		alert(checkPassword + "비밀번호를 입력하세요");
		return;
	}

	let memberData = {
		memId: $("#id").val(),
		memPassword: password,
		memName: $("#name").val(),
		memNickname: $("#nickname").val(),
		memEmail: $("#email").val(),
		memPhone: $("#phone").val()
	}
	if (password == checkPassword) {
		$.ajax({
			url: "/signup/insertMember",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(memberData),
			success: function(response) {
				alert("회원 가입 성공!");
				window.location.href = "/login";
			},
			error: function(xhr, staus, error) {
				alert("오류 발생 : " + xhr.responseText);
			}
		});
	} else {
		alert("비밀번호가 일치하지 않습니다.");
	}
}