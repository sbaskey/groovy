// Sample data for demonstration
def supportPeople = [
    [Resource: "Person1", Email: "person1@example.com"],
    [Resource: "Person2", Email: "person2@example.com"],
    [Resource: "Person3", Email: "person3@example.com"],
]

def lastAssignedPerson = "Person1" // Assuming "Person1" was the last assigned person

// Find the index of the last assigned person
def lastIndex = supportPeople.findIndex { it.Resource == lastAssignedPerson }

if (lastIndex != -1) {
    // Calculate the index of the next person in a round-robin fashion
    def nextIndex = (lastIndex + 1) % supportPeople.size

    // Get the next assigned person and their email
    def nextAssignedPerson = supportPeople[nextIndex]
    def nextPersonResource = nextAssignedPerson.Resource
    def nextPersonEmail = nextAssignedPerson.Email

    // Print the next assigned person and their email
    println("Next Assigned Person: $nextPersonResource, Email: $nextPersonEmail")
} else {
    println("Last assigned person not found in the list.")
}
