package com.formula.sprint

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
//
///**
// * Sprint is a Kotlin class designed to provide developers with a versatile tool for managing time-based iterations and intervals.
// * It offers the flexibility to handle both definite and indefinite iterations and allows developers to dynamically modify Sprint parameters at runtime.
// *
// * Sprint Key Features:
// *
// * 1. **Definite or Indefinite Iterations:** Developers can start a Sprint with a specific number of iterations, making it run a fixed number of times, or they can choose to run it indefinitely without a predetermined end.
// *
// * 2. **Runtime Iteration Control:** Developers have the ability to update the number of iterations while the Sprint is running, providing dynamic control over the Sprint's behavior.
// *
// * 3. **Dynamic Interval Adjustment:** The interval duration between Sprint iterations can be modified during runtime to suit changing requirements.
// *
// * 4. **Thread Flexibility:** Developers can seamlessly switch between running the Sprint on the main thread or the current thread, adapting to various threading needs.
// *
// * 5. **Countdown Sprints:** Sprint supports countdown-style iterations, where it counts down from a specified number of iterations to 1. This is useful for creating timers with countdown functionality.
// *
// * 6. **Awareness of Last Iteration:** Sprint keeps developers informed about whether the current round is the last iteration, making it easy to take specific actions when the Sprint is about to complete.
// *
// * Example Usage:
// *
// * ```kotlin
// * val sprint = Sprint()
// *
// * // Define a callback function to be executed at the start of each round
// * val onRoundStart: (timeMillis: Long, round: Long, roundType: RoundType) -> Unit = { timeMillis, round, roundType ->
// *     println("Round $round started at $timeMillis ms.")
// * }
// *
// * // Create a Sprint configuration
// * val sprintConfig = Config(
// *     intervalMillis = 1000, // Set the interval to 1000ms
// *     iterations = 10, // Set the number of iterations to 10
// *     countDown = false // Set countDown to false
// * )
// *
// * // Start the Sprint with the configuration and callback function
// * sprint.start(sprintConfig, onRoundStart)
// *
// * // Update the number of iterations to 5 while the Sprint is running
// * sprint.updateIterations(5)
// *
// * // Update the interval duration to 500ms during runtime
// * sprint.updateInterval(500)
// *
// * // Switch the Sprint to run on the main thread
// * sprint.updateDispatch(true)
// *
// * // Start a countdown Sprint with 3 iterations and a 200ms interval
// * val countdownConfig = Config(
// *     intervalMillis = 200, // Set the interval to 200ms
// *     iterations = 3, // Set the number of iterations to 3
// *     countDown = true // Set countDown to true
// * )
// * sprint.start(countdownConfig, onRoundStart)
// * ```
// *
// * Sprint offers a wide range of possibilities for managing timed tasks and is a valuable addition to your Kotlin and Android development toolkit.
// */
///*
//*
// * Sprint is a Kotlin class that provides a flexible and customizable way to manage time-based iterations and intervals,
// * often used for implementing timers or repeating tasks.
// * It provides methods for starting, updating, pausing, resuming, stopping, and querying the Sprint's status.
// *
// * Developers can use the Sprint class to implement various timing-related functionalities in their applications. By configuring the interval, iterations, and callback function, they can create customized timers and countdowns, making it versatile for tasks that involve precise timing and repetitive actions.
//*/

/**
 * Sprint is a Kotlin library designed to provide developers with a powerful and versatile tool for managing time-based iterations, intervals, and concurrent programming tasks.
 * It offers an array of features that make it suitable for implementing timers, loops, countdowns, and repetitive tasks, all with exceptional precision and flexibility.
 *
 * Sprint Key Features:
 *
 * 1. **Definite or Indefinite Iterations:**
 *    - Sprint supports both determinant (fixed number of iterations) and indeterminate (open-ended) loops.
 *    - Developers can configure a Sprint to run for a specific number of iterations or indefinitely until manually stopped.
 *
 * 2. **Runtime Iteration Control:**
 *    - Developers can dynamically update the number of iterations while the Sprint is running.
 *    - Useful for adapting to runtime changes, such as adjusting the duration of a loop based on user input or external conditions.
 *
 * 3. **Dynamic Interval Adjustment:**
 *    - The interval duration between Sprint iterations can be modified at runtime.
 *    - This allows for creating adaptive behaviors, such as accelerating or decelerating task execution.
 *
 * 4. **Pause and Resume Functionality:**
 *    - Sprint provides full control over execution with pause and resume methods.
 *    - This is ideal for use cases where tasks need to be temporarily halted and restarted without losing progress.
 *
 * 5. **Thread Flexibility:**
 *    - Developers can choose to run the Sprint on the main thread or the current thread, depending on the task’s requirements.
 *    - This makes Sprint suitable for both UI-related tasks and background operations.
 *
 * 6. **Countdown Sprints:**
 *    - Sprint includes built-in support for countdown timers, where iterations count down from a specified number to 1.
 *    - Perfect for creating countdown-based features like timers, reminders, or quiz countdowns.
 *
 * 7. **Awareness of Last Iteration:**
 *    - Sprint informs developers when the current iteration is the last one, enabling tailored actions at the completion of a loop.
 *
 * 8. **Exceptional Case Handling:**
 *    - Sprint is designed to handle edge cases and errors gracefully, ensuring robust and predictable behavior.
 *    - For example, invalid configurations such as a negative interval or iterations are handled appropriately to prevent crashes.
 *
 * 9. **Precise Interval Control:**
 *    - Sprint ensures that intervals between iterations are accurate and consistent, even under high load or varying system conditions.
 *    - This makes it a reliable choice for tasks requiring precise timing, such as animations or real-time monitoring.
 *
 * 10. **Concurrent Programming:**
 *    - Sprint supports concurrent execution of multiple instances, allowing developers to run multiple time-sensitive tasks simultaneously.
 *    - This is particularly useful for complex applications requiring simultaneous loops, countdowns, or animations.
 *
 * Example Usage:
 *
 * ```kotlin
 * val sprint = Sprint()
 *
 * // Define a callback function to be executed at the start of each round
 * val onRoundStart: (timeMillis: Long, round: Long, roundType: RoundType) -> Unit = { timeMillis, round, roundType ->
 *     println("Round $round started at $timeMillis ms.")
 * }
 *
 * // Create a Sprint configuration for 10 iterations with a 1000ms interval
 * val sprintConfig = Config(
 *     intervalMillis = 1000, // Set the interval to 1000ms
 *     iterations = 10, // Set the number of iterations to 10
 *     countDown = false // Set countDown to false
 * )
 *
 * // Start the Sprint with the configuration and callback function
 * sprint.start(sprintConfig, onRoundStart)
 *
 * // Update the number of iterations to 5 while the Sprint is running
 * sprint.updateIterations(5)
 *
 * // Update the interval duration to 500ms during runtime
 * sprint.updateInterval(500)
 *
 * // Pause the Sprint
 * sprint.pause()
 *
 * // Resume the Sprint
 * sprint.resume()
 *
 * // Switch the Sprint to run on the main thread
 * sprint.updateDispatch(true)
 *
 * // Start a countdown Sprint with 3 iterations and a 200ms interval
 * val countdownConfig = Config(
 *     intervalMillis = 200, // Set the interval to 200ms
 *     iterations = 3, // Set the number of iterations to 3
 *     countDown = true // Set countDown to true
 * )
 * sprint.start(countdownConfig, onRoundStart)
 * ```
 *
 * Sprint is a robust and reliable library that simplifies the management of time-sensitive and iterative tasks. Its comprehensive feature set makes it a valuable addition to any Kotlin or Android developer's toolkit.
 */

class Sprint {
    private var iterationES: ExecutorService? = null
    private var callBackES: ExecutorService? = null
    private var currentDelay: Long = 0L
    private var timeMillis: Long = 0L
    private var round: Long = 1L
    private var sprintStatus: Int = SprintStatus.STOP
    private val SPRINT_TAG = "SPRINT_TAG"
    private var roundType: RoundType = RoundType.START_ROUND
    private var isLastIteration: Boolean = false
    private var onRoundStart: ((timeMillis: Long, round: Long, roundType: RoundType) -> Unit)? =
        null
    private var iterationTime: Long? = null
    private var pauseAtMillis: Long? = null
    private var remainingDelay: Long? = null
    private var config: Config = Config()
    private var sprintStartTime : Long? = null
    private var sprintStartExecutionSpan : Long=0
    private var sprintLoopStartTime : Long?=null
    private var sprintLoopExecutionSpan : Long=0

    companion object {
        /**
         * Builder method to create and start a Sprint instance with defined parameters.
         *
         * @param sprintConfig The configuration object that specifies the interval, iterations, and countdown settings for the Sprint.
         * @param onRoundStart A callback function to execute at the start of each Sprint round.
         *                       The function is passed the current time in milliseconds, the round number, the round type (START_ROUND or END_ROUND).
         * @return A new Sprint instance.
         */
        fun getInstanceAndStart(
            sprintConfig: Config,
            onRoundStart: (timeMillis: Long, round: Long, roundType: RoundType) -> Unit
        ): Sprint {
            // Create a new instance of Sprint
            val sprint = Sprint()
            sprint.start(
                sprintConfig = sprintConfig,
                onRoundStart = onRoundStart
            )
            return sprint
        }

        /**
         * Creates a new instance of the Sprint class.
         *
         * @return A new Sprint instance.
         */
        fun getInstance(): Sprint {
            val sprint = Sprint()
            return sprint
        }
    }

    /**
     * Starts a Sprint, which is a repeating timer with customizable intervals and optional iterations.
     *
     * @param sprintConfig The configuration object that specifies the interval, iterations, and countdown settings for the Sprint.
     * @param onRoundStart A callback function to execute at the start of each Sprint round.
     *                      The function is passed the current time in milliseconds, the round number, the round type (START_ROUND or END_ROUND).
     * @return True if the Sprint was successfully started, false if the Sprint is already running.
     * @throws Exception if there is an error starting the Sprint, such as invalid intervalMillis or iterations values.
     */

    @Throws(Exception::class)
    fun start(
        sprintConfig: Config,
        onRoundStart: (timeMillis: Long, round: Long, roundType: RoundType) -> Unit//Need to change it with interface
    ): Boolean {
        // Check if the Sprint is already running
        if (sprintStatus == SprintStatus.STOP) {
            // Validate the intervalMillis value
            if (sprintConfig.intervalMillis <= 0L || sprintConfig.intervalMillis >= Long.MAX_VALUE) {
                Log.d(SPRINT_TAG, "Cannot start the Sprint : invalid IntervalMillis value!")
                throw Exception("Cannot start the Sprint : invalid IntervalMillis value!")
//                return
            }
            // Validate the iterations value
            if (sprintConfig.iterations != null) {
                if (sprintConfig.iterations!! <= 0 || sprintConfig.iterations!! >= Long.MAX_VALUE) {
                    Log.d(SPRINT_TAG, "Cannot start the Sprint : Invalid Iterations value!")
                    throw Exception("Cannot start the Sprint : Invalid Iterations value!")
//                return
                }
            } else {
                if (sprintConfig.countDown) {
                    throw Exception("Cannot Start the sprint : must declare iterations with count down!")
                }
            }

            // Set the Sprint status to RUNNING
            sprintStatus = SprintStatus.RUNNING
            // Set the Sprint configuration and callback function
            this.config = sprintConfig
            this.onRoundStart = onRoundStart
            // Start the Sprint execution loop
            startSprint(true)
            return true
        } else {
//            Log.d(SPRINT_TAG, "The Sprint is already running")
            logThread()
            return false
        }
    }


    /**
     * Starts the Sprint execution loop, managing the timing and rounds of the Sprint.
     *
     * This private method is responsible for executing the Sprint's iterations and intervals. It operates in the background using a CoroutineScope and a custom dispatcher obtained from 'getIterationExecutionService()'. The method initializes the timing and round variables and continuously runs while the Sprint status is not set to 'STOP'.
     *
     * @param startRound A flag indicating if it's the first round of the Sprint.
     */
    private fun startSprint(startRound: Boolean = false) {
        CoroutineScope(getIterationExecutionService().asCoroutineDispatcher()).launch {
            var isStartRound = startRound
            if (isStartRound) {
                if (this@Sprint.config.countDown) {
                    this@Sprint.timeMillis =
                        this@Sprint.config.intervalMillis.times(
                            this@Sprint.config.iterations?.minus(
                                1
                            ) ?: 0
                        )
                } else {
                    this@Sprint.timeMillis = 0L
                }
            }
            // Continue running the Sprint until the status is set to 'STOP'
            while (this@Sprint.sprintStatus != SprintStatus.STOP) {
                // Perform the logic for each round of the Sprint
                if (this@Sprint.sprintStatus == SprintStatus.RUNNING) {
                    if (pauseAtMillis == null) {
                        iterationTime = System.currentTimeMillis()
// Calculate the initial delay based on the interval and countdown settings
//                        val currentDelay = this@Sprint.config.intervalMillis
//                        this@Sprint.currentDelay = currentDelay
                        this@Sprint.sprintLoopStartTime?.let{sLT ->
                            this@Sprint.sprintLoopExecutionSpan = System.currentTimeMillis() -sLT
//                            this@Sprint.sprintLoopExecutionSpan?.let{sLE->
//                                sLE+=3
//
//                            }//add this to eliminate time after this statement to delay
//                            Log.d("check-loop-execution-time", "sprintLoopStartTime : ${sLT}")
//                            Log.d("check-loop-execution-time", "sprintLoopExecutionSpan : ${this@Sprint.sprintLoopExecutionSpan}")
                        }

                        // Calculate the initial delay based on the interval and countdown settings
                        val currentDelay = (this@Sprint.config.intervalMillis.minus(this@Sprint.sprintLoopExecutionSpan+2)).coerceAtLeast(1L)
                        this@Sprint.currentDelay = currentDelay
//                        this@Sprint.sprintLoopStartTime = null
//                        this@Sprint.sprintLoopExecutionSpan = null
                        // Delay the execution for the specified interval
                        delay(currentDelay)
                        this@Sprint.sprintLoopStartTime = System.currentTimeMillis()
                        this@Sprint.sprintLoopExecutionSpan = 0
                    }else{
                        pauseAtMillis = null
//                        this@Sprint.remainingDelay?.let { remainingDelay ->
//                            delay(remainingDelay)
//                        }
//                        this@Sprint.sprintLoopStartTime?.let{sLT ->
//                            this@Sprint.sprintLoopExecutionSpan = sLT-System.nanoTime()
//                        }
//
//                        this@Sprint.sprintLoopStartTime = null
//                        this@Sprint.sprintLoopExecutionSpan = null
                        this@Sprint.remainingDelay?.let { remainingDelay ->
                            delay(remainingDelay.coerceAtLeast(1L))
                        }
                        this@Sprint.sprintLoopStartTime = System.currentTimeMillis()
                        this@Sprint.sprintLoopExecutionSpan = 0

                    }
                        if (!this@Sprint.config.countDown) {
                            this@Sprint.timeMillis += this@Sprint.config.intervalMillis
                        }
                    // Check if it's the last iteration
                        this@Sprint.isLastIteration = ((this@Sprint.config.iterations != null) &&
                                ((this@Sprint.round + 1) > this@Sprint.config.iterations!!))

                    // Update the round type based on the last iteration flag
                        this@Sprint.roundType = if (this@Sprint.isLastIteration) {
                            this@Sprint.sprintStatus = SprintStatus.STOP
                            RoundType.LAST_ROUND
                        } else if (isStartRound) {
                            isStartRound = false
                            RoundType.START_ROUND
                        } else {
                            RoundType.INTERMEDIATE_ROUND
                        }


                        if (this@Sprint.config.dispatchOnMain) {
                            // Execute the callback function at the start of each round
                            withContext(Dispatchers.Main) {
                                this@Sprint.onRoundStart?.invoke(
                                    this@Sprint.timeMillis,
                                    this@Sprint.round,
                                    this@Sprint.roundType
                                )
                            }
                        } else {
                            withContext(getCallbackExecutionService().asCoroutineDispatcher()) {
                                this@Sprint.onRoundStart?.invoke(
                                    this@Sprint.timeMillis, this@Sprint.round,
                                    this@Sprint.roundType
                                )
                            }
                        }

                        if (this@Sprint.config.countDown) {
                            this@Sprint.timeMillis -= this@Sprint.config.intervalMillis
                        }
                        this@Sprint.round++
                        if (this@Sprint.config.iterations != null) {
                            if (this@Sprint.round > this@Sprint.config.iterations!!) {
                                autoStop()
                            }
                        }



                } else {
                    pause()
//                        Log.d(SPRINT_TAG, "The Sprint is Paused")
//                        logThread()
                }
            }

        }
    }

    /**
     * Updates the number of iterations for the current Sprint.
     *
     * This method allows updating the number of iterations for the current Sprint. If the new number of iterations
     * is null, it indicates that the Sprint should become indefinite. However, in countdown mode, setting
     * iterations to null is not allowed. The method first validates the new iteration value to ensure it is
     * greater than the current round and within acceptable limits.
     *
     * Key behaviors:
     * - If the provided 'iterations' is null, the method checks if the Sprint is in countdown mode and
     *   throws an exception if it is, since indefinite iterations cannot be set in this mode.
     * - If 'iterations' is not null, it checks if the value is greater than the current round.
     *   If it is valid, the configuration is updated; otherwise, an exception is thrown.
     * - If the new iterations value is the same as the current value, the method returns false.
     *
     * @param iterations The new number of iterations to set for the Sprint. If null, the Sprint will become indefinite unless in countdown mode.
     * @return True if the iterations were successfully updated, false if the update was not allowed due to
     *         Sprint configuration or if 'iterations' is not greater than the current round.
     * @throws Exception if there is an error updating the iterations, such as an invalid 'iterations' value
     *                   or attempting to update to an indefinite state during a countdown Sprint.
     */
    @Throws(Exception::class)
    fun updateIterations(iterations: Long? = null): Boolean {
        if (iterations != null) {
            if (iterations <= 0 || iterations >= Long.MAX_VALUE) {
                throw Exception("Cannot update the Iterations : Invalid Iteration value!")
            }
        }
        // Restrict setting iterations to null in countdown mode
        if (config.countDown && iterations == null) {
            throw Exception("Cannot make Sprint indefinite in countdown mode!")
        }

        /*if (iterations != this.config.iterations && config.countDown) {
            throw Exception(
                "Cannot update the Iterations on CountDown!"
            )
        } else*/ return if (iterations == config.iterations) {
            false
        } else {
            if (iterations != null) {
                if (iterations > round) {
                    config.iterations = iterations
                    true
                } else {
                    throw Exception(
                        "Cannot update the Iterations: New value must be greater that current round!"
                    )
                }
            } else {
                config.iterations = iterations
                true
            }
        }
    }


    /**
     * Updates the interval duration for the current Sprint.
     *
     * This method updates the interval between iterations for the current Sprint.
     * It first validates the new interval duration to ensure it's within acceptable limits.
     * If the new interval is valid and different from the current one, the Sprint is paused,
     * the interval is updated, and the Sprint is resumed with the new interval in effect.
     *
     * The method handles both regular and countdown modes, although countdown mode no longer prevents
     * interval updates. When the interval is updated during a countdown Sprint, the updated interval
     * will apply to the remaining rounds.
     *
     * Steps involved:
     * - The Sprint is paused to safely update the interval.
     * - The interval is updated with the new value.
     * - The Sprint is resumed, applying the new interval immediately by restarting the coroutine.
     *
     * @param intervalMillis The new interval duration in milliseconds to set for the Sprint. It must be greater than 0 and less than Long.MAX_VALUE.
     * @return True if the interval duration was successfully updated, false if the interval remained the same.
     * @throws Exception if the 'intervalMillis' value is invalid (i.e., less than or equal to 0 or greater than Long.MAX_VALUE).
     */
    @Throws(Exception::class)
    fun updateInterval(intervalMillis: Long): Boolean {
        if (intervalMillis <= 0L || intervalMillis >= Long.MAX_VALUE) {
//            Log.d(SPRINT_TAG, "Cannot update the Interval : invalid IntervalMillis value!")
            throw Exception("Cannot update the Interval : invalid IntervalMillis value!")
//            return
        }
       /* if (intervalMillis != this.config.intervalMillis && config.countDown) {
            throw Exception(
                "Cannot update the IntervalMillis on CountDown!"
            )
        } else */  return if (intervalMillis == this.config.intervalMillis) {
            false
        } else {
            // Pause the Sprint first to safely apply the new interval
            pause()

            // Update the interval with the new value
            this.config.intervalMillis = intervalMillis

            // Resume the Sprint with the new interval, restarting the coroutine
            resume()

            true
        }
    }

    /**
     * Updates the dispatch behavior for the current Sprint.
     *
     * @param dispatchOnMain If true, the 'onRoundStart' callback will be executed on the main thread. If false, it will be executed on the current thread.
     */
    fun updateDispatch(dispatchOnMain: Boolean): Boolean {
        return if (dispatchOnMain == this.config.dispatchOnMain) {
            false
        } else {
            this.config.dispatchOnMain = dispatchOnMain
            true
        }
    }

    /**
     * Pauses the current Sprint.
     *
     * This method is responsible for pausing the Sprint when it is running. It checks if the Sprint is currently running and, if so, changes the Sprint status to 'PAUSE'. It also shuts down the iteration execution service to stop the current iteration, captures the time at which the pause occurred, and calculates the remaining time in the current iteration. The remaining time is used to resume the Sprint from where it left off when 'resume' is called.
     *
     * @return True if the Sprint was successfully paused or already paused, false if the sprint is Stopped.
     * @see isSprintRunning
     * @see isSprintPaused
     * @see resume
     */
    fun pause(): Boolean {
//        Log.d(SPRINT_TAG, "pause ${Thread.currentThread().name} Thread!")


        return if (isSprintRunning()) {
            sprintStatus = SprintStatus.PAUSE
            iterationES?.shutdownNow() //we do shutdown thread so that while loop terminates.
            iterationTime?.let { iterationTime ->
                pauseAtMillis = System.currentTimeMillis().minus(iterationTime) // Get the iteration Elapse time on pause.
            }
            true
        } else isSprintPaused()
    }

    fun pauseAt(): Long? {
        return pauseAtMillis
    }

    /**
     * Resumes the current Sprint.
     *
     * This method is responsible for resuming a paused Sprint. It first checks if the Sprint is currently paused,
     * and if so, changes the Sprint status to 'RUNNING' and resumes the Sprint execution using the 'startSprint' method.
     * The Sprint will continue from where it was paused, ensuring a seamless progression.
     *
     * The method handles both regular and countdown modes:
     *
     * - In **regular mode**, the remaining delay is calculated based on how much time is left in the current interval.
     *   The Sprint's timing continues to increase with each iteration.
     *
     * - In **countdown mode**, the remaining delay is calculated based on the time left in the current countdown interval.
     *   Additionally, the countdown timer (`timeMillis`) is adjusted by subtracting the time that has already elapsed before the pause.
     *   This ensures the countdown resumes correctly from where it was paused.
     *
     * The method ensures that the Sprint resumes with the correct timing based on whether it is in regular or countdown mode.
     *
     * @return True if the Sprint was successfully resumed or already resumed, false if the Sprint is stopped.
     * @throws Exception if there is an error resuming the Sprint.
     */
    fun resume(): Boolean {
//        Log.d(SPRINT_TAG, "pause ${Thread.currentThread().name} Thread!")
        return if (isSprintPaused()) {
            sprintStatus = SprintStatus.RUNNING
            val remaining = if (this@Sprint.config.countDown) {
                // Countdown Mode: Adjust remaining time based on countdown logic
                this@Sprint.pauseAtMillis?.let { pauseAtMillis ->
                    // Remaining time for countdown: the time left in the current interval
                    this@Sprint.config.intervalMillis - pauseAtMillis
                } ?: 0L
            } else {
                // Regular Mode: Time left in the current interval
                this@Sprint.config.intervalMillis - (this@Sprint.pauseAtMillis ?: 0L)
            }

            // In countdown mode, adjust the countdown timer itself
            if (this@Sprint.config.countDown) {
                this@Sprint.timeMillis -= (this@Sprint.pauseAtMillis ?: 0L)
            }

            this@Sprint.remainingDelay = if(remaining<0){
                0
            }else{
                remaining
            }
            startSprint()
            true
        } else {
            isSprintRunning()
        }
    }

    /**
     * Stops the current Sprint.
     *
     * @return True if the Sprint was successfully stopped, false if it was already stopped or if an error occurred during the stop process.
     */
    fun stop(): Boolean {

        return if (isSprintStopped()) {
//            Log.d(SPRINT_TAG, "Cannot Stop the Sprint : ")
            false
        } else {
//            Log.d(SPRINT_TAG, "Stop ${Thread.currentThread().name} Thread!")
            sprintStatus = SprintStatus.STOP
            iterationES?.shutdownNow()
            stopCallbackES()
            resetSprint()
            true
        }
    }

    /**
     * Stops the current Sprint.
     *
     * @return True if the Sprint was successfully stopped, false if it was already stopped or if an error occurred during the stop process.
     */
    private fun autoStop() {
//            Log.d(SPRINT_TAG, "Stop ${Thread.currentThread().name} Thread!")

        sprintStatus = SprintStatus.STOP
        iterationES?.shutdownNow()
        stopCallbackES()
        resetSprint()

    }


    private fun resetSprint() {
        iterationES = null
        callBackES = null
        this.config = Config()
        currentDelay = 0L
        timeMillis = 0L
        round = 1L

        sprintStatus = SprintStatus.STOP
        isLastIteration = false
        onRoundStart = null
        iterationTime = null
        pauseAtMillis = null
        remainingDelay = null

        this@Sprint.sprintLoopStartTime = null
        this@Sprint.sprintLoopExecutionSpan = 0
    }

    private fun stopCallbackES(): Boolean {

        return if (callBackES?.isShutdown == true) {
//            Log.d(SPRINT_TAG, "Cannot Stop the Sprint : ")
            false
        } else {
            callBackES?.shutdownNow()
            true
        }
    }

    /**
     * Checks if the current Sprint is running.
     *
     * @return True if the Sprint is currently running, false otherwise.
     */
    fun isSprintRunning(): Boolean {
        return sprintStatus == SprintStatus.RUNNING
    }

    /**
     * Checks if the current Sprint is paused.
     *
     * @return True if the Sprint is currently paused, false otherwise.
     */
    fun isSprintPaused(): Boolean {
        return sprintStatus == SprintStatus.PAUSE
    }

    /**
     * Checks if the current Sprint is stopped.
     *
     * @return True if the Sprint is currently stopped, false otherwise.
     */
    fun isSprintStopped(): Boolean {
        return sprintStatus == SprintStatus.STOP
    }

    /**
     * Retrieves the current round number of the Sprint.
     *
     * @return The current round number if the Sprint is not stopped, or 0 if the Sprint has been stopped.
     */
    fun getCurrentRound(): Long {
        return if (sprintStatus != SprintStatus.STOP) {
            round
        } else {
            0L
        }
    }

    /**
     * Retrieves the single-thread ExecutorService used by the Sprint.
     * If the ExecutorService does not exist or is already shutdown, it creates a new one.
     *
     * @return The single-thread ExecutorService for the Sprint.
     */
    private fun getIterationExecutionService(): ExecutorService {
        if (iterationES == null || iterationES?.isShutdown == true) {
            iterationES = Executors.newSingleThreadExecutor()
        }
        return iterationES!!
    }

    private fun getCallbackExecutionService(): ExecutorService {
        if (callBackES == null || callBackES?.isShutdown == true) {
            callBackES = Executors.newSingleThreadExecutor()
        }
        return callBackES!!
    }

    private fun logThread() {
        Log.d(SPRINT_TAG, "I'm on ${Thread.currentThread().name} Thread!")
    }

    fun getSprintStatus(): Int {
        return sprintStatus
    }

    object SprintStatus {
        const val RUNNING = 0
        const val STOP = 1
        const val PAUSE = 2
    }

    final class Config(
        var intervalMillis: Long = 0L,
        var iterations: Long? = null,
        var countDown: Boolean = false,
        var dispatchOnMain: Boolean = false,
    )

    enum class RoundType(value: Int) {
        START_ROUND(0),
        INTERMEDIATE_ROUND(1),
        LAST_ROUND(2)
    }

    private interface RoundCallback{
        fun onStartRound()
        fun onIntermediateRound()
        fun onLastRound()
    }
}


