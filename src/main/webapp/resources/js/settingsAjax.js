var keyUpTime = 1000; // 1.5 sec
var keyUpTimeout = null;
$("#howManyBecause").change(function (e) {
    clearTimeout(keyUpTimeout);
    keyUpTimeout = setTimeout(function () {
        sendAjax();
    }, keyUpTime);
    e.preventDefault();
});
function sendAjax() {
    $.ajax({
        url: "/settings/because",
        type: "POST",
        data: $("#recommendationBecauseForm").serializeArray()
    }).done(function () {
        $("#settingsSuccess").css("display", "inline").fadeOut(2500);
    }).fail(function () {
        $("#settingsFail").css("display", "inline").fadeOut(2500);
    });
}
