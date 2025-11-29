# Keno Game 🎰

## Overview

A modern, interactive Keno game built with Java and JavaFX, featuring smooth animations, sound effects, and an intuitive user interface. Players can select between 1, 4, 8, or 10 spots, place bets, and watch as random numbers are drawn with engaging visual feedback. The game includes prize tables based on traditional Keno odds and a comprehensive betting system.

## Wireframes
<img width="2909" height="1585" alt="image" src="https://github.com/user-attachments/assets/813dd8af-c4f8-450a-b790-976d136e7cc0" />

## Application frames
<img width="600" height="600" alt="image" src="https://github.com/user-attachments/assets/071e6573-8354-4d42-bb94-df6ba3be47fc" />
<img width="1000" height="810" alt="image" src="https://github.com/user-attachments/assets/557cca92-5024-41fc-ae00-e95f809c8374" />
<img width="1000" height="810" alt="image" src="https://github.com/user-attachments/assets/9dc64d94-acaf-4ab7-9155-46e07c20e875" />

## Project Demo

<iframe width="560" height="315" src="https://youtu.be/J3K13KbvdWc" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

## 🎮 Features

- **Multiple Game Modes**: Support for 1, 4, 8, and 10-spot gameplay
- **Interactive UI**: Clean, modern interface with visual feedback
- **Smooth Animations**: Rolling number animations and visual effects
- **Audio Integration**: Sound effects for various game events
- **Prize System**: Authentic Keno prize tables with multipliers
- **Game History**: Track your wins, losses, and drawing history
- **Responsive Design**: Adaptive layout with custom-styled components

## 🛠️ Tech Stack

### Core Technologies
- ☕ **Java** - Primary programming language
- 🎨 **JavaFX** - UI framework for rich desktop applications
- 🔧 **Maven** - Dependency management and build automation

### Architecture & Patterns
- 🏗️ **MVC Pattern** - Model-View-Controller architecture
- 🎯 **Service Layer** - Separation of business logic
- 🔄 **Observer Pattern** - Event-driven state management
- 🏭 **Factory Pattern** - Component creation (MenuFactory, ButtonBuilder)
- 🎨 **Builder Pattern** - UI layout construction (LayoutBuilder, ButtonBuilder)
- 📦 **Singleton Pattern** - Service management (AudioService, AnimationService)

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── Main.java                    # Application entry point
│   │   ├── Controller/
│   │   │   └── GameController.java      # Game logic coordinator
│   │   ├── Model/
│   │   │   ├── GameDrawings.java        # Drawing results data
│   │   │   ├── GameHistory.java         # Game history tracking
│   │   │   ├── GameMode.java            # Game mode enumeration
│   │   │   ├── GameState.java           # Game state management
│   │   │   └── PrizeTable.java          # Prize calculation logic
│   │   ├── Service/
│   │   │   ├── AnimationService.java    # Animation handling
│   │   │   ├── AudioService.java        # Sound effects
│   │   │   └── GameService.java         # Core game logic
│   │   ├── Utils/
│   │   │   ├── ButtonStyles.java        # Button styling utilities
│   │   │   ├── MenuCallback.java        # Menu event interface
│   │   │   └── ThemeStyles.java         # Application theming
│   │   └── View/
│   │       ├── GameView.java            # Main game interface
│   │       ├── InfoWindow.java          # Information display
│   │       ├── WelcomeView.java         # Welcome screen
│   │       └── Component/               # Reusable UI components
│   │           ├── ButtonBuilder.java
│   │           ├── ControlButton.java
│   │           ├── LayoutBuilder.java
│   │           ├── MenuFactory.java
│   │           ├── NumberButton.java
│   │           ├── PrizeItemBox.java
│   │           ├── SelectableButton.java
│   │           ├── StatefulButton.java
│   │           └── StyledButton.java
│   └── resources/
│       ├── icons/                       # Visual assets
│       │   ├── coins.gif
│       │   ├── slot.gif
│       │   └── start.gif
│       └── sound/                       # Audio files
│           ├── button.wav
│           ├── clear.wav
│           ├── finish.wav
│           ├── jackpot.wav
│           ├── match.wav
│           ├── mode.wav
│           └── start.wav
└── test/
    └── java/
        └── MyTest.java                  # Unit tests
```

## 🎯 Design Patterns Implementation

### 1. **MVC (Model-View-Controller)**
- **Model**: `GameState`, `GameDrawings`, `GameHistory`, `PrizeTable`
- **View**: `GameView`, `WelcomeView`, `InfoWindow`, UI Components
- **Controller**: `GameController` coordinates between Model and View

### 2. **Service Layer Pattern**
- `GameService`: Core game logic and rules
- `AnimationService`: Centralized animation management
- `AudioService`: Sound effect handling

### 3. **Factory Pattern**
- `MenuFactory`: Creates standardized menu components
- `ButtonBuilder`: Flexible button creation with method chaining

### 4. **Builder Pattern**
- `LayoutBuilder`: Constructs complex UI layouts
- `ButtonBuilder`: Configurable button construction

### 5. **Strategy Pattern**
- Different game modes (1, 4, 8, 10 spots) with varying prize structures

## 🎲 Game Mechanics

### Prize Table Structure
- **1 Spot**: Match 1 → 2x multiplier (Odds: 4.00)
- **4 Spots**: Match 2→1x, 3→5x, 4→75x (Odds: 3.86)
- **8 Spots**: Match 4→2x, 5→12x, 6→50x, 7→750x, 8→10,000x (Odds: 9.77)
- **10 Spots**: Match 0→5x, 5→2x, 6→15x, 7→40x, 8→450x, 9→4,250x, 10→100,000x (Odds: 9.05)

### Gameplay Flow
1. Select game mode (number of spots)
2. Choose your numbers
3. Place bet
4. Watch animated drawing
5. Calculate winnings based on matches
6. Review history and statistics

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- Maven 3.6+
- JavaFX SDK

### Installation & Running

```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd cliu1051Project2

# Build with Maven
mvn clean install

# Run the application
mvn javafx:run
```

## 🎨 UI/UX Highlights

- **Custom Button Components**: Selectable, stateful, and styled buttons for enhanced interaction
- **Animation System**: Smooth rolling effects for number drawing
- **Audio Feedback**: Contextual sound effects for user actions
- **Theme Consistency**: Centralized styling through `ThemeStyles` and `ButtonStyles`
- **Responsive Layouts**: Built with `LayoutBuilder` for adaptive interfaces

## 📊 Development Timeline

### Phase 1: Foundation 🏗️
- Set up project structure with Maven
- Implemented MVC architecture
- Created base model classes (`GameState`, `PrizeTable`)

### Phase 2: Core Logic 🎯
- Developed `GameService` with betting and drawing logic
- Implemented prize calculation system
- Added game mode variations

### Phase 3: UI Components 🎨
- Built reusable button components
- Created layout builders
- Implemented welcome and game views

### Phase 4: Enhancement ✨
- Added animation system
- Integrated audio service
- Implemented game history tracking

### Phase 5: Polish 💎
- Refined UI/UX interactions
- Added visual feedback
- Optimized animations and performance

## 🧪 Testing

```bash
# Run tests
mvn test
```

Test coverage includes unit tests for game logic, prize calculations, and state management.

## 📝 License

This project is part of CS342 coursework at UIC.

## 👤 Author

**Chao Liu (cliu1051)**  
University of Illinois Chicago  
CS342 - Fall 2025

---

*Built with ☕ and JavaFX*
