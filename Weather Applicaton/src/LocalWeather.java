
public class LocalWeather {
	String city;
	String country;
	String condition;
	Object lowTemp;
	Object currentTemp;
	Object highTemp;
	Object feelsLike;
	
	public LocalWeather(String city, String country, String condition, Object lowTemp, Object currentTemp, Object highTemp, Object feelsLike) {
		this.city=city;
		this.country=country;
		this.condition=condition;
		this.lowTemp=lowTemp;
		this.currentTemp=currentTemp;
		this.highTemp=highTemp;
		this.feelsLike=feelsLike;
	}

}