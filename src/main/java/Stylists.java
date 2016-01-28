import org.sql2o.*;
import java.util.List;

public class Stylists {
  private int mId;
  private String mName;

  public Stylists (String name) {
    this.mName = name;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }


  @Override
  public boolean equals(Object otherStylists){
    if (!(otherStylists instanceof Stylists)) {
      return false;
    } else {
      Stylists newStylists = (Stylists) otherStylists;
      return this.getName().equals(newStylists.getName()) &&
        this.getId() == newStylists.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylists(name) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Stylists> all() {
    String sql = "SELECT id AS mId, name AS mName FROM stylists";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }

  //FIND
  public static Stylists find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName FROM stylists WHERE id=:id";
      Stylists myStylists = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylists.class);
      return myStylists;
    }
  }


  //UPDATE
  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE stylists SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //GET Clients
  public List<Clients> getClients() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylists_id AS mStylistsId FROM clients WHERE stylists_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeAndFetch(Clients.class);
    }
  }

}
