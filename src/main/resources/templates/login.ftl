<!DOCTYPE html>
<html lang="en">
<head>
    <title>Video Shuffle</title>
    <link href="/style.css" rel="stylesheet" />
</head>
<body>
<h1>Login</h1>
<section class="container">
    <form action="/login" method="POST">
        <label>
            <input placeholder="Username" type="text" name="username">
        </label>

        <label>
            <input placeholder="Password" type="password" name="password">
        </label>

        <input class="button" type="submit">
        <p>${loginStatus}</p>
    </form>
</section>
</body>
</html>