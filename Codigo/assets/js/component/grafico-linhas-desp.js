

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
        const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        const expensesData = getExpensesData(userData, months);
        const incomingsData = getIncomingsData(userData, months);

        console.log("Despesas do usuário:", expensesData);
        console.log("Receitas do usuário:", incomingsData);

        // Criar gráfico de despesas e receitas para o usuário específico
        var ctxExpensesIncomings = document.getElementById("expensesIncomingsChart");

        if (ctxExpensesIncomings) {
            var expensesIncomingsChart = new Chart(ctxExpensesIncomings, {
                type: 'line',
                data: {
                    labels: months,
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
    function getExpensesData(user, months) {
        if (user.expenses) {
            const fixos = generateRandomValues(months.length);
            const variados = generateRandomValues(months.length);
            const lazer = generateRandomValues(months.length);

            return { fixos, variados, lazer };
        } else {
            console.error("Usuário não possui dados de despesas.");
            return { fixos: Array(months.length).fill(0), variados: Array(months.length).fill(0), lazer: Array(months.length).fill(0) };
        }
    }

    // Função auxiliar para obter dados de receitas
    function getIncomingsData(user, months) {
        if (user.incomings) {
            const salario = generateRandomValues(months.length);
            const bonus = generateRandomValues(months.length);
            const ativos = generateRandomValues(months.length);

            return { salario, bonus, ativos };
        } else {
            console.error("Usuário não possui dados de receitas.");
            return { salario: Array(months.length).fill(0), bonus: Array(months.length).fill(0), ativos: Array(months.length).fill(0) };
        }
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
