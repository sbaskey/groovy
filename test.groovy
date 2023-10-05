import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Function to assign tickets in round-robin fashion and send emails
def assignTicketsAndSendEmails(csvFilePath) {
    // Function to read the last assigned persons from a JSON file
    def readLastAssignedPersons() {
        def fileName = "last_assigned.json"
        def file = new File(fileName)
        
        if (file.exists()) {
            def json = new JsonSlurper().parse(file)
            return json
        }
        
        return [:]
    }

    // Function to write the last assigned person to the JSON file
    def writeLastAssignedPerson(appCI, personName) {
        def fileName = "last_assigned.json"
        def file = new File(fileName)
        def json = readLastAssignedPersons()
        
        json[appCI] = personName
        
        file.text = JsonOutput.toJson(json)
    }
    
    // Load CSV data into a list of maps
    def csvData = []
    def csvFile = new File(csvFilePath)
    csvFile.eachLine { line ->
        def (appCI, personName, email) = line.split(',')
        csvData << [appCI: appCI, personName: personName, email: email]
    }
    
    // Define hardcoded tickets as a HashMap
    def ticketsToAssign = [
        "Ticket1": ["appCI": "AppCI-1"],
        "Ticket2": ["appCI": "AppCI-2"]
    ]
    
    ticketsToAssign.each { ticketName, ticketData ->
        def appCI = ticketData.appCI
        def peopleForAppCI = csvData.findAll { it.appCI == appCI }
        
        if (peopleForAppCI) {
            // Get the last assigned person for this app CI
            def lastAssignedPersons = readLastAssignedPersons()
            def lastPersonName = lastAssignedPersons[appCI]
            
            // Find the next person in the list in a round-robin manner
            def nextPersonIndex = (lastPersonName == null) ? 0 : (peopleForAppCI.findIndex { it.personName == lastPersonName } + 1) % peopleForAppCI.size()
            def nextPerson = peopleForAppCI[nextPersonIndex]
            
            // Assign the ticket to the next person
            println("Assigning $ticketName to ${nextPerson.personName} for App CI: $appCI")
            // sendEmail(nextPerson.email, ticketName) // Send email to the assigned person
            
            // Update the last assigned person for this app CI
            writeLastAssignedPerson(appCI, nextPerson.personName)
        } else {
            println("No people found for App CI: $appCI")
        }
    }
}

// Example usage:
// assignTicketsAndSendEmails('your_csv_file.csv')
