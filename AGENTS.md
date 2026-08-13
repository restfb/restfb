# Repository Guidelines

## Project Structure & Module Organization
RestFB ships as a single Maven module. Runtime sources live in `src/main/java/com/restfb/**`; Lombok-decorated definitions sit in `src/main/lombok` and are delomboked during `generate-sources`. Tests mirror the package tree inside `src/test/java` with JSON fixtures and `integration-test.properties` under `src/test/resources`. Helper scripts live in `misc/` and release metadata in `release/`; Maven writes build output to `target/`.

## Build, Test, and Development Commands
`mvn clean package` compiles, runs unit tests, and produces consumable JARs in `target/`. Use `mvn verify` when you need the full validation lifecycle, including Failsafe integration-test/verify goals. Use `mvn test` for the fast JUnit 5 suite, and only fall back to `mvn -DskipTests package` when you need a quick binary and have already run tests elsewhere. `mvn jacoco:report` regenerates coverage for SonarCloud under `target/site/jacoco`, and maintainers can drive publishing with `./release.sh <version>`. CI workflows run on JDK 17, but project source/target compatibility remains Java 11 as configured in `pom.xml`.

## Coding Style & Naming Conventions
Target Java 11 and UTF-8 encoding as defined in `pom.xml`. Stick to 4-space indentation, braces on the same line, UpperCamelCase for types, lowerCamelCase for members, and UPPER_SNAKE for constants. Use the repository formatter rules in `misc/eclipse/eclipse-code-formatting-rules.xml` for consistency. Mutable Graph models should remain package-private helpers; exported DTOs favor Lombok builders or value annotations—always edit the `src/main/lombok` versions so the delomboked copies stay current. Never edit generated delombok output in `target/generated-sources/delombok` directly. Mirror Graph API field names with `@JsonProperty` whenever casing diverges.

## Testing Guidelines
JUnit 5, Mockito, and AssertJ are available out of the box, while JaCoCo already hooks into the lifecycle to capture coverage. Unit tests follow `<ClassName>Test` naming and should assert both success and failure paths. Integration tests in `com.restfb.integration` require copying `src/test/resources/integration-test.sample.properties` to `integration-test.properties`, filling app/user/page tokens, and optionally setting `writeToFacebook=true`; without that file they self-skip via JUnit assumptions. Treat `integration-test.properties` as local secret material and never commit it.

## Graph API Version Updates
When Meta ships a new Graph API version, update `src/main/java/com/restfb/Version.java` and aligned docs in one change set. Add the new `VERSION_x_y` enum entry with the correct `@since` date and availability comment, update `LATEST` to the same `vX.Y` value, and update `CHANGELOG.md` for public release notes.

## Commit & Pull Request Guidelines
Use Conventional Commits for all commit messages (`type(scope): subject`), with imperative subjects and optional scopes. Common types in this repository include `build`, `fix`, `feat`, `doc`, `test`, and `refactor`. Run `mvn spotless:apply` before pushing changes, then verify formatting with `mvn spotless:check`. Pull requests should explain intent, list verification steps (`mvn test`, screenshots, manual Graph calls), and link issues. Update `CHANGELOG.md` and docs whenever public types or API mappings change, and consult `SECURITY.md` for any vulnerability disclosures to avoid leaking credentials or tokens.
