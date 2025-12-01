# Test Plan & Strategy

## 1. Testing Pyramid
- **Unit Tests (70%)**: Business logic, UseCases, Mappers. Uses `JUnit5` + `Mockk`.
- **Integration Tests (20%)**: Repository + Room/Retrofit interactions. Uses `Turbine` for Flow testing.
- **UI Tests (10%)**: Critical user flows (Login, Checkout). Uses `Compose UI Test`.

## 2. CI/CD Pipeline
- **Pre-Merge**: Lint (Ktlint), Detekt, Unit Tests.
- **Nightly**: UI Tests on Firebase Test Lab.
- **Release**: Build AAB -> Upload to Internal Sharing.

## 3. Example Test Case (ViewModel)
```kotlin
@Test
fun `when AddToCart event received, then UseCase is invoked`() = runTest {
    // Given
    val useCase = mockk<AddToCartUseCase>(relaxed = true)
    val viewModel = PdpViewModel(useCase)

    // When
    viewModel.handleEvent(PdpUiEvent.AddToCart("123"))

    // Then
    coVerify { useCase.execute("123") }
    assertTrue(viewModel.uiState.value.isLoading)
}
```