import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(0, Cuisine.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Cuisine firstCuisine = new Cuisine("Pizza");
    Cuisine secondCuisine = new Cuisine("Pizza");
    assertTrue(firstCuisine.equals(secondCuisine));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Cuisine myCuisine = new Cuisine("Pizza");
    myCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(myCuisine));
  }

  @Test
    public void find_findCuisineInDatabase_true() {
      Cuisine myCuisine = new Cuisine("Pizza");
      myCuisine.save();
      Cuisine savedCuisine = Cuisine.find(myCuisine.getId());
      assertTrue(myCuisine.equals(savedCuisine));
    }

  @Test
    public void update_changesCuisineInDatabase_true() {
      Cuisine myCuisine = new Cuisine("Pizza");
      myCuisine.save();
      myCuisine.update("Italian");
      assertEquals("Italian", myCuisine.getType());
    }

  @Test
  public void delete_removesCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("Pizza");
    myCuisine.save();
    myCuisine.delete();
    assertEquals(0, Cuisine.all().size());
  }

  @Test
    public void getRestaurants_retrievesAllRestaurantsFromDatabase_restaurantsList() {
      Cuisine myCuisine = new Cuisine("Pizza");
      myCuisine.save();
      Restaurant firstRestaurant = new Restaurant("Eddie's", myCuisine.getId());
      firstRestaurant.save();
      Restaurant secondRestaurant = new Restaurant("Dominoes", myCuisine.getId());
      secondRestaurant.save();
      Restaurant[] restaurants = new Restaurant[] { firstRestaurant, secondRestaurant };
      assertTrue(myCuisine.getRestaurants().containsAll(Arrays.asList(restaurants)));
    }
}
