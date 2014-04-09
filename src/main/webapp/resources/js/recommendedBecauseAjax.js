function registerRecommendedBecauseAjaxEvent() {
    $("#recommendationResult").find("span").click(function (e) {
        var actionUrl = $("#recommendedBecauseForm").attr("action");
        $.ajax({
            url: actionUrl,
            data: getRequestData($(this)),
            type: "GET",
            dataType: "JSON"
        }).done(function (data) {
            alert(JSON.stringify(data));
        });
        e.preventDefault();
    });
}

function getRequestData(button) {
    var requestParameterArray = $("#recommendedBecauseForm").serializeArray();
    var itemId = $(button).attr("id");
    requestParameterArray.push(getFormattedRequestParameter("itemId", itemId));
    return requestParameterArray;
}
