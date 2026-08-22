# Calculator

This is a simple calculator project built with Spring Boot.

## Run the Application

From the `demo` directory, run:

```powershell
.\mvnw.cmd spring-boot:run
```

## Sample JSON Request

```json
{
  "num1": 50,
  "num2": 10,
  "op": "/"
}
```

## Sample Response

```json
{
  "num1": 50.0,
  "num2": 10.0,
  "op": "/",
  "result": 5.0
}
```

## Problems and Working Solutions

### 1. Sending JSON with `curl.exe` in PowerShell

The following command did not work because PowerShell passed the JSON quotation marks incorrectly, causing an `Unexpected character ('n')` error:

```powershell
curl.exe -X POST http://localhost:8081/api/calculator/calculate `
  -H "Content-Type: application/json" `
  -d '{"num1": 50, "num2": 10, "op": "/"}'
```

Solution: use `Invoke-RestMethod` and let PowerShell convert the request body to JSON.

```powershell
$body = @{
    num1 = 50
    num2 = 10
    op   = "/"
} | ConvertTo-Json -Compress

Invoke-RestMethod -Uri "http://localhost:8081/api/calculator/calculate" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

### 2. Default Port Was Already in Use

The default port, `8080`, was occupied. The application port was changed to `8081` in `src/main/resources/application.properties`:

```properties
server.port=8081
```

### 3. Direct Compilation with `javac` Failed

Running the Java file directly with the VS Code **Run Java** option caused this error:

```text
package org.springframework.boot does not exist
```

Solution: run the Spring Boot project with the Maven wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

### 4. Endpoint Returned 404 (Component Scan Issue)

Problem: The server was running, but requests to `/api/calculator/calculate` returned `404 Not Found`.

Causes:

- `DemoApplication.java` was in the `com.example.demo` package, while the controller and service were in separate packages such as `com.example.controller`. Spring Boot did not automatically scan packages outside the main application's package hierarchy.
- An older process was still running in the background, so the new code was not loaded.

Solutions:

Stop the old background process in PowerShell:

```powershell
Stop-Process -Id <PID> -Force
```

Set the package-scanning root in `DemoApplication.java`:

```java
// Scan all base packages under com.example
@SpringBootApplication(scanBasePackages = "com.example")
```
