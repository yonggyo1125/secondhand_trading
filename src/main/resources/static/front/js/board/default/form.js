window.addEventListener("DOMContentLoaded", function() {
    // 위지윅 에디터 로드 S
    const { loadEditor } = commonLib;
    const el = document.getElementById("content");
    loadEditor(el)
        .then(editor => {
            window.editor = editor;
        })
    // 위지윅 에디터 로드 E

});


/**
* 파일 업로드 후속 처리
*
*/
function fileUploadCallback(items) {
    if (!items || items.length === 0) return;

    const { insertEditorImage } = commonLib;

    const editorTarget = document.querySelector(".editor-files");
    const attachTarget = document.querySelector(".attach-files");

    const editor_tpl = document.getElementById("editor-tpl").innerHTML;
    const attach_tpl = document.getElementById("attach-tpl").innerHTML;

    const domParser = new DOMParser();

    const editorImages = [];
    for (const {seq, fileUrl, location, fileName } of items) {
        let targetEl, tpl;
        switch (location) {
            case "editor":
                editorImages.push(fileUrl);
                targetEl = editorTarget;
                tpl = editor_tpl;
                break;
            default:
                targetEl = attachTarget;
                tpl = attach_tpl;
        }

        insertEditorImage(editorImages); // 에디터에 이미지 추가

        // 템플릿 치환
        tpl = tpl.replace(/\[seq\]/g, seq)
                .replace(/\[fileName\]/g, fileName)
                .replace(/\[fileUrl\]/g, fileUrl);
        const dom = domParser.parseFromString(tpl, "text/html");
        const fileItem = dom.querySelector(".file-items");

        targetEl.append(fileItem);
    }
}