# Task Manager - Version 0.4.1

## Overview

The Task Manager is a command-line tool to help you manage tasks and their statuses. It allows you to add, delete, update, and mark tasks as "In Progress," "Done," or "Not Done." The tasks are stored in a JSON-like format in a file, making it easy to maintain and edit manually if needed.

## Features

- **Add tasks** with names and statuses.
- **Delete tasks** by their unique ID.
- **Update task status** to new statuses (Custom statuses).
- **Mark tasks as In Progress, Done, or Not Done.**
- Data is stored persistently in a file (`taskmanager.json`).

## Requirements

- Java 8 or later is required to run this program.

## Installation

1. **Clone or download** the repository to your local machine.
2. Compile the code:
   ```sh
   javac Main.java

## Commands
### **Run the program** using the following command:
   ```sh
      java Main <command> [arguments]
   ```  
### 1. Add a task:
  ```sh
     java Main add "<task_name>"
  ```
### 2. Delete a task:
   ```sh
     java Main delete <task_id>
   ``` 
### 3. Update a task:
   ```sh
      java Main update <task_id> "<new_status>"
   ```
### 4. Mark a task as Done:
   ```sh
      java Main mark-done <task_id>
   ```
### 5. Mark a task as In Progress:
   ```sh
      java Main mark-in-progress <task_id>
   ```
### 6.Mark a task as Not Done:
   ```sh
      java Main mark-not-done "<task_id>"
   ```
### 7.List all tasks:
   ```sh
      java Main list
   ```
## File Structure

- Main.java: The main Java program that contains all the logic for managing tasks.

- taskmanager.json: A JSON file that stores all the tasks in the format:
      
-    ```sh
             {
             "Task id": ["Task Name", "Status"]
              }
       ```
## Contributing

   Feel free to open issues or pull requests if you want to contribute to the project.