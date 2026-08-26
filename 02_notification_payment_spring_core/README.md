# Notification and Payment with Spring Core

A small framework-only Spring application demonstrating IoC wiring for an order workflow. It is not Spring Boot; the context starts directly with `AnnotationConfigApplicationContext`.

## What it demonstrates

- Component scanning from `com.example` via `AppConfig`.
- Constructor injection in `OrderService`.
- Multiple `PaymentService` implementations (`bkashPayment` and `cardPayment`).
- `@Qualifier("bkashPayment")` to select the payment implementation.
- Email notifications and an in-memory `OrderRepository`.
- `@PostConstruct` and `@PreDestroy` lifecycle callbacks.

## Requirements

- JDK 21 or newer
- Maven 3.9+

The Maven compiler uses `release` 21 so newer JDKs can emit Java 21-compatible bytecode for Spring 6.1.

## Run

From the module directory:

```powershell
cd notification
mvn clean compile
```

Run `com.example.Main` from your IDE using the Maven project classpath. The sample performs two checkouts, prints payment/order/notification messages, and closes the context.

## Project layout

```text
notification/src/main/java/com/example/
├── Main.java
├── config/AppConfig.java
├── model/Order.java
├── repository/OrderRepository.java
└── service/
    ├── OrderService.java
    ├── PaymentService.java
    ├── BkashPayment.java
    ├── CardPayment.java
    ├── Notification.java
    ├── Email.java
    └── SendSms.java
```

## Troubleshooting

### `Unsupported class file major version 69`

Java 25 emits class-file major version 69, while the Spring/ASM version used here reads up to Java 21 (major version 65). `<maven.compiler.release>21</maven.compiler.release>` makes JDK 25 emit Java 21-compatible bytecode.

### `NoUniqueBeanDefinitionException`

Two payment beans implement `PaymentService`, so Spring cannot choose by type alone. `OrderService` uses `@Qualifier("bkashPayment")`; change it to `cardPayment` to select the other implementation.

---

**Q1: What caused the `Unsupported class file major version 69` error, and how did setting `<maven.compiler.release>21</maven.compiler.release>` fix it?**

**Answer:**

* **Java Bytecode Versioning:** When Java source code (`.java`) is compiled into bytecode (`.class`), each major JDK release stamps the file with a specific **Major Version Number**:
* Java 17 $\rightarrow$ Major Version **61**
* Java 21 $\rightarrow$ Major Version **65**
* Java 25 $\rightarrow$ Major Version **69**


* **Spring's ASM Reader Mechanism:** During startup, Spring runs `@ComponentScan` by inspecting `.class` files directly using an internal bytecode manipulation library called **ASM (ClassReader)** to detect annotations like `@Component`, `@Service`, and `@Repository`.
* **The Incompatibility:** The Spring framework version in use contained an ASM parser designed to read bytecode up to version 65 (Java 21). Because the installed JDK was Java 25, Maven compiled the classes into version 69. Upon reading `AppConfig.class`, the ASM parser encountered an unknown bytecode version and threw `IllegalArgumentException: Unsupported class file major version 69`.
* **The Fix:** Configuring `<maven.compiler.release>21</maven.compiler.release>` instructed the JDK 25 compiler to produce Java 21-compliant bytecode (Major Version 65). This allowed Spring's ASM parser to read and scan the compiled classes without requiring a JDK downgrade.

---

**Q2: Why did Spring throw a `NoUniqueBeanDefinitionException`, and how did `@Qualifier("bkashPayment")` resolve it?**

**Answer:**

* **Autowiring by Type:** By default, Spring performs dependency injection **by Type**. When instantiating `OrderService`, Spring inspected the constructor parameter expecting a bean of type `PaymentService`:
```java
public OrderService(OrderRepository orderRepository,
                    PaymentService paymentService,
                    NotificationService notificationService)

```


* **Bean Ambiguity Conflict:** The IoC container discovered two distinct beans implementing the `PaymentService` interface:
1. `BkashPaymentService` (`@Component("bkashPayment")`)
2. `CardPaymentService` (`@Component("cardPayment")`)


Because both candidates were equally valid and neither was designated as the default, Spring could not determine which instance to inject, resulting in `NoUniqueBeanDefinitionException: expected single matching bean but found 2: bkashPayment,cardPayment`.
* **The Fix with `@Qualifier`:** Adding `@Qualifier("bkashPayment")` explicitly instructs Spring to switch from pure type matching to **by-name / by-qualifier** resolution:
```java
@Autowired
public OrderService(OrderRepository orderRepository,
                    @Qualifier("bkashPayment") PaymentService paymentService,
                    NotificationService notificationService)

```


Spring uses the qualifier string to select `BkashPaymentService` and injects it into `OrderService`.
* **Comparison with `@Primary`:** `NotificationService` did not encounter this exception because `EmailNotificationService` was annotated with `@Primary`, designating it as the automatic fallback implementation whenever multiple bean candidates of that type exist.



# Spring Order Processing Engine: 20 Task Solutions

This project is a console-based Spring Framework application. The following sections briefly describe each task, how it was completed, and what was observed after running it.

## 1. Setter Injection vs Constructor Injection

**Task:** Remove constructor injection from `OrderService` and inject its dependencies through setter methods annotated with `@Autowired`.

**How it was done:** Setter methods were created for `OrderRepository`, `PaymentService`, and `Notification`. Spring called those methods and supplied the required beans.

**Result:** The application produced the same output as constructor injection. This showed that Spring can inject dependencies through setters after constructing the object.

## 2. Testing Field Injection Problems

**Task:** Use `@Autowired` directly on fields and create `OrderService` manually with `new OrderService()`.

**How it was done:** Dependencies were placed on fields, and the service was tested both as a Spring bean and as a manually created Java object.

**Result:** The Spring-managed object worked, but the manually created object did not receive injected dependencies and could cause a `NullPointerException`. This demonstrated why constructor injection is generally safer and easier to test.

## 3. `@Qualifier` vs `@Primary`

**Task:** Keep one notification implementation as `@Primary` and use `@Qualifier` to request another implementation.

**How it was done:** A notification implementation was marked as the primary bean, while a specific bean name was supplied with `@Qualifier` at the injection point.

**Result:** The qualified bean was selected instead of the primary bean. This showed that an explicit `@Qualifier` has priority over `@Primary`.

## 4. Creating `NoUniqueBeanDefinitionException`

**Task:** Remove both `@Primary` and `@Qualifier` while injecting one `Notification` bean.

**How it was done:** Both `Email` and `SendSms` remained Spring components implementing the same `Notification` interface, but no bean-selection rule was provided.

**Result:** Spring could not choose one implementation and reported `NoUniqueBeanDefinitionException`. Adding `@Primary`, `@Qualifier`, or injecting a list resolves this ambiguity.

## 5. Fixing a Specific Payment Method

**Task:** Inject one `PaymentService` and select the card payment implementation using `@Qualifier`.

**How it was done:** The card implementation was registered as a named Spring component and selected at the injection point with its bean name.

**Result:** Checkout used card payment instead of the default payment implementation, confirming that a qualifier can fix a specific strategy.

## 6. Sending Multiple Notifications with List Injection

**Task:** Inject all notification services instead of selecting only one.

**How it was done:** `OrderService` uses:

```java
@Autowired
private List<Notification> notify;
```

After successful payment, it loops through the list and calls every notification implementation.

**Result:** Both `Email` and `SendSms` were discovered by Spring and executed for the same successful order.

## 7. Adding Nagad Payment with the Open-Closed Principle

**Task:** Add a new Nagad payment method without changing the main checkout logic.

**How it was done:** `Nagad` implements `PaymentService` and is registered with:

```java
@Component("Nagad")
```

Spring automatically includes it in `Map<String, PaymentService>`.

**Result:** Nagad became an available payment option without adding a new conditional branch to `OrderService`.

## 8. Showing an Error for an Invalid Payment Method

**Task:** Print a clear message and all available payment methods when the user enters an unknown method.

**How it was done:** `OrderService.checkout()` checks the result of `lookup.get(paymentMethod)`. When it is `null`, the method prints an error and `lookup.keySet()`.

**Result:** Invalid input no longer causes an unclear failure. The console displays `Unknown method` and the valid payment bean names.

## 9. Case-Insensitive Payment Selection

**Task:** Accept different uppercase and lowercase forms of a payment method.

**How it was done:** A case-insensitive lookup map was created:

```java
lookup = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
lookup.putAll(payment);
```

**Result:** Inputs such as `bkash`, `BKASH`, and `Bkash` select the same payment service.

## 10. Stopping Processing After Payment Failure

**Task:** Reject a Bkash payment above a configured amount and stop the order from being saved.

**How it was done:** `BkashPayment.processPayment()` checks the amount and returns `false` when the limit is exceeded. `OrderService` saves and notifies only inside the successful-payment branch.

**Result:** When Bkash payment fails, the repository is not updated and no Email or SMS notification is sent.

## 11. Verifying Singleton Scope

**Task:** Request `OrderService` twice from the same Spring context and compare the references.

**How it was done:** Two variables received `context.getBean(OrderService.class)`, and their references were compared with `==`.

**Result:** The comparison returned `true`, showing that Spring beans use singleton scope by default.

## 12. Constructor and `@PostConstruct` Execution Order

**Task:** Print messages from an `OrderService` constructor and its `@PostConstruct` method.

**How it was done:** Separate console messages were placed in the constructor and initialization callback.

**Result:** The constructor ran first. Spring then injected the dependencies and called `@PostConstruct`, confirming the bean initialization order.

## 13. Context Shutdown and `@PreDestroy`

**Task:** Observe what happens to cleanup methods when the Spring context is closed or left open.

**How it was done:** `context.close()` was tested in `Main`. The application was also tested without explicitly closing the context.

**Result:** Calling `context.close()` caused Spring to invoke the registered `@PreDestroy` methods. Without a proper shutdown, cleanup callbacks were not guaranteed to run.

## 14. Tracking Multiple Bean Lifecycles

**Task:** Add lifecycle callbacks to `OrderRepository` and the Email notification component.

**How it was done:** Both classes contain methods annotated with `@PostConstruct` and `@PreDestroy` that print lifecycle messages.

**Result:** Startup logs showed bean initialization, and closing the context showed the destruction of `OrderService`, `OrderRepository`, and `Email`.

## 15. Checking Dependencies During Initialization

**Task:** Check whether the payment-service map is empty inside `OrderService.init()`.

**How it was done:** The `@PostConstruct` method checks the injected map, prints a message when it is empty, and prints its size.

**Result:** The payment implementations were already injected before `init()` ran. This verified that dependencies are ready before checkout begins.

## 16. Retrieving Order History

**Task:** Return all stored orders from the repository and print their summaries from the service.

**How it was done:** `OrderRepository.getOrders()` returns the in-memory `List<Order>`. `OrderService.orderHistory()` loops through that list and prints the order ID, customer name, item, and amount.

**Result:** All successfully saved orders could be viewed from the console while the application context remained active.

## 17. Preventing Duplicate Order IDs

**Task:** Do not save a new order when its ID already exists.

**How it was done:** `OrderRepository.save()` loops through the existing list and compares each stored order ID with the new ID.

**Result:** A unique ID is saved successfully. A repeated ID prints `Order Id exists already. Try again` and is not added to the list.

## 18. Testing a Component-Scan Package Mismatch

**Task:** Change component scanning to only `com.example.service` and observe the result.

**How it was done:** The restricted scan was tested and then the configuration was restored to:

```java
@ComponentScan(basePackages = "com.example")
```

**Result:** Scanning only the service package excluded `OrderRepository`, causing a missing-bean error. Scanning `com.example` discovers the service, repository, configuration, payment, and notification components.

## 19. Adding a Notification Message Formatter

**Task:** Create a formatter component and inject it into the Email notification service.

**How it was done:** `MassegeFormatter` was marked with `@Component`. `Email` receives it through constructor injection and calls `getFormatedMessage(item, amount)` from `sendFormatterd()`.

**Result:** Email can create an order-specific formatted message using a separate Spring-managed helper component. This also demonstrates chained dependency injection.

> The current source uses the names `MassegeFormatter`, `getFormatedMessage()`, and `sendFormatterd()`. The requested spellings would be `MessageFormatter`, `format()`, and `sendFormatted()`.

## 20. Taking Real-Time Console Input

**Task:** Use `Scanner` in `Main.java` to read order details and the payment method, then call `orderService.checkout()`.

**How it was done:** `Main` reads the order ID, customer name, item, amount, and payment method. After `nextDouble()`, it calls an extra `nextLine()` to consume the remaining newline before reading the payment method.

```java
double amount = s.nextDouble();
s.nextLine();
String paymentMethod = s.nextLine();

orderService.checkout(
        orderid,
        cus_name,
        item,
        amount,
        paymentMethod);
```

**Result:** The user can create an order interactively from the console. The entered payment name selects the matching Spring payment service, and a successful payment saves the order and triggers notifications.

## Running the Project

Compile the application from the `notification` directory:

```bash
mvn clean compile
```

Then run `com.example.Main` from the IDE. Valid payment bean names in the current source are `bkash`, `Card`, and `Nagad`; the lookup is case-insensitive.
