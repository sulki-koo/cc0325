function validateForm() {
    let memId = document.getElementById("username").value;
    let memPassword = document.getElementById("password").value;
    let memName = document.getElementById("name").value;
    let memNickname = document.getElementById("nickname").value;

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
