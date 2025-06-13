window.addEventListener("DOMContentLoaded", function() {
    const { mapLib } = commonLib;

    const el = document.getElementById("map");
    mapLib.load(el);
});