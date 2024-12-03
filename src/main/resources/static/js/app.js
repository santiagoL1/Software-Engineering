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
let tempInFahrenheit = null; // Store the temperature in Fahrenheit
let forecastData = []; // Store forecast data
let currentUnit = 'F'; // Default unit is Fahrenheit

button.addEventListener('click', function(event) {
    event.preventDefault(); 
    
    const city = inputValue.value.trim();
    
    if (city === "") {
        alert("Please enter a city name."); 
        return; 
    }

    fetch('https://api.openweathermap.org/geo/1.0/direct?q=' + city + '&limit=1&appid=d4f2c486e541abf1beabcf1eae8b8053')
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
            
            // Fetch current weather data
            fetch('https://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lon + '&appid=d4f2c486e541abf1beabcf1eae8b8053&units=metric')
            .then(response => {
                if (!response.ok) {
                    throw new Error("Weather API responded with an error: " + response.status);
                }
                return response.json();
            })
            .then(weatherData => {
                console.log('Weather Data:', weatherData); 
                tempInCelsius = weatherData.main.temp; // Store temperature in Celsius
                tempInFahrenheit = (tempInCelsius * 9/5) + 32; // Convert to Fahrenheit
                updateTemperatureDisplay(tempInFahrenheit, currentUnit); // Display temperature in the selected unit

                // Update humidity, wind speed, and weather type
                humidityElement.textContent = weatherData.main.humidity + '%'; // Display humidity
                windElement.textContent = weatherData.wind.speed + ' m/s'; // Display wind speed
                weatherTypeElement.textContent = weatherData.weather[0].description.charAt(0).toUpperCase() + weatherData.weather[0].description.slice(1); // Display weather condition
            })
            .catch(err => {
                console.error("Error fetching weather data:", err);
                alert("Error fetching weather data!");
            });

            // Fetch 5-day forecast data
            fetch('https://api.openweathermap.org/data/2.5/forecast?lat=' + lat + '&lon=' + lon + '&appid=d4f2c486e541abf1beabcf1eae8b8053&units=metric')
            .then(response => {
                if (!response.ok) {
                    throw new Error("Forecast API responded with an error: " + response.status);
                }
                return response.json();
            })
            .then(forecastDataResponse => {
                console.log('Forecast Data:', forecastDataResponse);
                forecastData = forecastDataResponse.list.filter((data, index) => index % 8 === 0); // Get forecast data for 5 days
                updateForecastDays(forecastData);
            })
            .catch(err => {
                console.error("Error fetching forecast data:", err);
                alert("Error fetching forecast data!");
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
function updateTemperatureDisplay(temp, unit) {
    if (unit === 'C') {
        currentTemp.textContent = temp.toFixed(1) + '°C'; // Display temperature in Celsius
    } else {
        currentTemp.textContent = temp.toFixed(1) + '°F'; // Display temperature in Fahrenheit
    }
}

// Event listeners for temperature toggle
celsiusButton.addEventListener('click', function(event) {
    event.preventDefault();
    currentUnit = 'C'; // Set current unit to Celsius
    if (tempInCelsius !== null) {
        updateTemperatureDisplay(tempInCelsius, currentUnit); // Show Celsius
    }
});

fahrenheitButton.addEventListener('click', function(event) {
    event.preventDefault();
    currentUnit = 'F'; // Set current unit to Fahrenheit
    if (tempInFahrenheit !== null) {
        updateTemperatureDisplay(tempInFahrenheit, currentUnit); // Show Fahrenheit
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

// Function to update the forecast days
function updateForecastDays(forecastData) {
    const today = new Date();
    const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    // Get the current day index (0-6, where 0 is Sunday)
    const currentDayIndex = today.getDay();

    // Update the day names and temperatures for the next 5 days
    for (let i = 0; i < 5; i++) {
        const forecastDayElement = document.querySelector(`.week-forecast .col:nth-child(${i + 1})`);
        const dayData = forecastData[i];

        // Set the day name
        const forecastDayName = daysOfWeek[(currentDayIndex + i + 1) % 7]; // Calculate next days
        forecastDayElement.querySelector('h3').textContent = forecastDayName;

        // Set the temperature and weather condition
        forecastDayElement.querySelector('.weather').textContent = dayData.weather[0].description.charAt(0).toUpperCase() + dayData.weather[0].description.slice(1);
        const temp = (dayData.main.temp * 9/5) + 32; // Convert to Fahrenheit
        forecastDayElement.querySelector('span').textContent = temp.toFixed(1) + '°F';

        // Set the weather icon
        const iconCode = dayData.weather[0].icon;
        forecastDayElement.querySelector('img').src = `https://openweathermap.org/img/wn/${iconCode}@2x.png`; 
    }
}


