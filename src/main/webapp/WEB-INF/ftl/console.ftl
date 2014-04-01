<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="/resources/css/style.css"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
<header>
	<img id="logo_image" src="/resources/image/logo.png"/>

	<h1>Recommender Lab</h1>
</header>
<#assign userList = consoleMetaData.userList/>
<#assign recommendationTypeList = consoleMetaData.recommendationTypeList/>
<div id="recommenderConsole">
	<div id="options" class="column">
		<h2>Options:</h2>

		<div id="recommendationTypeOption">
			<table>
				<tr>
				<#list recommendationTypeList as recommendationType>
					<td onclick="selectButton(this);" class="button"
						id="${recommendationType}">${recommendationType.displayName}</td>
				</#list>
				</tr>
			</table>
		</div>
		<div id="similarityTypeOption">
		</div>
		<form id="recommendationForm" action="/recommend">
			<div id="numericOptions">
				<label for="howMany">Number of recommendations:</label>
				<input required id="howMany" name="howMany" type="number"/>
			</div>
		</form>
	</div>
	<div id="users" class="column">
		<h2>Users:</h2>

		<table>
		<#list userList as user>
			<tr onclick="selectButton(this);">
				<#assign id= user.userId/>
				<td id="${id}" class="button">User ${id}</td>
			</tr>
		</#list>
		</table>
	</div>
	<div id="recommendations" class="column">
		<h2>Recommendations:</h2>
		<table>
		</table>
	</div>
</div>
<footer><p>&copy; Najum Ali 2014</p></footer>
<script>
	$(document).ready(function () {
		$("#recommendationTypeOption").find("td").on("click", function () {
			var recommendationType = $(this).attr('id');
			$.ajax({
				url: "/similarityTyp",
				data: "recommendationType=" + recommendationType,
				dataType: "json"
			}).done(function (data) {
				$("#similarityTypeOption").html("<table></table>");
				$("#numericOptions").css("display", "block");
				$.each(data, function (index, similarityType) {
					$("#similarityTypeOption table")
							.append('<tr onclick="selectButton(this);">' +
									'<td id="' + similarityType.enumName + '" class="button">'
									+ similarityType.displayName + '</td></tr>');
				});
			});
		});
	});

	$(document).ready(function () {
		$("#recommendationForm").submit(function (e) {
			var postData = getRecommendationRequestParameters($(this).serializeArray());
			var actionUrl = $(this).attr("action");
			$.ajax({
				url: actionUrl,
				type: "POST",
				dataType: "json",
				data: postData
			}).done(function (data) {
				alert("jahoo");
			});
			e.preventDefault();
//			e.unbind(); //unbind. to stop multiple form submit.
		});
	});

	function getRecommendationRequestParameters(requestParameterArray) {
		var recommendationType = $("#recommendationTypeOption").find(".selected").attr("id");
		var similarityMetric = $("#similarityTypeOption").find(".selected").find("td").attr("id");
		var userId = $("#users").find(".selected").find("td").attr("id");
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

	function selectButton(td) {
		$(td).siblings().removeClass("selected");
		$(td).addClass("selected");
	}
</script>
</body>
</html>
