# Assumptions & Tech Decisions

## 1. Missing Visual Context
- Assumed standard bottom navigation pattern as it is industry standard for e-commerce.
- Assumed "Dark Mode" support is required given the luxury aesthetic trend (OLED black).

## 2. Third-Party Libraries
- **Stripe SDK**: Chosen for payment processing due to robust UI elements.
- **Lottie**: For onboarding animations to reduce APK size vs MP4.
- **Timber**: Logging (stripped in release).
- **Firebase**: Push Notifications (FCM) and Crashlytics are standard requirements.

## 3. Offline Strategy
- The app will implement a **"Read-only Offline Mode"**.
- Home feed results are cached in Room for 24 hours.
- Cart actions require internet connection; optimistic UI updates will check connectivity before committing changes.