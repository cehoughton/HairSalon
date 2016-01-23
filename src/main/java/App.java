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
      model.put("clients", Clients.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newclient", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/newclient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newClientsName = request.queryParams("newClientsName");
      int newClientsStylistsId = Integer.parseInt(request.queryParams("newClientsStylistsId"));
      Clients newClients = new Clients(newClientsName, newClientsStylistsId);
      newClients.save();
      model.put("clients", Clients.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  //
  //   /******************************************************
  //   STUDENTS:
  //   TODO: Create page to display information about the selected restaurant
  //   TODO: Create page to display clients by cuisine type
  //   *******************************************************/
  //
  }
}
