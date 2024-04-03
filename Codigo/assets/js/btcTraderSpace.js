document.addEventListener('DOMContentLoaded', function() {
    const ctx = document.getElementById('myChart').getContext('2d');
    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [], // This will be populated with timestamps
            datasets: [{
                data: [], // This will be populated with trade prices
                label: 'BTC/USD',
                borderColor: '#3e95cd',
                fill: false
            }]
        },
        options: {
            title: {
                display: true,
                text: 'Real-Time BTC/USD Trade Prices'
            }
        }
    });

    const socket = new WebSocket('wss://ws.coinapi.io/v1/');
    socket.onopen = function (event) {
    socket.send(JSON.stringify({
    "type": "hello",
    "apikey": "D7101F0B-0493-4D5B-BFEE-532789FC7B44",
    "subscribe_data_type": ["trade"],
    "subscribe_filter_symbol_id": ["BITSTAMP_SPOT_BTC_USD$", "BITFINEX_SPOT_BTC_LTC$"]
    }));
    };

    socket.onmessage = function (event) {

    const data = JSON.parse(event.data);
    console.log(data);
    // Add new data to the chart
    chart.data.labels.push(data.time_exchange);
    chart.data.datasets[0].data.push(data.price);

    // Remove the oldest data point if there are more than 50
    if (chart.data.labels.length > 50) {
        chart.data.labels.shift();
        chart.data.datasets[0].data.shift();
    }

    // Update the chart
    chart.update();
    };

    socket.onerror = function (error) {
    console.log(`WebSocket error: ${error}`);
    }
});



//Gráfico de baixo----->
const API_KEY = "D7101F0B-0493-4D5B-BFEE-532789FC7B44";
const url = `https://rest.coinapi.io/v1/ohlcv/BITSTAMP_SPOT_BTC_USD/history?apikey=${API_KEY}&period_id=1DAY&time_start=2021-01-01T00:00:00&limit=1095`;

async function fetchData() {
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
}


function transformData(data) {
    return data.map(entry => ({
      date: new Date(entry.time_period_start),
      price: entry.price_close
    }));
  }


  document.addEventListener('DOMContentLoaded', () => {
    const svg = d3.select("svg"); // Select the existing SVG element in the HTML
    
    fetchData()
      .then(data => {
        const transformedData = transformData(data);
  
        // Define chart dimensions
        const width = 800;
        const height = 600;
        const margin = { top: 50, right: 50, bottom: 50, left: 50 };
  
        // Create a group for the chart elements
        const chartGroup = svg.append("g")
          .attr("transform", `translate(${margin.left}, ${margin.top})`);
  
        // Create scales
        const xScale = d3.scaleUtc()
          .domain(d3.extent(transformedData, d => d.date))
          .range([0, width - margin.left - margin.right]);
  
        const yScale = d3.scaleLinear()
          .domain([0, d3.max(transformedData, d => d.price)])
          .range([height - margin.bottom - margin.top, 0]);
  
        // Create a line generator
        const line = d3.line()
          .x(d => xScale(d.date))
          .y(d => yScale(d.price));
  
        // Draw the line
        chartGroup.append("path")
          .datum(transformedData)
          .attr("class", "line")
          .attr("d", line);
  
        // Add axes
        chartGroup.append("g")
          .attr("class", "x-axis")
          .attr("transform", `translate(0, ${height - margin.bottom - margin.top})`)
          .call(d3.axisBottom(xScale));
  
        chartGroup.append("g")
          .attr("class", "y-axis")
          .call(d3.axisLeft(yScale));
      })
      .catch(error => console.log(error));
  });