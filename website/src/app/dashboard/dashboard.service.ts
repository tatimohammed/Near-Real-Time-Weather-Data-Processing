import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { WeatherData } from './weather.data';

@Injectable()
export class DataService {
    constructor(private http: HttpClient) { }

    fetchData(city: string): Observable<WeatherData> {
        return this.http.get("https://AWS_GATEWAY_API.com/weather?city=" + city)
            .pipe(
                map((response: any) => {
                    let data = new WeatherData();
                    data.country = response.country;
                    data.directionName = response.directionName;
                    data.feelsLike = response.feelsLike;
                    data.humidity = response.humidity;
                    data.lastUpdate = response.lastUpdate.split('.')[0].replace('T', ' ');
                    data.name = response.name;
                    data.pressure = response.pressure;
                    data.speed = response.speed;
                    data.speedName = response.speedName;
                    data.sunrise = response.sunrise;
                    data.sunset = response.sunset;
                    data.temperature = response.temperature;
                    data.weatherIcon = response.weatherIcon;
                    data.weatherValue = response.weatherValue;

                    return data;
                }),
                catchError((error: HttpErrorResponse) => {
                    console.error("An error occurred:", error);
                    return throwError("Error fetching weather data. Status: " + error.status + ", Message: " + error.message);
                })
            );
    }
}
