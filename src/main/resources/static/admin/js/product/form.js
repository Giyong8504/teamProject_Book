window.addEventListener("DOMContentLoaded", function() {
    ClassicEditor
    		.create( document.querySelector( '#description' ), {
    		    height: 350
    		} )
    		.then( editor => {
    			window.editor = editor;
    			editor.ui.view.editable.element.style.height = '450px';
    		} )
    		.catch( err => {
    			console.error( err.stack );
    		} );

    /** 파일 삭제 버튼 클릭 처리 */
    const removeEls = document.querySelectorAll(".uploaded_images .remove");
    for (const el of removeEls) {
        el.addEventListener("click", deleteFile);
    }
});

function insertEditor(imgUrl) {
    editor.execute( 'insertImage', { source: imgUrl } );
}

function fileUploadCallback(files) {
    if (!files || files.length == 0) {
        return;
    }

    const { fileManager } = commonLib;


    const tplImage = document.getElementById("tpl_image1").innerHTML;
    const tplEditor = document.getElementById("tpl_editor").innerHTML;

    const mainImages = document.getElementById("main_images");
    const listImages = document.getElementById("list_images");
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
            case "list":
                html = tplImage;
                targetEl = listImages;
                break;
            case "editor" :
                html = tplEditor;
                targetEl = editorImages;

                /** 이미지 에디터 본문 삽입 */
                insertEditor(file.fileUrl);
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
        removeEl.addEventListener("click", deleteFile);

        const insertEditorEl = fileEl.querySelector(".insert_editor");
        if (insertEditorEl) {
            insertEditorEl.addEventListener("click", function() {
                insertEditor(this.dataset.url);
            });
        }
    }
}

function deleteFile() {
  const { fileManager } = commonLib;
  if (!confirm('정말 삭제하시겠습니까?')) {
     return;
  }

    const id = this.dataset.id;
    fileManager.delete(id)
        .then(res => {
            const el = document.getElementById(`file_${id}`);
            el.parentElement.removeChild(el);
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        })

}