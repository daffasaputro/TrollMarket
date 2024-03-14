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
            url: `/product/merchandise/productDetail/${productId}`,
            success: (response) => {
                $(".box-popup-layer").addClass("box-popup-layer-open");
                $(".box-popup-list").addClass("box-popup-list-open");
                $(".name").text(response.name);
                $(".category").text(response.category);
                $(".description").text(response.description);
                $(".price").text(rupiah(response.price));
                $(".discontinue").text(response.discontinue);
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
    addDetailButtonListener();
    addCloseButtonListener();
})();