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

    // get("/newclient", (request, reponse) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("stylists", Stylists.all());
    //   model.put("template", "templates/newclient.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
       Stylists stylist = new Stylists(request.queryParams("stylist"));
       stylist.save();
      //int newClientStylistId = Integer.parseInt(request.queryParams("newClientStylistId"));
    //  Clients newClients = new Clients(newClientsName, newClientStylistId);
      //newClients.save();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylists stylist = Stylists.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  //   post("/stylists", (request,response) -> {
  //    Map<String, Object> model = new HashMap<String, Object>();
  //    Stylists stylists = new Stylists(request.queryParams("stylists"));
  //    model.put("stylists", Stylists.all());
  //    model.put("template", "templates/stylists.vtl");
  //    return new ModelAndView(model, layout);
  //  }, new VelocityTemplateEngine());
  // //   /******************************************************
  // //   STUDENTS:
  // //   TODO: Create page to display information about the selected restaurant
  // //   TODO: Create page to display clients by cuisine type
  // //   *******************************************************/
  // //
  }
}
