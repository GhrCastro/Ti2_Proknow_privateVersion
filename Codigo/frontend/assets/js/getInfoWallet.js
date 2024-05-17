
async function getInfoWallet() {
    const user = getSessionUser();
    //console.log(user);

    let userWallet = {};

    await fetch(`http://localhost:4567/wallets/${user.id}`)
        .then(response => response.json())
        .then(data => {

            userWallet = data.data;
        })

        .catch(responseError => {
            console.error("Erro ao buscar informações da carteira do usuario!", responseError);
        })
    
    //console.log(userWallet);
    var balance = document.getElementById("balance");
    var hidden_icon = document.getElementById("hidden-icon");
    var userName = document.getElementById("welcomeUser");
    userName.innerText = `Seja Bem vindo, ${user.name}`;

    document.getElementById("hidden-balance").addEventListener("click", function () {
        if (balance.classList.contains("hidden")) {
            balance.classList.remove("hidden");
            balance.innerText = "R$ 1920.89"; // Retorna o saldo original ao mostrar
            hidden_icon.src = "../assets/images/disable-visualy-wallet.png"
        } else {
            balance.classList.add("hidden");
            balance.innerText = "R$ ******"; // Substitui o saldo por asteriscos ao ocultar
            hidden_icon.src = "../assets/images/enable-visualy-wallet.png"

        }
    });
}

window.onload = getInfoWallet();