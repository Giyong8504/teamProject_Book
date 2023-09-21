/** claim.html period 선택 js S */
window.addEventListener("DOMContentLoaded", function() {
    const searchPeriodEls = document.querySelectorAll(".c_search_period .c_period");
    for (const el of searchPeriodEls) {
        el.addEventListener("click", function() {
            const els = document.querySelectorAll(".c_search_period .c_period");
            for (const e of els) {
                e.classList.remove("c_period_on");
                e.classList.remove("c_period_off");
                e.classList.add("c_period_off");
            }
            this.classList.remove("c_period_off");
            this.classList.add("c_period_on");
        });
    }
});
/** claim.html period 선택 js E */
