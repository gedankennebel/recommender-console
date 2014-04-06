<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Recommender Lab</title>
	<link rel="stylesheet" href="/resources/css/style.css"/>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/dark-hive/jquery-ui.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
</head>
<body>
<header>
	<img id="logo_image" src="/resources/image/logo.png"/>

	<h1><a href="/">Recommender Lab</a></h1>
</header>
<#assign userList = consoleMetaData.userList/>
<#assign recommendationTypeList = consoleMetaData.recommendationTypeList/>
<div id="recommenderConsole">
	<div id="options" class="column">
		<h2>Options:</h2>

		<div id="recommendationTypeOption">
		<#list recommendationTypeList as recommendationType>
			<span onclick="selectButton(this);" class="button" id="${recommendationType}">
			${recommendationType.displayName}
			</span>
		</#list>
		</div>
		<div id="formWrapper" class="inputForm">
			<form id="recommendationForm" action="/recommend/cf">
				<input min="1" required id="howMany" name="howMany" type="number" placeholder="#"/>
				<input type="submit">
			</form>
		</div>
		<div id="similarityTypeOption">
		</div>
	</div>
	<div id="recommendationsColumn" class="column">
		<h2>Recommendations:</h2>

		<div class="innerColumn">

			<div id="recommendationResult">
			</div>
		</div>
	</div>
	<div id="users" class="column">
		<h2>Users:</h2>

		<div id="quickSearch">
			<img src="/resources/image/search.png">
			<label for="quickUserSelect"> Quick select:
			</label>
			<input type="text" id="quickUserSelect"/>
		</div>
		<div class="innerColumn">
		<#list userList as user>
			<#assign userId= "User " + user.userId/>
			<span onclick="selectUserButton(this);" id="${userId}" class="button">
			${userId}
			</span>
		</#list>
		</div>
	</div>
	<div id="information" class="column">
		<h2>Information:</h2>

		<div id="fileInformation">
			<ul>
				<li>Users: ${consoleMetaData.numberOfUsers}</li>
				<li>Items: ${consoleMetaData.numberOfItems}</li>
			</ul>
		</div>

		<div id="calculationResults">
			<ul>
				<li id="calculatedTime"></li>
			</ul>
		</div>
	</div>
	<img id="backgroundImage" src="/resources/image/elephant.png"/>
</div>
<#--<footer><p>&copy; iSYS jStage 2014</p></footer>-->
<script type="text/javascript" src="/resources/js/similarityAjax.js"></script>
<script type="text/javascript" src="/resources/js/recommendationAjax.js"></script>
<script type="text/javascript">
	function selectUserButton(button) {
		var buttonId = $(button).attr("id");
		$("#quickUserSelect").val(buttonId);
		selectButton(button);
	}

	function selectButton(button) {
		$(button).siblings().removeClass("selected");
		$(button).addClass("selected");
	}

	$(function () {
		var availableTags = [
		<#list userList as user>
			<#assign userId= "User " + user.userId/>
			"${userId}",
		</#list>
		];
		$("#quickUserSelect").autocomplete({
			source: availableTags,
			select: function (event, ui) {
				var span = document.getElementById(ui.item.value);
				selectButton($(span));
			}
		});
	});
</script>
</body>
</html>
