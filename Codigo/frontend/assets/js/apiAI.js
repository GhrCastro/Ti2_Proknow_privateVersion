const apiPath = 'https://analisador-proknow.cognitiveservices.azure.com/customvision/v3.0/Prediction/9830bce3-f815-4a6d-829e-10e2bcdf65d4/classify/iterations/Iteration4/image';


document.getElementById('formFile').addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (event) {
            const imgElement = document.getElementById('preview');
            imgElement.src = event.target.result;
            imgElement.style.display = 'block';
        }
        reader.readAsDataURL(file);
    }
});

document.getElementById('btnSubmit').addEventListener('click', async function () {
    const input = document.getElementById('formFile');
    const inputResp = document.getElementById('respInput');
    const respDesc = document.getElementById('descResp');

    const user = getSessionUser();

    let respTransection;

    const withdraw = {
        "name": "PROKNOW",
        "amount": 5.0,
    };
    
    // Função para converter um objeto para o formato x-www-form-urlencoded
    function encodeFormData(data) {
        return Object.keys(data)
            .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(data[key]))
            .join('&');
    }

    const encodedBody = encodeFormData(withdraw);

    await fetch(`http://localhost:4567/wallets/withdraw/${user.id}`, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'POST',
        body: encodedBody
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            respTransection = data
        })
        .catch((error) => {
            console.error(error);
        })

    const file = input.files[0];
    let predictions = [];

    if (file && respTransection.status == "SUCCESS") {

        await fetch(apiPath , {
            headers: {
                'Prediction-Key': '25c88134ce364f8bad6090c03f958b60',
                'Content-Type': 'application/octet-stream'
            },
            method: 'POST',
            body: file
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                predictions = data.predictions
            })
            .catch((error) => {
                console.error(error);
            })
    } else {
        console.log("Insira um arquivo!");
        inputResp.placeholder = "Insira um arquivo!"
    }

    let biggerPrecision;
    if (predictions.length > 0) {
        biggerPrecision = predictions[0];
        predictions.forEach(predict => {
            if (predict.probability > biggerPrecision.probability) {
                biggerPrecision = predict;
            }
        });
        //console.log(biggerPrecision)

        inputResp.placeholder = biggerPrecision.tagName;

        //const respDesc = document.createElement("p");

        if (biggerPrecision.tagName == "Bandeiras") {
            respDesc.innerHTML = "Ele é identificado por 3 picos e 3 covas, acontece ao final de uma tendência de queda e indica reversão.";
        } else if (biggerPrecision.tagName == "Cabeça e ombros") {
            respDesc.innerHTML = "Esse é um padrão de reversão geralmente formado em uma tendência de alta, apontando uma queda. Ele é formado por uma cabeça (o pico central e maior), dois ombros (picos menores ao lado da cabeça) e um pescoço, que é uma linha que conecta os três elementos no gráfico de linha.";
        } else if(biggerPrecision.tagName == "Duplo topo") {
            respDesc.innerHTML = "Geralmente formado no final de uma tendência de alta, há dois picos seguidos de altura similar (existindo uma cova entre eles). Sua existência demonstra uma possibilidade de reversão para queda."
        } else if(biggerPrecision.tagName == "Retângulos") {
            respDesc.innerHTML = "Os aumentos e reduções de preço permanecem equilibrados por um longo intervalo de tempo. Ele pode indicar tanto reversões quanto continuações."
        }else if(biggerPrecision.tagName=="Padrão não identificado"){
            respDesc.innerHTML = " "
        }

        //console.log(respDesc);
        //respDiv.append(respDesc);
    }

});