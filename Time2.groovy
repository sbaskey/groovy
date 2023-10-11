// Create a Date object representing the current date in the system's default time zone
def currentTimeDefault = new Date()

// Adjust the current time to the EST time zone
def estTimeZone = TimeZone.getTimeZone("America/New_York")
def currentTimeEST = new Date(currentTimeDefault.time + estTimeZone.getOffset(currentTimeDefault.time))

// Define the shift start time (2 AM EST)
def shiftStartTime = new Date(currentTimeEST)
shiftStartTime.hours = 2
shiftStartTime.minutes = 0

// Calculate the time difference
def timeDiff = currentTimeEST - shiftStartTime

// Calculate the number of shifts passed
def shiftsPassed = timeDiff / (8 * 60 * 60 * 1000) // 8 hours in milliseconds

// Shift names
def shiftNames = ['1st Shift', '2nd Shift', '3rd Shift', '4th Shift', '5th Shift']

// Determine the current shift
def currentShiftIndex = (shiftsPassed as int) % shiftNames.size()

// Get the current time in hours and minutes
def currentHour = currentTimeEST.hours
def currentMinute = currentTimeEST.minutes

// Get the current shift name
def currentShift = shiftNames[currentShiftIndex]

println("Current Time (EST): $currentHour:$currentMinute")
println("Current Shift: $currentShift")
