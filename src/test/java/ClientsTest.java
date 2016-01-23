import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Clients.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Clients firstClients = new Clients("Dominoes", 1);
    Clients secondClients = new Clients("Dominoes", 1);
    assertTrue(firstClients.equals(secondClients));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Clients myClients = new Clients("Dominoes", 1);
    myClients.save();
    assertTrue(Clients.all().get(0).equals(myClients));
  }

  @Test
    public void find_findClientsInDatabase_true() {
      Clients myClients = new Clients("Dominoes", 1);
      myClients.save();
      Clients savedClients = Clients.find(myClients.getId());
      assertTrue(myClients.equals(savedClients));
    }

  @Test
    public void update_changesClientsInDatabase_true() {
      Clients myClients = new Clients("Dominoes", 1);
      myClients.save();
      myClients.update("Eddie's");
      assertEquals("Eddie's", myClients.getName());
    }

  @Test
  public void delete_removesClientsInDatabase_true() {
    Clients myClients = new Clients("Dominoes", 1);
    myClients.save();
    myClients.delete();
    assertEquals(0, Clients.all().size());
  }

  // @Test
  //   public void getStylistsName_retrievesCuisineTypeofClientsFromDatabase_cuisineList() {
  //     Cuisine myCuisine = new Cuisine("Pizza");
  //     myCuisine.save();
  //     Clients myClients = new Clients("Eddie's", myStylists.getId());
  //     myClients.save();
  //     assertEquals("Pizza", myClients.getStylistsName());
  //   }
}
