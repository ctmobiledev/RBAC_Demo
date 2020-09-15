# RBAC_Demo
 Role-Based Access Control - an Android-based security coding challenge.

A demo of my coding skills for a particular company, RBAC_Demo was intended to be a bare-bones, no-frills, Android app to model a role-based security system for a collection of web pages. 

The system includes role definitions for specific web page URL's. A click of the "Test" button runs a series of operations, generating messages to the Debug Log (LOGCAT). No messages are issued to the app's UI (except for a single Toast message directing users to look at the LOGCAT).

A collection of unit tests runs a variety of operations defined to the system, including the adding and deleting of roles, permissions, users, and URL's. 

The original challenge specified being as simple as possible, without any meaningful or stylish UI of any sort, so there are no other screens or UI controls to reflect the values of objects within the system. No operation to update individuals objects exists, and rather than using a SQLite database, all operations are done with List collections in memory.

There were two pieces in the solution to this challenge - the library module which defines the objects and associated operations, and a driver, which calls the library module as an AAR file.
