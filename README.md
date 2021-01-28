# BookTaggingProject

## Project Description

Allows users to add and remove 'tags' (brief descriptive qualities) to books and documents from a databse, as well as search for books based on title, author, ISBN, or tags,
in order to see the title and complete list of tags possessed by each book that meets the criteria. Also allows users to publish books, complete with title, author name, 
price, and the book's content, and search for books filtered by any of those (I was not responsible for this part of the project).

## Technologies Used

* Java
* HTML
* CSS
* JavaScript
* SQL
* JDBC
* PostgreSQL

## Features

List of features created by me:
* Search for book(s) by title, author, ISBN, or tag, returning all documents in the database that meet the criteria.
* View all of the tags other users have given to the book(s) you searched for.
* Add or remove tags from a book, permanently storing the change in a database.

## Getting Started
   
https://github.com/goldenwaffle21/BookTaggingProject.git

> Open a Git Perspective in Eclipse, and choose the option to clone a git repository and add it to this view.  
> Copy the above URL into the URI input and your github username and password in the authorization inputs.
> Everything else is either prefilled or unnecessary; keep clicking "next" until you reach "finish".
> In BookTaggingProject/WorkingTree/Pubhub/WebContent, running any of the views (e.g. bookDetails.jsp) will open the application in Eclipse.

## Usage

> From any page, the Book Publishing nav dropdown can take you to the Homepage (search for book data other than tags), Book Publishing (add a book/document to the
database), Search For/By Book Tags, or Add/Remove Book Tags. (I can only take credit for the last two of those pages).
> From Search For/By Book Tags you can select what your search will be filtered by with a dropdown, input your parameters in the text field, and hit "Go!" to search.
This will display the book or books in the database that match your input as a table. You can also travel directly to Add/Remove Book Tags via a link below the table.
> From Add/Remove Book Tags, you choose whether to add or remove with a dropdown, input the target book's ISBN, and then type any number of tags you wish to add or remove
in the textbox, separated by a comma.

## License

This project uses the following license: [<MIT License>](<https://opensource.org/licenses/MIT>).
