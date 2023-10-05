function fileUploadCallback(files) {
    if (!files || files.length == 0) {
        return;
    }

    const { fileManager } = commonLib;


    const tplImage = document.getElementById("tpl_image1").innerHTML;
    const tplEditor = document.getElementById("tpl_editor").innerHTML;

    const mainImages = document.getElementById("main_images");
    const editorImages = document.getElementById("editor_images");
    const domParser = new DOMParser();

    for (const file of files) {
        const location = file.location;
        let html = "", targetEl;
        switch(location) {
            case "main" :
                html = tplImage;
                targetEl = mainImages;
                break;
            case "editor" :
                html = tplEditor;
                targetEl = editorImages;
                break;
        }

        html = html.replace(/\[id\]/gm, file.id)
                    .replace(/\[url\]/gm, file.thumbsUrl[0])
                    .replace(/\[fileName\]/gm, file.fileName)
                    .replace(/\[orgUrl\]/gm, file.fileUrl);

        const dom = domParser.parseFromString(html, "text/html");
        const fileEl = dom.querySelector("body > *");
        targetEl.appendChild(fileEl);

        const removeEl = fileEl.querySelector(".remove");
        removeEl.addEventListener("click", function() {
            if (!confirm('정말 삭제하시겠습니까?')) {
                return;
            }

            const id = this.dataset.id;
            fileManager.delete(id);
        });
    }
}