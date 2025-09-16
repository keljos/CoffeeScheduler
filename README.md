# Coffee Scheduler

Coffee Scheduler is a Java application that is designed to help a group of colleagues manage their coffee buying schedule. The application prompts the user for their inputs and calculates a purchaser schedule for the group based on the provided information.

## Design Notes

- The CoffeeScheduler program builds the optimal fair schedule by considering the total cost of each person's preferred drink, and maintaining a credit system that updates after each purchase for the group. For each trip, the algorithm updates each individual's credit based on whether they paid for drinks or not. For each iteration, it will pick the user who has the lowest credit value and assigns them to purchase the drinks taht day. This continues until the end of the desired plan duration.
- As this program works on a credit system, the longer the plan duration the more number of times individuals with expensive drinks will be scheduled to purchase drinks. This means that the algorithm will make more equatible schedules for longer plan durations.

## Assumptions

- There will always be exactly seven coffee drinkers participating in the schedule.
- Each participant will be present for every trip to the coffee shop.
- Each participant will always get their favorite coffee. Trying new drinks is not allowed. (Sorry, Bob, no pumpkin spice latte for you in the fall!)
- There will be at least 7 trips scheduled. The more trips scheduled, the more equatable the schedule, but fewer than 7 is not allowed.

## Algorithm

- Step 1: Get user input for plan duration, output location, coffee prices, and validate the user input. If no input is provided use default(s) as appropriate.
- Step 2: Calculate the total cost per visit to the coffee shop.
- Step 3: Identify the individual with the most expensive coffee.
- Step 4: Generate the schedule order:
   - Step 4a: Initialize the nextPayer with the person with the most expensive drink identified in 3.
   - Step 4b: In a loop, for every visit calculate the updates credit for all individuals based on the formula:
      - If individual is buying: new credit = current credit - menu price + total bill from step 2.
      - For all others: new credit = current credit - menu price.
      - Schedule (print) who pays the bill that trip.
- Step 5: Generate overall summary of total cost of individuals and the average price they paid. 


## Getting Started

- The program allows for specifying the desired schedule duration and price list.
- Default values for each of the inputs are provided if the user does not specify their own values:
   - Default plan duration: 68 days
   - Default coffee price list: [3, 4, 5, 5, 5, 6, 6] (based on local coffee shop prices)

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd CoffeeScheduler
   ```

### Running the Application

To run the Coffee Scheduler application, run the included jar file CoffeeScheduler.jar:
```
java -jar CoffeeScheduler.jar
```

Follow the prompts in the console to input your coffee preferences and plan duration.

### Notes

The jar file contains the code in the src folder. A alternative version that I originally implemented can be found in the src_old folder.