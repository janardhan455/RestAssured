package fourassigs.fourassigs;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class fourasn {
 
	public static void main(String[] args) {
		RestAssured.baseURI = "http://services.groupkt.com/state/search/";
		RequestSpecification httpRequest = RestAssured.given(); 
		Response response = httpRequest.request(Method.GET, "IND?text=pradesh");
		String responseBody = response.getBody().asString();
 
int records=response.jsonPath().getList("RestResponse.result").size();
if (records==5) {
	System.out.println("Number of records retrieved :"+ records);
} else {
	System.out.println("Number of records retrieved are not 5");
}

//Getting the largest area to get the largest city
String areas= response.jsonPath().getList("RestResponse.result.area").toString().replace("[", "").replace("]", "");
System.out.println(areas);
String[] numberStrs = areas.split(",");

int[] areanumbers= new int[numberStrs.length];
for(int i = 0;i<numberStrs.length;i++)
{
   areanumbers[i] = Integer.parseInt(numberStrs[i].replace("SKM", "").replace(" ", ""));
   
} 
int n=areanumbers.length;
int max = areanumbers[0];
int maxareaindex = 0;
for(int i = 0; i < n; i++)
{
    if(max < areanumbers[i])
    {
        max = areanumbers[i];
        maxareaindex=i;
    }
}
System.out.println("Maximum area in the JSON response is :"+max);

String resquery="RestResponse.result"+"["+maxareaindex+"].name";
String statename= response.jsonPath().getJsonObject(resquery).toString().replace("[", "").replace("]", "");
System.out.println("Largest State Name : "+statename);

String resquery1="RestResponse.result"+"["+maxareaindex+"].largest_city";
String cityname= response.jsonPath().getJsonObject(resquery1).toString().replace("[", "").replace("]", "");
System.out.println("Largest city Name : "+cityname);

String names = response.jsonPath().getList("RestResponse.result.name").toString().replace("[", "").replace("]", "");
String[] z=names.split(",");
for(int j = 0; j < z.length; j++)
{
    if(z[j].equalsIgnoreCase("Madhya Pradesh"))
    {
    	String resquery3="RestResponse.result["+j+"]";
    	String details= response.jsonPath().getJsonObject(resquery3).toString().replace("[", "").replace("]", "");
    	System.out.println("Printing details of Madhya Pradesh:"+ details);
    }
}
}
 
}