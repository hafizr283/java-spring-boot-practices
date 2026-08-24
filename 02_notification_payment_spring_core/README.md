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
