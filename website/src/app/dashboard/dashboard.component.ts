import { Component } from '@angular/core';
import { WeatherData } from './weather.data';
import { DataService } from './dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  cityName: string = ''; // Store the user's input city name
  weatherData: WeatherData = new WeatherData(); // Store the retrieved weather data
  isLoading: boolean = false;

  constructor(private service: DataService) { }

  getWeatherData() {
    this.isLoading = true;
    this.service.fetchData(this.cityName).subscribe((response: WeatherData) => {
      this.weatherData = response;
      this.isLoading = false;
    },
    (error) => {
      console.error(error);
      this.isLoading = false; // Stop loading in case of an error
    });
  }

  onSubmit() {
    this.getWeatherData();
  }

  getWeatherIconUrl(weatherIcon: string): string {
    return `https://openweathermap.org/img/wn/${weatherIcon}@2x.png`;
  }

  // Method to construct the background image URL based on weatherIcon value
  getWeatherIconBackgroundUrl(weatherIcon: string): string {
    const c = weatherIcon.slice(0, 2);
    if (c == '01' || c == '04' || c == '09' || c == '11' || c == '13' || c == '50') {
      return `url(../../assets/${c}.jpg)`;
    }
    if( c == '02' || c == '10'){
      return `url(../../assets/${c}.png)`;
    }
    if(c == '03'){
      return `url(../../assets/${c}.jpeg)`;
    }
    return "";
  }

}
