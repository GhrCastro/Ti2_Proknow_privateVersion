document.addEventListener("DOMContentLoaded", async function () {
  try {
    const apiPath = 'http://localhost:4567/usuarios';

    async function getData(userId) {
      const response = await fetch(`${apiPath}/${userId}`);

      if (!response.ok) {
        throw new Error("Não foi possível encontrar o usuário!");
      }

      return await response.json();
    }

    // Obtenha dinamicamente o ID do usuário após o login (substitua esta lógica pelo seu método real)
    const userId = getSessionUserId();

    // Obter dados do servidor JSON dinamicamente
    const userDataResponse = await getData(userId);
    const userData = userDataResponse.data;
    console.log("Dados do usuário:", userData);

    // Verificar se o usuário possui os dados esperados
    if (userData.expenses === undefined || userData.salary === undefined) {
      throw new Error("Usuário não possui dados de despesas ou salário.");
    }

    // Definir os dias da semana
    const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    // Gerar dados fictícios para os dias da semana
    const expensesData = generateWeeklyData(userData.expenses, daysOfWeek.length);
    const salaryData = generateWeeklyData(userData.salary, daysOfWeek.length);

    // Obter o elemento com id "valor"
    const valorElement = document.getElementById("valor");
    if (valorElement) {
      // Exibir o valor total de despesas e salário na div com id "valor"
      const totalExpenses = userData.expenses;
      const totalSalary = userData.salary;
      valorElement.textContent = `Total Salário: R$ ${(totalSalary).toFixed(2)} | Total Despesas: R$ ${(totalExpenses).toFixed(2)}`;
    } else {
      console.error("Elemento com id 'valor' não encontrado.");
    }

    // Criar gráfico de despesas e receitas para o usuário específico
    var ctxExpensesIncomings = document.getElementById("dados-card");

    if (ctxExpensesIncomings) {
      // Verificar se o gráfico já foi criado e destruir se existir
      if (window.expensesIncomingsChart instanceof Chart) {
        window.expensesIncomingsChart.destroy();
      }

      window.expensesIncomingsChart = new Chart(ctxExpensesIncomings, {
        type: 'line',
        data: {
          labels: daysOfWeek,
          datasets: [
            {
              label: 'Despesas',
              data: expensesData,
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 2,
              fill: false
            },
            {
              label: 'Salário',
              data: salaryData,
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 2,
              fill: false
            }
          ]
        },
        options: {
          responsive: true,
          maintainAspectRatio: true,
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
        window.expensesIncomingsChart.resize();
      });
    } else {
      console.error("Elemento canvas não encontrado.");
    }
  } catch (error) {
    console.error("Erro ao obter os dados do usuário:", error);
  }

  // Função auxiliar para gerar dados fictícios semanais com variação aleatória
  function generateWeeklyData(total, length) {
    const baseValue = total / length;
    return Array.from({ length }, () => (baseValue + (Math.random() * baseValue * 0.5 - baseValue * 0.25)).toFixed(2));
  }

  // Função fictícia para obter dinamicamente o ID do usuário após o login
  function getSessionUserId() {
    const userJson = sessionStorage.getItem('usuario');
    if (userJson) {
      const user = JSON.parse(userJson);
      return user.id;
    } else {
      return null;
    }
  }
});
