# Supported Validations & Usage Guide

This document lists **all validation constraints supported by this framework** and shows how to use them with:

1. **Annotation-based validation** (put annotations on fields)
2. **Programmatic validation** (build constraints at runtime using `ConstraintBuilder`)

It also explains the three validation entrypoints:

- `validate(object)`
- `validateProperty(object, fieldName)`
- `validateValue(type, value, fieldName)`

---

## Quick start

Create a `Validator` with one (or both) providers:

- `AnnotationClassValidatorProvider` → reads annotations from your entity fields
- `ProgrammaticClassValidatorProvider` → uses constraints registered by `ConstraintBuilder`

```java
Validator validator = new Validator(List.of(
	new AnnotationClassValidatorProvider(),
	new ProgrammaticClassValidatorProvider()
));
```

> Note: all validation methods return **`null` when there are no violations**, otherwise a `List<ValidationViolation>`.

---

## Validator API

### `validate(Object object)`

Validates **all fields** of the given object.

```java
List<ValidationViolation> violations = validator.validate(user);
if (violations != null) {
	for (ValidationViolation v : violations) {
		System.out.println(v.getPath());
		v.getMessages().forEach(System.out::println);
	}
}
```

### `validateProperty(Object object, String propertyName)`

Validates a **single field** (by Java field name) on an already-created object.

```java
List<ValidationViolation> v = validator.validateProperty(user, "email");
```

### `validateValue(Class<?> type, Object value, String propertyName)`

Validates a **raw value** as if it were assigned to `propertyName` of class `type`.

This is useful for UI input validation before you update your entity.

```java
List<ValidationViolation> v = validator.validateValue(Credential.class, "", "password");
```

### Observers (optional)

You can subscribe to validation results using `ValidationObserver`:

```java
validator.addObserver(new ValidationObserver() {
	@Override
	public void onValidationSuccess() {
		System.out.println("All good!");
	}

	@Override
	public void onValidationFailure(List<ValidationViolation> violations) {
		System.out.println("Validation failed: " + violations.size());
	}
});
```

Observers are notified after each call to `validate*`.

---

## Supported constraints

### Summary table

| Constraint        | Annotation     | Programmatic Definition | Supported field types                                             | Parameters                                   | Null handling (important!)                             |
| ----------------- | -------------- | ----------------------- | ----------------------------------------------------------------- | -------------------------------------------- | ------------------------------------------------------ |
| Not null          | `@NotNull`     | `NotNullDefinition`     | Any reference type (including boxed primitives)                   | `message`                                    | **Fails** when value is `null`                         |
| Not blank         | `@NotBlank`    | `NotBlankDefinition`    | `String`                                                          | `message`                                    | **Fails** when `null` or `isBlank()`                   |
| Not empty         | `@NotEmpty`    | `NotEmptyDefinition`    | `String`, `Collection`, `Map`, `Object[]`, primitive arrays       | `message`                                    | **Fails** when `null` or empty                         |
| Size              | `@Size`        | `SizeDefinition`        | `String`, `Collection`, `Map`, `Object[]`, primitive arrays       | `min`, `max`, `message`                      | `null` is **treated as valid**                         |
| Min               | `@Min`         | `MinDefinition`         | Any `Number` (incl. boxed primitives, `BigDecimal`, `BigInteger`) | `value`, `message`                           | `null` is **treated as valid**                         |
| Max               | `@Max`         | `MaxDefinition`         | Any `Number` (incl. boxed primitives, `BigDecimal`, `BigInteger`) | `value`, `message`                           | `null` is **treated as valid**                         |
| Email             | `@Email`       | `EmailDefinition`       | `String`                                                          | `message`                                    | `null` and **empty string** are treated as valid       |
| Pattern           | `@Pattern`     | `PatternDefinition`     | `String`                                                          | `regex`, `message`                           | `null` is **treated as valid**                         |
| Assert true       | `@AssertTrue`  | `AssertTrueDefinition`  | `Boolean` (and `boolean`)                                         | `message`                                    | `null` is **treated as valid**                         |
| Assert false      | `@AssertFalse` | `AssertFalseDefinition` | `Boolean` (and `boolean`)                                         | `message`                                    | `null` is **treated as valid**                         |
| Nested validation | `@IsValid`     | `IsValidDefinition`     | Any nested object type                                            | _(message exists but is currently not used)_ | If nested object is `null`, it is **treated as valid** |

### Notes on supported container/array types

Both `@NotEmpty` and `@Size` support:

- `String`
- `Collection<?>`
- `Map<?, ?>`
- Arrays:
  - `Object[]`
  - primitive arrays: `int[]`, `long[]`, `double[]`, `float[]`, `boolean[]`, `byte[]`, `short[]`, `char[]`

---

## How to use constraints (annotation-based)

Add annotations to your entity fields.

Example:

```java
public class Credential {
	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 20, message = "Password must be 8-20 characters")
	private String password;
}
```

Nested validation with `@IsValid`:

```java
public class User {
	@IsValid
	private Credential credential;
}
```

If `credential.password` is invalid, the reported path becomes:

- `credential.password`

---

## How to use constraints (programmatic)

Use `ConstraintBuilder` to register constraints at runtime.

```java
ProgrammaticClassValidatorProvider programmatic = new ProgrammaticClassValidatorProvider();
ConstraintBuilder builder = new ConstraintBuilder(programmatic);

builder
	.on(User.class)
	.constraints(
		"email",
		new NotNullDefinition().message("Email is required"),
		new EmailDefinition().message("Email is invalid")
	)
	.constraints(
		"age",
		new MinDefinition().value(1).message("Age must be at least 1")
	)
	.build();
```

Nested validation (programmatic) uses `IsValidDefinition`:

```java
// 1) Build validators for Credential first
new ConstraintBuilder(programmatic)
	.on(Credential.class)
	.constraints("password", new NotBlankDefinition(), new SizeDefinition().min(8).max(20))
	.build();

// 2) Then reference it from User
new ConstraintBuilder(programmatic)
	.on(User.class)
	.constraints("credential", new IsValidDefinition())
	.build();
```

> Important: in `ProgrammaticClassValidatorProvider`, nested validators are only attached if the nested type already has validators registered.

---

## Practical recipes

### “Required” fields

Most constraints treat `null` as valid. If you want a field to be required:

- use `@NotNull` (or `NotNullDefinition`) for objects
- use `@NotBlank` for required text (also rejects whitespace-only)
- use `@NotEmpty` for required text/collections/arrays

Examples:

- Required email: `@NotEmpty` + `@Email`
- Required boolean must be true: `@NotNull` + `@AssertTrue`

### Validating UI inputs without building objects

Use `validateValue(...)`:

```java
List<ValidationViolation> v = validator.validateValue(User.class, "not-an-email", "email");
```

### Validating only one field after edit

Use `validateProperty(...)`:

```java
List<ValidationViolation> v = validator.validateProperty(user, "phoneNumbers");
```

---

## Current limitations / gotchas

- All `validate*` methods return **`null` for success**, not an empty list.
- `@Email` does **not** fail on empty string. Use `@NotEmpty`/`@NotBlank` together with `@Email` when you want “required email”.
- `@AssertTrue` / `@AssertFalse` do not fail on `null`. Combine with `@NotNull` if needed.
- `@IsValid` validates a nested object, but does not currently validate elements inside a collection (e.g., `List<SomeType>`).
- The `message` attribute on `@IsValid` / `IsValidDefinition` currently isn’t used for reporting (nested violations come from the nested fields).
