# RestAssured Employee Management API Framework

This project contains a RestAssured automation framework for the Employee Management System API described in the provided PRD.

## Features
- Token-based authentication tests
- Employee CRUD API coverage
- TestNG suite and groups
- JSON schema validation
- Extent and Allure integrations
- Centralized configuration and payload management

## Project structure
- `src/main/java/com/hrms/api/base` - framework base classes
- `src/main/java/com/hrms/api/utils` - helpers, payload manager, validators
- `src/main/java/com/hrms/api/constants` - endpoint constants
- `src/test/java/com/hrms/api/tests` - API test cases
- `src/test/resources` - configuration and JSON schema files

## Run tests
- `mvn clean test`
- Use `-Dtestng.groups=smoke` to run smoke tests

## Notes
- Base URL is configured in `src/test/resources/config.properties`
- The framework is designed for the QA environment from the PRD
