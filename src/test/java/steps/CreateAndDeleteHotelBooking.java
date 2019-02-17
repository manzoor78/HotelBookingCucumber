package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HotelBookingForm;
import testBase.TestBaseCommon;
import utilities.BrowserDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class CreateAndDeleteHotelBooking extends TestBaseCommon {

    HotelBookingForm hotelBookingFormPage = new HotelBookingForm();
    Random rand = new Random();
    String firstName = "test_FN" + rand.nextInt(100);
    String lastName = "test_SN" + rand.nextInt(100);
    String price = "100";

    @Before
    public void before() throws FileNotFoundException, IOException, InterruptedException {
        initialise();
    }

    @After
    public void after() {
        BrowserDriver.driver.quit();
    }


    @Given("^I navigate to the hotel booking site$")
    public void iNavigateToTheHotelBookingSite() throws Throwable {
        hotelBookingFormPage.assertOnHomePage();
    }

    @And("^Click on Save button$")
    public void clickOnSaveButton() throws Throwable {
        hotelBookingFormPage.clickSave();
    }

    @And("^Hotel booking is created and details are saved$")
    public void hotelBookingIsCreatedAndDetailsAreSaved() throws Throwable {
        hotelBookingFormPage.assertDetailsAreSaved(firstName, lastName);

    }

    @When("^I enter Firstname , Surname, and Price$")
    public void iEnterFirstnameSurnameAndPrice() throws Throwable {
        hotelBookingFormPage.enterFirstName(firstName);
        hotelBookingFormPage.enterLastName(lastName);
        hotelBookingFormPage.enterPrice(price);
    }

    @And("^Enter Deposit as True or False$")
    public void enterDepositAsTrueOrFalse() throws Throwable {
        hotelBookingFormPage.selectDepositPaid();

    }

    @And("^Select Check-in and Check-out dates$")
    public void selectCheckInAndCheckOutDates() throws Throwable {
        hotelBookingFormPage.selectCheckinDate();
        hotelBookingFormPage.selectCheckoutDate();
    }


    @Then("^Delete the booking and confirm that it is deleted$")
    public void deleteTheBookingAndConfirmThatItIsDeleted() throws Throwable {
        hotelBookingFormPage.deleteBooking(firstName, lastName);
        hotelBookingFormPage.assertRecordIsDeleted(firstName, lastName);
    }
}
