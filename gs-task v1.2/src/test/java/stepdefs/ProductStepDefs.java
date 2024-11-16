package stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

    private final WebDriver driver;
    private Long productId;
    private final Long productId2 = 39654546243787L;
    private static final String SHORTS_URL = "https://uk.gymshark.com/products/gymshark-speed-7-shorts-black-aw23";


    public ProductStepDefs() {
        this.driver = Hooks.getDriver();
    }

    @Given("the user is on a product page")
    public void theUserIsOnAProductPage() {
        driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
        productId = 39654522814667L;
        new ProductDisplayPage(driver);
    }

    @When("adding the product to the Bag")
    public void addingTheProductToTheBag() {
        ProductDisplayPage productDisplayPage = new ProductDisplayPage(driver);
        productDisplayPage.selectSmallSize();
        productDisplayPage.selectAddToBag();
    }

    @Then("the product should appear in the Bag")
    public void theProductShouldAppearInTheBag() {
        BagPage bagPage = new BagPage(driver);
        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is in Bag").contains(productId);
    }

    @Given("there are products in the bag")
    public void userIsOnABagWithDifferentProductsPage() {
        theUserIsOnAProductPage();
        addingTheProductToTheBag();

        driver.get(SHORTS_URL);
        addingTheProductToTheBag();
    }

    @When("remove a product from the bag")
    public void removeProductFromBag() {
        BagPage bagPage = new BagPage(driver);
        bagPage.removeProduct(String.valueOf(productId2));
    }

    @Then("the product is no longer in the bag")
    public void checkThatProductIsRemoved() {
        BagPage bagPage = new BagPage(driver);
        List<Long> variantIds = bagPage.getVariantIdsInBag();
        assertThat(variantIds).as("Expected product is not in Bag").doesNotContain(productId2);
    }

    @When("increase the quantity of the product on {int}")
    @When("decrease the quantity of the product on {int}")
    @And("the selected product quantity is greater than one and it is {int}")
    public void changeQuantity(int quantity) {
        BagPage bagPage = new BagPage(driver);
        bagPage.setQuantity(productId2, quantity);
    }

    @Then("the quantity of the product in the bag is increased on {int}")
    @Then("the quantity of the product in the bag is decreased on {int}")
    public void checkThatQuantityIsChanged(int quantity){
        BagPage bagPage = new BagPage(driver);
        bagPage.getQuantity(productId2);
        assertThat(quantity).isEqualTo(bagPage.getQuantity(productId2));
    }
}
