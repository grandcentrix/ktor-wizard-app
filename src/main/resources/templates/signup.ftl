<#import "_layout.ftl" as layout />
<@layout.base>

    <h1>
        <span class="material-symbols-outlined">edit_square</span>
        Sign-up
    </h1>

    <form class="form form-signup" action="/signup" method="POST">
        <div class="register-fields">
            <label>
                <input placeholder="Name" required type="text" name="name">
            </label>

            <label>
                <input placeholder="Surname" required type="text" name="surname">
            </label>
        </div>
        <div class="register-fields">
            <label>
                <input placeholder="E-mail" required type="email" name="email">
            </label>

            <label>
                <input placeholder="Username" required type="text" name="username">
            </label>
        </div>

        <div class="register-fields">
            <label>
                <input placeholder="Password" required type="password" name="password">
            </label>

            <div class="register-fields">
                <label class="select-label" for="houses">Choose your Hogwarts' House:</label>
                <select class="round-arrow" name="houses" id="houses">
                    <#list houses as house>
                        <option name="house" value="${house}">${house}</option>
                    </#list>
                </select>
            </div>
        </div>

        <input class="register-button button" type="submit" value="Register">

    </form>

</@layout.base>
