# Logging Challenge

## Overview
Our Android SDK allows users to capture logs in a session. To ensure the session payload does not get too large, we want to limit the number of logs that are captured during a session. To achieve this we want to implement one or more limits on log capture rate and count.

## Tasks
1. Describe the rate and count limiters that you would want to implement to limit log capture, explaining how the limiting would be done and stating the benefit of each limiter.  
2. Create a sample Android app with a “Submit log button” and a text field to enter a log message in. Implement one or more of the log limiters you proposed to determine if the message would be captured or not. Display on the screen if it would be captured or not. For simplicity you can:
    a. Adjust any time-based parameters in the limiters to suit this test app which has the logs submitted by a person rather than having them come from code.
    b. Assume that there is no meta data for the logs, which would normally accompany a log message.
    
# Resolution

## About the resolution
The main screen contains a text input where we can write a message to log. Once we press the submit log button, we see if we could log it or not in the status label.
I used Hilt for dependency injection, unit tested most of the logic and used a layered architecture, with the fragments and viewmodels on a presentation layer, and the use cases, repositories and limiters on an infrastructure layer. 

## Limiters
I created 4 simple limiters: 

* **Count limiter**: allow logging until certain count is surpassed
* **Message limiter**: allow logging as long as the message isn't empty, and it doesn't contain a pre defined word. This could be useful to filter some logs that are not important for a specific session.
* **Time limiter**: allow a single log every defined interval of milliseconds. For instance, if the interval is 500ms, we have to wait 500ms after a log, to log again.
* **Repeated Message Rate limiter**: allows logging a message if it has not been logged before in a defined span of time. For instance, if the time window is 500ms, and we log "message", we have to wait 500ms to log the same message again.

## Resolution structure
![Project structure](https://github.com/priettt/LoggingChallenge/blob/master/Logging.jpg)
