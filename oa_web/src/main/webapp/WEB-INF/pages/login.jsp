<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>欢迎使用</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Trendy Login Form template Responsive, Login form web template,Flat Pricing tables,Flat Drop downs  Sign up Web Templates, Flat Web Templates, Login sign up Responsive web template, SmartPhone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Custom Theme files -->
<link href="assets/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- //Custom Theme files -->
<!-- web font -->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'><!--web font-->
<!-- //web font -->
</head>
<body>
	<!-- main -->
	<div class="agileits-main"> 
	<div class="w3top-nav">	
				<div class="w3top-nav-left">	

				</div>	
				<div class="w3top-nav-right">	

				</div>	
				<div class="clear"></div>
			</div>	
		<div class="header-main">
		<h2>Login Now</h2>
			<div class="header-bottom">
				<div class="header-right w3agile">
					<div class="header-left-bottom agileinfo">
						<form action="/login" method="post">
							<div class="icon1">
								<input type="text" placeholder="Username" id="sn" name="sn" required=""/>
							</div>
							<div class="icon1">
								<input type="password" placeholder="Password" id="password" name="password" required=""/>
							</div>
							<div class="login-check">
								<span style="color: red"> ${msg3}</span>
							</div>
							<div class="bottom">
								<input type="submit" value="Log in" />
							</div>

					</form>	
					</div>
				</div>
			</div>
	</div>

	</div>	

</body>
</html>