// DOMContentLoaded 이벤트에서 탭 설정 및 상태 관리
document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".tab-button");
    const tabs = document.querySelectorAll(".content-tab");

    // 기본 탭 설정 (내 레시피)
    const defaultTab = "my-recipes";
    const initialTab = sessionStorage.getItem("activeTab") || defaultTab;
    showTab(initialTab, false);

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            const targetId = this.getAttribute("data-target");
            showTab(targetId, true);
        });
    });

    // URL 변경 없이 탭 상태 관리
    function showTab(targetId, updateHistory = true) {
        tabs.forEach(tab => tab.classList.remove("active"));
        document.getElementById(targetId).classList.add("active");

        buttons.forEach(btn => btn.classList.remove("active"));
        document.querySelector(`[data-target="${targetId}"]`).classList.add("active");

        sessionStorage.setItem("activeTab", targetId); // 탭 상태 저장

        if (updateHistory) {
            history.pushState({ tab: targetId }, "", `#${targetId}`);
        }
    }

    // 뒤로 가기/앞으로 가기 버튼 처리
    window.addEventListener("popstate", function (event) {
        if (event.state && event.state.tab) {
            showTab(event.state.tab, false);
        }
    });
});

// 로그아웃 버튼 클릭 시 logout() 함수 호출
document.getElementById("logoutLink")?.addEventListener("click", function(event) {
    event.preventDefault();  // 기본 링크 동작을 막음
    logout();  // 로그아웃 함수 호출
});
function logout() {
    sessionStorage.clear();  // 로그아웃 시 모든 세션 스토리지 초기화
    window.location.href = '/';  // 로그아웃 후 홈으로 이동
}

// 메시지 읽음 처리
function markMessageRead(messageId) {
    fetch('/mypage/message/' + messageId + '/read', {
        method: 'POST'
    }).then(response => {
        if (response.ok) {
            alert('메시지 읽음 처리되었습니다.');
        }
    });
}

// 메시지 삭제
function deleteMessage(messageId) {
    fetch('/mypage/message/' + messageId, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            alert('메시지가 삭제되었습니다.');
            location.reload();
        }
    });
}

let offset = 0;
const limit = 10;
const memId = document.getElementById("memId").value;
let loading = false;
let currentType = "recipes"; // 기본값

document.querySelectorAll(".tab-button").forEach(button => {
    button.addEventListener("click", function () {
        currentType = this.dataset.type; // 현재 선택된 탭 타입 변경
        offset = 0; // 새로운 데이터 로딩을 위해 offset 초기화
        document.getElementById(`${currentType}-container`).innerHTML = ""; // 기존 데이터 삭제
        loadMoreData(currentType);
    });
});

function loadMoreData(type) {
    if (loading) return;
    loading = true;

    fetch(`/api/mypage/${type}?memId=${memId}&offset=${offset}&limit=${limit}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                renderData(type, data);
                offset += limit;
            }
            loading = false;
        })
        .catch(error => {
            console.error("Error loading data:", error);
            loading = false;
        });
}

function renderData(type, data) {
    const container = document.getElementById(`${type}-container`);
    data.forEach(item => {
        const div = document.createElement("div");
        div.classList.add("item");
        div.innerHTML = `<p>${item.title || item.name || item.content}</p>`;
        container.appendChild(div);
    });
}

// 무한 스크롤 이벤트
window.addEventListener("scroll", () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        loadMoreData(currentType);
    }
});
