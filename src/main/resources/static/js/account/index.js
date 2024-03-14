const addTopupButtonListener = () => {
    $(".topup-button").click(function(event) {
        $(".form-popup-layer").addClass("form-popup-layer-open");
        $(".form-popup-box").addClass("form-popup-box-open");
    });
};

const addSubmitButtonListener = () => {
    $(".form-popup-layer .form-popup-box .popup-save-button").click(function(event) {
        event.preventDefault();
        const username = $(this).attr("username");
        const accountTopupDTO = {
            topupBalance: $(".topup-amount").val()
        }

        $.ajax({
            method: "POST",
            url: `/account/topup/${username}`,
            data: JSON.stringify(accountTopupDTO),
            contentType: "application/json",
            success: (response) => {
                location.reload();
            },
            error: (response) => {
                $(".error-message-popup").text(response.responseJSON[0].defaultMessage);
            }
        });
    });
};

const addCloseButtonListener = () => {
    $(".form-popup-layer .form-popup-box .popup-close-button").click(function(event) {
        $(".form-popup-layer").removeClass("form-popup-layer-open");
        $(".form-popup-box").removeClass("form-popup-box-open");
        $('.form-popup-layer .form-popup-box input').val("");
        $('.form-popup-layer .form-popup-box .error-message-popup').text("");
    });
};

(() => {
    addTopupButtonListener();
    addSubmitButtonListener();
    addCloseButtonListener();
})();