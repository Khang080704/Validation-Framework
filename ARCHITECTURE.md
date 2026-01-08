# Kiến Trúc Validation Framework

## Tổng Quan

Framework này là một thư viện validation linh hoạt cho Java, cho phép validate dữ liệu thông qua annotation hoặc programmatic API. Framework hỗ trợ kết hợp nhiều validation rules trên cùng một field và sử dụng nhiều design patterns để đảm bảo tính mở rộng và bảo trì.

## Design Patterns Được Sử Dụng

### 1. **Strategy Pattern**

- **Vị trí**: `validators/constraintvalidators/ConstraintValidator.java`
- **Mục đích**: Cho phép chuyển đổi giữa các thuật toán validation khác nhau
- **Ứng dụng**: Mỗi loại validation (NotNull, Size, Email...) là một strategy riêng

### 2. **Registry Pattern**

- **Vị trí**:
  - `validators/ValidatorRegistry.java`
  - `converters/AnnotationToConfigConverterRegistry.java`
- **Mục đích**: Quản lý mapping giữa Config/Annotation và Validator/Converter tương ứng
- **Ứng dụng**: Đăng ký và tra cứu validator/converter tại runtime

### 3. **Builder Pattern**

- **Vị trí**: `constraintbuilder/ConstraintBuilder.java`
- **Mục đích**: Tạo validation configuration một cách fluent và dễ đọc
- **Ứng dụng**: Programmatic API cho phép xây dựng validation rules theo chuỗi

### 4. **Decorator Pattern (Proxy/Wrapper)**

- **Vị trí**: `configproviders/CachedConfigProvider.java`
- **Mục đích**: Thêm caching layer cho ConfigProvider
- **Ứng dụng**: Wrap nhiều providers và cache kết quả để tối ưu hiệu năng

### 5. **Composite Pattern**

- **Vị trí**: `common/FieldConfig.java`
- **Mục đích**: Kết hợp nhiều Config objects cho một field
- **Ứng dụng**: Một field có thể có nhiều validation rules (Set<Config>)

### 6. **Template Method Pattern**

- **Vị trí**: `constraints/definition/ConstraintDefinition.java`
- **Mục đích**: Định nghĩa skeleton cho constraint definitions
- **Ứng dụng**: Các subclass implement `getConfig()` theo cách riêng

### 7. **Factory Pattern**

- **Vị trí**: `converters/AnnotationToConfigConverter.java`
- **Mục đích**: Chuyển đổi từ Annotation sang Config object
- **Ứng dụng**: Mỗi converter biết cách tạo Config từ Annotation tương ứng

### 8. **Observer Pattern (Notification)**

- **Vị trí**: `common/IConstraintViolationNotifier.java`
- **Mục đích**: Thông báo khi có validation violation
- **Ứng dụng**: Validator notify cho notifier khi validation fails

## Cấu Trúc Thư Mục Chi Tiết

### 📁 `common/` - Các Thành Phần Dùng Chung

#### `FieldConfig.java`

**Chức năng**:

- Container lưu trữ configuration cho một field cụ thể
- Quản lý nhiều validation rules (Config) cho cùng một field
- Sử dụng Reflection để access field values

**Design Pattern**:

- **Composite Pattern**: Chứa `Set<Config>` để kết hợp nhiều constraints

**Thuộc tính chính**:

- `Field field`: Java Reflection field object
- `Set<Config> configs`: Tập hợp các validation rules

**Phương thức quan trọng**:

- `addConfig(Config)`: Thêm validation rule
- `getConfigs()`: Lấy tất cả validation rules

---

#### `IConstraintViolationNotifier.java`

**Chức năng**:

- Interface cho notification mechanism
- Cho phép custom cách hiển thị validation errors

**Design Pattern**:

- **Observer Pattern**: Định nghĩa interface cho observers nhận thông báo violations

**Phương thức**:

- `display(ValidationViolation)`: Hiển thị thông tin lỗi

---

#### `ValidationViolation.java`

**Chức năng**:

- Chứa kết quả validation
- Lưu trữ tất cả các lỗi validation theo field

**Cấu trúc dữ liệu**:

- `Map<String, List<String>> violations`: Map từ field name đến danh sách lỗi

**Phương thức**:

- `addViolation(String field, List<String> messages)`: Thêm lỗi cho field
- `isViolated()`: Kiểm tra có lỗi hay không
- `getViolations()`: Lấy tất cả violations

---

### 📁 `config/` - Validation Configuration Objects

#### `Config.java`

**Chức năng**:

- Base class cho tất cả validation configurations
- Chứa error message

**Design Pattern**:

- **Template Method**: Base class cho hierarchy của configs

**Thuộc tính**:

- `String message`: Thông báo lỗi khi validation fails

---

#### `NotNullConfig.java`

**Chức năng**:

- Configuration cho NotNull validation
- Kế thừa từ Config

**Sử dụng**:

- Được tạo từ `@NotNull` annotation hoặc `NotNullDefinition`

---

### 📁 `configproviders/` - Cung Cấp Validation Configurations

#### `ConfigProvider.java`

**Chức năng**:

- Interface định nghĩa contract cho config providers
- Strategy interface cho các nguồn config khác nhau

**Design Pattern**:

- **Strategy Pattern**: Interface cho các strategy khác nhau

**Phương thức**:

- `Set<FieldConfig> getConfig(Class<?> type)`: Lấy configs cho một class

---

#### `AnnotationConfigProvider.java`

**Chức năng**:

- Đọc validation rules từ annotations trên class
- Sử dụng Reflection để scan annotations
- Chuyển đổi annotations thành Config objects

**Design Pattern**:

- **Strategy Pattern**: Concrete strategy cho annotation-based config
- **Factory Pattern**: Sử dụng AnnotationToConfigConverter để tạo configs

**Quy trình hoạt động**:

1. Duyệt tất cả fields của class
2. Scan annotations trên mỗi field
3. Chuyển đổi annotation → Config qua AnnotationToConfigConverterRegistry
4. Thêm Config vào FieldConfig

---

#### `ProgrammaticConfigProvider.java`

**Chức năng**:

- Lưu trữ validation rules được định nghĩa qua code
- Cho phép add configs dynamically tại runtime

**Design Pattern**:

- **Strategy Pattern**: Concrete strategy cho programmatic config
- **Registry Pattern**: Map lưu trữ configs theo class type

**Thuộc tính**:

- `Map<Class<?>, Set<FieldConfig>> configs`: Lưu configs theo class

**Phương thức**:

- `putConfigs(Class<?>, Set<FieldConfig>)`: Lưu configs cho class
- `getConfig(Class<?>)`: Lấy configs của class

---

#### `CachedConfigProvider.java`

**Chức năng**:

- Kết hợp nhiều ConfigProviders (Annotation + Programmatic)
- Cache kết quả để tối ưu performance
- Tránh việc scan/process lặp lại cho cùng một class

**Design Pattern**:

- **Decorator Pattern**: Wrap nhiều providers và thêm caching
- **Composite Pattern**: Kết hợp nhiều providers

**Thuộc tính**:

- `Map<Class<?>, Set<FieldConfig>> cache`: Cache configs theo class
- `List<ConfigProvider> providers`: Danh sách providers

**Quy trình**:

1. Check cache trước
2. Nếu không có, gọi tất cả providers
3. Merge kết quả từ tất cả providers
4. Lưu vào cache và return

---

### 📁 `constraintbuilder/` - Fluent API Builder

#### `ConstraintBuilder.java`

**Chức năng**:

- Fluent API để định nghĩa validation rules qua code
- Tạo configs và lưu vào ProgrammaticConfigProvider

**Design Pattern**:

- **Builder Pattern**: Fluent interface cho việc xây dựng configs

**Phương thức chính**:

- `on(Class<?>)`: Chỉ định class cần validate
- `constraints(String fieldName, ConstraintDefinition...)`: Thêm validation rules cho field
- `build()`: Hoàn tất và lưu configs vào provider

**Ví dụ sử dụng**:

```java
builder
    .on(User.class)
    .constraints("email",
        new NotNullDefinition().message("Email required"),
        new EmailDefinition().message("Invalid email")
    )
    .build();
```

---

### 📁 `constraints/` - Constraint Definitions

#### 📁 `constraints/annotation/` - Java Annotations

**Các file annotation**:

- `NotNull.java`: Kiểm tra giá trị không null
- `NotEmpty.java`: Kiểm tra string/collection không rỗng
- `Size.java`: Kiểm tra độ dài string hoặc kích thước collection
- `Min.java`: Giá trị tối thiểu cho số
- `Max.java`: Giá trị tối đa cho số
- `Email.java`: Validate email format
- `Pattern.java`: Validate theo regex pattern

**Chức năng chung**:

- Đánh dấu fields cần validate
- Chứa metadata (message, parameters)
- Được process bởi AnnotationConfigProvider

**Retention**: `RUNTIME` - Available qua reflection

**Ví dụ**:

```java
@NotNull(message = "Name is required")
@Size(min = 2, max = 50, message = "Name must be 2-50 characters")
private String name;
```

---

#### 📁 `constraints/definition/` - Programmatic Definitions

#### `ConstraintDefinition.java`

**Chức năng**:

- Abstract base class cho programmatic constraint definitions
- Cho phép fluent configuration

**Design Pattern**:

- **Template Method**: Abstract class với method `message()` chung

**Phương thức**:

- `message(String)`: Set error message (fluent)
- `abstract Config getConfig()`: Subclass implement để tạo Config

---

#### `NotNullDefinition.java`

**Chức năng**:

- Programmatic definition cho NotNull constraint
- Tạo NotNullConfig object

**Sử dụng**:

- Với ConstraintBuilder trong programmatic API

---

### 📁 `converters/` - Annotation to Config Converters

#### `AnnotationToConfigConverter.java`

**Chức năng**:

- Interface cho việc convert annotation → Config
- Generic interface với type parameter

**Design Pattern**:

- **Factory Pattern**: Interface cho factory methods

**Phương thức**:

- `Config convert(T annotation)`: Convert annotation thành Config

---

#### `NotNullToConfigConverter.java`

**Chức năng**:

- Convert `@NotNull` annotation thành `NotNullConfig`
- Extract message từ annotation

**Implementation**:

```java
public Config convert(NotNull annotation) {
    return new NotNullConfig(annotation.message());
}
```

---

#### `AnnotationToConfigConverterRegistry.java`

**Chức năng**:

- Registry lưu trữ mapping: Annotation Class → Converter
- Singleton static registry

**Design Pattern**:

- **Registry Pattern**: Map-based registry
- **Singleton Pattern**: Static registry instance

**Thuộc tính**:

- `Map<Class<? extends Annotation>, AnnotationToConfigConverter> registry`

**Phương thức**:

- `register(Class<Annotation>, Converter)`: Đăng ký converter
- `getConverter(Class<Annotation>)`: Lấy converter

**Static initialization**:

```java
static {
    register(NotNull.class, new NotNullToConfigConverter());
    // Đăng ký các converters khác...
}
```

---

### 📁 `validators/` - Validation Engine

#### `IValidator.java`

**Chức năng**:

- Interface cho validation service
- Định nghĩa public API của framework

**Phương thức**:

- `ValidationViolation validate(Object)`: Validate toàn bộ object
- `ValidationViolation validateProperty(Object, String)`: Validate một field cụ thể

---

#### `Validator.java`

**Chức năng**:

- Core validation engine
- Orchestrate toàn bộ quá trình validation
- Kết hợp ConfigProvider và ConstraintValidators

**Design Pattern**:

- **Strategy Pattern**: Sử dụng các ConstraintValidator strategies
- **Facade Pattern**: Đơn giản hóa interface cho complex validation subsystem

**Dependencies**:

- `ConfigProvider`: Lấy validation configs
- `IConstraintViolationNotifier`: Thông báo violations

**Quy trình validation**:

1. Lấy configs từ ConfigProvider
2. Với mỗi FieldConfig:
   - Get field value qua reflection
   - Với mỗi Config trong field:
     - Get validator từ ValidatorRegistry
     - Execute `isValid(value)`
     - Collect error messages nếu fail
3. Return ValidationViolation object

---

#### `ValidatorRegistry.java`

**Chức năng**:

- Registry mapping Config class → ConstraintValidator class
- Lookup validator tại runtime

**Design Pattern**:

- **Registry Pattern**: Map-based registry
- **Singleton Pattern**: Static registry

**Thuộc tính**:

- `Map<Class<? extends Config>, Class<? extends ConstraintValidator>> registry`

**Phương thức**:

- `register(Class<Config>, Class<Validator>)`: Đăng ký validator
- `get(Class<Config>)`: Lấy validator class

**Static initialization**:

```java
static {
    register(NotNullConfig.class, NotNullValidator.class);
    // Đăng ký các validators khác...
}
```

---

#### 📁 `validators/constraintvalidators/` - Validator Implementations

#### `ConstraintValidator.java`

**Chức năng**:

- Interface cho tất cả constraint validators
- Generic interface với Config và Value type

**Design Pattern**:

- **Strategy Pattern**: Strategy interface cho validation algorithms

**Type Parameters**:

- `C extends Config`: Loại config
- `T`: Loại giá trị cần validate

**Phương thức**:

- `default initialize(C config)`: Khởi tạo validator với config (optional)
- `boolean isValid(T value)`: Kiểm tra giá trị có hợp lệ không

---

#### `NotNullValidator.java`

**Chức năng**:

- Validate giá trị không null
- Concrete implementation của ConstraintValidator

**Implementation**:

```java
public class NotNullValidator implements ConstraintValidator<NotNullConfig, Object> {
    @Override
    public boolean isValid(Object value) {
        return value != null;
    }
}
```

---

### 📁 `entities/` - Example Domain Models

#### `User.java` và `Credential.java`

**Chức năng**:

- Example entities sử dụng framework
- Demonstrate cách sử dụng annotations

**Ví dụ**:

```java
public class Credential {
    @NotNull(message = "Username must not be null")
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 5, max = 20, message = "Username must be 5-20 chars")
    private String username;

    @NotNull(message = "Password required")
    @Pattern(regex = "^(?=.*[A-Z])(?=.*[0-9]).*$",
             message = "Password must contain uppercase and number")
    private String password;
}
```

---

### 📁 `Main.java` - Entry Point

**Chức năng**:

- Demo application
- Showcase framework capabilities

**Typical setup**:

```java
public static void main(String[] args) {
    // Setup providers
    AnnotationConfigProvider annotationProvider = new AnnotationConfigProvider();
    ProgrammaticConfigProvider programmaticProvider = new ProgrammaticConfigProvider();

    // Setup builder
    ConstraintBuilder builder = new ConstraintBuilder(programmaticProvider);
    builder.on(User.class)
           .constraints("email", new EmailDefinition())
           .build();

    // Create cached provider
    CachedConfigProvider cachedProvider = new CachedConfigProvider(
        List.of(annotationProvider, programmaticProvider)
    );

    // Create validator
    IValidator validator = new Validator(cachedProvider, violations -> {
        System.out.println("Violations: " + violations.getViolations());
    });

    // Validate
    User user = new User();
    ValidationViolation result = validator.validate(user);
}
```

---

## Luồng Hoạt Động (Flow)

### 1. Configuration Phase

```
Annotation-based:
Class with @Annotations
    → AnnotationConfigProvider.getConfig()
    → AnnotationToConfigConverterRegistry.getConverter()
    → AnnotationToConfigConverter.convert()
    → Config objects
    → FieldConfig

Programmatic-based:
ConstraintBuilder
    .on(Class)
    .constraints(field, definitions...)
    .build()
    → ConstraintDefinition.getConfig()
    → Config objects
    → FieldConfig
    → ProgrammaticConfigProvider.putConfigs()
```

### 2. Validation Phase

```
validator.validate(object)
    → ConfigProvider.getConfig(class) [với cache]
    → For each FieldConfig:
        → Get field value (Reflection)
        → For each Config in field:
            → ValidatorRegistry.get(configClass)
            → Create ConstraintValidator instance
            → validator.isValid(value)
            → Collect violations
    → Return ValidationViolation
    → Notify IConstraintViolationNotifier
```

---

## Extensibility (Khả Năng Mở Rộng)

### Thêm Validation Rule Mới

1. **Tạo Annotation** (`constraints/annotation/NewConstraint.java`)
2. **Tạo Config** (`config/NewConstraintConfig.java extends Config`)
3. **Tạo Definition** (`constraints/definition/NewConstraintDefinition.java`)
4. **Tạo Validator** (`validators/constraintvalidators/NewConstraintValidator.java`)
5. **Tạo Converter** (`converters/NewConstraintToConfigConverter.java`)
6. **Đăng ký**:
   - `AnnotationToConfigConverterRegistry.register(NewConstraint.class, converter)`
   - `ValidatorRegistry.register(NewConstraintConfig.class, NewConstraintValidator.class)`

### Ví dụ: Thêm Email Validation

```java
// 1. Annotation
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Invalid email";
}

// 2. Config
public class EmailConfig extends Config {
    public EmailConfig(String message) {
        super(message);
    }
}

// 3. Definition
public class EmailDefinition extends ConstraintDefinition {
    @Override
    public Config getConfig() {
        return new EmailConfig(message != null ? message : "Invalid email");
    }
}

// 4. Validator
public class EmailValidator implements ConstraintValidator<EmailConfig, String> {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public boolean isValid(String value) {
        return value == null || EMAIL_PATTERN.matcher(value).matches();
    }
}

// 5. Converter
public class EmailToConfigConverter implements AnnotationToConfigConverter<Email> {
    @Override
    public Config convert(Email annotation) {
        return new EmailConfig(annotation.message());
    }
}
adjfạdgadkfjahdkjfahdfkjahdfka