const buttonConvert = document.querySelector(".button-convert");

let nomeMoeda = "BTC";

buttonConvert.addEventListener("click", function RequisicaoAPI() {
  let url = "https://rest.coinapi.io/v1/exchangerate/BRL/" + nomeMoeda;
  const key = "22109CC0-5827-463E-91C2-88046A3C909A"

  let valueCoin = document.querySelector(".valorCoin");
  valueCoin = valueCoin.textContent;

  const options = {
    maxBodyLength: Infinity,
    method: 'get',
    headers: {
      'Accept': 'application/json',
      'X-CoinAPI-Key': key
    }
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => {

      const value = data.rate
      let result = parseFloat(valueCoin) / parseFloat(value);

      const divresposta = document.querySelector(".resposta")
      divresposta.innerHTML = result
    })
  
})








// let -> variveis que mudam
// const -> nome diz
