### Architecture Documentation

#### Clean Architecture Layers
1. **Presentation Layer:** Compose UI components, ViewModel with StateFlow.
2. **Domain Layer:** Use cases, business logic, Hilt for DI.
3. **Data Layer:** Repositories, data sources (local/remote), Room DB integration.
4. **Android Framework Layer:** Activity/Fragment, navigation, lifecycle management.

#### Module Structure
- **core:** Domain logic, data models.
- **ui:** Compose screens, state management.
- **data:** API interactions, local storage.
- **presentation:** ViewModels, adapters.

#### Dependency Injection with Hilt
- Modules for App, ViewModel, Repository dependencies.
- Component graph defined in hilt.gradle.
- DI annotations used for scoped dependencies.

#### Compose Theming
- Material 3 design system implemented.
- Dynamic themes based on user preferences.
- Custom components styled to match the theme.