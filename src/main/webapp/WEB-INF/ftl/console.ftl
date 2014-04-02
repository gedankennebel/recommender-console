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
		<form id="recommendationForm" action="/recommend/cf">
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
	<div id="recommendationsColumn" class="column">
		<h2>Recommendations:</h2>

		<div id="recommendationResult">
		</div>
	</div>
</div>
<footer><p>&copy; iSYS jStage 2014</p></footer>
<script src="/resources/js/similarityAjax.js"></script>
<script src="/resources/js/recommendationAjax.js">
</script>
</body>
</html>
