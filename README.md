# SQL queries for CSV files
- [About](https://github.com/jawwr/CLI-sql-for-csv#about)
- [How to work with interpreter](https://github.com/jawwr/CLI-sql-for-csv#how-to-work-with-interpreter)
    - [How to run the program](https://github.com/jawwr/CLI-sql-for-csv#how-to-run-the-program)
    - [Available implemented features](https://github.com/jawwr/CLI-sql-for-csv#available-implemented-features)
    - [Commands](https://github.com/jawwr/CLI-sql-for-csv#commands)
- [Example](https://github.com/jawwr/CLI-sql-for-csv#example-1)
# About
> This is CLI-program, interpreter SQL queries for CSV files. It helps to work with CSV files using SQL queries. 
The program uses directory as DataBase and the CSV files as Table in DataBase. To use program without bugs and problems
first row in CSV file should be the header and the following rows should be values and separated by the `;`
#### For example
```
id;name;department_id
1;Ilya;1
2;Alex;3
3;Denis;2
4;Sam;1
5;Jane;2
```
# How to work with interpreter
## How to run the program
1.
    ```
    cd sql-interpreter-for-csv
    ```
2.
    ```
    mvn clean package
    ```
3.
    ```
    java -jar target\csv-sql-cli.jar "<directory path>"
    ```
## Available implemented features
- [x] **CREATE**
- [x] **DELETE**
- [x] **DROP**
- [x] **FROM**
- [x] **INSERT**
- [x] **JOIN**
- [x] **LIMIT**
- [x] **SELECT**
- [x] **UPDATE**
- [x] **WHERE**
## Commands
To use command you need to enter `:` before command and enter command name
#### Example
```
:help
```
### Available commands
- `h, help`- Show all available commands
- `cd`- Change working directory
- `f, feature`- Show all available feature and their description
# Example
```
$ cd D:\sql-interpreter-for-csv
$ mvn clean package
$ java -jar target\csv-sql-cli.jar "D:\folder with csv files"

Input Query:
select id, name from employees limit 5
+----+-----+----------+
|    |  id |   name   |
+----+-----+----------+
|  1 |    1|      Ilya|
+----+-----+----------+
|  2 |    5|      Alex|
+----+-----+----------+
|  3 |    6|     Denis|
+----+-----+----------+
|  4 |   10|       Sam|
+----+-----+----------+
|  5 |   12|      Jane|
+----+-----+----------+
Input Query:
select e.id, e.name, d.department_id, d.name from employees e join department d on e.department_id = d.department_id limit 5
+---+----+--------+---------------+-------------+
|   | id |  name  | department_id |     name    |
+---+----+--------+---------------+-------------+
| 1 |   1|    Ilya|              1|           IT|
+---+----+--------+---------------+-------------+
| 2 |   5|    Alex|              3|      Sellers|
+---+----+--------+---------------+-------------+
| 3 |   6|   Denis|              2|   Management|
+---+----+--------+---------------+-------------+
| 4 |  10|     Sam|              1|           IT|
+---+----+--------+---------------+-------------+
| 5 |  12|    Jane|              3|      Sellers|
+---+----+--------+---------------+-------------+
Input Query:
update employees set department_id = 2 where id = 12
Complete!
Input Query:
select e.id, e.name, d.department_id, d.name from employees e join department d on e.department_id = d.department_id limit 5
+---+----+--------+---------------+-------------+
|   | id |  name  | department_id |     name    |
+---+----+--------+---------------+-------------+
| 1 |   1|    Ilya|              1|           IT|
+---+----+--------+---------------+-------------+
| 2 |   5|    Alex|              3|      Sellers|
+---+----+--------+---------------+-------------+
| 3 |   6|   Denis|              2|   Management|
+---+----+--------+---------------+-------------+
| 4 |  10|     Sam|              1|           IT|
+---+----+--------+---------------+-------------+
| 5 |  12|    Jane|              2|   Management|
+---+----+--------+---------------+-------------+
```
