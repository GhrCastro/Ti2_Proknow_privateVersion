export default convertValue = async (valueCoin, currency) => {
  let url = `https://rest.coinapi.io/v1/exchangerate/${currency}/BRL` ;
  const key = "22109CC0-5827-463E-91C2-88046A3C909A"



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

      const value = data.rate
      result = parseFloat(valueCoin) / parseFloat(value);

    })

  return result;
}

//test








// let -> variveis que mudam
// const -> nome diz
