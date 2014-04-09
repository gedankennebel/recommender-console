var speedGauge = new JustGage({
    id: "gauge",
    value: 0,
    min: 0,
    max: 1000,
    title: "calculation time (ms)",
    titleFontColor: "white",
    valueFontColor: "white",
    showMinMax: false,
    decimals: true,
    donut: true,
    gaugeWidthScale: 0.2,
    startAnimationType: "linear",
    startAnimationTime: 300,
    refreshAnimationTime: 500,
    refreshAnimationType: "linear",
    counter: true
});

//	AJAX: Get recommendations with given parameters
$(document).ready(function () {
    $("#recommendationForm").submit(function (e) {
        var postData = getRecommendationRequestParameters($(this).serializeArray());
        var actionUrl = $(this).attr("action");
        $.ajax({
            url: actionUrl,
            type: "GET",
            dataType: "JSON",
            data: postData
        }).done(function (data) {
            $("#calculatedTime").text(data.calculationTime + " ms");
            speedGauge.refresh(data.calculationTime);
            $("#recommendationResult").html("");
            $.each(data.recommendedItemList, function (index, recommendation) {
                index++;
                var itemId = recommendation.itemID;
                $("#recommendationResult")
                    .append('<span class="button" id="' + itemId + '">'
                        + index + ". Item " + itemId +
                        " (Rating: " + recommendation.value + ")" + '</span>');
            });
            if (data.recommendationParameters.recommendationType == "ITEM_BASED") {
                $("#userId_recommendBecause").val(data.recommendationParameters.userId);
                $("#similarityType_recommendedBecause").val(data.recommendationParameters.appliedSimilarity.enumName);
                registerRecommendedBecauseAjaxEvent();
            }
        });
        e.preventDefault();
    });
});

function getRecommendationRequestParameters(requestParameterArray) {
    var recommendationType = $("#recommendationTypeOption").find(".selected").attr("id");
    var similarityMetric = $("#similarityTypeOption").find(".selected").attr("id");
    var userId = $("#users").find(".selected").attr("id").replace("User ", "");
    requestParameterArray.push(getFormattedRequestParameter("recommendationType", recommendationType),
        getFormattedRequestParameter("similarityMetric", similarityMetric),
        getFormattedRequestParameter("userId", userId));
    return requestParameterArray;
}
