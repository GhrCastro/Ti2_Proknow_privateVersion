//import convertValue from "./cryptoApi.js";

const convertValue = async (valueCoin, currency) => {
    let url = `https://rest.coinapi.io/v1/exchangerate/${currency}/BRL`;
    const key = "D7101F0B-0493-4D5B-BFEE-532789FC7B44";
  
    const options = {
      maxBodyLength: Infinity,
      method: 'get',
      headers: {
        'Accept': 'application/json',
        'X-CoinAPI-Key': key
      }
    };
  
    let result;
  
    await fetch(url, options)
      .then(response => response.json())
      .then(data => {
        const value = data.rate;
        console.log(value);
        result = parseFloat(valueCoin) * parseFloat(value);
      });
  
    return result;
}

async function getInfoWallet() {
    const user = getSessionUser();
    //console.log(user);

    let userWallet = {};

    await fetch(`http://localhost:4567/userCoins/${user.wallet_id}`)
        .then(response => response.json())
        .then(data => {

            userWallet = data.data;
        })

        .catch(responseError => {
            console.error("Erro ao buscar informações da carteira do usuario!", responseError);
        })
    
    console.log(userWallet);
    var balance = document.getElementById("balance");
    var hidden_icon = document.getElementById("hidden-icon");
    var userName = document.getElementById("welcomeUser");
    userName.innerText = `Seja Bem vindo, ${user.name}`;

    const walletBalances = document.getElementById('wallet-balances');
    userWallet.forEach( coin => {
        walletBalances.innerHTML += `
            <div style="color:white; margin-left:10px; margin-bottom: 20px; margim-top: 10px; border-style: solid; border-radius:10px; border-color: white"> 
                <h4 style="padding: 10px">${coin.currency}</h4>
                <h5 style="padding: 10px">${coin.amount}</h5>
            </div>
        `;
    });

    const valueBTC = await convertValue(userWallet[1].amount , userWallet[1].currency);
    const valueETH = await convertValue(userWallet[2].amount , userWallet[2].currency);
    const valueUSD = await convertValue(userWallet[3].amount , userWallet[3].currency);

    const soma = (valueBTC+valueETH+valueUSD+userWallet[4].amount).toFixed(2);

    balance.innerText = `R$ ${soma}`

    console.log(valueBTC + " " + valueETH + " " + valueUSD);

    document.getElementById("hidden-balance").addEventListener("click", function () {
        if (balance.classList.contains("hidden")) {
            balance.classList.remove("hidden");
            balance.innerText = `R$ ${soma}`; // Retorna o saldo original ao mostrar
            hidden_icon.classList.replace('fa-eye-slash', 'fa-eye'); // Corrected line
        } else {
            balance.classList.add("hidden");
            balance.innerText = "R$ ******"; // Substitui o saldo por asteriscos ao ocultar
            hidden_icon.classList.replace('fa-eye', 'fa-eye-slash'); // Corrected line
        }
    });
    


}
    
window.onload = getInfoWallet();