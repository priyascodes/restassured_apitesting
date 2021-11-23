import Files.PayLoad;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Basics {

    public static void main(String[] args) {
        //Validate is AddPlace api is working as expected.
        //given, when, then
        //given - all input details
        //when - submit the api - resouce and http method here
        // then - validate the response
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json")
                .body(PayLoad.addPlace()).when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server","Apache/2.4.18 (Ubuntu)");

        // Add place and then update place with new address. Perform getplace to validate if new address is updated
        // and present in response - 3 API used, Addplace, putplace and getplace - End to end test

    }
}
