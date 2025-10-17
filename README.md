# 💰 Expense Tracker - Beautiful Android App

A stunning, modern expense tracking app with animated bubbles, gradient themes, and smooth UI interactions.

## ✨ Features

- 🎨 **Beautiful Live Bubble Background** - Animated floating bubbles for immersive experience
- 🌈 **Gradient Color Theme** - Purple to blue gradient design
- 💾 **Room Database** - Local data persistence
- 📊 **Real-time Balance** - Track income, expenses, and balance
- ✏️ **CRUD Operations** - Create, Read, Update, Delete transactions
- 🎭 **Material Design** - Modern UI with Material Components
- 🎬 **Smooth Animations** - Scale-in animations for list items
- 📱 **Intuitive Interface** - Easy to use with floating action button

## 🎨 Color Palette

- **Primary Purple**: `#6C63FF`
- **Accent Blue**: `#00D4FF`
- **Income Green**: `#00E676`
- **Expense Red**: `#FF5252`
- **Dark Background**: `#0F0F1E` to `#1F1F3A` gradient

## 📁 Project Structure

```
app/
├── src/main/java/com/expensetracker/app/
│   ├── database/
│   │   ├── Transaction.java          # Entity class
│   │   ├── TransactionDao.java       # Database operations
│   │   └── AppDatabase.java          # Room database instance
│   ├── adapter/
│   │   └── TransactionAdapter.java   # RecyclerView adapter
│   ├── views/
│   │   └── BubbleView.java           # Animated bubble background
│   ├── MainActivity.java              # Main screen
│   └── AddTransactionActivity.java   # Add/Edit screen
├── src/main/res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_add_transaction.xml
│   │   └── item_transaction.xml
│   ├── drawable/
│   │   ├── gradient_background.xml
│   │   ├── card_gradient.xml
│   │   ├── income_card_bg.xml
│   │   ├── expense_card_bg.xml
│   │   ├── button_gradient.xml
│   │   └── ... (other drawables)
│   ├── anim/
│   │   └── scale_in.xml
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   └── themes.xml
│   └── AndroidManifest.xml
└── build.gradle
```

## 🚀 Setup Instructions

### 1. Create New Android Project

1. Open **Android Studio**
2. Click **"New Project"**
3. Select **"Empty Activity"**
4. Name: `Expense Tracker`
5. Package: `com.expensetracker.app`
6. Language: **Java**
7. Minimum SDK: **API 24 (Android 7.0)**

### 2. Configure Gradle Files

#### Replace `build.gradle (Project Level)`:
```gradle
// Add maven { url 'https://jitpack.io' } to repositories
```

#### Replace `build.gradle (Module: app)`:
```gradle
// Add Room, Material Design, and Chart dependencies
```

### 3. Add All XML Files

#### Create Drawable Resources (in `res/drawable/`):
- `gradient_background.xml`
- `card_gradient.xml`
- `income_card_bg.xml`
- `expense_card_bg.xml`
- `button_gradient.xml`
- `button_circle.xml`
- `spinner_background.xml`
- `category_badge.xml`

#### Create Animation Resource (in `res/anim/`):
- `scale_in.xml`

#### Add Layout Files (in `res/layout/`):
- `activity_main.xml`
- `activity_add_transaction.xml`
- `item_transaction.xml`

#### Update Values (in `res/values/`):
- `colors.xml`
- `strings.xml`
- `themes.xml`

### 4. Create Java Classes

#### Create Package Structure:
```
com.expensetracker.app/
├── database/
├── adapter/
└── views/
```

#### Add Java Files:
1. **Database Package**:
   - `Transaction.java`
   - `TransactionDao.java`
   - `AppDatabase.java`

2. **Views Package**:
   - `BubbleView.java`

3. **Adapter Package**:
   - `TransactionAdapter.java`

4. **Root Package**:
   - `MainActivity.java`
   - `AddTransactionActivity.java`

### 5. Update AndroidManifest.xml

Replace the manifest file with the provided one.

### 6. Sync and Build

1. Click **"Sync Project with Gradle Files"**
2. Wait for dependencies to download
3. Click **"Build" > "Make Project"**
4. Fix any import errors (Alt+Enter)

### 7. Run the App

1. Connect Android device or start emulator
2. Click **"Run"** (Shift+F10)
3. Enjoy your beautiful expense tracker! 🎉

## 🎯 Usage

### Adding a Transaction
1. Click the **Floating Action Button** (+)
2. Select **Income** or **Expense**
3. Enter title, amount, category, and optional note
4. Pick a date
5. Click **"Save Transaction"**

### Editing a Transaction
1. Click the **Edit** button on any transaction card
2. Modify the details
3. Click **"Update Transaction"**

### Deleting a Transaction
1. Click the **Delete** button on any transaction card
2. Confirm deletion

### View Summary
- **Total Balance**: Income - Expense
- **Total Income**: Sum of all income transactions
- **Total Expense**: Sum of all expense transactions

## 🎨 Customization

### Change Color Theme
Edit `colors.xml`:
```xml
<color name="purple_primary">#YOUR_COLOR</color>
<color name="blue_accent">#YOUR_COLOR</color>
```

### Modify Bubble Effects
Edit `BubbleView.java`:
```java
// Change bubble count
for (int i = 0; i < 20; i++) { // Increase for more bubbles
```

### Add More Categories
Edit `AddTransactionActivity.java`:
```java
String[] categories = {"Food", "YourCategory", ...};
```

## 📱 Screenshots Features

- **Dark Theme** with gradient backgrounds
- **Live Animated Bubbles** in background
- **Glassmorphism Cards** for transactions
- **Color-coded** income (green) and expense (red)
- **Smooth Animations** on list items
- **Material Design** components throughout

## 🛠️ Technologies Used

- **Java** - Programming language
- **Android SDK** - Framework
- **Room Database** - Local data persistence
- **Material Components** - UI library
- **RecyclerView** - List display
- **CardView** - Card-based UI
- **Custom Views** - Animated bubbles

## 📝 Database Schema

### Transaction Table
| Column   | Type   | Description          |
|----------|--------|----------------------|
| id       | int    | Primary key (auto)   |
| title    | String | Transaction title    |
| amount   | double | Transaction amount   |
| type     | String | "income" or "expense"|
| category | String | Transaction category |
| date     | String | Transaction date     |
| note     | String | Optional note        |

## 🐛 Troubleshooting

### Error: Cannot resolve symbol 'R'
- Solution: Click **"File" > "Invalidate Caches / Restart"**

### Room Database errors
- Solution: Make sure `annotationProcessor` is in `build.gradle`

### Bubble animation not showing
- Solution: Check if `BubbleView` class is in correct package

### UI elements not displaying colors
- Solution: Verify all drawable XML files are in `res/drawable/`

## 🚀 Future Enhancements

- [ ] Export transactions to CSV/PDF
- [ ] Charts and analytics
- [ ] Budget planning
- [ ] Multiple accounts support
- [ ] Cloud backup
- [ ] Dark/Light theme toggle
- [ ] Biometric authentication
- [ ] Receipt scanning

## 📄 License

This project is open source and available for educational purposes.

## 👨‍💻 Author

Created with ❤️ for learning Android development

---

**Enjoy your beautiful expense tracker! 🎨💰**
