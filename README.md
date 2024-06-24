# StockMarketApp

StockMarketApp is an Android application built using Jetpack Compose for the UI, Dagger-Hilt for dependency injection, OpenCSV for CSV handling, Room for local caching, Retrofit for remote API interactions. It allows users to explore a list of companies on the American stock market, save them to a local database with a caching mechanism, and visualize the companies' value graphs over time. This project demonstrates the use of modern Android development technologies.

## Technologies Used

- **Architecture**: Clean Architecture
- **UI**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Network Requests**: Retrofit
- **Concurrency**: Kotlin Coroutines and Flow
- **Programming Language**: Kotlin

## Features

- Retrieve a list of companies from the API using Retrofit.
- Local Caching: Uses Room to cache the company list locally.
- Search for companies in the list.
- View detailed information and value graphs of selected companies.
- CSV Handling: Imports and exports stock data using OpenCSV.
- Custom UI Components: Utilizes Jetpack Compose for modern UI design and Canvas for custom.

## Screenshots

| Company Listings Screen | Company Info Screen |
| ---------------------- | ------------------ |
| ![home](https://github.com/Anand-Prasad-14/StockMarkteApp/assets/118905953/bd586ff6-23f8-4575-be6a-7c876379eaa0) |![description](https://github.com/Anand-Prasad-14/StockMarkteApp/assets/118905953/7b655648-6302-450c-9789-a79dd983c333)|

## Clone the repository: 
`https://github.com/Anand-Prasad-14/StockMarkteApp.git`

The app follows the MVVM (Model-View-ViewModel) architecture pattern.

