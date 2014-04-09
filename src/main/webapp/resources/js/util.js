function selectUserButton(button) {
    var buttonId = $(button).attr("id");
    $("#quickUserSelect").val(buttonId);
    selectButton(button);
}

function selectButton(button) {
    $(button).siblings().removeClass("selected");
    $(button).addClass("selected");
}

function getFormattedRequestParameter(name, value) {
    var queryString = {};
    queryString["name"] = name;
    queryString["value"] = value;
    return queryString;
}
