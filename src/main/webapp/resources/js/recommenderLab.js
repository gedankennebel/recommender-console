// Gauge chart for calculations speed of recommendations
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

// UTILS
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
      renderSimilarities(data);
    });
    e.preventDefault();
  });
});

function renderSimilarities(data) {
  $("#similarityTypeOption").html("");
  $("#formWrapper").css("display", "block");
  $.each(data, function (index, similarityType) {
    $("#similarityTypeOption")
        .append('<span onclick="selectButton(this);" id="'
            + similarityType.enumName + '" class="button">'
            + similarityType.displayName +
            '</span>');
  });
}

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
  var userId = $("#quickUserSelect").val();
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

//	AJAX: Get recommendations because
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
  isFlipped = true;
}

function flipback() {
  $("#flipWrapper").revertFlip();
  isFlipped = false;
}

// Set recommended because number
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

//User ID quick select --> suggestions AJAX
$(document).ready(function () {
  var cache = {};
  $("#quickUserSelect").autocomplete({
    minLength: 2,
    source: function (request, response) {
      var term = request.term;
      if (term in cache) {
        response(cache[ term ]);
        return;
      }
      $.getJSON("/user/suggest", request, function (data) {
        cache[ term ] = data;
        response(data);
      });
    }, select: function (event, ui) {
      var span = document.getElementById(ui.item.value);
      if (span != null) {
        selectButton($(span));
      } else {
        $("#users").find(".userButton").siblings().removeClass("selected");
      }
    }
  });
});
