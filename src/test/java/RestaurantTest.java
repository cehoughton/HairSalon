import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Restaurant firstRestaurant = new Restaurant("Dominoes", 1);
    Restaurant secondRestaurant = new Restaurant("Dominoes", 1);
    assertTrue(firstRestaurant.equals(secondRestaurant));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Dominoes", 1);
    myRestaurant.save();
    assertTrue(Restaurant.all().get(0).equals(myRestaurant));
  }

  @Test
    public void find_findRestaurantInDatabase_true() {
      Restaurant myRestaurant = new Restaurant("Dominoes", 1);
      myRestaurant.save();
      Restaurant savedRestaurant = Restaurant.find(myRestaurant.getId());
      assertTrue(myRestaurant.equals(savedRestaurant));
    }

  @Test
    public void update_changesRestaurantInDatabase_true() {
      Restaurant myRestaurant = new Restaurant("Dominoes", 1);
      myRestaurant.save();
      myRestaurant.update("Eddie's");
      assertEquals("Eddie's", myRestaurant.getName());
    }

  @Test
  public void delete_removesRestaurantInDatabase_true() {
    Restaurant myRestaurant = new Restaurant("Dominoes", 1);
    myRestaurant.save();
    myRestaurant.delete();
    assertEquals(0, Restaurant.all().size());
  }

  @Test
    public void getCuisineType_retrievesCuisineTypeofRestaurantFromDatabase_cuisineList() {
      Cuisine myCuisine = new Cuisine("Pizza");
      myCuisine.save();
      Restaurant myRestaurant = new Restaurant("Eddie's", myCuisine.getId());
      myRestaurant.save();
      assertEquals("Pizza", myRestaurant.getCuisineType());
    }
}
