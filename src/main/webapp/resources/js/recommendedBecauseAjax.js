function registerRecommendedBecauseAjaxEvent() {
    $("#recommendationResult").find("span").click(function (e) {
        var actionUrl = $("#recommendedBecauseForm").attr("action");
        $.ajax({
            url: actionUrl,
            data: getRequestData($(this)),
            type: "GET",
            dataType: "JSON"
        }).done(function (data) {
            renderRecommendedBecause(data);
            addFlipAnimation();
        });
        e.preventDefault();
    });
}

function renderRecommendedBecause(data) {
    $("#recommendedBecause").html("");
    $("#recommendedBecause").prepend('<h3>Item ' + data.recommendationParameters.itemId + ' was recommended because:</h3>');
    $.each(data.recommendedItemList, function (index, recommendation) {
        index++;
        var itemId = recommendation.itemID;
        $("#recommendedBecause").append('<span class="button staticButton" id="' + itemId + '">'
            + index + ". Item " + itemId +
            " (Rating: " + recommendation.value + ")" + '</span>');
    });
}

function getRequestData(button) {
    var requestParameterArray = $("#recommendedBecauseForm").serializeArray();
    var itemId = $(button).attr("id");
    requestParameterArray.push(getFormattedRequestParameter("itemId", itemId));
    return requestParameterArray;
}
