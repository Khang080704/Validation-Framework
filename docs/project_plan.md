# PROJECT PLAN: JAVA VALIDATION FRAMEWORK

**Project Overview:**
* **Topic:** Build a custom Validation Framework[cite: 2, 7].
* **Language:** Java (utilizing Builder Pattern & Design Patterns)[cite: 25].
* [cite_start]**Team Size:** 3 Members (M1, M2, M3)[cite: 9].
* **Duration:** 5 Weeks.
* [cite_start]**Goal:** Achieve maximum score (2.5 pts) with a complete report, source code, and demo[cite: 37, 38, 39].

---

## I. Development Timeline (5 Weeks)

### Week 1: Analysis & Design Architecture
**Goal:** Finalize Design Patterns and create the skeletal Class Diagram.
* [cite_start]**Requirement Analysis:** Define basic validator operations (Notification mechanism, aggregation, UI display)[cite: 12, 13, 14, 15].
* [cite_start]**Design Pattern Selection (Min 3 Patterns)[cite: 10]:**
    1.  **Strategy Pattern:** To define various validation algorithms (Email, Phone, Range).
    2.  [cite_start]**Composite Pattern:** To combine multiple validations for a single data field[cite: 20].
    3.  [cite_start]**Builder Pattern:** To facilitate validation setup via fluent API[cite: 16].
* [cite_start]**Output:** Preliminary Class Diagram[cite: 44].

### Week 2: Build Core Framework (Validator Engine)
**Goal:** Implement Core Interfaces and Basic Validators.
* [cite_start]Create `IValidator` Interface or `BaseValidator` Abstract Class[cite: 26].
* Implement **Strategy Pattern** for specific rules.
* [cite_start]Build a library of basic validators: `StringEmptyValidator`, `NumberRangeValidator`[cite: 21].
* [cite_start]Implement **Composite Pattern** to group validators (Validation Chain)[cite: 20].

### Week 3: Advanced Features (Builder Pattern & Custom Validation)
**Goal:** Implement Builder Pattern for fluent API and enable custom validations.
* [cite_start]Create AbstractValidatorBuilder and concrete builders (StringValidatorBuilder, NumberValidatorBuilder)[cite: 16].
* [cite_start]Implement fluent API with method chaining[cite: 17].
* [cite_start]Enable creation of **Custom Validations**[cite: 23, 29].

### Week 4: Integration & Demo
**Goal:** Finalize the framework and build the Demo App.
* [cite_start]Implement ValidatorContext for error management[cite: 14].
* Develop the Demo Application (Console-based).
* [cite_start]Test framework with various data types and custom validators[cite: 15].
* [cite_start]Implement "Code-based Validation" setup[cite: 16, 17].

### Week 5: Packaging, Reporting & Demo Recording
**Goal:** Finalize submission deliverables.
* [cite_start]**Report Writing:** Explain Class Diagrams and the usage of Design Patterns (Code + Theory)[cite: 42, 45, 46].
* [cite_start]**Demo:** Record a video demonstrating the framework[cite: 59].
* [cite_start]**Packaging:** Organize folders according to the required structure[cite: 62].

---

## II. Detailed Task Assignment

**Roles:**
* **M1 (Leader/Architect):** Core Architecture, Reflection, Complex Logic.
* **M2 (Core Dev):** Validator Library, Regex, Unit Tests.
* **M3 (UI/Integration):** Demo App, Error Management, Reporting.

| Week | General Tasks | Member 1 (M1) | Member 2 (M2) | Member 3 (M3) |
| :--- | :--- | :--- | :--- | :--- |
| **W1** | Team meeting, Pattern selection | - [cite_start]Design `Validator` Interface.<br>- Draw General Class Diagram[cite: 43]. | - [cite_start]List required rules (email, phone...).<br>- Research Regex integration[cite: 22]. | - Setup Java Project structure.<br>- Research Builder Pattern for fluent API. |
| **W2** | Code Core Framework | - Implement **Strategy** & **Composite Pattern**.<br>- Write `ValidatorContext`. | - [cite_start]Code `StringValidator`, `IntValidator`[cite: 26].<br>- Code `RegexValidator`. | - [cite_start]Design `ValidatorResult` class.<br>- Code `ValidatorComposite`[cite: 20]. |
| **W3** | Code Advanced Features | - [cite_start]Implement **Builder Pattern** for fluent API[cite: 16].<br>- Create Builder classes. | - [cite_start]Code **Custom Validator** feature[cite: 23].<br>- Write Unit Tests for logic. | - Build error collection in ValidatorContext.<br>- Start building the Demo Form. |
| **W4** | Integration & Demo | - [cite_start]Code Review, optimize inheritance/extensibility[cite: 32].<br>- Assist M3 with demo integration. | - Add complex rules (e.g., Password match).<br>- Test framework with various data types. | - [cite_start]Finalize Demo App (Console).<br>- Implement error display[cite: 15]. |
| **W5** | Finalize | - [cite_start]Write "Pattern Explanation" section in Report [cite: 46][cite_start].<br>- Check folder structure[cite: 62]. | - [cite_start]Record Demo Video [cite: 59][cite_start].<br>- Write User Guide/Setup Guide[cite: 56, 57]. | - [cite_start]Finalize detailed Class Diagram.<br>- Compile Report, create Zip file[cite: 68]. |

---

## III. Submission Requirements Checklist

[cite_start]Ensure the final submission follows this folder structure[cite: 62]:

* **Folder Name:** `<Framework name>`
    * [cite_start]`1.Documents`: Report, Slides (must include Class Diagram & Pattern explanations)[cite: 64, 42].
    * [cite_start]`2.Source code`: Full Java Source[cite: 65].
    * [cite_start]`3.Functions List`: Table of completed features[cite: 66, 51].
    * [cite_start]`4.Others`: Video Demo, Setup file[cite: 67, 59].
* [cite_start]**Archive:** Compress the folder into `MSSV1-MSSV2-MSSV3.zip`[cite: 68].

[cite_start]**Note:** The framework must ensure **extensibility** and **inheritance** to easily add new validations[cite: 32]. The implementation uses **Strategy**, **Composite**, and **Builder** patterns for flexibility.