import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int mId;
  private String mName;
  private int mCuisineId;

  public Restaurant (String name, int cuisineId) {
    this.mName = name;
    this.mCuisineId = cuisineId;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getCuisineId() {
    return mCuisineId;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
             this.getId() == newRestaurant.getId() &&
             this.getCuisineId() == newRestaurant.getCuisineId();
    }
  }
  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, cuisine_id) VALUES (:name, :cuisine_id)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("cuisine_id", this.mCuisineId)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Restaurant> all() {
    String sql = "SELECT id AS mId, name AS mName, cuisine_id AS mCuisineId FROM restaurants";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  //FIND
  public static Restaurant find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, cuisine_id AS mCuisineId FROM restaurants WHERE id=:id";
      Restaurant myRestaurant = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
      return myRestaurant;
    }
  }

  //UPDATE
  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //GET CUISINE TYPE
  public String getCuisineType() {
    return Cuisine.find(mCuisineId).getType();
  }

}
