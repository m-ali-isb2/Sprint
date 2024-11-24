package com.example.pulsegenerator

import com.formula.sprint.Sprint

class TestSprint {

    private var sprint : Sprint = Sprint.getInstance()


    fun startSprint(){

         // Define a callback function to be executed at the start of each round
/*         val onRoundStart:  (timeMillis: Long, round: Long, lastRound: Boolean) -> Unit= { timeMillis, round, lastRound ->
                 println("Round $round started at $timeMillis ms.")
             }

         // Start an indefinite Sprint with a 1000ms interval and the custom callback
         sprint.start(1000, onRoundStart = onRoundStart)

         // Update the number of iterations to 10 while the Sprint is running
         sprint.updateIterations(10)

         // Update the interval duration to 500ms during runtime
         sprint.updateInterval(500)

         // Switch the Sprint to run on the main thread
         sprint.updateDispatch(true)

         // Start a countdown Sprint with 3 iterations and a 200ms interval
         sprint.start(200, iterations = 3, countDown = true, onRoundStart = onRoundStart)*/

    }

    fun updatingIterations(){
        // Update the number of iterations to 10
        //        sprint.updateIterations(10)

        // Attempt to update iterations during a countdown Sprint (will throw an exception)
        try {
            sprint.updateIterations(10)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

    }


    fun updatingIntervalDuration(){
        // Update the interval duration to 300ms
//        sprint.updateInterval(300)

// Attempt to update interval during a countdown Sprint (will throw an exception)
        try {
            sprint.updateInterval(500)
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

    }

    fun dispatchOnMain(){
        // Update dispatch behavior to execute on the main thread
        sprint.updateDispatch(true)
    }

    fun dispatchOnCurrentThread(){
        // Update dispatch behavior to execute on the main thread
        sprint.updateDispatch(false)
    }

    fun pauseSprint(){
// Pause the Sprint
        if (sprint.pause()) {
            println("Sprint paused.")
        } else {
            println("Sprint is already paused.")
        }
    }

    fun resumeSprint(){
// Resume the Sprint
        if (sprint.resume()) {
            println("Sprint resumed.")
        } else {
            println("Sprint is already running.")
        }


    }

    fun stopSprint(){
        // Stop the Sprint
        if (sprint.stop()) {
            println("Sprint stopped.")
        } else {
            println("Sprint is already stopped or an error occurred.")
        }
    }

    fun checkSprintStatus(){
        // Check if the Sprint is running
        if (sprint.isSprintRunning()) {
            println("Sprint is currently running.")
        } else {
            println("Sprint is not running.")
        }

    }

    fun getCurrentRound(){
        // Get the current round number
        val currentRound = sprint.getCurrentRound()
        println("Current round: $currentRound")

    }

    fun checkSprintSopped() {
        // Check if the Sprint is paused
        if (sprint.isSprintPaused()) {
            println("Sprint is currently paused.")
        } else {
            println("Sprint is not paused.")
        }
    }

    fun checkSprintStopped() {
        // Check if the Sprint is stopped
        if (sprint.isSprintStopped()) {
            println("Sprint is currently stopped.")
        } else {
            println("Sprint is not stopped.")
        }
    }

}