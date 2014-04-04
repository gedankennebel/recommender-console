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
            $("#recommendationResult").html("<table></table>");
            $.each(data, function (index, recommendation) {
                $("#recommendationResult").find("table")
                    .append('<tr class="button">' +
                        '<td>' + recommendation.itemID + '</td>' +
                        '<td>' + recommendation.value + '</td>' +
                        '</tr>')
            });
        });
        e.preventDefault();
    });
});

function getRecommendationRequestParameters(requestParameterArray) {
    var recommendationType = $("#recommendationTypeOption").find(".selected").attr("id");
    var similarityMetric = $("#similarityTypeOption").find(".selected").attr("id");
    var userId = $("#users").find(".selected").attr("id");
    requestParameterArray.push(getFormattedRequestParameter("recommendationType", recommendationType),
        getFormattedRequestParameter("similarityMetric", similarityMetric),
        getFormattedRequestParameter("userId", userId));
    return requestParameterArray;
}

function getFormattedRequestParameter(name, value) {
    var queryString = {};
    queryString["name"] = name;
    queryString["value"] = value;
    return queryString;
}

//
function selectButton(td) {
    $(td).siblings().removeClass("selected");
    $(td).addClass("selected");
}
