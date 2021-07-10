
function clickDemo() {

    window.open("http://localhost:8080/index/login", "_blank")
}

function addUser(value) {

    var password = $("#password").text();
    var email = $("#email").text();

    console.log(username+","+password+","+email);

}