/**
 * Copyright (C) 2012 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mypack.bus.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


@Singleton
public class BATSProviderImpl {


	public String refreshVehicles(String busType) throws IOException, JSONException {

		int pRoute = 0 ;
		if (busType.equalsIgnoreCase("DCL") || busType.equalsIgnoreCase("Riverside")) {
			pRoute = 15;
		}
		else if(busType.equalsIgnoreCase("DE") || busType.equalsIgnoreCase("Downtown Express")) {
			pRoute = 4;
		}
		else if(busType.equalsIgnoreCase("WS") || busType.equalsIgnoreCase("was")) {
			pRoute = 14;
		}
		else if(busType.equalsIgnoreCase("Campus Shuttle")|| busType.equalsIgnoreCase("Campus")) {
			pRoute = 8;
		}
		else if(busType.equalsIgnoreCase("UP") || busType.equalsIgnoreCase("UP Shuttle")) {
			pRoute = 11;
		}


		JSONObject vehicle= downloadVehicleDetails();
		JSONArray s = vehicle.getJSONArray("get_vehicles");
		int newD=0,minD=999999999;
		double longitude = 0;
		double latitude = 0;
		double originlong = -75.9586716;
		double originlat = 42.0943802;
		int route = 0;
		String value="";
		String minVal="";
		String duration = "";
		String fresult = "There are no "+ busType + " busses at the moment. I got some horses on standby though ;)" ;
		String distance = "";
		String ndistance = "";
		int isActive=0;
		boolean flag=false;
		for(int i=0;i<s.length();i++) {
			JSONObject obj = s.getJSONObject(i);
			longitude = obj.getDouble("lng");
			latitude = obj.getDouble("lat");
			route = obj.getInt("routeID");
			isActive = obj.getInt("inService");
			if(route == pRoute && isActive == 1) {
				flag = true;
				//send long and lat to g-api;
				//https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=42.098686,-75.917976&key=AIzaSyDAh8QNZ6dxd4YFi8ygMnOldtOuUtSrkKY
				JSONObject result = downloadDistanceDetails(longitude,latitude,originlong,originlat);
				newD = result.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getInt("value");
				value = result.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text");
				distance = result.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text");
				if(newD< minD) {
					minD = newD;
					minVal = value;
					ndistance = distance;
				}

			}

		}
		//System.out.println(longitude +" , "+ latitude);
		if(flag) {
			fresult = "Closest "+busType+" bus is "+ minVal.substring(0, minVal.length()-1) + "utes away (" + ndistance + "les)";
		}
		System.out.println(fresult);
		return String.valueOf(fresult);
	}


	/**
	 * 
	 * Method to get Vehicle data from OCCT
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private JSONObject downloadVehicleDetails() throws IOException, JSONException {
		URL testURL = new URL("http://binghamtonupublic.etaspot.net/service.php?service=get_vehicles&includeETAData=1&orderedETAArray=1&token=TESTING");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				testURL.openStream()));
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject vehicles = new JSONObject(tokener);
		return vehicles;
	}
	/**
	 * 
	 * Method to get distance between 2 sets of latitudes 
	 * 
	 * @param longitude
	 * @param latitude
	 * @param origLongitude
	 * @param origLatitude
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private JSONObject downloadDistanceDetails(double longitude,double latitude,double origLongitude,double origLatitude ) throws IOException, JSONException {
		URL testURL = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+latitude +","+longitude+"&destinations="+origLatitude+","+origLongitude+"&key=AIzaSyDAh8QNZ6dxd4YFi8ygMnOldtOuUtSrkKY");
		BufferedReader reader = new BufferedReader(new InputStreamReader(testURL.openStream()));
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject distances = new JSONObject(tokener);
		return distances;
	}

}
