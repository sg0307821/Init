<header>
	<nav class="navbar navbar-expand-md navbar-dark"
		style="background-color: tomato">
		<div>
			<a href="<%= request.getContextPath() %>/login" class="navbar-brand"> Hotel Management App</a>
		</div>

		<ul class="navbar-nav navbar-collapse justify-content-end">
			<li><a href="<%= request.getContextPath() %>/login" class="nav-link">Login</a></li>
			<li><a href="<%= request.getContextPath() %>/register" class="nav-link">Signup</a></li>
			<li><a href="<%= request.getContextPath() %>/customerLogin" class="nav-link">Customer Login</a></li>
		</ul>
	</nav>
</header>