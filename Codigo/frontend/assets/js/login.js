//const LOGIN_URL = "login.html";
const apiPath = "http://localhost:4567/login";

function generateUUID() {
    var d = new Date().getTime();
    var d2 = (performance && performance.now && (performance.now() * 1000)) || 0;
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16;
        if (d > 0) {
            r = (d + r) % 16 | 0;
            d = Math.floor(d / 16);
        } else {
            r = (d2 + r) % 16 | 0;
            d2 = Math.floor(d2 / 16);
        }
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}


async function fetchData(user) {
    try {
        const response = await fetch(apiPath, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        return response.json();
    } catch (error) {
        console.log(error);
    }
}



async function login(email, password) {

    const user = {
        "email": email,
        "password": password
    }

    let response = await fetchData(user);
    console.log(response);

    if (response.status == "SUCCESS") {
        sessionStorage.setItem("usuario", JSON.stringify(response.data));

        window.location = './frontend/pages/dashboard.html';

        return true;
    }else{
        return false;
    }

}


function logout() {
    console.log("logout")
    window.location = '../../index.html';
    sessionStorage.removeItem("usuario");
}




