package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utilities.BrowserDriver;
import utilities.CommonUtils;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class HotelBookingForm {

    By firstName = By.id("firstname");
    By lastName = By.id("lastname");
    By price = By.id("totalprice");
    By depositPaid = By.id("depositpaid");
    By checkin = By.id("checkin");
    By checkout = By.id("checkout");
    By saveBtn = By.xpath(".//div[@id='form']//input[@type='button']");
    By homePage = By.xpath("/html/body/div[1]/div[1]/h1");

    public void assertOnHomePage() {
        String homePageTitle = BrowserDriver.driver.findElement(homePage).getText();
        Assert.assertEquals("Hotel booking form", homePageTitle);
    }

    public void enterFirstName(String firstName) {
        BrowserDriver.driver.findElement(this.firstName).click();
        BrowserDriver.driver.findElement(this.firstName).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        BrowserDriver.driver.findElement(this.lastName).click();
        BrowserDriver.driver.findElement(this.lastName).sendKeys(lastName);
    }

    public void enterPrice(String price) {
        BrowserDriver.driver.findElement(this.price).click();
        BrowserDriver.driver.findElement(this.price).sendKeys(price);
    }

    public void selectDepositPaid() {
        Select depositPaidDropDown = new Select(BrowserDriver.driver.findElement(depositPaid));
        depositPaidDropDown.selectByVisibleText("true");
    }

    public void selectCheckinDate() {
        BrowserDriver.driver.findElement(checkin).click();

        String today = Integer.toString(getCurrentDay());

        WebElement dateWidgetFrom = BrowserDriver.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody"));
        List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

        for (WebElement cell : columns) {
            if (cell.getText().equals(today)) {
                cell.click();
                break;
            }
        }

    }

    public void selectCheckoutDate() {
        BrowserDriver.driver.findElement(checkout).click();

        WebElement dateWidgetFrom = BrowserDriver.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody"));
        List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));

        for (WebElement cell : columns) {
            if (cell.getText().equals(Integer.toString(getCurrentDay() + 7))) {
                cell.click();
                break;
            }
        }

    }

    public void clickSave() throws InterruptedException {
        CommonUtils.waitUntilCondition().until(ExpectedConditions.elementToBeClickable(saveBtn));
        BrowserDriver.driver.findElement(saveBtn).click();
        Thread.sleep(5000); // TODO: Replace this with better waiting mechanism
    }

    public int getCurrentDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentDay = calendar.get(calendar.DAY_OF_MONTH);

        return currentDay;
    }

    public void assertDetailsAreSaved(String firstName, String lastName) throws InterruptedException {
        List<WebElement> bookings = BrowserDriver.driver.findElements(By.xpath(".//div[@id='bookings']//div[@class='row']"));
        System.out.println("Asserting details are saved");
        int position = searchRecordAndReturnPosition(bookings.size(), firstName, lastName);

        Assert.assertTrue(position != -1);

        String firstNamePath = ".//div[@id='bookings']/div[" + position + "]/div[1]";
        String lastNamePath = ".//div[@id='bookings']/div[" + position + "]/div[2]";

        String foundFirstName = BrowserDriver.driver.findElement(By.xpath(firstNamePath)).getText();
        String foundSurName = BrowserDriver.driver.findElement(By.xpath(lastNamePath)).getText();

        Assert.assertEquals(foundFirstName, firstName);
        Assert.assertEquals(foundSurName, lastName);

    }

    /**
     * Search and returns the row number of the booking if available
     *
     * @param bookingCount
     * @param firstName
     * @param lastName
     * @return position of the booking if available else -1
     * Observation : First valid booking in the form starts at index 4
     */
    private int searchRecordAndReturnPosition(int bookingCount, String firstName, String lastName) {

        for (int i = 2; i <= bookingCount; i++) {
            String firstNamePath = ".//div[@id='bookings']/div[" + i + "]/div[1]";
            String lastNamePath = ".//div[@id='bookings']/div[" + i + "]/div[2]";
            try {
                String foundFirstName = BrowserDriver.driver.findElement(By.xpath(firstNamePath)).getText();
                String foundLastName = BrowserDriver.driver.findElement(By.xpath(lastNamePath)).getText();

                if (foundFirstName.equals(firstName) && foundLastName.equals(lastName)) {
                    return i;
                }
            } catch (org.openqa.selenium.NoSuchElementException noSuchElement) {
                System.out.println("Script element found - Ignore");
                noSuchElement.printStackTrace();
                continue;
            }
        }
        return -1;
    }

    /**
     * @param firstName
     * @param lastName
     * @throws InterruptedException Assumption: The combination of Firstname and Surname is unique and NO duplicate records present
     */
    public void deleteBooking(String firstName, String lastName) throws InterruptedException {
        List<WebElement> bookings = BrowserDriver.driver.findElements(By.xpath(".//div[@id='bookings']//div[@class='row']"));
        System.out.println("Attempting to delete booking");
        int position = searchRecordAndReturnPosition(bookings.size(), firstName, lastName);

        Assert.assertTrue(position != -1);

        String deleteButtonPath = ".//div[@id='bookings']/div[" + position + "]//input[@type='button']";
        BrowserDriver.driver.findElement(By.xpath(deleteButtonPath)).click();
        Thread.sleep(4000); // TODO: Replace this with better waiting mechanism

    }

    public void assertRecordIsDeleted(String firstName, String lastName) throws InterruptedException {
        List<WebElement> getAllbookings = BrowserDriver.driver.findElements(By.xpath(".//div[@id='bookings']//div[@class='row']"));
        System.out.println("Asserting that a record is deleted");
        int deletedRecordPosition = searchRecordAndReturnPosition(getAllbookings.size(), firstName, lastName);

        Assert.assertTrue(deletedRecordPosition == -1);

    }
}

