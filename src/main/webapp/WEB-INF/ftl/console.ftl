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
					<td class="button" id="${recommendationType}">${recommendationType.displayName}</td>
				</#list>
				</tr>
			</table>
		</div>
		<div id="similarityTypeOption">
		</div>
	</div>
	<div id="users" class="column">
		<h2>Users:</h2>

		<table>
		<#list userList as user>
			<tr>
				<td class="button">User ${user.userId}</td>
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
		$("#recommendationTypeOption td").on("click", function () {
			var recommendationType = $(this).attr('id');
			$.ajax({
				url: "/similarityTyp",
				data: "recommendationType=" + recommendationType,
				dataType: "json"
			}).done(function (data) {
				$("#similarityTypeOption").html("<table class='buttons'></table>");
				$.each(data, function (index, similarityType) {
					$("#similarityTypeOption table")
							.append('<tr><td class="button">' + similarityType.displayName + '</td></tr>');
				});
			});
		});
	});
</script>
</body>
</html>
