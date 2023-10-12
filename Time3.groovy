// Create a Date object representing the current time in the EST time zone
def estTimeZone = TimeZone.getTimeZone("America/New_York")
def currentTimeEST = new Date().with { time = time + TimeZone.getDefault().getOffset(time) - estTimeZone.getOffset(time) }

// Define shift start times (2 AM, 10 AM, 2 PM)
def shiftStartTimes = [2, 10, 14] // Hours in EST

// Initialize the shift name
def currentShift = "Unknown"

// Compare the current time with shift start times
if (currentTimeEST.hours >= shiftStartTimes[0] && currentTimeEST.hours < shiftStartTimes[1]) {
    currentShift = "1st Shift"
} else if (currentTimeEST.hours >= shiftStartTimes[1] && currentTimeEST.hours < shiftStartTimes[2]) {
    currentShift = "2nd Shift"
} else if (currentTimeEST.hours >= shiftStartTimes[2]) {
    currentShift = "3rd Shift"
}

// Print the current time in hours and minutes and the determined shift
println("Current Time (EST): ${currentTimeEST.hours}:${currentTimeEST.minutes}")
println("Current Shift: $currentShift")
