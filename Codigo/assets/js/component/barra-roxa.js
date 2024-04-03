document.addEventListener("DOMContentLoaded", async function () {
  try {
    const apiPath = 'https://jsonserver-proknow.joopaulopaulo33.repl.co/usuarios';

    async function getData(username) {
      const response = await fetch(`${apiPath}?login=${username}`);

      if (!response.ok) {
        throw new Error("Não foi possível encontrar o usuário!");
      }

      return await response.json();
    }

    // Obtenha dinamicamente o nome de usuário após o login (substitua esta lógica pelo seu método real)
    const username = getLoggedUsername();

    // Obter dados do servidor JSON dinamicamente
    const [userData] = await getData(username);
    console.log("Dados do usuário:", userData);

    // Utilizar o usuário encontrado para criar gráficos
    const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    const expensesData = getExpensesData(userData, daysOfWeek);

    // Modificação aqui
    const incomingsData = getIncomingsData(userData, daysOfWeek);
    const totalIncomings = sumIncomingsData(incomingsData);

    console.log("Despesas do usuário:", expensesData);
    console.log("Receitas do usuário:", incomingsData);

    // Criar gráfico de despesas e receitas para o usuário específico
    var ctxExpensesIncomings = document.getElementById("dados-card");

    if (ctxExpensesIncomings) {
      var expensesIncomingsChart = new Chart(ctxExpensesIncomings, {
        type: 'line',
        data: {
          labels: daysOfWeek,
          datasets: [
            {
              label: 'Despesas Fixas',
              data: expensesData.fixos,
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Despesas Variadas',
              data: expensesData.variados,
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Lazer',
              data: expensesData.lazer,
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Receitas Salário',
              data: incomingsData.salario,
              borderColor: 'rgba(255, 206, 86, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Receitas Bônus',
              data: incomingsData.bonus,
              borderColor: 'rgba(153, 102, 255, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Receitas Ativos',
              data: incomingsData.ativos,
              borderColor: 'rgba(255, 159, 64, 1)',
              borderWidth: 2,
              fill: false
            },
            // Adiciona uma nova série para o total de receitas
            {
              label: 'Total Receitas',
              data: Array(daysOfWeek.length).fill(totalIncomings.totalIncomings),
              borderColor: 'rgba(0, 0, 0, 1)', // Pode personalizar a cor conforme necessário
              borderWidth: 2,
              fill: false
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                callback: function (value, index, values) {
                  return 'R$ ' + value.toFixed(2);
                }
              }
            }
          }
        }
      });

      // Update chart size when the window is resized
      window.addEventListener('resize', function () {
        expensesIncomingsChart.resize();
      });
    } else {
      console.error("Elemento canvas não encontrado.");
    }
  } catch (error) {
    console.error("Erro ao obter os dados do usuário:", error);
  }

  // Função auxiliar para obter dados de despesas
  function getExpensesData(user, daysOfWeek) {
    if (user.expenses) {
      const fixos = generateRandomValues(daysOfWeek.length);
      const variados = generateRandomValues(daysOfWeek.length);
      const lazer = generateRandomValues(daysOfWeek.length);

      return { fixos, variados, lazer };
    } else {
      console.error("Usuário não possui dados de despesas.");
      return { fixos: Array(daysOfWeek.length).fill(0), variados: Array(daysOfWeek.length).fill(0), lazer: Array(daysOfWeek.length).fill(0) };
    }
  }

  // Função auxiliar para obter dados de receitas
  function getIncomingsData(user, daysOfWeek) {
    if (user.incomings) {
      const salario = generateRandomValues(daysOfWeek.length);
      const bonus = generateRandomValues(daysOfWeek.length);
      const ativos = generateRandomValues(daysOfWeek.length);

      return { salario, bonus, ativos };
    } else {
      console.error("Usuário não possui dados de receitas.");
      return { salario: Array(daysOfWeek.length).fill(0), bonus: Array(daysOfWeek.length).fill(0), ativos: Array(daysOfWeek.length).fill(0) };
    }
  }

  // Adição da função sumIncomingsData antes de ser chamada
  function sumIncomingsData(incomingsData) {
    const totalSalario = incomingsData.salario.reduce((acc, value) => acc + value, 0);
    const totalBonus = incomingsData.bonus.reduce((acc, value) => acc + value, 0);
    const totalAtivos = incomingsData.ativos.reduce((acc, value) => acc + value, 0);

    const totalIncomings = (totalSalario + totalBonus + totalAtivos) / 4 / 7;

    return { totalSalario, totalBonus, totalAtivos, totalIncomings };
  }

  // Função auxiliar para gerar valores fictícios
  function generateRandomValues(length) {
    return Array.from({ length }, () => Math.floor(Math.random() * 1000));
  }

  // Função fictícia para obter dinamicamente o nome de usuário após o login
  function getLoggedUsername() {
    // Substitua esta lógica pelo método real que você está usando para obter o nome de usuário após o login
    return "carol";
  }
});


























// document.addEventListener("DOMContentLoaded", function () {
//   const apiUrl = 'https://jsonserver-proknow.joopaulopaulo33.repl.co/usuarios';
//   const userId = 1; // Substitua pelo ID do usuário desejado

//   // Função para buscar os dados da API e definir as alturas das barras roxas
//   async function fetchData() {
//     try {
   
//       const response = await fetch(apiUrl);
//       if (response.ok) {
//         const usuarios = await response.json();

//         // Encontrar o usuário com o ID especificado
//         const usuario = usuarios.find((user) => user.id === 1);

//         if (usuario) {
//           // Calcular a soma das despesas a partir de despesas, despesasMes e lazer
//           const despesas = parseFloat((usuario.expenses?.despesas || 'R$ 0,00').replace('R$ ', '').replace(',', '.')) || 0;
//           const despesasMes = parseFloat((usuario.expenses?.despesasMes || 'R$ 0,00').replace('R$ ', '').replace(',', '.')) || 0;
//           const lazer = parseFloat((usuario.expenses?.lazer || 'R$ 0,00').replace('R$ ', '').replace(',', '.')) || 0;

//           const totalDespesas = despesas + despesasMes + lazer;

//           // Altura máxima da barra roxa
//           const alturaMaxima = 80;
          
//           // Porcentagens de gastos fictícias para cada dia da semana (ajuste conforme necessário)
//           const porcentagensSemanais = {
//             domingo: 5,   // Porcentagem de gastos para domingo
//             segunda: 10,  // Porcentagem de gastos para segunda
//             terca: 15,    // Porcentagem de gastos para terça
//             quarta: 20,   // Porcentagem de gastos para quarta
//             quinta: 15,   // Porcentagem de gastos para quinta
//             sexta: 10,    // Porcentagem de gastos para sexta
//             sabado: 25,   // Porcentagem de gastos para sábado
//           };

//           // Definir a altura da barra roxa para cada dia com base nas porcentagens
//           for (const dia in porcentagensSemanais) {
//             const porcentagem = porcentagensSemanais[dia];
//             const alturaBarra = (totalDespesas * porcentagem / 100) * (alturaMaxima / totalDespesas);
//             const barraRoxa = document.getElementById(dia);
//             barraRoxa.style.height = `${alturaBarra}px`;
//           }

//          // Exibir o valor total de despesas na div com id "valor"
//          document.getElementById("valor").textContent = `R$ ${(totalDespesas/4).toFixed(2)}`;
//         } else {
//           console.error("Usuário não encontrado.");
//         }
//       } else {
//         console.error("Erro ao buscar os dados da API.");
//       }
//     } catch (error) {
//       console.error("Ocorreu um erro: ", error);
//     }
//   }

//   // Chame a função para buscar os dados da API e calcular as despesas do usuário
//   fetchData();
// });














































// /*document.addEventListener("DOMContentLoaded", function () {
//   const apiUrl = "https://jsonserver-proknow.joopaulopaulo33.repl.co/usuarios";

//   // Função para buscar os dados da API e atualizar as barras no HTML
//   async function fetchData() {
//     try {
//       const response = await fetch(apiUrl);
//       if (response.ok) {
//         const usuarios = await response.json();

//         // Calcular a soma total de totalIncomings a partir de salario, bonus e ativos
//         const totalIncomings = usuarios.reduce((total, usuario) => {
//           const salario = parseFloat((usuario.incomings.salario || 'R$ 0.00').replace('R$ ', '').replace(',', '.')) || 0;
//           const bonus = parseFloat((usuario.incomings.bonus || 'R$ 0.00').replace('R$ ', '').replace(',', '.')) || 0;
//           const ativos = parseFloat((usuario.incomings.ativos || 'R$ 0.00').replace('R$ ', '').replace(',', '.')) || 0;

//           return total + salario + bonus + ativos;
//         }, 0);

//         // Definir as porcentagens para cada dia da semana com base no totalIncomings
//         const porcentagensPorDia = {
//           domingo: (totalIncomings / 4) / 7 * 0.01,  // Ajuste o número de semanas do mês conforme necessário
//           segunda: (totalIncomings / 4) / 7 * 0.01,
//           terca: (totalIncomings / 4) / 7 * 0.01,
//           quarta: (totalIncomings / 4) / 7 * 0.01,
//           quinta: (totalIncomings / 4) / 7 * 0.01,
//           sexta: (totalIncomings / 4) / 7 * 0.9,
//           sabado: (totalIncomings / 4) / 7 * 0.01
//         };

//         // Atualizar as alturas das barras com as porcentagens calculadas
//         const diasDaSemana = ["domingo", "segunda", "terca", "quarta", "quinta", "sexta", "sabado"];
//         diasDaSemana.forEach(dia => {
//           const barra = document.getElementById(dia);
//           barra.style.height = `${porcentagensPorDia[dia]}px`;
//         });
//       } else {
//         console.error("Erro ao buscar os dados da API.");
//       }
//     } catch (error) {
//       console.error("Ocorreu um erro: ", error);
//     }
//   }

//   // Chame a função para buscar os dados da API e atualizar o HTML
//   fetchData();
// });


// */