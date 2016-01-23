import org.sql2o.*;
import java.util.List;

public class Clients {
  private int mId;
  private String mName;
  private int mStylistsId;

  public Clients (String name, int stylistsId) {
    this.mName = name;
    this.mStylistsId = stylistsId;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getStylistsId() {
    return mStylistsId;
  }

  @Override
  public boolean equals(Object otherClients){
    if (!(otherClients instanceof Clients)) {
      return false;
    } else {
      Clients newClients = (Clients) otherClients;
      return this.getName().equals(newClients.getName()) &&
             this.getId() == newClients.getId() &&
             this.getStylistsId() == newClients.getStylistsId();
    }
  }
  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name, stylistsid) VALUES (:name, :stylistsid)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("stylistsid", this.mStylistsId)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Clients> all() {
    String sql = "SELECT id AS mId, name AS mName, stylistsid AS mStylistsId FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Clients.class);
    }
  }

  //FIND
  public static Clients find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, stylistsid AS mStylistsId FROM clients WHERE id=:id";
      Clients myClients = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Clients.class);
      return myClients;
    }
  }

  //UPDATE
  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE clients SET name = :newName WHERE id = :id";
      con.createQuery(sql)
        .addParameter("newName", newName)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM clients WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.mId)
        .executeUpdate();
    }
  }

  //GET CUISINE TYPE
  public String getStylistsName() {
    return Stylists.find(mStylistsId).getName();
  }

}
