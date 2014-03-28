<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="/resources/css/style.css"/>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>

<img src="/resources/image/logo.png"/>

<h1>Recommender Console</h1>

<div id="users">
	<h2>Users:</h2>
	<ul>
	<#list userList as user>
		<li>
			<a href="javascript:">Item ${user.userId}</a>
		</li>
	</#list>
	</ul>
</div>
<div id="recommendations">
	<h2>Recommendations</h2>
	<ul>
		<li></li>
	</ul>
</div>
<div id="options">
	<label for="recommendationType">Choose CF-Recommendation approach:</label>
	<select id="recommendationType">
	<#list recommendationTypeList as recommendationType>
		<option value="${recommendationType.name}">${recommendationType.displayName}</option>
	</#list>
	</select>
</div>
<#--<script type="text/javascript">-->

<#--function getRecommendation(recommenderType, userId, howMany) {-->

<#--}-->
<#--</script>-->
</body>
</html>
