<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Recommender Lab</title>
	<link rel="stylesheet" href="/resources/css/style.css"/>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/dark-hive/jquery-ui.css">
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
		<h2>Options</h2>

		<div id="recommendationTypeOption">
		<#list recommendationTypeList as recommendationType>
			<span onclick="selectButton(this);" class="button" id="${recommendationType}">
			${recommendationType.displayName}
			</span>
		</#list>
		</div>
		<div id="formWrapper" class="inputForm">
			<form id="recommendationForm" action="/recommendation/cf">
				<label id="howManyLabel" for="howMany">#recommendations: </label>
				<input class="howManyInput" min="1" required id="howMany" name="howMany" placeholder="#" type="number"
					   value="${settings.numberOfRecommendation}"/>
				<input type="submit">
			</form>
		</div>
		<div id="similarityTypeOption">
		</div>
	</div>
	<div id="recommendationsColumn" class="column">
		<h2>Recommendations</h2>

		<div class="innerColumn">
			<form id="recommendedBecauseForm" action="/recommendation/because">
				<input id="similarityType_recommendedBecause" name="similarityMetric" type="hidden">
				<input id="userId_recommendBecause" name="userId" type="hidden">
			</form>
			<div id="flipWrapper" style="height: 15em">
				<div id="recommendationResult">
				</div>
				<div id="recommendedBecause" style="display: none">
				</div>
			</div>
		</div>
	</div>
	<div id="users" class="column">
		<h2>Users</h2>

		<div id="quickSearch">
			<img src="/resources/image/search.png">
			<label id="quickSearchLabel" for="quickUserSelect">Quick select:</label>
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
		<h2>Information</h2>

		<div id="fileInformation">
			<ul>
				<li>Total Users: <span>${consoleMetaData.numberOfUsers}</span></li>
				<li>Total Items: <span>${consoleMetaData.numberOfItems}</span></li>
				<li>
					(Mahout) DataModel created in: <span>${consoleMetaData.dataModelCreationTime} ms</span>
				</li>
				<li>
					<form id="recommendationBecauseForm" action="/settings/because" method="POST">
						<label id="howManyBecauseLabel" for="howManyBecause">#recommended because: </label>
						<input min="1" class="howManyBecauseInput" required id="howManyBecause" name="howManyBecause"
							   placeholder="#" type="number"
							   value="${settings.numberOfRecommendedBecause}"/>
						<span class="successText" id="settingsSuccess">saved!</span>
						<span class="failText" id="settingsFail">invalid input!</span>
					</form>
				</li>
			</ul>
		</div>

		<div id="calculationResults">
			<div id="gauge"></div>
		</div>
	</div>
	<img id="backgroundImage" src="/resources/image/elephant.png"/>
</div>
<#--<footer><p>&copy; iSYS jStage 2014</p></footer>-->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://justgage.com/demos/lib/raphael.2.1.0.min.js"></script>
<script type="text/javascript" src="http://justgage.com/justgage.js"></script>
<script type="text/javascript" src="/resources/js/speedGauge.js"></script>
<script type="text/javascript" src="/resources/js/lib/jquery.flip.min.js"></script>
<script type="text/javascript" src="/resources/js/util.js"></script>
<script type="text/javascript" src="/resources/js/similarityAjax.js"></script>
<script type="text/javascript" src="/resources/js/recommendationAjax.js"></script>
<script type="text/javascript" src="/resources/js/recommendedBecauseAjax.js"></script>
<script type="text/javascript" src="/resources/js/flipAnimation.js"></script>
<script type="text/javascript" src="/resources/js/settingsAjax.js"></script>
<script type="text/javascript">
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
