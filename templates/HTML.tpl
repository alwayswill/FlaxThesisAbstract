<html>
<head>
<title>{$title}</title>
<meta http-equiv='content-type' content='text/html;charset=utf-8' />
<meta name="identifier" content="{$identifier}">
<meta name="datestamp" content="{$datestamp}">
<meta name="setSpec" content="">
<meta name="setSpec" content="">

<meta name="creator" content="{$creator}">
<meta name="institution" content="{$institution}">
<meta name="publisher" content="{$publisher}">
<meta name="issued" content="{$issued}">
<meta name="type" content="{$dctype}">
<meta name="qualificationname" content="{$qualificationName}">
<meta name="qualificationlevel" content="{$qualificationLevel}">
<meta name="accessRights" content="{$accessRights}">
<meta name="dc_identifier" content="{$dcIdentifier}">
<meta name="dc_source" content="{$dcSource}">
<meta name="DDC" content="{$dcSubject}">

</head>
<body>
<div id='maintext'>
<h1>{$title}</h1>
<p>{$content}</p>
</div>
<div id='auxtext'>
<h2>Source</h2>
<ul class='source'>
<li>{$creator}. ({$issued}). {$title}. {$qualificationName}. {$institution}. Retrieved from {$dcSource}</li>
</ul>
</div>
</body>
</html>
