# Functional Specification Document (FSD)

## 1. Authentication Flow
**User Story**: As a user, I want to sign in via biometric authentication so I can access my account quickly.
- **Pre-condition**: App installed, first launch.
- **Flow**: Splash -> Landing -> Biometric Prompt (if registered) OR Login Options -> Home.
- **Edge Cases**: Biometric failure triggers PIN fallback. Network timeout triggers retry snackbar.

## 2. Browsing & Discovery
**User Story**: As a shopper, I want to filter items by specific luxury brands.
- **Flow**: Home -> Tap Filter Chip -> Select "Gucci", "Prada" -> Apply -> Feed refreshes.
- **Validation**: If no items match, show "No Results" state with similar recommendations.

## 3. Cart & Checkout
**User Story**: As a shopper, I want to see the total cost including shipping before paying.
- **Flow**: PDP -> Add to Cart -> Cart Screen (Auto-calc Tax/Shipping) -> Proceed to Checkout -> Payment Sheet -> Success.
- **Logic**: Stock reservation lasts 15 minutes. Timer displayed in Cart.

## 4. Acceptance Criteria (Global)
- All UI interactions must provide haptic feedback.
- Use Skeleton Loaders for all async data fetching.
- Error states must include a "Retry" action.