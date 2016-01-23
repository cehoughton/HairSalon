import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(0, Stylists.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylists firstStylists = new Stylists("Jim");
    Stylists secondStylists = new Stylists("Jim");
    assertTrue(firstStylists.equals(secondStylists));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylists myStylists = new Stylists("Jim");
    myStylists.save();
    assertTrue(Stylists.all().get(0).equals(myStylists));
  }

  @Test
    public void find_findStylistsInDatabase_true() {
      Stylists myStylists = new Stylists("Jim");
      myStylists.save();
      Stylists savedStylists = Stylists.find(myStylists.getId());
      assertTrue(myStylists.equals(savedStylists));
    }

  @Test
    public void update_changesStylistsInDatabase_true() {
      Stylists myStylists = new Stylists("Jim");
      myStylists.save();
      myStylists.update("Italian");
      assertEquals("Italian", myStylists.getName());
    }

  @Test
  public void delete_removesStylistsInDatabase_true() {
    Stylists myStylists = new Stylists("Jim");
    myStylists.save();
    myStylists.delete();
    assertEquals(0, Stylists.all().size());
  }

//   @Test
//     public void getClients_retrievesAllClientsFromDatabase_ClientsList() {
//       Stylists myStylists = new Stylists("Jim");
//       myStylists.save();
//       Client firstClient = new Client("Eddie's", myStylists.getId());
//       firstClient.save();
//       Client secondClient = new Client("Dominoes", myStylists.getId());
//       secondClient.save();
//       Client[] Clients = new Client[] { firstClient, secondClient
// };
//       assertTrue(myStylists.getClients().containsAll(Arrays.asList(Clients)));
    //}
}
