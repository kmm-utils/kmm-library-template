# Kotlin Multiplatform Library Template

Template to create Kotlin Multiplatform Library

## How to use it

1. [ ] On `settings.gradle.kts` change `rootProject.name` to your project name.
2. [ ] On `build.gradle.kts` change the app namespaces/identifiers
3. [ ] On the share project, under
   the `src/commonMain`, `src/commonTest`, `src/iosMain`, `src/iosTest`, `androidMain`
   and `androidUnitTest` folders, change the namespace to the same one you have set on the step
   above. (right-click the item, choose `Refactoring` and then `Move`, choosing to move
   to `To Directory`)
4. [ ] Check if the package definition of each file matches the updated namespaces and directories
5. [ ] Change the `Bundle Identifier` on the Xcode projects to your desired value
