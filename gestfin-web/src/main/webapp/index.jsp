<html>
<head>
<title>Logout</title>
</head>
<body>
	<%
		String redirect = request.getContextPath();
	    if(request.getUserPrincipal() == null){
			redirect += "/login.jsf";
	    }else{
			redirect += "/pages/home/home.jsf";
	    }
	    response.sendRedirect(redirect);
	%>
</body>
</html>