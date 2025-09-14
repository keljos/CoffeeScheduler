# Coffee Scheduler

Coffee Scheduler is a Java application that is designed to help a group of colleagues manage their coffee buying schedule. The application prompts the user for their inputs and calculates a purchaser schedule for the group based on the provided information.

## Design Notes

- The CoffeeScheduler program builds the optimal fair schedule by finding the average cost of a cup of coffee, considering the total cost of the group's preferred drinks, and the variance of each individual's drink choice from that overall average. From there, the program calculates what percentage of the total planned visits each individual should pay for the group. The visits are then set in an order such that the individuals with the most expensive preferred drinks pay first, which ensures that for shorter durations the individuals with less expensive preferred drinks are not unfairly penalized.
- Visits cannot be partial! Due to rounding up to whole visit numbers, the program generates increasingly more fair schedules as the number of planned visits increases. Similarly, if the total number of planned visits are low, there could be a greater variation in the average cost each individual pays over the course of the plan verses the actual cost of their preferred drink. 

## Algorithm

- Step 1: Get user input for plan duration, output location, coffee prices, and validate the user input. If no input is provided use default(s) as appropriate.
- Step 2: Calculate average price of a coffee (total cost per visit  / number of drinks per vist).
- Step 3: Calculate percentage variance between each individual's menu cost vs average calculated price in step 2.
- Step 4: Calcuate ideal visit count for average drink price by dividing the total planned visits by the number of coffee drinkers.
- Step 5: Calculate true required visits for each individual by multiplying the ideal visit count from step 4 with the percentage variance calculated in step 3. Since visits cannot be fractions round to the nearest whole number.
- Step 6: Generate a summary
   Step 6.a: Total planned visits by each individual based off step 5
   Step 6.b: Difference between the menu price of their drink choice vs what they paid [(# of times individual paid * total cost per visit) / plan duration]
- Step 7: Generate the visit schedule of who pays for the group by iterating through the list of planned visits per individual. Start with the individual with the most expensive drink and proceeding in descending order, decrementing the number of remaining visits left for each individual at the end of the loop.
- Step 8: Print the summary and schedule to console or file as per the user's preference.


## Getting Started

- The program allows for specifying the desired schedule duration, price list, and whether to print the result to console or a file.
- Default values for each of the inputs are provided if the user does not specify their own values:
   - Default plan duration: 68 days
   - Default coffee price list: [3, 4, 5, 5, 5, 6, 6] (based on local coffee shop prices)
   - Default output location: Console

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Gradle 6.0 or higher

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd CoffeeScheduler
   ```

3. Build the project using Gradle:
   ```
   ./gradlew build
   ```

### Running the Application

To run the Coffee Scheduler application, use the following command:
```
./gradlew run
```

Follow the prompts in the console to input your coffee preferences and plan duration.
