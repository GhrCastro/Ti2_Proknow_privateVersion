
async function getInfoWallet(id){

    let userWallet = {};

    fetch(`http://localhost:4567/wallets/${id}`)
        .then(response => response.json())
        .then(data =>{
            userWallet = data.data;
        })

        .catch(responseError => {
            console.error("Erro ao buscar informações da carteira do usuario!", responseError);
        })

    return userWallet;
}