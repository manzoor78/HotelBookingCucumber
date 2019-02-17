Feature: Hotel Booking creation and deletion
  This feature deals with creating, saving and deleting a Hotel Booking

  Scenario: Create and delete a hotel booking
    Given I navigate to the hotel booking site
    When I enter Firstname , Surname, and Price
    And Enter Deposit as True or False
    And Select Check-in and Check-out dates
    And Click on Save button
    And Hotel booking is created and details are saved
    Then Delete the booking and confirm that it is deleted
