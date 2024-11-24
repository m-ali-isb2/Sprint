package com.example.pulsegenerator.sprintUI.Model

enum class LogType (var value:Int){
    START(1),
    ROUND(2),
    LAST_ROUND(3),
    PAUSE(4),
    RESUME(5),
    STOP(6),
    UPDATE(7),
    EXCEPTION(8)
}