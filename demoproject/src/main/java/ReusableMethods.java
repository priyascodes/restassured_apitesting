import io.restassured.path.json.JsonPath;

public class ReusableMethods {

    //Alternative code to abstract out JsonPath class
    public static JsonPath rawToJson(String response){
        JsonPath js = new JsonPath(response);
        return js;

    }
}
