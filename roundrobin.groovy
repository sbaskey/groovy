import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.csv.CsvParser

// Read the JSON file containing last assigned persons
def jsonFile = new File('last_assigned_persons.json')
def lastAssignedPersons = [:]

if (jsonFile.exists()) {
    lastAssignedPersons = new JsonSlurper().parse(jsonFile.text)
}

// Read the CSV file containing appci vs. resources mapping
def csvFile = new File('appci_mapping.csv')
def csvData = []

csvFile.withReader { reader ->
    csvData = new CsvParser().parse(reader)
}

// Simulated data: appci name and open ticket
def appci = 'AppCI1'
def openTicket = 'Ticket1'

// Find the next support person based on round-robin assignment
def supportPerson = getNextSupportPerson(appci, lastAssignedPersons, csvData)

if (supportPerson) {
    // Assign the open ticket to the support person
    assignTicketToSupportPerson(openTicket, supportPerson)
    
    // Update the last assigned person in the JSON file
    lastAssignedPersons[appci] = supportPerson
    jsonFile.text = JsonOutput.toJson(lastAssignedPersons)
    println("Assigned ticket $openTicket for AppCI $appci to $supportPerson")
} else {
    println("No support person found for AppCI $appci")
}

// Function to get the next support person based on round-robin assignment
def getNextSupportPerson(appci, lastAssignedPersons, csvData) {
    def lastAssignedPerson = lastAssignedPersons[appci]
    def supportPersons = csvData.findAll { it.AppCI == appci }.collect { it.Resource }

    if (supportPersons.isEmpty()) {
        return null
    }

    if (lastAssignedPerson) {
        def lastIndex = supportPersons.indexOf(lastAssignedPerson)
        return supportPersons[(lastIndex + 1) % supportPersons.size()]
    } else {
        return supportPersons[0]
    }
}

// Function to simulate ticket assignment to a support person
def assignTicketToSupportPerson(openTicket, supportPerson) {
    println("Assigned ticket $openTicket to $supportPerson")
    // Implement your logic to assign the ticket here
}
