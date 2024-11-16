package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private final WebDriver driver;

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  private static final By SPINNER = By.cssSelector("[data-locator-id=miniBag-loadingOverlay-read]");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  public static final String REMOVE_BUTTON = "[data-locator-id='miniBag-remove-%s-remove']";
  public static final String QUANTITY_BUTTON = "[data-locator-id='miniBag-quantityDropdown-%s-select']";
  public static final String QUANTITY = "[data-locator-id=miniBag-miniBagItem-%s-read] [class='qty-selector_text__4uAGo']";

  public BagPage(WebDriver driver) {
    this.driver = driver;
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProduct(String productId) {
    getCommands().waitForAndClickOnElementLocated(By.cssSelector(String.format(REMOVE_BUTTON, productId)));
    getCommands().waitForInvisibilityElementLocated(By.cssSelector(String.format(REMOVE_BUTTON, productId)));
  }

  public void setQuantity(Long productId, int desiredQuantity) {
    WebElement quantityDropdown = driver.findElement(By.cssSelector(String.format(QUANTITY_BUTTON, productId)));
    Select dropdown = new Select(quantityDropdown);
    dropdown.selectByVisibleText(String.valueOf(desiredQuantity));
    getCommands().waitForInvisibilityElementLocated(SPINNER);
  }

  public int getQuantity(Long productId){
    WebElement webElement = driver.findElement(By.cssSelector(String.format(QUANTITY, productId)));
    return StringUtils.extractNumberFromString(webElement.getText());
  }
}
