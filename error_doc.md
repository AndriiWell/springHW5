Error like

## [ERROR] Failed to execute goal org.liquibase:liquibase-maven-plugin:4.29.2:update (default-cli) on project homework: The database URL has not been specified either as a parameter or in a properties file. -> [Help 1]

requires url in command executed, so instead of
`mvn ....`
do
`mvn ... -Dliquibase.propertyFile=src/main/resources/liquibase.properties`
for example
`mvn liquibase:update -Dliquibase.propertyFile=src/main/resources/liquibase.properties`

##