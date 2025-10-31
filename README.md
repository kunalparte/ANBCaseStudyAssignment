Overview -
The Following app demonstrates the BookList Screen which displays the list of books fetch from open source api.
Clicking on any item the Details screen of Book is shown


Libraries Integrated -

Retrofit + Coroutines - To make seamless api calls asynchronouslyt and fetch the data that need to be displayed

Hilt - Used For Dependency Injection 

Compose Ui - For Build App Screens Ui

Unit - Writing Unit test Cases

Coil  - Used for rendering image asynchronously and displaying it seamlessly and give a better and more interactive UX of Image rendering 


Dependency Injection -
AppModule - Provides App level singleton dependencies, here it provides ApiService dependency  needed for Api Call

ApiClientModule - Binds the ApiClient with ApiClientImpl and provides the dependency for ApiClient

RepositoryModule - Binds the BookRepository with BookRepositoryImpl and provides dependency for BookRepository



Classes and Implementation -

Network  Layer -
ApiService - To Define the retrofit Api  functions
ApiClient - Defines the abstract idea of Api Implementation 
ApiClientImpl - Defined the implementation of Api Client
ApiResult - Used for Handling the Api Data generically for all api calls 


Book Feature-
Domain -
Book - Defines the Business logic fields that are required by Book features 
BookRepository - Defines the abstract Business logic idea for Book Feature 

Data-
BookDTO/BookListDTO - Represents th structure fetched from Api Call 
BookMapper - Maps the data from BookDto to Book
BookRepositoryImpl - Defines the concrete implementation of abstract business logic in BookRepository


Presentation - 
BookList Screen -
BookListItem - Defines the Ui for the Book List Screen item
BookList - Defines the Ui for the Book List 
BookListScreen - Defines the  Ui representation of Book listScreen
BookListScreenActions - Which defines all the actions that can be performed on BookListScreen which in return change and update the state of BookListScreen 

BookDetailScreen - 
BookCover - Defines the representation of UI of Book Cover Image
BookLabelKey - Defines the Label that are displayed in BookDetailsScreen
RatingRow - Defines the Rating row representing rating via stars
BookDetailScreen - Represents the Ui Of Book Details Screen 



App Level - 
BookMainActivity - Responsible  for displaying handling navigation and providing a setContet container where all the composable Ui can rendered

MyApplication - Defines the HiltMainApplication with annotation@HiltAndroidApp


















 
