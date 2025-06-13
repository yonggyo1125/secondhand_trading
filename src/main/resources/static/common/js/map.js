var commonLib = commonLib ?? {}
commonLib.mapLib = {
    // 카카오 맵 SDK를 동적 로딩
    init() {
        const headEl = document.head;
        // 카카오 맵 SDK 자바스크립트가 추가된 상태가 아니라면 추가
        if (!headEl.getElementById("kakao-map-sdk")) {
            const scriptEl = document.createElement("script");
            scriptEl.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=c353cbff16c035545fd3661e0c9019ed";
        }
    }
};

window.addEventListener("DOMContentLoaded", function() {
    commonLib.mapLib.init();
});