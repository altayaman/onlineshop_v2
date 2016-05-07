<%@ page session="true"%>

Bye '<%= session.getAttribute("clientName")%>', you are logged out.
<% session.invalidate(); %>

<p><a href="index.html">Login page</a>
