# Flashcards API

[![Project Status: WIP – Initial development is in progress, but there has not yet been a stable, usable release suitable for the public.](https://www.repostatus.org/badges/latest/wip.svg)](https://www.repostatus.org/#wip)

## About this project

This is the API for the flashcards application located at this other [repository](https://github.com/gabrielnakaema/flashcards-front)

This is still a work in progress

This project's goal is to create an application where users can create and store decks of flashcards that can assist them in learning through spaced repetition methods.

At the moment, users can create decks and add cards to it and study from decks created by other people, API endpoints can be located down [below](#api-endpoints).

### Technologies used

- Java 11
- Spring Boot
- Spring Security
- Lombok
- PostgreSQL

## Planned features

- Allow users to set created decks to private and public
- Add record of last accessed cards by a user so the next time they can get different cards
- Add possibility to prioritize cards or not, so they can come first or last
- Allow users to save copies of someone else's decks

## <a id="api-endpoints">API Endpoints</a>

For now, everyone has read rights to all resources, but only its creator has modification rights.

JWT token is obtained by signing up and then logging in with the correct credentials.

\* Indicates that endpoint needs an authenticated user by using a valid bearer token in the authorization header

\*\* Indicates that the desired action can only be performed by the creator of the deck

### Auth

| Method |         URL         | Description | Sample Request Body  |
| :----: | :-----------------: | :---------: | :------------------: |
|  POST  | /api/v1/auth/login  |   Log in    | [JSON](#login-body)  |
|  POST  | /api/v1/auth/signup |   Sign up   | [JSON](#signup-body) |

### User

| Method |         URL         | Description | Sample Request Body  |
| :----: | :-----------------: | :---------: | :------------------: |
|  GET*  | /api/v1/user/me  |   Get logged in user details    |   |

### Decks

|   Method   |          URL           |   Description    |    Sample Request Body    |
| :--------: | :--------------------: | :--------------: | :-----------------------: |
|   POST\*   |     /api/v1/decks      |     Add deck     |  [JSON](#add-deck-body)   |
|    GET     |     /api/v1/decks      |  List all decks  |                           |
|    GET     | /api/v1/decks/{deckId} | Get deck details |                           |
|  PUT\*\*   | /api/v1/decks/{deckId} |   Update deck    | [JSON](#update-deck-body) |
| DELETE\*\* | /api/v1/decks/{deckId} |   Delete deck    |                           |

### Cards

|   Method   |                  URL                  |     Description      |    Sample Request Body    |
| :--------: | :-----------------------------------: | :------------------: | :-----------------------: |
|  POST\*\*  |     /api/v1/decks/{deckId}/cards      |  Add cards to deck   |  [JSON](#add-cards-body)  |
|    GET     |     /api/v1/decks/{deckId}/cards      | List cards in a deck |                           |
|    GET     | /api/v1/decks/{deckId}/cards/{cardId} |  Get a single card   |                           |
|  PUT\*\*   | /api/v1/decks/{deckId}/cards/{cardId} |     Update card      | [JSON](#update-card-body) |
| DELETE\*\* | /api/v1/decks/{deckId}/cards/{cardId} |     Delete card      |                           |

### Sample JSON Request Bodies

#### <a id="signup-body">Sign up</a>

```json
{
  "name": "John Doe",
  "username": "JohnDoe777",
  "password": "password",
  "confirmPassword": "password"
}
```

#### <a id="login-body">Login</a>

```json
{
  "username": "JohnDoe777",
  "password": "password"
}
```

#### <a id="add-deck-body">Add deck</a>

```json
{
  "title": "Lorem ipsum",
  "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
}
```

#### <a id="update-deck-body">Update deck</a>

```json
{
  "title": "Updated deck title",
  "description": "Updated deck description"
}
```

#### <a id="add-cards-body">Add cards</a>

Accepts array of cards

```json
[
  {
    "question": "Lorem ipsum",
    "answer": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
    "hint": "Duis aute irure dolor in reprehenderit in voluptate velit esse"
  },
  {
    "question": "Lorem ipsum",
    "answer": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
    "hint": "Duis aute irure dolor in reprehenderit in voluptate velit esse"
  }
]
```

#### <a id="update-card-body">Update card</a>

```json
{
  "question": "Updated question",
  "answer": "Updated answer",
  "hint": "Updated hint"
}
```
