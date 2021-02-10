import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;


public class WeatherGUI {

	protected Shell shell;
	String searchType="city";
	private Text searchText;
	JSONObject weatherJSON;
	LocalWeather currentWeather;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WeatherGUI window = new WeatherGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 500);
		shell.setText("Weather Application");
		
		Label lblSearchLocalWeather = new Label(shell, SWT.None);
		lblSearchLocalWeather.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblSearchLocalWeather.setBounds(10, 10, 221, 25);
		lblSearchLocalWeather.setText("Search Local Weather By:");
		
		Button btnCityName = new Button(shell, SWT.RADIO);
		btnCityName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				searchType="city";
			}
		});
		btnCityName.setBounds(10, 72, 133, 25);
		btnCityName.setText(" City Name");
		
		Button btnZipCode = new Button(shell, SWT.RADIO);
		btnZipCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				searchType="zip";
			}
		});
		btnZipCode.setBounds(10, 41, 133, 25);
		btnZipCode.setText(" Zip Code");
		
		Button btnEnter = new Button(shell, SWT.NONE);
		btnEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label labelHoriz1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
				labelHoriz1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				labelHoriz1.setBounds(0, 167, 438, 3);
				
				Label lblSearchType = new Label(shell, SWT.NONE);
				lblSearchType.setBounds(10, 190, 92, 25);
				
				searchText = new Text(shell, SWT.BORDER);
				searchText.setBounds(105, 187, 127, 31);
				
				Label labelHoriz2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
				labelHoriz2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				labelHoriz2.setBounds(0, 227, 438, 3);
				
				Button btnSearch = new Button(shell, SWT.NONE);
				btnSearch.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						String searchTextValue= searchText.getText();
						
						currentWeather=getAndParseWeather(searchTextValue);
						
						Label lblCity = new Label(shell, SWT.NONE);
						lblCity.setBounds(10, 235, 41, 25);
						lblCity.setText("City:");
						
						Label lblCountry = new Label(shell, SWT.NONE);
						lblCountry.setBounds(10, 265, 72, 25);
						lblCountry.setText("Country:");
						
						Label lblConditions = new Label(shell, SWT.NONE);
						lblConditions.setBounds(10, 295, 95, 25);
						lblConditions.setText("Conditions:");
						
						Label lblLow = new Label(shell, SWT.NONE);
						lblLow.setBounds(10, 325, 40, 25);
						lblLow.setText("Low:");
						
						Label lblHigh = new Label(shell, SWT.NONE);
						lblHigh.setBounds(10, 355, 50, 25);
						lblHigh.setText("High:");
						
						Label lblFeelsLike = new Label(shell, SWT.NONE);
						lblFeelsLike.setBounds(10, 385, 81, 25);
						lblFeelsLike.setText("Feels Like:");
						
						Label lblCityDisplay = new Label(shell, SWT.NONE);
						lblCityDisplay.setBounds(50, 235, 163, 25);
						lblCityDisplay.setText(currentWeather.city);
						
						Label lblCountryDisplay = new Label(shell, SWT.NONE);
						lblCountryDisplay.setBounds(84, 265, 163, 25);
						lblCountryDisplay.setText(currentWeather.country);
						
						Label lblConditionsDisplay = new Label(shell, SWT.NONE);
						lblConditionsDisplay.setBounds(105, 295, 163, 25);
						lblConditionsDisplay.setText(currentWeather.condition);
						
						Label lblLowTempDisplay = new Label(shell, SWT.NONE);
						lblLowTempDisplay.setBounds(50, 325, 163, 25);
						lblLowTempDisplay.setText(String.valueOf(currentWeather.lowTemp));
						
						Label lblHighTempDisplay = new Label(shell, SWT.NONE);
						lblHighTempDisplay.setBounds(60, 355, 163, 25);
						lblHighTempDisplay.setText(String.valueOf(currentWeather.highTemp));
						
						Label lblFeelsLikeDisplay = new Label(shell, SWT.NONE);
						lblFeelsLikeDisplay.setBounds(97, 385, 163, 25);
						lblFeelsLikeDisplay.setText(String.valueOf(currentWeather.feelsLike));
						
						Button btnSearchNew = new Button(shell, SWT.NONE);
						btnSearchNew.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseDown(MouseEvent e) {
								lblCity.dispose();
								lblCountry.dispose();
								lblConditions.dispose();
								lblLow.dispose();
								lblHigh.dispose();
								lblFeelsLike.dispose();
								lblCityDisplay.dispose();
								lblCountryDisplay.dispose();
								lblConditionsDisplay.dispose();
								lblLowTempDisplay.dispose();
								lblHighTempDisplay.dispose();
								lblFeelsLikeDisplay.dispose();
								btnSearchNew.dispose();
								lblSearchType.dispose();
								searchText.dispose();
								btnSearch.dispose();
								labelHoriz1.dispose();
								labelHoriz2.dispose();
							}
						});
						btnSearchNew.setBounds(289, 377, 105, 35);
						btnSearchNew.setText("Search New");
					
					}
				});
				btnSearch.setBounds(290, 187, 105, 31);
				btnSearch.setText("Search");
				
				
				if (searchType.equals("city")) {
					lblSearchType.setText("City Name:");
			
				}
				else if(searchType.equals("zip")) {
					lblSearchType.setText("Zip Code:");
					
				}
			}
		});
		btnEnter.setBounds(289, 41, 105, 35);
		btnEnter.setText("Enter");

	}
	
	public LocalWeather getAndParseWeather(String searchText) {
		char[] chars=searchText.toCharArray();
		String urlString="";
		
		try {	//is a zipcode string.
			if (Character.isDigit(chars[0])) {
				urlString= ("https://api.openweathermap.org/data/2.5/weather?zip="+searchText+"&units=imperial&appid=insertapikeyhere");
			}
			
			//is a city string.
			else {
				urlString= ("https://api.openweathermap.org/data/2.5/weather?q="+searchText+"&units=imperial&appid=insertapikeyhere");
			}
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode = conn.getResponseCode();
			
			if (responseCode != 200) {
			    throw new RuntimeException("HttpResponseCode: " + responseCode);
			}
			else {
				//start getting json info
				
				String jsonStr="";
				Scanner scan= new Scanner(url.openStream());
				
				while(scan.hasNext()) {
					jsonStr+=scan.nextLine();
				}
				scan.close();
				
				JSONParser jsonParser = new JSONParser();
				// the JSONObject represents everything in the file
				JSONObject allData = (JSONObject) jsonParser.parse(jsonStr);
				JSONObject tempsStr= (JSONObject)allData.get("main");
				JSONArray weatherArray= (JSONArray)allData.get("weather");
				JSONObject weatherStr= (JSONObject)weatherArray.get(0);
				JSONObject countryStr= (JSONObject)allData.get("sys");
				
				LocalWeather localWeather= getLocalWeather(allData, weatherStr, countryStr, tempsStr);
				
				
				return localWeather;		
			}
	
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	public LocalWeather getLocalWeather(JSONObject allData, JSONObject weatherStr, JSONObject countryStr, JSONObject tempsStr) {
		String city =  (String)allData.get("name");
		String country= (String)countryStr.get("country");
		String conditions= (String)weatherStr.get("description");
		
		Object tempLow= tempsStr.get("temp_min");
		Object currentTemp= tempsStr.get("temp");
		Object tempHigh= tempsStr.get("temp_max");
		Object feelsLike= tempsStr.get("feels_like");
		
		LocalWeather localWeather= new LocalWeather(city, country, conditions, tempLow, currentTemp,tempHigh, feelsLike);
		
		return localWeather;
	}
	
}
