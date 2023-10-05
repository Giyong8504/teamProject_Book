/* 책 상세 */
window.addEventListener("DOMContentLoaded", function() {
    const actionButtons = document.getElementsByClassName("action_button");
    for (const el of actionButtons) {
        el.addEventListener("click", function() {
            frmView.mode.value = this.dataset.mode;

            frmView.submit();
        });
    }
});