# Daily Planner App

A mobile application designed as a personal organizer to efficiently manage tasks.

## Features

### Task List with Calendar

#### Main Screen
- Displays a calendar and a list of tasks.
- Allows users to select a specific day.
- Upon selection, a table updates dynamically at the bottom of the screen, displaying hourly slots (e.g., 14:00–15:00).
- Tasks scheduled for a specific hour are displayed as blocks containing the task's name and time.

#### Task Format
Tasks are represented in JSON format:
```json
{
  "id": 1,
  "dateStart": "147600000",
  "dateFinish": "147610000",
  "name": "My task",
  "description": "Description"
}
```
**Note:** `date_start` and `date_finish` are timestamps.

### Task Details Screen
- Displays detailed information about a task, including:
  - Task name.
  - Date and time.
  - A brief text description.

### Create Task Screen
- Allows users to:
  - Enter a task name.
  - Select a date and time.
  - Add a brief description.

#### Calendar
- The custom calendar (implemented in Jetpack Compose) only permits selection of today's date and future dates.

## Technical Details

### Compatibility
- **Supported Versions:** Android 8+
- **Orientation:** Portrait-only

### Architecture
The app follows the Model-View-Intent (MVI) architecture. Key components:
- **State:** Represents the UI's current state (e.g., selected date, list of tasks).
- **Event:** Captures user actions or interactions (e.g., selecting a date, creating a task).
- **Effect:** Handles one-time actions or side effects (e.g., navigation between screens).
- MVI is implemented using ViewModel and Kotlin Flows.

### Modular Design
- Project modules are organized by feature for better scalability and maintainability.

### Design Patterns
- **Singleton:** Ensures a single instance for specific components.
- **Repository:** Handles data operations and provides a unified interface for the ViewModel.
- **UseCase:** Encapsulates business logic.
- **Mapper:** Converts data models between different layers.

### Data Handling
- **Local Storage:** Uses Room (with TaskDAO) for managing task data.
- **Service Layer:** Prepares and processes data before passing it to the UI layer.

### Dependency Injection
- Dependencies are injected using Dagger Hilt for each module.

### Testing
- Includes two unit tests for `CreateTaskUseCase` to ensure robust functionality.

