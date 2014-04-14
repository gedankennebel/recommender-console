<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Recommender Lab</title>
	<link rel="stylesheet" href="/resources/css/recommenderLab.css"/>
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

<#--OPTIONS COLUMN-->
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

<#--RECOMMENDATIONS COLUMN-->
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

<#--USERS COLUMN-->
	<div id="users" class="column">
		<h2>Users</h2>

		<div id="quickSearch">
			<img src="/resources/image/search.png">
			<label id="quickSearchLabel" for="quickUserSelect">Fast User-ID selection: </label>
			<input type="number" name="term" id="quickUserSelect" autocomplete="off"
				   class="quickSelect" placeholder="#" required min="1"/>
		</div>
		<div class="innerColumn">
		<#list userList as user>
			<#assign userId = user.userId/>
			<span onclick="selectUserButton(this);" id="${userId}" class="button userButton">
			${userId}
			</span>
		</#list>
		</div>
	</div>

<#--INFORMATIONS COLUMN-->
	<div id="information" class="column">
		<h2>Information</h2>

		<div id="gauge"></div>
		<div id="fileInformation">
			<ul>
				<li>Total Users: <span>${consoleMetaData.numberOfUsers}</span></li>
				<li>Total Items: <span>${consoleMetaData.numberOfItems}</span></li>
				<li>
					(Mahout) DataModel created in: <span>${consoleMetaData.dataModelCreationTime} seconds</span>
				</li>
				<li>Neighborhood type (user-based): <span>${settings.neighborhoodType.displayName}</span></li>
				<li>Neighborhood threshold: <span>${settings.neighborhoodThreshold}</span></li>
				<li>Recommender-Caching: <span>
				<#if settings.cachingRecommender>
					enabled
				<#else >
					disabled
				</#if>
				</span>
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
	</div>
	<img id="backgroundImage" src="/resources/image/elephant.png"/>
</div>
<#--<footer><p>&copy; iSYS jStage 2014</p></footer>-->
<script type="text/javascript" src="/resources/js/lib/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/jquery-ui.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/raphael.min.js"></script>
<script type="text/javascript" src="/resources/js/lib/justGage.js"></script>
<script type="text/javascript" src="/resources/js/lib/jquery.flip.min.js"></script>
<script type="text/javascript" src="/resources/js/recommenderLab.js"></script>
</body>
</html>
