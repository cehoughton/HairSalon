import org.sql2o.*;
import java.util.List;

public class Stylists {
  private int mId;
  private String mType;

  public Stylists (String type) {
    this.mType = type;
  }

  public int getId() {
    return mId;
  }

  public String getType() {
    return mType;
  }


  @Override
  public boolean equals(Object otherStylists){
    if (!(otherStylists instanceof Stylists)) {
      return false;
    } else {
      Stylists newStylists = (Stylists) otherStylists;
      return this.getType().equals(newStylists.getType()) &&
        this.getId() == newStylists.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine(type) VALUES (:type)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("type", this.mType)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Stylists> all() {
    String sql = "SELECT id AS mId, type AS mType FROM cuisine";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }

  //FIND
  public static Stylists find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, type AS mType FROM cuisine WHERE id=:id";
      Stylists myStylists = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylists.class);
      return myStylists;
    }
  }


  //UPDATE
  public void update(String newType) {
    this.mType = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisine SET type = :newType WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newType", newType)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisine WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //GET RESTAURANTS
  public List<Restaurant> getRestaurants() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, cuisine_id AS myStylistsId FROM restaurants WHERE cuisine_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(Restaurant.class);
    }
  }

}
