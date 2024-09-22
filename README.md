# NutritionTrackerRMA

*NutritionTrackerRMA* is a comprehensive meal planning and tracking application that focuses on user-friendly design and detailed functionality. The app is designed to help users track their nutrition, organize meals, and manage dietary preferences efficiently.

## Features

- *User Authentication*: The app begins with a splash screen that checks if the user has previously logged in, directing them to the appropriate screen. For new users, a basic validation and login process is provided.
  
- *Meal Categories*: Users can browse and select from a list of meal categories, each displayed with an image and name. Categories can be browsed via a dialog box, where users can navigate to a list of dishes by category.

- *Detailed Dish Information*: The app provides detailed descriptions for each dish, accessible through the dish list. Users can view preparation instructions, relevant categories, regions, tags, and more. There’s also integration with YouTube for video instructions and information on ingredients and caloric values.

- *Search and Filtering*: To refine the user experience, the app implements a filter screen. Users can search and sort dishes based on categories, regions, and ingredients. The filter options provide detailed filtering functionality and allow users to find appropriate dishes based on their preferences.

- *Personal Meal Plans*: Users can save dishes to a personal menu, specifying the date and meal type. The app provides an option to capture a new photo for the dish, replacing the default image. Saved dishes are stored in a database for future access and modification.

- *Statistics and Meal Planning*: The app includes a statistics page where users can view their meal planning activity over the past week. Data is presented in graphical form, tracking both the number of meals and their caloric content, with daily calorie thresholds.

- *Customizable Weekly Meal Plan*: Users can organize their weekly meals through a custom meal plan page. Each meal can be directly managed within the app, with easy access through a link that opens the meal-planning section.

- *User Profile*: The profile page gives users insight into their meal preferences and favorite dishes, categories, or regions. It also includes a visual representation of caloric intake, providing a clear overview of the user's dietary habits.

## How to Use

1. *Login*: Begin by logging in or creating a new user account.
2. *Browse Meals*: Select meal categories to explore dishes and view detailed descriptions.
3. *Create a Meal Plan*: Use the filter feature to search for specific dishes and add them to your personal meal plan.
4. *Track Your Progress*: Monitor your meal planning progress and caloric intake through the statistics page.
5. *Personalize*: Save your favorite dishes and manage your profile to get personalized recommendations.

## Technologies Used

- *Android Development*: The app is built using Android Studio, in Kotlin,  targeting Android devices.
- *SQLite*: Local storage is managed with SQLite, ensuring that saved dishes and meal plans can be accessed offline.
- *Firebase* : for database and user authentication
- *Graphical Libraries for charting* (e.g., MPAndroidChart)
- *REST APIs for nutritional data*
- *YouTube API*: The app integrates with YouTube to provide video instructions for various dishes.
