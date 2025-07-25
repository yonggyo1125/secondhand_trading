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

    const editorImages = [];
    for (const {seq, fileUrl, location, fileName } of items) {
        switch (location) {
            case "editor":
                editorImages.push(fileUrl);
                break;
            default:
                //
        }

        insertEditorImage(editorImages);
    }
}