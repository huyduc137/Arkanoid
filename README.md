# 🎮 Arkanoid - Project Game (Lập Trình Hướng Đối Tượng) 

Game được xây dựng và phát triển bằng ngôn ngữ **java**, nhằm mục đích để áp dụng các nguyên tắc cơ bản của OOP
như đóng gói, kết thừa, đa hình,...Giúp phát triển kỹ năng phân tích, giải quyết vấn đề, rèn luyện kĩ năng làm việc nhóm và 
quản lý dự án phần mềm.

---
## 🔎 Thông Tin Nhóm

- Nguyễn Đức Huy
- Phạm Tất Đạt
- Nguyễn Doãn Bảo Long
- Phan Văn Đức


---

## 📂 Cấu trúc thư mục

```bash
src
├── main
│   ├── java
│   │   └── game
│   │       ├── controller
│   │       │   └── GameController
│   │       ├── model
│   │       │   ├── entity
│   │       │   │   ├── Ball
│   │       │   │   ├── Brick
│   │       │   │   ├── Bullet
│   │       │   │   ├── GameObject
│   │       │   │   ├── Level
│   │       │   │   ├── MovableObject
│   │       │   │   ├── Paddle
│   │       │   │   └── Particle
│   │       │   ├── manager
│   │       │   │   ├── CollisionManager
│   │       │   │   ├── FontManager
│   │       │   │   ├── GameStateManager
│   │       │   │   ├── GraphicsManager
│   │       │   │   ├── HighScoreManager
│   │       │   │   ├── LevelManager
│   │       │   │   ├── PowerUpManager
│   │       │   │   ├── ScoreSystem
│   │       │   │   ├── SettingManager
│   │       │   │   └── TileManager
│   │       │   ├── powerups
│   │       │   │   ├── ActivePowerUp
│   │       │   │   ├── ExtendPaddle
│   │       │   │   ├── FireBall
│   │       │   │   ├── Invert
│   │       │   │   ├── MultiBall
│   │       │   │   ├── PaddleWithGun
│   │       │   │   ├── PowerUp
│   │       │   │   └── PowerUpFactory
│   │       │   └── GameModel
│   │       ├── sound
│   │       │   ├── Sound
│   │       │   └── SoundManager
│   │       ├── view
│   │       │   ├── screens
│   │       │   │   ├── DifficultyScreen
│   │       │   │   ├── GameScreen
│   │       │   │   ├── HightScoreScreen
│   │       │   │   ├── MenuScreen
│   │       │   │   ├── OverScreen
│   │       │   │   ├── PauseScreen
│   │       │   │   ├── Screen
│   │       │   │   ├── ScreenManager
│   │       │   │   ├── SettingScreen
│   │       │   │   ├── TutorialScreen
│   │       │   │   └── WinnerScreen
│   │       │   ├── UI
│   │       │   │   ├── Clickable
│   │       │   │   ├── HudElements
│   │       │   │   ├── UIButton
│   │       │   │   ├── UIElement
│   │       │   │   ├── UILabel
│   │       │   │   └── UIManager
│   │       │   └── GameView
│   │       ├── Constants   
│   │       └── Main
│   │ 
│   └── resources
│       ├── bg
│       ├── fonts
│       ├── map
│       ├── sounds
│       └── sprites
└── test
    └── java
        └── CollisionManagerTest