var button = document.querySelector('.button');
var inputValue = document.querySelector('.inputValue');
var cityNameElement = document.querySelector('.name'); 
var currentTemp = document.querySelector('.currentTemp');
var humidityElement = document.getElementById('humidity'); // Element to display humidity
var windElement = document.getElementById('wind'); // Element to display wind
var weatherTypeElement = document.getElementById('weather-type'); // Element to display weather type
var celsiusButton = document.getElementById('celcius-link');
var fahrenheitButton = document.getElementById('fahrenheit-link');

let tempInCelsius = null; // Store the temperature in Celsius

button.addEventListener('click', function(event) {
    event.preventDefault(); 
    
    const city = inputValue.value.trim();
    
    if (city === "") {
        alert("Please enter a city name."); 
        return; 
    }

    fetch('https://api.openweathermap.org/geo/1.0/direct?q=' + city + '&limit=1&appid=73102921a96041c57d3a90e735ba4ec3')
    .then(response => {
        if (!response.ok) {
            throw new Error("Geolocation API responded with an error: " + response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log('Geolocation Data:', data); 

        if (data.length > 0) {
            var lat = data[0].lat;
            var lon = data[0].lon;
            var cityName = data[0].name; 
            console.log('Latitude: ' + lat, 'Longitude: ' + lon, 'City Name: ' + cityName);

            cityNameElement.textContent = cityName;
            
            fetch('https://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lon + '&appid=73102921a96041c57d3a90e735ba4ec3&units=metric')
            .then(response => {
                if (!response.ok) {
                    throw new Error("Weather API responded with an error: " + response.status);
                }
                return response.json();
            })
            .then(weatherData => {
                console.log('Weather Data:', weatherData); 
                tempInCelsius = weatherData.main.temp; // Store temperature in Celsius
                updateTemperatureDisplay(tempInCelsius); // Update display to Celsius

                // Update humidity, wind speed, and weather type
                humidityElement.textContent = weatherData.main.humidity + '%'; // Display humidity
                windElement.textContent = weatherData.wind.speed + ' m/s'; // Display wind speed
                weatherTypeElement.textContent = weatherData.weather[0].description.charAt(0).toUpperCase() + weatherData.weather[0].description.slice(1); // Display weather condition
            })
            .catch(err => {
                console.error("Error fetching weather data:", err);
                alert("Error fetching weather data!");
            });
        } else {
            alert("City not found in geolocation API response!");
        }
    })
    .catch(err => {
        console.error("Error fetching geolocation data:", err);
        alert("Error fetching geolocation data!");
    });
});

// Function to update the displayed temperature
function updateTemperatureDisplay(temp) {
    currentTemp.textContent = temp.toFixed(1) + '°C'; // Display temperature in Celsius
}

// Event listeners for temperature toggle
celsiusButton.addEventListener('click', function(event) {
    event.preventDefault();
    if (tempInCelsius !== null) {
        updateTemperatureDisplay(tempInCelsius); // Show Celsius
    }
});

fahrenheitButton.addEventListener('click', function(event) {
    event.preventDefault();
    if (tempInCelsius !== null) {
        const tempInFahrenheit = (tempInCelsius * 9/5) + 32; // Convert to Fahrenheit
        currentTemp.textContent = tempInFahrenheit.toFixed(1) + '°F'; // Display temperature in Fahrenheit
    }
});

// Function to update the current time
function updateCurrentTime() {
    const now = new Date();
    let hours = now.getHours();
    const minutes = now.getMinutes().toString().padStart(2, '0');
  
    const amPm = hours >= 12 ? 'PM' : 'AM';
  
    hours = hours % 12 || 12; // Convert 24-hour time to 12-hour time
  
    const timeString = `${hours}:${minutes} ${amPm}`;
    
    document.getElementById('current-time').textContent = timeString;
}
  
setInterval(updateCurrentTime, 60000);
updateCurrentTime();

// Function to update forecast days
function updateForecastDays() {
    const today = new Date();
    const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    // Get the current day index (0-6, where 0 is Sunday)
    const currentDayIndex = today.getDay();

    // Update the day names for the next 5 days
    for (let i = 0; i < 5; i++) {
        const forecastDayElement = document.querySelector(`.week-forecast .col:nth-child(${i + 1}) h3`);
        forecastDayElement.textContent = daysOfWeek[(currentDayIndex + i + 1) % 7]; // Calculate next days
    }
}

// Call the function to update the forecast days when the script runs
updateForecastDays();

