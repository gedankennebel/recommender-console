// AJAX: Get similarity metric for selected recommender type
$(document).ready(function () {
    $("#recommendationTypeOption").find("td").on("click", function (e) {
        var recommendationType = $(this).attr('id');
        $.ajax({
            url: "/similarityTyp",
            data: "recommendationType=" + recommendationType,
            type: "GET",
            dataType: "JSON"
        }).done(function (data) {
            $("#similarityTypeOption").html("<table></table>");
            $("#numericOptions").css("display", "block");
            $.each(data, function (index, similarityType) {
                $("#similarityTypeOption").find("table")
                    .append('<tr onclick="selectButton(this);">' +
                        '<td id="' + similarityType.enumName + '" class="button">'
                        + similarityType.displayName + '</td></tr>');
            });
        });
        e.preventDefault();
    });
});
