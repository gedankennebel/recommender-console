// AJAX: Get similarity metric for selected recommender type
$(document).ready(function () {
    $("#recommendationTypeOption").find("span").click(function (e) {
        var recommendationType = $(this).attr('id');
        $.ajax({
            url: "/similarityTyp",
            data: "recommendationType=" + recommendationType,
            type: "GET",
            dataType: "JSON"
        }).done(function (data) {
            $("#similarityTypeOption").html("");
            $("#formWrapper").css("display", "block");
            $.each(data, function (index, similarityType) {
                $("#similarityTypeOption")
                    .append('<span onclick="selectButton(this);" id="'
                        + similarityType.enumName + '" class="button">'
                        + similarityType.displayName +
                        '</span>');
            });
        });
        e.preventDefault();
    });
});
