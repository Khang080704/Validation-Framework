# Hướng dẫn thêm validator/constraint mới

Tài liệu này hướng dẫn cách **thêm một validation constraint mới** vào framework, để dùng được ở cả:

1. **Annotation-based**: gắn annotation lên field
2. **Programmatic**: khai báo bằng `ConstraintBuilder` + `*Definition`

Trong codebase hiện tại, một constraint thường gồm 4 phần:

1. **Annotation**: `src/main/java/org/example/constraints/annotation/*.java`
2. **Definition (programmatic)**: `src/main/java/org/example/constraints/definition/*.java`
3. **Validator implementation**: `src/main/java/org/example/constraints/validators/*.java`
4. **Đăng ký vào registry**: `src/main/java/org/example/constraints/validators/ConstraintValidatorRegistry.java`

---

## 1) Luồng hoạt động (để hiểu trước khi thêm)

### Annotation-based

- `AnnotationClassValidatorProvider` quét tất cả field annotations.
- Mỗi annotation (trừ `@IsValid`) sẽ được map sang `ConstraintValidator` thông qua `ConstraintValidatorRegistry.getInstance(annotationType, fieldType)`.
- Provider gọi `validator.initialize(attributes)` lấy attributes từ annotation (via reflection), rồi gọi `validator.validate(value)`.

### Programmatic

- Bạn tạo các `ConstraintDefinition` (ví dụ `MinDefinition`, `SizeDefinition`), gắn vào field bằng `ConstraintBuilder`.
- `ProgrammaticClassValidatorProvider` chuyển các `ConstraintDefinition` sang `ConstraintValidator` cũng qua `ConstraintValidatorRegistry`.

---

## 2) Checklist: thêm constraint mới cần làm gì?

Để thêm constraint mới **đầy đủ** (annotation + programmatic), bạn làm theo checklist sau:

1. Tạo annotation mới trong `org.example.constraints.annotation`
2. Tạo validator mới trong `org.example.constraints.validators`
3. Nếu có tham số (ví dụ `min/max/value/regex`), override `initialize(Map<String,Object>)`
4. Đăng ký mapping trong `ConstraintValidatorRegistry`
5. Tạo `*Definition` trong `org.example.constraints.definition` (để dùng programmatic)
6. Viết ví dụ sử dụng / cập nhật docs (`SUPPORTED_FEATURES.md`)

---

## 3) Ví dụ hoàn chỉnh: thêm constraint `@StartsWith(prefix)`

Mục tiêu: validate `String` phải bắt đầu bằng một tiền tố.

### Bước A — Tạo annotation

Tạo file: `src/main/java/org/example/constraints/annotation/StartsWith.java`

```java
package org.example.constraints.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartsWith {
	String prefix();
	String message() default "Field must start with the given prefix";
}
```

### Bước B — Tạo validator

Tạo file: `src/main/java/org/example/constraints/validators/StartsWithValidator.java`

```java
package org.example.constraints.validators;

import java.util.Map;

public class StartsWithValidator extends ConstraintValidator<String> {
	private String prefix;

	@Override
	public void initialize(Map<String, Object> attributes) {
		super.initialize(attributes);
		this.prefix = (String) attributes.get("prefix");
	}

	@Override
	public String validate(String value) {
		// Quy ước hiện tại của project:
		// - Nhiều constraint coi null là hợp lệ (trừ NotNull/NotBlank/NotEmpty)
		if (value == null) {
			return null;
		}

		return value.startsWith(prefix) ? null : message;
	}
}
```

### Bước C — Đăng ký vào registry

Sửa file: `src/main/java/org/example/constraints/validators/ConstraintValidatorRegistry.java`

Trong `static { ... }` thêm dòng:

```java
register(StartsWith.class, String.class, StartsWithValidator.class);
```

> Lưu ý về type mapping:
>
> - Registry sẽ tự wrap primitive types (`int` → `Integer`, ...)
> - Nó còn hỗ trợ lookup theo `isAssignableFrom` (ví dụ bạn register `Collection.class`, field type là `ArrayList.class` vẫn match)

### Bước D — Tạo Definition (programmatic)

Tạo file: `src/main/java/org/example/constraints/definition/StartsWithDefinition.java`

```java
package org.example.constraints.definition;

import org.example.constraints.annotation.StartsWith;

import java.util.Map;

public class StartsWithDefinition extends ConstraintDefinition {
	private String prefix;

	public StartsWithDefinition() {
		this.annotationType = StartsWith.class;
		this.message = "Field must start with the given prefix";
	}

	public StartsWithDefinition prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public StartsWithDefinition message(String message) {
		this.message = message;
		return this;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Map.of(
			"message", message,
			"prefix", prefix
		);
	}
}
```

### Bước E — Cách dùng (annotation-based)

```java
public class User {
	@StartsWith(prefix = "USR_", message = "User code must start with USR_")
	private String code;
}
```

### Bước F — Cách dùng (programmatic)

```java
ProgrammaticClassValidatorProvider programmatic = new ProgrammaticClassValidatorProvider();

new ConstraintBuilder(programmatic)
	.on(User.class)
	.constraints(
		"code",
		new StartsWithDefinition().prefix("USR_")
	)
	.build();
```

---

## 4) Thêm constraint cho nhiều kiểu dữ liệu (Collection/Map/Array)

Nếu constraint của bạn áp dụng cho nhiều kiểu, có 2 cách:

### Cách 1 — Nhiều validator class (giống `NotEmpty`, `Size`)

Ví dụ `NotEmpty` có:

- `NotEmptyValidatorForString`
- `NotEmptyValidatorForCollection`
- `NotEmptyValidatorForMap`
- `NotEmptyValidatorForArray` và các primitive arrays

Bạn đăng ký từng type cụ thể trong registry:

```java
register(NotEmpty.class, String.class, NotEmptyValidatorForString.class);
register(NotEmpty.class, Collection.class, NotEmptyValidatorForCollection.class);
register(NotEmpty.class, Map.class, NotEmptyValidatorForMap.class);
register(NotEmpty.class, int[].class, NotEmptyValidatorForIntegerArray.class);
// ...
```

### Cách 2 — 1 validator + dùng `isAssignableFrom`

Đăng ký ở type “cha” (ví dụ `Collection.class`) và để registry tự match các subclass.

---

## 5) Quy ước null-handling (rất quan trọng)

Trong codebase hiện tại:

- `NotNull` **fail** nếu `null`
- `NotBlank`/`NotEmpty` cũng **fail** nếu `null`
- Nhiều constraint khác **coi `null` là hợp lệ** (ví dụ `Size`, `Min`, `Max`, `Pattern`, `AssertTrue/False`)

Khi thêm constraint mới, bạn nên quyết định rõ:

- Constraint đó có phải “required” không?
  - Nếu có: hãy fail khi `null`
  - Nếu không: thường trả `null` (không vi phạm) khi input `null`

---

## 6) Thêm nested validation?

Nested object dùng `@IsValid` / `IsValidDefinition`.

### Annotation-based

Chỉ cần gắn `@IsValid` lên field object:

```java
public class User {
	@IsValid
	private Credential credential;
}
```

### Programmatic

Bạn phải build validators cho nested type trước, rồi mới gắn `IsValidDefinition`:

```java
new ConstraintBuilder(programmatic)
	.on(Credential.class)
	.constraints("password", new NotBlankDefinition())
	.build();

new ConstraintBuilder(programmatic)
	.on(User.class)
	.constraints("credential", new IsValidDefinition())
	.build();
```

---

## 7) Troubleshooting

### Lỗi: "No validator found for annotation ... and field type ..."

Nguyên nhân thường gặp:

- Bạn quên `register(...)` trong `ConstraintValidatorRegistry`
- Bạn register sai type (ví dụ register `Integer.class` nhưng field là `Long`)
- Bạn muốn hỗ trợ interface/superclass nhưng lại register type cụ thể quá (hãy register `Collection.class` thay vì `ArrayList.class`)

### Lỗi ClassCastException trong `initialize(...)`

Ví dụ:

- annotation attribute là `long`, reflection trả về `Long`
- attribute là `int`, reflection trả về `Integer`

Hãy cast đúng kiểu wrapper (`Long`, `Integer`, ...).

### Lỗi message bị null

`ConstraintValidator.initialize(...)` mặc định đọc `attributes.get("message")`.

Nếu bạn tạo `Definition`, hãy chắc chắn `getAttributes()` luôn trả về key `"message"`.
