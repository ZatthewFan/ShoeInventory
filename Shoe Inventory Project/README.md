# Shoe Inventory Manager
#### by Matthew Fan

##### This application is used for keeping track of any shoes.

This project is designed for people who have a shoe collection of any size and for any purpose.
The application would be used to keep track of shoes and classify them based on properties to ease the search on finding them in the user's inventory.

I chose to design this application because I collect shoes for reselling and I would like an easy way to keep track of them and easily find what shoes I have based on some characteristics I would remember.


#### The properties of a shoe:
- Shoe name
- Size (US M, US W, EU M, EU W, UK M, UK W, etc.)
- Shoe size (multiple of 0.5)
- *Price* (format of xx.xx)
- Brand (starts with capital letter)
- Name of colorway (Starts with capital letter)
- Main color (lowercase)
- *Secondary color* (lowercase)
- *Name of shoe line* (Starts with capital letter)
- *Version of shoe* (integer)
- *Collab* (Starts with capital letter)
- *Release year* (integer)
- Condition (new, used, beat)
- id (unique id you enter to identify the shoe)

*properties in *italics* are optional to enter

##User Story
- As a user, I want to be able to add a Shoe to my shoeInventory
- As a user, I want to be able to remove a shoe from my shoeInventory
- As a user, I want to be able to set some properties for empty slots (optional properties)
- As a user, I want to be able to see my whole inventory
- As a user, I want to be able to search the shoes in my shoeInventory based on certain properties (set filters)**
- As a user, I want to be able to clear filters from the list of filters
- As a user, I want to be able to be able to save my inventory and be prompted to save my inventory when I exit the application if I have made any changes
- As a user, I want to be able to load a saved inventory

<br>
**if you add another shoe while filters are active, it won't be added to the filtered inventory unless you reset filters and reapply them

<br>

####List of filters (refer to The properties of a shoes for spelling, case and space sensitive):
* name
* size
* shoe size
* brand
* main color
* condition
* id
* secondary color
* colorway
* line
* version
* collab
* year
* price (lower bound, upper bound, inclusive)

<br>

####Phase 4: Task 2<br>
Loaded an inventory.<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe Checkered Stripe Vans to the inventory. ID: 0<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe White Ultraboosts to the inventory. ID: 1<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe Green Blazer sacais to the inventory. ID: 2<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe Israfils to the inventory. ID: 3<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe Jordan 1 Seafoams to the inventory. ID: 4<br>
Wed Nov 24 13:44:46 PST 2021<br>
Added shoe Jordan 1 University Blue to the inventory. ID: 5<br>
Wed Nov 24 13:44:54 PST 2021<br>
Added shoe test1 to the inventory. ID: 6<br>
Wed Nov 24 13:44:58 PST 2021<br>
Added shoe test2 to the inventory. ID: 7<br>
Wed Nov 24 13:44:59 PST 2021<br>
Saved the inventory.<br>
Wed Nov 24 13:45:03 PST 2021<br>
Added shoe test3 to the inventory. ID: 8<br>
Wed Nov 24 13:45:08 PST 2021<br>
Removed shoe test1 from the inventory. ID: 6<br>
Wed Nov 24 13:45:10 PST 2021<br>
Removed shoe test3 from the inventory. ID: 8<br>
Wed Nov 24 13:45:17 PST 2021<br>
Added shoe test4 to the inventory. ID: 9<br>
Wed Nov 24 13:45:21 PST 2021<br>
Saved the inventory.<br>
Wed Nov 24 13:45:23 PST 2021<br>
Removed shoe test2 from the inventory. ID: 7<br>
Wed Nov 24 13:45:26 PST 2021<br>
Removed shoe test4 from the inventory. ID: 9<br>
Wed Nov 24 13:45:28 PST 2021<br>
Saved the inventory.<br>

<br>

####Phase 4: Task 3
I believe my program is designed well as it has low coupling. 
However, there are a few improvements that I can do for things that 
aren't displayed in the UML design diagram.
- Some methods can be abstracted to reduce duplication.
- Cohesion on some classes might be a bit low which can be refactored into an internal class or another class entirely.
- Improvements on robustness of the application as possible as there might be some runtime exceptions