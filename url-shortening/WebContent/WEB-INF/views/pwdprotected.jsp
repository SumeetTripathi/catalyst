<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Shrink Url</title>
</head>

<body>
	<form action="pwdprotected" method="post">
		<table>
			<tr>
				<td>Please Enter password</td> 
				<td>
					<input type="password" name="pwd">
					<input type="hidden" id="url" name="url" value=${shrinkurl}>
				</td>
			</tr>
			
			<tr>
				<td><input type="submit" value="Open Url"></td>
			</tr>
		</table>
	</form>
</body>
</html>

