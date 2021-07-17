# Stock-screening application

A stock-screening application created using Scala/SBT/ScalaFX combined with Yahoo finance API.

The application gathers data from Yahoo Finance API, sorts and visualizes it in a user friendly manner.

The user can analyze the chosen stock, change themes and lots more.

## Built With

  - [Scala](http://www.scala-lang.org/) 2.13.4
  - [SBT](http://www.scala-sbt.org/)
  - [scalaj-http](https://github.com/scalaj/scalaj-http) 2.4.2
  - [ScalaFX](https://www.scalafx.org/) 14-R19
  - [Circe](https://circe.github.io/circe/) 0.12.3

## Examples

Choosing the stocks:![Screenshot_20210717_132659](https://user-images.githubusercontent.com/68393238/126035755-5f84c7dc-91db-42cc-ae81-47e6276a7fdc.png)

Basic visuals:![Screenshot_20210717_132734](https://user-images.githubusercontent.com/68393238/126035786-eb9a1cd7-c2aa-4d45-a4cc-d3c957fd4a5c.png)


## Prerequisites

You will need to have **Scala** and **sbt** installed to run the project.

### Installation

To install and run locally please follow these steps

1. Clone the repo
   ```sh
   git clone https://github.com/LinuzJ/stock-screening-app.git
   ```
2. Go to the root directory
   ```sh
   cd stock-screening-app
   ```
3. Execute `sbt run` in the project root directory.
   ```sh
   sbt run
   ```
