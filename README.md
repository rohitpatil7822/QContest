# Leaderboard API Service

This is a RESTful API service for managing the leaderboard of a coding platform contest. The service allows users to register, update scores, and retrieve leaderboard rankings.

## Features

- User registration with an initial score of 0 and no badges.
- Score updates with automatic badge assignment based on the score.
- Retrieval of all users sorted by score in descending order.
- Deletion of user registrations.

## Badges

- **Code Ninja**: 1 <= Score < 30
- **Code Champ**: 30 <= Score < 60
- **Code Master**: 60 <= Score <= 100

## Endpoints

- `GET /users`: Retrieve all users.
- `GET /users/{userId}`: Retrieve a specific user.
- `POST /users`: Register a new user.
- `PUT /users/{userId}`: Update the score of a specific user.
- `DELETE /users/{userId}`: Deregister a user.

## Running the Application

1. Clone the repository.
2. Run the application with `./gradlew bootRun`.
3. Access the API at `http://localhost:8080/users`.

## Postman Collection

[https://api.postman.com/collections/21583443-06d1254e-9e62-4d9b-bcca-24f83ff6494c?access_key=PMAT-01J70QW4GSFAQSAGBQWTRCB507]
