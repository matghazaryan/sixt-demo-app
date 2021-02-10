# sixt-demo-app

I have used MVVM and Clean archtecture approach. 

# Archtecture and libraries

- **Architecture Components** (ViewModel, ViewBinding)

- **Retrofit** for networking

- **Coroutines** for async programming

- **Hilt** for dependency injection

# Multi-modules

I have divided project into several modules

- **App module** - *This module is responsible for almost nothing and is actually doing nothing except managing dependency injection and including other modules*

- **Domain modules** - *Those modules are about providing data.*

- **Feature module** - *Those modules represent the features of app. I can say each screen. Feature module is responsible for creating its dependencies through DI Module*

- **Utils module** - *Those modules provides some tools for easy writing the code*

- **Car-api** - *Those modules provides networking tools for making a request and creating mapper for easy parsing the data. *




