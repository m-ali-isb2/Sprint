# Sprint Library

**Sprint** is a Kotlin library designed to provide developers with a powerful and versatile tool for managing time-based iterations, intervals, and concurrent programming tasks. With its flexible features, Sprint simplifies the creation of timers, countdowns, and repetitive task management, making it a valuable addition to your Kotlin or Android development toolkit.

---

## Features

### 1. Definite or Indefinite Iterations
- Configure Sprints to run for a fixed number of iterations or indefinitely until stopped.
- Suitable for tasks with a specific duration or ongoing processes.

### 2. Runtime Iteration Control
- Dynamically update the number of iterations while the Sprint is running.
- Adjust the behavior of loops in response to runtime conditions, such as user input or external events.

### 3. Dynamic Interval Adjustment
- Modify the interval duration between iterations at runtime.
- Allows adaptive behavior, such as accelerating or slowing down tasks dynamically.

### 4. Pause and Resume Functionality
- Pause and resume Sprint execution seamlessly.
- Retain progress during interruptions, ideal for interactive or conditional workflows.

### 5. Thread Flexibility
- Run Sprint on the main thread for UI-related tasks or on a background thread for concurrent operations.
- Tailored for a wide range of application scenarios.

### 6. Countdown Sprints
- Built-in support for countdown timers with iterations counting down from a specified number to 1.
- Useful for creating timers, reminders, and countdown-based functionality.

### 7. Awareness of Last Iteration
- Informs developers when the current iteration is the last one.
- Enables specific actions to be executed at the end of a Sprint.

### 8. Exceptional Case Handling
- Handles edge cases and invalid configurations gracefully.
- Prevents crashes by addressing issues like negative intervals or invalid iteration counts.

### 9. Precise Interval Control
- Maintains accurate and consistent timing, even under varying system load.
- Ensures precision for tasks such as animations, monitoring, or time-critical operations.

### 10. Concurrent Programming
- Supports concurrent execution of multiple Sprints.
- Allows simultaneous management of time-sensitive tasks, making it ideal for complex applications.

---

## Installation

To include Sprint in your Kotlin or Android project,copy the `sprint` folder into your project, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation(project(mapOf("path" to ":Sprint")))
}
```

---

# Usage

## 1. Basic Setup

Follow these steps to use Sprint in your project:

1. Create a Sprint instance.
2. Define a callback function to execute at the start of each iteration.
3. Configure Sprint with the desired interval and number of iterations.
4. Start the Sprint with the configuration and callback function.

### Example

```kotlin
val sprint = Sprint()

// Define a callback function to execute at the start of each round
val onRoundStart: (timeMillis: Long, round: Long, roundType: RoundType) -> Unit = { timeMillis, round, roundType ->
    println("Round $round started at $timeMillis ms.")
}

// Create a Sprint configuration
val sprintConfig = Config(
    intervalMillis = 1000, // Set the interval to 1000ms
    iterations = 10,       // Set the number of iterations to 10
    countDown = false      // Set countDown to false
)

// Start Sprint with the configuration and callback
sprint.start(sprintConfig, onRoundStart)
```
---

# Sprint Documentation

## 2. Advanced Features

### Dynamic Updates
- **Update the number of iterations during runtime:**
```kotlin
  // sprint.updateIterations(5)
```

## Pause and Resume

- **Pause the Sprint:**
```kotlin
  // sprint.pause()
```

## Resume the Sprint

- **Resume the Sprint:**
```kotlin
  // sprint.resume()
```

## Thread Management

- **Run the Sprint on the main thread:**
```markdown
  // sprint.updateDispatch(true)
```

