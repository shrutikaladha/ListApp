# ListApp

ListApp is structured in following way with an intention to keep the code modular, unit testable, extensible and readable.
It is divided into following packages:

1. Dagger: It has all classes related to dependency injection and also for maintaining scopes for various classes.
2. UI: It has all classes related to views : Activity, Fragments, CustomViews, ViewModels, Adapters.
3. Data: It has all classes which serve data from a remote/local/other source.
4. Model: It has classes which are used by GSON adapter for parsing API responses.
5. Base: It has all classes which would be used as base for multiple operations. They can be extended and modified as per the requirement.
keeping the basic underlying structure the same.
6. Unit tests: It has all unit test cases using Roboelectric framework and Mockito.

Classes:
- Constants: This class defines constants, such as ApiIdentifiers. This can be extended to multiple subclasses defining IntentConstants, ApiConstants, and so on.
- ListApplication: This application class is added extending Application.java class to build ApplicationComponent and dependency graph.

1. Dagger:
This is designed and implemented in code to be used based on Component and Subcomponent attributes for dependency injection.

- ApplicationComponent: This is scoped at application level and is defined as component to initialize NetworkModule.
- ListComponent: This is scoped at ListActivity level using attribute subcomponent. Any new activity can be defined in a similar way.
- SubcomponentsModule: Subcomponents are scoped at activity level. [Defined ActivityScope annotation for that]

2. UI:
Complete UI is designed based on Material Design guidelines to display list items.
Have Used grid system in dimens for maintaining the spacing grid, and fontSizes for header, title and content.

Currently, two activities are added: MainActivity and ListActivity.
- MainActivity has the startButton that would start ListActivity onClick.
- ListActivity observes on ListViewModel for the response of /getPost and /getUsers api.
It shows results of /getPost api in a recyclerview and result of /getUsers api in a textview as count of number of users
and number of characters in API response.
While observing on the view model, stateLiveData sends exact state of API requests which drives UI.
View handling is based on state sent by stateLiveData and is handled as follows:
LOADING: show progress loader.
ERROR: show an error UI.
SUCCESS: show recycler view. Here, added an extra handling for the case where there are zero results been returned from API.
It shows "No results found" view for that.

TotalElapsed time is measured as time elapsed between API Request and when both API Responses are received successfully.
It is not shown when one or both of them fails.

3. Data
This is structured as DataRepository and corresponding DataSources. Currently, only RemoteDataSource is added which would fetch
data from API response using retrofit. It can be further extended to support local cache by adding LocalDataSource in data repository.

4. Models:
--

5. Base -> StateLiveData
This is added to observe and display the exact data state on UI using LiveData. This is used so that multiple observers for observing
various states(loading, success, error, ..) can be managed through a single component in an efficient way.

6. Unit Tests
Currently, Roboelectric tests are added for ListActivity and MainActivity using Mockito to check the correct state of various UI components
in various scenarios.
Extensions:
Can write further test cases for RetrofitService to test the response being received and further handling for it.

For Logging:
Defined an HTTPLoggingInterceptor as part of Network module for tracking request/response for easy debugging of API.


