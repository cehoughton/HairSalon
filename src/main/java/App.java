import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newrestaurant", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/newrestaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newRestaurantName = request.queryParams("newRestaurantName");
      int newRestaurantCuisineId = Integer.parseInt(request.queryParams("newRestaurantCuisineId"));
      Restaurant newRestaurant = new Restaurant(newRestaurantName, newRestaurantCuisineId);
      newRestaurant.save();
      model.put("restaurants", Restaurant.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  //
  //   /******************************************************
  //   STUDENTS:
  //   TODO: Create page to display information about the selected restaurant
  //   TODO: Create page to display restaurants by cuisine type
  //   *******************************************************/
  //
  }
}
