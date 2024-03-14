const addInsertButtonListener = () => {
    $(".insert-button").click(function(event) {
        $(".form-popup-layer").addClass("form-popup-layer-open");
        $(".form-popup-box").addClass("form-popup-box-open");
    });
};

const addEditButtonListener = () => {
    $(".edit-button").click(function(event) {
        const shipperId = $(this).attr("shipper-id");

        $.ajax({
            url: `/shipper/${shipperId}`,
            success: (response) => {
                $(".form-popup-layer").addClass("form-popup-layer-open");
                $(".form-popup-box").addClass("form-popup-box-open");
                $(".shipper-id").val(response.shipperId);
                $(".name").val(response.name);
                $(".price").val(response.price);
                $(".service").val(response.service);
            }
        });
    });
};

const addSubmitButtonListener = () => {
    $(".form-popup-layer .form-popup-box .popup-save-button").click(function(event) {
        event.preventDefault();
        const shipperUpsertDTO = {
            shipperId: $(".shipper-id").val() == "" ? null : $(".shipper-id").val(),
            name: $(".name").val(),
            price: $(".price").val(),
            service: true
        };

        $.ajax({
            method: shipperUpsertDTO.shipperId == null ? "POST" : "PUT",
            url: "/shipper/upsert",
            data: JSON.stringify(shipperUpsertDTO),
            contentType: "application/json",
            success: (response) => {
                location.reload();
            },
            error: (response) => {
                if (response.status === 400) {
                    for (let res of response.responseJSON) {
                        $(`.form-popup-layer .form-popup-box [data-for=${res.field}]`).text(res.defaultMessage);
                    }
                }
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
    addInsertButtonListener();
    addEditButtonListener();
    addSubmitButtonListener();
    addCloseButtonListener();
})();