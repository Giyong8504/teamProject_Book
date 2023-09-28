
window.addEventListener("DOMContentLoaded", function() {
    const uploadFiles = document.getElementsByClassName("upload_files");
    const fileEl = document.getElementById("file");
    if (fileEl) {
        for (const el of upload_files) {
            el.addEventListener("click", function() {
                fileEl.click();
            });
        }
    }
});