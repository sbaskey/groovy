// Function to calculate the shift name based on current time
def getShiftName(currentTime) {
    // Define the shift start time (2 AM EST)
    def shiftStartTime = new Date()
    shiftStartTime.setHours(2)
    shiftStartTime.setMinutes(0)
    shiftStartTime.setSeconds(0)
    
    // Calculate the time difference in milliseconds
    def timeDiffMillis = currentTime.time - shiftStartTime.time
    
    // Calculate the number of shifts passed
    def shiftsPassed = timeDiffMillis / (8 * 60 * 60 * 1000)
    
    // Shift names
    def shiftNames = ['1st Shift', '2nd Shift', '3rd Shift', '4th Shift', '5th Shift']
    
    // Determine the current shift
    def currentShiftIndex = (shiftsPassed as int) % shiftNames.size()
    
    return shiftNames[currentShiftIndex]
}

// Get the current time in EST (Eastern Standard Time)
def estTimeZone = TimeZone.getTimeZone("America/New_York")
def currentTimeEST = new Date().with { time = time + TimeZone.getDefault().getOffset(time) - estTimeZone.getOffset(time) }

// Determine the current shift name
def currentShift = getShiftName(currentTimeEST)

println("Current shift: $currentShift")
