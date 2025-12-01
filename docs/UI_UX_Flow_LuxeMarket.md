# UI/UX & Flow Map

## 1. Screen Inventory 

### A. Onboarding
- **Splash**: Animated logo (Lottie).
- **Welcome**: Carousel of value props with "Get Started" button.
- **Auth**: Email input, Social buttons, Biometric toggle.

### B. Main Tabs
1.  **Home (Feed)**
    - **Layout**: `LazyVerticalGrid` (Adaptive).
    - **Components**: Hero Banner, Horizontal Scroll "New Arrivals", Vertical Grid "Just For You".
    - **Top Bar**: Search Icon, Notification Bell.
2.  **Search/Explore**
    - **Layout**: Category Pills (`FlowRow`), Trending Search List.
3.  **Sell (FAB)**
    - **Layout**: Center docked Floating Action Button launches Camera flow.
4.  **Cart**
    - **Layout**: `LazyColumn` of CartItems, Sticky Bottom Bar for Checkout Summary.
5.  **Profile**
    - **Layout**: Avatar toggle, Grid of "My Listings", List of "Orders".

## 2. Navigation Map
`NavHost` (Start: OnboardingGraph)
 -> OnAuthSuccess -> MainGraph (BottomNav)

**MainGraph Routes**:
- `Home` -> `ProductDetails/{id}` -> `Checkout`
- `Search` -> `SearchResults/{query}` -> `ProductDetails/{id}`
- `Profile` -> `Settings` -> `WebView (Legals)`

## 3. Design Tokens (Material 3)
- **Typography**: Display/Headline (Serif - 'Playfair Display'), Body/Label (Sans - 'Inter').
- **Color Palette**:
    - `primary`: Charcoal (#121212)
    - `secondary`: Gold (#D4AF37)
    - `surface`: Off-White (#FAFAFA)
- **Shapes**: Minimal corner radius (4dp).