import Files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Lesson18ParsingJsonUsingOwnMethod {

        public static void main(String[] args) {
            // Add place and then update place with new address. Perform getplace to validate if new address is updated
            // and present in response - 3 API used, Addplace, putplace and getplace - End to end test

            //*****************************
            //given, when, then
            //given - all input details
            //when - submit the api - resouce and http method here
            // then - validate the response
            //*****************************
            //1. AddPlace api first to add a place and extract the place_id from the response received.
            RestAssured.baseURI = "https://rahulshettyacademy.com";
            String apiResponse = given().log().all().queryParam("key","qaclick123")
                    .header("Content-Type", "application" + "/json").body(PayLoad.addPlace())
                    .when().post("/maps/api/place/add/json")
                    .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                    .header("Server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
            System.out.println("this is the response" + apiResponse);

            JsonPath js = new JsonPath(apiResponse);
            String placeId = js.getString("place_id");


            //2. Update Place with new address
            String newAddress = "70 Summer walk, USA";
            given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\n" +
                    "\"place_id\":\""+placeId+"\",\n" +
                    "\"address\":\""+newAddress+"\",\n" +
                    "\"key\":\"qaclick123\"\n" +
                    "}")
                    .when().put("/maps/api/place/update/json")
                    .then().log().all().assertThat().statusCode(200)
                    .body("msg",equalTo("Address successfully updated"));

            //3. Use GetPlace api to verify if address shows new address in the response.
            String getPlaceResponse =  given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
                    .when().get("/maps/api/place/get/json")
                    .then().log().all().assertThat().statusCode(200).extract().response().asString();

            JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse); // alternative to abstract out JsonPath
            String responseAddress = js1.getString("address");
            System.out.println(responseAddress);

            //Use Junit or testNG to compare and validate newAddress with responseAddress
            Assert.assertEquals(responseAddress, newAddress);


        }
    }


