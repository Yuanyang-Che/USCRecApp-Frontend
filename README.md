# USCRecApp

This project is intended to be imported and run through [Android Studio](https://developer.android.com/studio).
The project management tool we used is [Gradle](https://gradle.org/);


## Emulator Setting:

Required: Google Play Service

Recommend Models:
Pixel XL, Pixel 5, Pixel 4, Pixel 5 XL, Pixel 4 XL

API Level: 30, 31, 32

## Login / Sign Up

* Login: Require correct email address and password combination

* Sign Up: Requires a valid email address, student NetID(10 digits), username(not empty), password(not empty), and an avatar(optional).


## Google Maps

* There are 8 recreational centers displayed on the Google Map, each of them are noted with a red marker. 

* To go to the center, simply clicking on the marker.

* Summary Button and Notification Center Button are on the left upper corner. 

* User Avatar is at the right upper corner. 

## Booking page

* The booking page display a list of timeslots of a selected date, in ascending order. Each timeslot is has 1 hr of duration. 

* For each timeslot, the maximum capacity is set to 2 for the simplicity of the testing.

* You can only book the timeslots that are not `in the past`. (More specifically, the current time must be earlier than start time of that timeslot). Booking page does not display past timeslots upen entry. 

* There are two buttons for user to navigate to the previous and next day. You cannot go to a date earlier than today as they are `in the past`.

* Book button: user can book the timeslot simply click on the book button on the right of each timeslot. After booking, app will ask you to confirm or cancel such action. Then, you will be directed to main map page with a toast indicating success or error message.

* Waitlist: the fully booked timeslots are waitlist-able, the button on the right of the slot should display `Waitlist`. 

* Booking failed: When too many people booking on the same timeslot, or you try to book a past timeslot, you will get a toast pop up stating your booking failed. 

## Summary Page

* A list view of past and upcoming appointments will be displayed. 

* Past appointments cannot be interacted.

* Upcoming appointments can be cancelled. The timeslot format follows the ones of the booking page. Click on the cancel button on the right of each timeslot to cancel.

## Notification Center Page

* The notification center button on the Google Map page will display any notification from server, notifying user a timeslot he/she waitlisted just freed up. The number in text indicate how many messages there are.

* Open and close the notification center page will reset the content. The unhandled notifications will be cleared (The server can still send you notification for the same timeslot).

* Each timeslot will have 2 buttons, a book buttons on the left to book this freed up timeslot the same as in the booking page. And a cancel button on the right to tell the server you no longer waitlist for this timeslot. Click on either button will stay on the notification page.

### 2.5 Final Improvement

* Added waitlist section in summary page. User can now stop waitlist a spot actively. 
* Color Adjustment - now the color in Book and Cancel button fits nicely with the background.
* Added a complete user profile page - access by click on the user avator in the main page. It displays the username, email, Net ID, and avatar. 