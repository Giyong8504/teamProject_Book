window.addEventListener("DOMContentLoaded", function() {
    const actionButtons = document.getElementsByClassName("action_button");
    const chkNos = document.getElementsByName("chkNo");
    const target = frmCart._method;

    for (const el of actionButtons) {
        el.addEventListener("click", function() {
            try {
                if (!confirm('정말 진행하시겠습니까?')) {
                    return;
                }

                /* 상품 선택 상태 체크 S */
                let isChecked = false;
                const cartNos = [];
                if (chkNos && chkNos.length > 0) {
                    for (const chkNo of chkNos) {
                        if (chkNo.checked) {
                            isChecked = true;

                            const el = document.querySelector(`[name='cartNo_${chkNo.value}']`);
                            if (el) cartNos.push(`cartNo=${el.value}`);
                        }
                    }
                }
                if (!isChecked) throw new Error("상품을 선택하세요.");
                /* 상품 선택 상태 체크 E */

                const mode = this.dataset.mode;

                switch(mode) {
                    case "edit" : target.value='PATCH'; frmCart.submit(); break;
                    case "delete" : target.value='DELETE'; frmCart.submit(); break;
                    case "order" : location.href = "/order?" + cartNos.join("&"); break;
                }

            } catch (err) {
                alert(err.message);

            }
        });
    }
});