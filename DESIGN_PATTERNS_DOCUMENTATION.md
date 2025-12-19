# Design Patterns Documentation / توثيق أنماط التصميم
## Cinema Ticket Booking System - 8 Design Patterns Implementation

---

## 1. SINGLETON PATTERN / نمط الوحيد
**Location:** All Spring Services (`@Service` annotation)

**English:**
The Singleton pattern ensures that only one instance of a class exists throughout the application lifecycle. In Spring Boot, this is achieved through the `@Service` annotation, which registers services as singleton beans in the Spring IoC container.

**Implementation:**
- `MovieService.java` - Manages movie operations
- `ShowtimeService.java` - Manages showtime operations  
- `SeatService.java` - Manages seat operations
- `BookingService.java` - Manages booking operations
- `BookingFacade.java` - Facade for booking workflow

**Benefits:**
- Single point of access for business logic
- Efficient memory usage
- Consistent state management

**العربية:**
نمط الوحيد يضمن وجود نسخة واحدة فقط من الكلاس طوال دورة حياة التطبيق. في Spring Boot، يتم تحقيق ذلك من خلال `@Service` الذي يسجل الخدمات كـ beans وحيدة في Spring IoC container.

**الفوائد:**
- نقطة وصول واحدة للمنطق التجاري
- استخدام فعال للذاكرة
- إدارة حالة متسقة

---

## 2. OBSERVER PATTERN / نمط المراقب
**Location:** `com.cinema.observer` package

**English:**
The Observer pattern defines a one-to-many dependency between objects. When one object (Subject) changes state, all dependent objects (Observers) are notified automatically.

**Implementation:**
- `BookingNotificationManager.java` - Subject that manages observers
- `BookingObserver.java` - Observer interface
- `EmailNotificationObserver.java` - Sends email notifications
- `SmsNotificationObserver.java` - Sends SMS notifications

**Usage:**
When a booking is confirmed or cancelled, the `BookingNotificationManager` notifies all registered observers (Email and SMS) to send notifications.

**Benefits:**
- Loose coupling between subject and observers
- Easy to add new notification types
- Follows Open/Closed Principle

**العربية:**
نمط المراقب يحدد علاقة اعتماد واحد-للكثير بين الكائنات. عندما يتغير كائن (Subject)، يتم إشعار جميع الكائنات المعتمدة (Observers) تلقائياً.

**الاستخدام:**
عند تأكيد أو إلغاء الحجز، يقوم `BookingNotificationManager` بإشعار جميع المراقبين المسجلين (البريد الإلكتروني وSMS) لإرسال الإشعارات.

**الفوائد:**
- اقتران فضفاض بين Subject والمراقبين
- سهولة إضافة أنواع إشعارات جديدة
- يتبع مبدأ Open/Closed

---

## 3. FACADE PATTERN / نمط الواجهة
**Location:** `com.cinema.service.BookingFacade.java`

**English:**
The Facade pattern provides a unified interface to a set of interfaces in a subsystem. It simplifies complex interactions by hiding the complexity of multiple services behind a single interface.

**Implementation:**
`BookingFacade` simplifies interactions between:
- `MovieService` - Movie operations
- `ShowtimeService` - Showtime operations
- `SeatService` - Seat operations
- `BookingService` - Booking operations

**Key Methods:**
- `initiateBooking()` - Handles entire booking workflow
- `getAllMovies()` - Gets all active movies
- `getSeatLayoutForShowtime()` - Gets seat layout
- `confirmBookingPayment()` - Processes payment and confirms booking

**Benefits:**
- Simplifies client code
- Reduces dependencies
- Provides a clean API

**العربية:**
نمط الواجهة يوفر واجهة موحدة لمجموعة من الواجهات في نظام فرعي. يبسط التفاعلات المعقدة بإخفاء تعقيدات الخدمات المتعددة خلف واجهة واحدة.

**الفوائد:**
- يبسط كود العميل
- يقلل الاعتماديات
- يوفر API نظيف

---

## 4. PROXY PATTERN / نمط الوكيل
**Location:** `com.cinema.config.SecurityConfig.java`

**English:**
The Proxy pattern provides a surrogate or placeholder for another object to control access to it. Spring Security acts as a proxy to protect admin routes by intercepting requests and checking user roles before allowing access.

**Implementation:**
- `SecurityConfig.java` - Configures security proxy
- `@PreAuthorize("hasRole('ADMIN')")` - Method-level proxy protection
- `.hasRole("ADMIN")` - URL pattern protection

**Protection:**
- `/admin/**` routes are protected
- Only users with ADMIN role can access
- Customers get 403 Forbidden if they try to access

**Benefits:**
- Access control without modifying business logic
- Centralized security management
- Transparent to the client

**العربية:**
نمط الوكيل يوفر بديلاً أو placeholder لكائن آخر للتحكم في الوصول إليه. Spring Security يعمل كـ proxy لحماية مسارات المدير من خلال اعتراض الطلبات والتحقق من أدوار المستخدمين قبل السماح بالوصول.

**الحماية:**
- مسارات `/admin/**` محمية
- فقط المستخدمون بدور ADMIN يمكنهم الوصول
- العملاء يحصلون على 403 Forbidden إذا حاولوا الوصول

**الفوائد:**
- التحكم في الوصول دون تعديل المنطق التجاري
- إدارة أمنية مركزية
- شفاف للعميل

---

## 5. FACTORY PATTERN / نمط المصنع
**Location:** `com.cinema.payment.PaymentFactory.java`

**English:**
The Factory pattern provides an interface for creating objects without specifying their exact classes. It centralizes object creation logic.

**Implementation:**
`PaymentFactory` creates appropriate payment strategies based on payment method:
- `VISA` → `VisaPaymentStrategy`
- `CASH` → `CashPaymentStrategy`
- `NET_BANKING` → `NetBankingPaymentStrategy`

**Key Method:**
```java
public PaymentStrategy createPaymentStrategy(Booking.PaymentMethod paymentMethod)
```

**Usage:**
```java
PaymentStrategy strategy = paymentFactory.createPaymentStrategy(Booking.PaymentMethod.VISA);
```

**Benefits:**
- Centralizes object creation
- Easy to add new payment methods
- Reduces coupling between client and concrete classes

**العربية:**
نمط المصنع يوفر واجهة لإنشاء الكائنات دون تحديد فئاتها الدقيقة. يركز منطق إنشاء الكائنات.

**الاستخدام:**
```java
PaymentStrategy strategy = paymentFactory.createPaymentStrategy(Booking.PaymentMethod.VISA);
```

**الفوائد:**
- يركز إنشاء الكائنات
- سهولة إضافة طرق دفع جديدة
- يقلل الاقتران بين العميل والفئات الملموسة

---

## 6. STRATEGY PATTERN / نمط الاستراتيجية
**Location:** `com.cinema.service` package (MovieFilterStrategy)

**English:**
The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from clients that use it.

**Implementation:**
- `MovieFilterStrategy.java` - Strategy interface
- `GenreFilterStrategy.java` - Filters by genre
- `RatingFilterStrategy.java` - Filters by minimum rating
- `MovieFilterContext.java` - Context that uses strategies

**Usage:**
```java
// Filter by genre
List<Movie> movies = filterContext.executeFilter("GENRE", "Action");

// Filter by rating
List<Movie> movies = filterContext.executeFilter("RATING", "8.0");
```

**Benefits:**
- Easy to add new filtering strategies
- Algorithms are interchangeable
- Follows Open/Closed Principle

**العربية:**
نمط الاستراتيجية يحدد عائلة من الخوارزميات، يغلف كل واحدة، ويجعلها قابلة للتبديل. يسمح للخوارزمية بالاختلاف بشكل مستقل عن العملاء الذين يستخدمونها.

**الاستخدام:**
```java
// فلترة حسب النوع
List<Movie> movies = filterContext.executeFilter("GENRE", "Action");

// فلترة حسب التقييم
List<Movie> movies = filterContext.executeFilter("RATING", "8.0");
```

**الفوائد:**
- سهولة إضافة استراتيجيات فلترة جديدة
- الخوارزميات قابلة للتبديل
- يتبع مبدأ Open/Closed

---

## 7. BUILDER PATTERN / نمط الباني
**Location:** `com.cinema.entity.BookingBuilder.java`

**English:**
The Builder pattern constructs complex objects step by step. It allows you to produce different types and representations of an object using the same construction code.

**Implementation:**
`BookingBuilder` constructs `Booking` objects with fluent interface:
```java
Booking booking = new BookingBuilder()
    .withUser(user)
    .withMovie(movie)
    .withShowtime(showtime)
    .withSeats(seats)
    .withTotalPrice(totalPrice)
    .withPaymentMethod(paymentMethod)
    .build();
```

**Benefits:**
- Clear, readable code
- Flexible object construction
- Prevents invalid object states
- Easy to add new parameters

**العربية:**
نمط الباني يبني الكائنات المعقدة خطوة بخطوة. يسمح بإنتاج أنواع مختلفة وتمثيلات لكائن باستخدام نفس كود البناء.

**الفوائد:**
- كود واضح وقابل للقراءة
- بناء كائنات مرن
- يمنع حالات الكائنات غير الصالحة
- سهولة إضافة معاملات جديدة

---

## 8. ADAPTER PATTERN / نمط المحول
**Location:** `com.cinema.observer.NotificationAdapter.java`

**English:**
The Adapter pattern allows incompatible interfaces to work together. It acts as a bridge between two incompatible interfaces by wrapping an object to make it compatible with another interface.

**Implementation:**
`NotificationAdapter` unifies different notification providers:
- `EmailNotificationObserver` - Email notifications
- `SmsNotificationObserver` - SMS notifications

**Key Method:**
```java
public void sendNotification(Booking booking, String notificationType)
```

**Usage:**
```java
notificationAdapter.sendNotification(booking, "CONFIRMED");
// Sends via both Email and SMS
```

**Benefits:**
- Unifies different interfaces
- Easy to add new notification channels
- Client code doesn't need to know about individual providers

**العربية:**
نمط المحول يسمح للواجهات غير المتوافقة بالعمل معاً. يعمل كجسر بين واجهتين غير متوافقتين من خلال تغليف كائن لجعله متوافقاً مع واجهة أخرى.

**الاستخدام:**
```java
notificationAdapter.sendNotification(booking, "CONFIRMED");
// يرسل عبر البريد الإلكتروني وSMS
```

**الفوائد:**
- يوحد الواجهات المختلفة
- سهولة إضافة قنوات إشعار جديدة
- كود العميل لا يحتاج لمعرفة المزودين الفرديين

---

## Summary / الملخص

All 8 design patterns are implemented and documented throughout the codebase:
1. ✅ **Singleton** - Spring Services
2. ✅ **Observer** - Notification System
3. ✅ **Facade** - BookingFacade
4. ✅ **Proxy** - Spring Security
5. ✅ **Factory** - PaymentFactory
6. ✅ **Strategy** - MovieFilterStrategy
7. ✅ **Builder** - BookingBuilder
8. ✅ **Adapter** - NotificationAdapter

جميع أنماط التصميم الثمانية مطبقة وموثقة في جميع أنحاء الكود:
1. ✅ **Singleton** - خدمات Spring
2. ✅ **Observer** - نظام الإشعارات
3. ✅ **Facade** - BookingFacade
4. ✅ **Proxy** - Spring Security
5. ✅ **Factory** - PaymentFactory
6. ✅ **Strategy** - MovieFilterStrategy
7. ✅ **Builder** - BookingBuilder
8. ✅ **Adapter** - NotificationAdapter
