package ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import static ui.drivers.Driver.driver;

public class AuctionPage extends BasePage {

    @FindBy(xpath = "//a[@class='button css-ryz5k5']")
    private List<WebElement> placeBetButtons;
    @FindBy(xpath = "(//button[@class='button css-1l3sw6q'])[3]")
    private WebElement bidButton;
    @FindBy(xpath = "//div[@class='typography secondary-text default css-4wstx7']")
    private WebElement numberOfLot;
    @FindBy(xpath = "//div[@class=' css-1a0g2vr']")
    private List<WebElement> lotItems;
    @FindBy(xpath = "(//div[@class='hiding-panel css-456by9'])[2]")
    private WebElement historyOfTradingDropDown;
    @FindBy(xpath = "(//tr[@class='custom-table-body-row css-1vumfdj'])[1]")
    private WebElement firstRowInTradingHistory;
    @FindBy(id = "like-lot-btn")
    private List<WebElement> likeBtn;

    @FindBy(xpath = "//button[@class='button css-159zyqw']")
    private WebElement likedButton;
    private WebElement lastLikedElement;


    public void bidding() {
        elementActions.scrollToElements(lotItems);
        elementActions.waitElementsToBeVisible(lotItems);
        if (lotItems.isEmpty()) {
            System.out.println("Нет лотов");
        } else {
            Random random = new Random();
            for (WebElement betButton : placeBetButtons) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", betButton);
                elementActions.waitElementToBeVisible(betButton);
            }
            WebElement randomBetButton = placeBetButtons.get(random.nextInt(placeBetButtons.size()));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomBetButton);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elementActions.waitButtonToBeClickable(bidButton);
            elementActions.clickButton(bidButton);
        }
    }

    public boolean isBidInHistoryOfTrading() throws InterruptedException {
        elementActions.waitElementToBeVisible(historyOfTradingDropDown);
        elementActions.scrollBy(driver, 0, 150);
        elementActions.clickButton(historyOfTradingDropDown);
        String firstRowText = firstRowInTradingHistory.getText();
        return firstRowText.contains("Вы");
    }

    public void addToFavorites() {
        elementActions.scrollToElements(lotItems);
        elementActions.waitElementsToBeVisible(lotItems);
        Random random = new Random();
        WebElement randomLikeButton = likeBtn.get(random.nextInt(likeBtn.size()));
        elementActions.clickButton(randomLikeButton);
        lastLikedElement = randomLikeButton;
    }

    public boolean isItemColored() {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(lastLikedElement).perform();
            elementActions.waitElementToBeVisible(lastLikedElement);
            WebElement pathElement = lastLikedElement.findElement(By.tagName("path"));
            String fillColor = pathElement.getAttribute("blue");
            return "#FF6347".equalsIgnoreCase(fillColor);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }
}


