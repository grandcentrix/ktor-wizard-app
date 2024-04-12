<!DOCTYPE html>
<html lang="en">
<head>
    <title>Video Shuffle</title>
    <link href="/style.css" rel="stylesheet" />
</head>
<body>
<h1>Sign-up</h1>
<section class="container">
    <form action="/signup" method="POST">
        <label>
            <input placeholder="Name" required type="text" name="name">
        </label>

        <label>
            <input placeholder="Surname" required type="text" name="surname">
        </label>

        <label>
            <input placeholder="E-mail" required type="text" name="email">
        </label>

        <label>
            <input placeholder="Username" required type="text" name="username">
        </label>

        <label>
            <input placeholder="Password" required type="password" name="password">
        </label>

        <label for="houses">Choose your Hogwarts' House:</label>
        <select name="houses" id="houses">
            <#list houses as house>
                <option name="house" value="${house}">${house}</option>
            </#list>
        </select>

        <input class="button" type="submit" value="Register">
        <p>${signUpStatus}</p>
    </form>
</section>
</body>
</html>