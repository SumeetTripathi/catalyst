<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Track Usage</title>
</head>
<script type="text/javascript">
	  var hits ="${hits}";
	   
   </script>
<body>
	Total hits for = ${hits}
	<form action="shrinkurl" method="post">
		<table>
			<tr>
				<td>Enter a long Url to shrink it:</td> 
				<td><input type="text" name="url"></td>
			</tr>
			<tr>
				<td>Custom alias (optional): </td>
				<td><input type="text" name="customUrl"></td>
			</tr>
			<tr>
				<td>Url Expiry in minutes (optional): </td>
				<td><input type="number" min="1" max="99999" name="expiry"></td>
			</tr>
			<tr>
				<td>Max Url Hits (optional): </td>
				<td><input type="number" min="1" max="600" name="privateCount"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Shrink Url"></td>
			</tr>
		</table>
	</form>
	
	<form action="trackusage" method="post">
	<table>
	<tr>
		<td>Enter a Url to get hits:</td> <td><input type="text" name="shrinkurl"></td></tr>
		<tr> <td><input type="submit" value="Get Url Hit Count "></td></tr></table>
		
	</form>
</body>
</html>

