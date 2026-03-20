# Task CLI

A simple and lightweight command-line task manager written in Java — no external libraries, no frameworks, just pure Java and a JSON file.


## Project Structure

```
task-cli/
├── Task.java        # Data model — holds task fields and serialization
├── TaskList.java    # Business logic — manages the collection and JSON file
└── TaskCli.java     # Entry point — parses and routes CLI commands
```

---

##  Requirements

- Java 11 or higher
- No external dependencies

---

## Getting Started

**1. Clone the repository**
```bash
git clone https://github.com/your-username/task-cli.git
cd task-cli
```

**2. Compile**
```bash
javac Task.java TaskList.java TaskCli.java
```

**3. Run**
```bash
java TaskCli add "Buy groceries"
```


Now use it from anywhere:
```bash
task add "Buy groceries"
task list
```

---

## Usage

```bash
# Add a task
java TaskCli add "<description>"

# Update a task
java TaskCli update <id> "<new description>"

# Delete a task
java TaskCli delete <id>

# Mark as in progress
java TaskCli mark-in-progress <id>

# Mark as done
java TaskCli mark-done <id>

# List all tasks
java TaskCli list

# List by status
java TaskCli list todo
java TaskCli list in-progress
java TaskCli list done
```

---

## Examples

```bash
$ java TaskCli add "Buy groceries"
Task added successfully (ID: 1)

$ java TaskCli add "Study for math exam"
Task added successfully (ID: 2)

$ java TaskCli mark-in-progress 1
Task 1 marked as in-progress.

$ java TaskCli mark-done 2
Task 2 marked as done.

$ java TaskCli list
ID    Status       Description                              Updated At
--------------------------------------------------------------------------------
1     in-progress  Buy groceries                            2026-03-20 15:38:03
2     done         Study for math exam                      2026-03-20 15:40:17

$ java TaskCli list done
ID    Status       Description                              Updated At
--------------------------------------------------------------------------------
2     done         Study for math exam                      2026-03-20 15:40:17

$ java TaskCli update 1 "Buy groceries and cook dinner"
Task 1 updated.

$ java TaskCli delete 1
Task 1 deleted.
```

---

## Task Properties

Each task is stored with the following fields:

| Field         | Type   | Description                          |
|---------------|--------|--------------------------------------|
| `id`          | int    | Auto-incremented unique identifier   |
| `description` | String | Short description of the task        |
| `status`      | String | `todo`, `in-progress`, or `done`     |
| `createdAt`   | String | ISO timestamp of creation            |
| `updatedAt`   | String | ISO timestamp of last update         |

---

## Storage

Tasks are saved in a `tasks.json` file in the current directory. It is created automatically on first use.

```json
[
  {
    "id": 1,
    "description": "Buy groceries",
    "status": "todo",
    "createdAt": "2026-03-20T15:38:03",
    "updatedAt": "2026-03-20T15:38:03"
  }
]
```

---

## Design Decisions

- **No external libraries** — JSON is parsed and written manually using only `java.nio` and `java.time`.
- **Three-class architecture** — `Task` handles data, `TaskList` handles logic, `TaskCli` handles input. Each class has one responsibility.
- **Auto-incrementing IDs** — IDs never repeat, even after deletions.
- **Timestamps** — `createdAt` and `updatedAt` are managed automatically by the `Task` class.

---
