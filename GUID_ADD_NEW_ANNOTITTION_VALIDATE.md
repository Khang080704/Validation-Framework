# Hướng dẫn thêm 1 annotation validation mới

Tài liệu này hướng dẫn cách thêm **một annotation validate mới** (annotation-based) vào project.

> Nếu bạn muốn thêm constraint theo kiểu programmatic (ConstraintBuilder + Definition) thì xem thêm `NEW_VALIDATE.md`.

---

## 1) Kiến trúc & luồng chạy (tóm tắt)

Khi bạn gọi `validator.validate(obj)`, provider `AnnotationClassValidatorProvider` sẽ:

1. Duyệt các field của class
2. Lấy tất cả annotation trên field
3. Với mỗi annotation:
   - Nếu là `@IsValid`: validate nested object (đệ quy)
   - Ngược lại: lấy `ConstraintValidator` từ `ConstraintValidatorRegistry` theo cặp:
   - `annotationType` (class của annotation)
   - `fieldType` (kiểu của field)
4. Gọi `validator.initialize(attributes)` để truyền các attribute của annotation
5. Gọi `validator.validate(value)` để lấy message lỗi (nếu có)

Vì vậy, để thêm annotation mới, bạn cần:

- Tạo annotation
- Tạo validator class tương ứng
- Đăng ký mapping (annotation + field type → validator) trong registry

---

## 2) Checklist nhanh

Để thêm một annotation validate mới, bạn thực hiện:

1. **Tạo annotation** trong `org.example.constraints.annotation`
2. **Tạo validator** trong `org.example.constraints.validators` (extends `ConstraintValidator<T>`)
3. (Nếu có tham số) **override `initialize(Map<String,Object>)`** để nhận attribute
4. **Register** vào `ConstraintValidatorRegistry`
5. **Dùng annotation** trên entity field + chạy demo để kiểm tra
6. (Khuyến nghị) Cập nhật `SUPPORTED_FEATURES.md`

---

## 3) Ví dụ từ đầu đến cuối: tạo `@StartsWith(prefix)`

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

**Lưu ý quan trọng:**

- Bắt buộc có `@Retention(RetentionPolicy.RUNTIME)` vì framework đọc annotation bằng reflection lúc runtime.
- Framework hiện tại validate annotation ở **field level**, nên dùng `@Target(ElementType.FIELD)`.

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
		// Quy ước phổ biến của project: null thường được coi là hợp lệ
		// (trừ các constraint kiểu required như NotNull/NotBlank/NotEmpty)
		if (value == null) {
			return null;
		}

		return value.startsWith(prefix) ? null : message;
	}
}
```

**Giải thích:**

- `ConstraintValidator<T>` cung cấp field `message` và `initialize()` mặc định đọc `attributes.get("message")`.
- Các attribute của annotation được trích ra tự động bởi `AnnotationClassValidatorProvider` (method name → value).
  - Ví dụ: method `prefix()` trên annotation → key `"prefix"` trong map.

### Bước C — Register vào registry

Mở file: `src/main/java/org/example/constraints/validators/ConstraintValidatorRegistry.java`

Trong block `static { ... }` thêm:

```java
register(StartsWith.class, String.class, StartsWithValidator.class);
```

**Lưu ý về type matching:**

- Registry sẽ tự wrap primitive type sang wrapper (ví dụ `int` → `Integer`).
- Registry cũng dùng `isAssignableFrom` để match kiểu cha/con.
  - Ví dụ bạn register với `Number.class` thì field `Integer`/`Long`/... đều match.

### Bước D — Dùng annotation trong entity

Ví dụ:

```java
public class User {
	@StartsWith(prefix = "USR_", message = "User code must start with USR_")
	private String code;
}
```

### Bước E — Chạy validate để kiểm tra

```java
Validator validator = new Validator(List.of(new AnnotationClassValidatorProvider()));

User u = new User();
u.setCode("ABC");

List<ValidationViolation> violations = validator.validate(u);
```
11234565432345654345654345
---

## 4) Thêm annotation cho nhiều kiểu dữ liệu

Nếu annotation của bạn áp dụng cho nhiều kiểu (vd: `String`, `Collection`, `Map`, arrays...), bạn có 2 lựa chọn:

### Lựa chọn 1 — Nhiều validator class (giống `NotEmpty`, `Size`)

Bạn tạo nhiều validator khác nhau và register từng type cụ thể.

### Lựa chọn 2 — 1 validator dùng type cha

Nếu bạn muốn 1 validator cho tất cả collection, hãy register với `Collection.class`.
Registry sẽ match `ArrayList`, `HashSet`, ... nhờ `isAssignableFrom`.

---

## 5) Quy ước về null/empty

Trong project hiện tại:

- `NotNull` / `NotBlank` / `NotEmpty`: **fail khi null**
- Nhiều constraint khác: **null được coi là valid**

Khi viết validator mới, hãy quyết định rõ:

- Nếu constraint là “required” → fail khi `value == null`
- Nếu constraint chỉ kiểm tra format/range → thường return `null` khi `value == null`

---

## 6) Troubleshooting

### 6.1 Lỗi: `No validator found for annotation ... and field type ...`

Nguyên nhân phổ biến:

- Quên `register(...)` trong `ConstraintValidatorRegistry`
- Register sai field type (vd register `String.class` nhưng field lại là `CharSequence`)
- Bạn muốn match interface/superclass nhưng register type quá cụ thể

### 6.2 Lỗi: message bị `null`

`ConstraintValidator.initialize(...)` sẽ đọc key `"message"`.

Với annotation-based, hãy chắc chắn annotation có method `message()`.

### 6.3 `ClassCastException` trong `initialize(...)`

Do cast sai kiểu wrapper.

Ví dụ:

- `long` → `Long`
- `int` → `Integer`

---

## 7) Khuyến nghị cập nhật docs

Sau khi thêm annotation mới, hãy cập nhật:

- `SUPPORTED_FEATURES.md`: thêm constraint mới vào bảng + mô tả type/params/null-handling.
