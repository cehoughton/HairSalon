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


    get("/stylists",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists",Stylists.all());
      model.put("clients", Clients.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("stylist");
       Stylists newstylist = new Stylists(name);
       newstylist.save();
       response.redirect("/stylists");
       return null;
    });

    post("/clients/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String newClient = request.queryParams("newClient");
      int newClientStylistsId = Integer.parseInt(request.queryParams("stylistId"));
      Clients client = new Clients(newClient, newClientStylistsId);
      client.save();
      model.put("clients", Clients.all());
      model.put("stylists",Stylists.all());
      model.put("template", "templates/clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylists thisStylist = Stylists.find(Integer.parseInt(request.params(":id")));
      model.put("clients", Clients.all());
      model.put("stylist", thisStylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}
