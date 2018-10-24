# Atlassian-Interview

Clone the code in local machine

change `JIRA_BASE_URL` and `QUEUE_URL` in `application.properties` file with actual jira and queue urls

Use `mvn clean package` on the `pom.xml` file

use `java -jar <jar_name>` to start the application


## Testing
Hit the below endpoint to actually do the query<br>`http://localhost:8080/api/issue/sum?query=<seach_query>&name=<descriptive_name>` 

For understanding/debugging purpose, I am dumping the actual response in console just after it has pushed to the queue.

We can also use this URL `http://localhost:9324/queue/cst-test-queue?Action=ReceiveMessage` to view messages
