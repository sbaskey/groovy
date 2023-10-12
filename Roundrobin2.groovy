import com.opencsv.CSVReader
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Function to read JSON data from a file
def readJsonFile(filePath) {
    def file = new File(filePath)
    if (file.exists()) {
        return new JsonSlurper().parseText(file.text)
    } else {
        return [:] // Return an empty map if the file doesn't exist
    }
}

// Function to save JSON data to a file
def saveJsonToFile(data, filePath) {
    new File(filePath).text = JsonOutput.toJson(data)
}

// Define your CSV file path
def csvFileName = 'appci_mapping.csv'

// Define your JSON file path
def jsonFileName = 'shift_assignments.json'

// Create a CSVParser with the desired configuration (e.g., separator, quote character)
def parser = new CSVParserBuilder()
    .withSeparator(',')
    .withQuoteChar('"')
    .build()

// Create a CSVReader using the parser
def reader = new CSVReaderBuilder(new FileReader(csvFileName))
    .withCSVParser(parser)
    .build()

// Read and process the CSV data into a list of maps
def csvData = []
reader.readAll().each { row ->
    csvData << [AppCI: row[0], Resource: row[1], Shift: row[2]]
}

// Read the last assigned persons from the JSON file
def lastAssignedPersons = readJsonFile(jsonFileName)

// Simulated data: appci name and open tickets
def appci = 'AppCI1'
def openTickets = ['Ticket1', 'Ticket2', 'Ticket3']

// Find the support personnel for the current shift for the given AppCI from CSV data
def currentShift = '1st Shift' // Example shift

// Function to get the list of support personnel for a shift from the CSV data
def supportPeopleForShift = csvData.findAll { it.AppCI == appci && it.Shift == currentShift }.collect { it.Resource }

if (supportPeopleForShift) {
    openTickets.each { ticket ->
        def supportPerson = supportPeopleForShift.remove(0)
        if (!supportPerson) {
            supportPeopleForShift.addAll(supportPeopleForShift)
            supportPerson = supportPeopleForShift.remove(0)
        }

        println("Assigned ticket $ticket for AppCI $appci to $supportPerson in $currentShift")
        // Implement your logic to assign the ticket here

        // Update the last assigned person in the JSON data
        lastAssignedPersons[appci][currentShift] = supportPerson
        saveJsonToFile(lastAssignedPersons, jsonFileName)
        println("Assigned tickets for AppCI $appci to $supportPerson in $currentShift")
    }
} else {
    println("No support personnel found for AppCI $appci in $currentShift")
}
