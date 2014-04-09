function addFlipAnimation() {
    $("#recommendedBecause").append('<span onclick="flipback();" class="button backButton">go back!</span>');
    $("#flipWrapper").flip({
        direction: 'lr',
        content: $("#recommendedBecause"),
        speed: 300,
        color: 'rgba(140, 242, 255, 0.0)',
        onEnd: function () {
            registerRecommendedBecauseAjaxEvent();
        }
    });
}

function flipback() {
    $("#flipWrapper").revertFlip();
}
