
# Project Title

Tecnical test for Mercado Libre

# ItemController

## Overview
The `ItemController` handles HTTP requests related to items and coupons in the application. It provides endpoints to retrieve affordable items based on a budget and to get statistics about favorite items.

## Usage

### 1. Retrieving Affordable Items

#### Endpoint:
- **URL**: `/cupon`
- **Method**: `POST`
- **Request Body**: JSON object with the following structure:
  ```json
  {
    "itemIds": ["item1", "item2", "item3"],
    "amount": 100.0
  }

- **Response**
  ```json
  {
    "itemIds": ["item2", "item3"],
    "quantity": 80.0
  }
  
- itemIds: A list of item IDs that fit within the budget.
- total: The total cost of the selected items.


### 2. Getting Favorite Item Statistics

#### Endpoint:
- **URL**: `/cupon/stats`
- **Method**: `GET`
- **Response**
  ```json
  [
    {
     "itemId": "item1",
     "quantity": 10
    },
    {
     "itemId": "item2",
     "quantity": 20
    },
    {
     "itemId": "item3",
     "quantity": 30
    }
  ]
  
- itemId:  The ID of the item.
- quantity: The number of times the item has been marked as a favorite.


## Local Set Up

- **Clone repository**: Clone the repository and get inside the file

- **Build the application**: Run command the following command to build the application

  ```json
  mvn clean package

- **Run the JAR file**: Use the java -jar
  ```json
  java -jar pruebaMl.jar MLTest-0.0.1.jar


## PreRequisites

- [Java JDK 11](https://adoptopenjdk.net/)
- [Apache Maven](https://maven.apache.org/)


