# ContactsBackend

Setup: <br />
   1) Import the project in Intellij <br />
   2) Run elasticsearch process in the background (./bin/elasticsearch)<br />
   3) Run the application in Intellij<br />
   4) Use Postman to issue GET, POST, PUT, DELETE requests<br />
   5) Run unit tests at ContactsBackendApplicationTests<br />
   <br />
Notes:<br />
  1. Transport client talks to elasticsearch at port 9300. To configure change the port number in the ContactsRepository.<br />
  2. Queries can be made directly via HTTP requests to elasticsearch at port 9200<br />
