# Golden Restaurant Accounting App
## [@1KVueltasAlCampo](https://www.github.com/1KVueltasAlCampo) and [zacwastaken](https://www.github.com/zacwastaken)
---
#####
[Changelog](docs/Changelog.pdf)

#### About

"Golden Restaurant Accounting App" is a desktop accounting app designed specifically for the restaurant "La Casa Dorada", designed in Java with JavaFX. The app can keep a detailed registry of the orders made by registered clients, the availability of products in the restaurant, the employees and registered clients of the restaurants, the former having their respective credentials to operate the software. The app offers the functionality of generating reports with order, product, ingredient, client and employee info, to enhance accesibility.

#### Lateral Panel

The lateral panel houses the buttons to interact with the different panes of the app. Each button is respectively labeled with the section of the app their in charge of. The panel has a label that shows the username of the current user, as well as a button to go back home with the logo of the restaurant.

#### Restaurant Panes

The restaurant panes are those conformed by the ones accessed to by the buttons labeled "Ordenes", "Productos", "Ingredientes", "Tipos de Plato", "Clientes" and "Personal". These panes house the registry of the area they are in charge of, this is, all of the information of the restaurant. All of these display a table, which rows are filled by each new entry, and columns are defined as the main information of them which will be displayed. All of these System Objects can be enabled (and in the specific case of the Orders, changed of status). All of the tables have a tooltip explaining how to operate it: single click on an entry to select, double click on a field (if editable) to edit, and left click (or context menu request) for full details of the entry. All but "Tipos de Plato" have an import and export button: the former imports directly from the specified directory within the project`/data/generated-data` and have to mimic the naming convention and formatting convention displayed within the dummy files located in said directory. The latter button (export) will dump the information in the directory `/data/otherExports`. All of these panes have a button to create or delete an entry of their kind (with the exception of "Ordenes", which replaces delete with cancel).

The pane "Ordenes" has another button, which will display the next Status an order will be in (In process, sent, delivered, or canceled) and will change according to selection on the table. The pane "Clientes" has a search box for clients by name, and will display the time it took to search in milliseconds at the bottom of it. The pane "Personal" has a button to edit the password of the employee once the correct username and old password are introduced, otherwise rejecting the action request.

#### Login Pane

The login pane is the one that will open the first time the app is opened, and will request an username and a password, as well as offering the possibility to registering as an employee into the app. It also has a label that displays the current user.

#### Reports Pane

The reports pane is used to generate the final reports of the Orders, Employees, and Products. The pane takes the starting and finishing day and hour (as specified by the user by the input methods given), a separator and a type of report, and generates the specified report in `/data/reports`.

##### IF YOU FIND THIS DOCUMENT ELSEWERE ON THE INTERNET, REFER TO [THIS GITHUB REPOSITORY](https://www.github.com/zacwastaken/golden-restaurant-accounting-app) FOR THE FULL PROJECT
