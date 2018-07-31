<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
   <head>
      <title>Shrink Url</title>
   </head>
   <script type="text/javascript">
      var shrinkurl='<c:out value="${shrinkurl}"/>';
      function validateForm(){
		if(document.getElementsByName("url")[0].value.trim().length>0){
			return true;
		}else{
			alert("Please Enter a Url");
			return false;
		}
      }
      
      function validateHits(){
		if(document.getElementsByName("shrinkurl")[0].value.trim().length>0){
			return true;
		}else{
			alert("Please Enter a Url");
			return false;
		}
      }
   </script>
   <body>
      ${message}</br>
      Alias for your url is <a href=${serverName}uri/${shrinkurl}>${serverName}uri/${shrinkurl}</a>
      <form action="shrinkurl" onsubmit="return validateForm()" method="post">
         <table>
            <tr>
               <td>Enter a long Url to shrink it:</td>
               <td><input type="text" name="url"></td>
            </tr>
            <tr>
               <td>Custom alias (optional): </td>
               <td>${serverName}uri/<input type="text" name="customUrl"></td>
            </tr>
            <tr>
               <td>Url Expiry in minutes (optional): </td>
               <td><input type="number" min="1" max="99999" name="expiry"></td>
            </tr>
            <tr>
               <td>Private Url Password (optional): </td>
               <td><input type="password" name="password"></td>
            </tr>
            <tr>
               <td><input type="submit" value="Shrink Url"></td>
            </tr>
         </table>
      </form>
      <form action="trackusage" onsubmit="return validateHits()" method="post">
         <table>
            <tr>
               <td>Enter a Url to get hits:</td>
               <td>${serverName}uri/<input type="text" name="shrinkurl"></td>
            </tr>
            <tr>
               <td><input type="submit" value="Get Url Hit Count "></td>
            </tr>
         </table>
      </form>
   </body>
</html>