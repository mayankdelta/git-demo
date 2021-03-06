import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class IFEHelper {
	
	public JSONArray parseIfeData(JSONArray arr) throws IOException, JSONException {
		   
		Map<String,Map<String,Integer>> regionMap = new HashMap<String,Map<String,Integer>>();
		Map<String,Integer> northAmericaMap = new HashMap<String,Integer>();
		northAmericaMap.put("Eastbound/Northbound", 21);
		northAmericaMap.put("Westbound/Southbound", 22);
		Map<String,Integer> alaskaMap = new HashMap<String,Integer>();
		alaskaMap.put("To Alaska/Hawaii/Mexico/Caribbean/Central America", 31);
		alaskaMap.put("From Alaska/Hawaii/Mexico/Caribbean/Central America", 32);
		alaskaMap.put("To Alaska/Hawaii from Atlanta/Cincinnati Only", 33);
		alaskaMap.put("From Alaska/Hawaii from Atlanta/Cincinnati Only", 34);
		Map<String,Integer> europeMap = new HashMap<String,Integer>();
		europeMap.put("To Europe/South America", 41);
		europeMap.put("From Europe/South America", 42);
		europeMap.put("To Israel only", 43);
		Map<String,Integer> africaMap = new HashMap<String,Integer>();
		africaMap.put("To Africa", 51);
		africaMap.put("From Africa", 52);
		Map<String,Integer> indiaMap = new HashMap<String,Integer>();
		indiaMap.put("To India", 61);
		indiaMap.put("From India", 62);
		Map<String,Integer> asiaMap = new HashMap<String,Integer>();
		asiaMap.put("To Asia", 71);
		asiaMap.put("From Asia", 72);
		asiaMap.put("From Japan to Intra-Asia", 73);
		asiaMap.put("From Intra-Asia to Japan", 74);
		regionMap.put("North America", northAmericaMap);
		regionMap.put("Alaska/Hawaii/Mexico/Caribbean/Central America", alaskaMap);
		regionMap.put("Europe and South America", europeMap);
		regionMap.put("India", indiaMap);
		regionMap.put("Asia", asiaMap);
		JSONArray finalArray = new JSONArray();
	    	for(int i=0;i<arr.length();i++)
	        {
	        	JSONObject obj = arr.getJSONObject(i);
	        	String month = obj.getString("month");
		        JSONArray regionsArray = obj.getJSONArray("regions");
	               for(int k=0;k<regionsArray.length();k++)
	               {
	            	   JSONObject regionObject = regionsArray.getJSONObject(k);
	            	   String regionName = regionObject.getString("region");
	            	   JSONArray directions = regionObject.getJSONArray("directions");
	            	   for(int l=0;l<directions.length();l++)
	            	   {
	            		   JSONObject directionsObject = directions.getJSONObject(l);
	            		   Map<String,Integer> directionsMap = regionMap.get(regionName);
	            		   if(directionsMap!=null)
	            		   {
		            		   for (Entry<String, Integer> e : directionsMap.entrySet()) {
		            			    String key    = e.getKey();
		            			    int value  = e.getValue();
		            			    if(directionsObject.has(key))
		            			    {
		            			    	//System.out.println(directionsObject.get(key));
		            			    	JSONArray directionMovieArray = directionsObject.getJSONArray(key);
		            			    	for(int m=0;m<directionMovieArray.length();m++)
		            			    	{
		            			    		JSONObject directionMovie = directionMovieArray.getJSONObject(m);
		            			    		String title = directionMovie.getString("trackCensoredName")+"-"+directionMovie.getString("feature")+"-"+key;
		            			    		directionMovie.put("title", title);
		            			    		directionMovie.remove("artistName");
		            			    		directionMovie.remove("previewUrl");
		            			    		directionMovie.remove("trackTimeMillis");
		            			    		directionMovie.remove("artworkName");
		            			    		directionMovie.remove("contentAdvisoryRating");
		            			    		directionMovie.remove("primaryGenreName");
		            			    		directionMovie.remove("longDescription");
		            			    		directionMovie.put("direction", value);
		            			    		directionMovie.put("month", month);
		            			    		finalArray.put(directionMovie);
		            			    	}
		            			    }
		            			}
	            		   }
	            	   }
	               }

	        }

	    return finalArray;
	}

	

}
