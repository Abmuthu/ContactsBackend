# ContactsBackend
Setup:
   Step 1: Import the project in Intellij
   Step 2: Run elasticsearch process in the background (./bin/elasticsearch)
   Step 3: Run the application in Intellij
   Step 4: Use Postman to issue GET, POST, PUT, DELETE requests
   Step 5: Run unit tests at ContactsBackendApplicationTests
   
Notes:
  1) Transport client talks to elasticsearch at port 9300. To configure change the port number in the ContactsRepository.
  2) Queries can be made directly via HTTP requests to elasticsearch at port 9200
