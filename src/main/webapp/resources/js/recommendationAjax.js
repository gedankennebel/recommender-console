var isFlipped = false;

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
            if (isFlipped) {
                flipback();
                setTimeout(function () {
                    renderRecommendation(data);
                    prepareRecommendedBecauseForItemBased(data.recommendationParameters);
                }, data.calculationTime + 1000);
            }
            else {
                renderRecommendation(data);
                prepareRecommendedBecauseForItemBased(data.recommendationParameters);
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

function renderRecommendation(data) {
    speedGauge.refresh(data.calculationTime);
    $("#recommendationResult").html("");
    $.each(data.recommendedItemList, function (index, recommendation) {
        index++;
        var itemId = recommendation.itemID;
        $("#recommendationResult")
            .append('<span class="button" id="' + itemId + '" ' + '>'
                + index + ". Item " + itemId +
                " (Rating: " + recommendation.value + ")" + '</span>');
    });
}

function prepareRecommendedBecauseForItemBased(param) {
    if (param.recommendationType == "ITEM_BASED") {
        $("#userId_recommendBecause").val(param.userId);
        $("#similarityType_recommendedBecause").val(param.appliedSimilarity.enumName);
        registerRecommendedBecauseAjaxEvent();
    } else {
        if (param.recommendationType == "USER_BASED") {
            $("#recommendationResult").find("span").addClass("staticButton");
        }
    }
}


