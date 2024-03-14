const rupiah = (number) => {
    return new Intl.NumberFormat("id-ID", {
      style: "currency",
      currency: "IDR"
    }).format(number);
}

const addDetailButtonListener = () => {
    $(".detail-button").click(function(event) {
        var productId = $(this).attr("productId");

        $.ajax({
            url: `/product/detail/${productId}`,
            success: (response) => {
                $(".box-popup-layer").addClass("box-popup-layer-open");
                $(".box-popup-list").addClass("box-popup-list-open");
                $(".name").text(response.name);
                $(".category").text(response.category);
                $(".description").text(response.description);
                $(".price").text(rupiah(response.price));
                $(".seller-name").text(response.seller);
            }
        });
    });
};

const addAddToCartButtonListener = () => {
    $(".add-to-cart-button").click(function(event) {
        var productId = $(this).attr("productId");
        var buyerUsername = $(this).attr("buyerUsername");

        $.ajax({
            url: "/product/shipperDropdown",
            success: (response) => {
                let shipperDropdownDTO;

                for (let i = 0; i < response.length; i++) {
                    shipperDropdownDTO += `<option value=${response[i].value}>${response[i].text}</option>`;
                }

                $(".form-popup-layer").addClass("form-popup-layer-open");
                $(".form-popup-box").addClass("form-popup-box-open");
                $(".product-id").val(productId);
                $(".buyer-username").val(buyerUsername);
                $(".shipper").html(shipperDropdownDTO);
            }
        });
    });
};

const addSubmitButtonListener = () => {
    $(".form-popup-layer .form-popup-box .popup-save-button").click(function(event) {
        event.preventDefault();
        const productAddToCartDTO = {
            productId: $(".product-id").val(),
            buyerUsername: $(".buyer-username").val(),
            shipperId: $(".shipper").val(),
            quantity: $(".quantity").val()
        };

        $.ajax({
            method: "POST",
            url: "/product/addToCart",
            data: JSON.stringify(productAddToCartDTO),
            contentType: "application/json",
            success: (response) => {
                location.reload();
            },
            error: (response) => {
                console.log(response);
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
    $(".popup-close-button").click(function() {
        $(".box-popup-layer").removeClass("box-popup-layer-open");
        $(".box-popup-list").removeClass("box-popup-list-open");
        $(".form-popup-layer").removeClass("form-popup-layer-open");
        $(".form-popup-box").removeClass("form-popup-box-open");
        $('.form-popup-layer .form-popup-box select').html("");
        $('.form-popup-layer .form-popup-box input').val("");
        $('.form-popup-layer .form-popup-box .error-message-popup').text("")
    });
};

(() => {
    addAddToCartButtonListener();
    addSubmitButtonListener();
    addDetailButtonListener();
    addCloseButtonListener();
})();