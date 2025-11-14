# Demo Game
- [Demo ở đây!!!](https://drive.google.com/drive/folders/1v3eJjU0s-r_G_GgHz-F1KymjGxDPtneS?usp=drive_link)
# 🎮 Arkanoid - Project Game (Lập Trình Hướng Đối Tượng) 

Game được xây dựng và phát triển bằng ngôn ngữ **java**, nhằm mục đích để áp dụng các nguyên tắc cơ bản của OOP
như đóng gói, kết thừa, đa hình,...Giúp phát triển kỹ năng phân tích, giải quyết vấn đề, rèn luyện kĩ năng làm việc nhóm và 
quản lý dự án phần mềm.

---
## 🔎 Thông Tin Nhóm
### - Nhóm 5
### -Thành Viên
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
│   │       │   └── GameController.java
│   │       ├── model
│   │       │   ├── entity
│   │       │   │   ├── Ball.java
│   │       │   │   ├── Brick.java
│   │       │   │   ├── Bullet.java
│   │       │   │   ├── GameObject.java
│   │       │   │   ├── Level.java
│   │       │   │   ├── MovableObject.java
│   │       │   │   ├── Paddle.java
│   │       │   │   └── Particle.java
│   │       │   ├── manager
│   │       │   │   ├── CollisionManager.java
│   │       │   │   ├── FontManager.java
│   │       │   │   ├── GameStateManager.java
│   │       │   │   ├── GraphicsManager.java
│   │       │   │   ├── HighScoreManager.java
│   │       │   │   ├── LevelManager.java
│   │       │   │   ├── PowerUpManager.java
│   │       │   │   ├── ScoreSystem.java
│   │       │   │   ├── SettingManager.java
│   │       │   │   └── TileManager.java
│   │       │   ├── powerups
│   │       │   │   ├── ActivePowerUp.java
│   │       │   │   ├── ExtendPaddle.java
│   │       │   │   ├── FireBall.java
│   │       │   │   ├── Invert.java
│   │       │   │   ├── MultiBall.java
│   │       │   │   ├── PaddleWithGun.java
│   │       │   │   ├── PowerUp.java
│   │       │   │   └── PowerUpFactory.java
│   │       │   └── GameModel.java
│   │       ├── sound
│   │       │   ├── Sound.java
│   │       │   └── SoundManager.java
│   │       ├── view
│   │       │   ├── screens
│   │       │   │   ├── DifficultyScreen.java
│   │       │   │   ├── GameScreen.java
│   │       │   │   ├── HightScoreScreen.java
│   │       │   │   ├── MenuScreen.java
│   │       │   │   ├── OverScreen.java
│   │       │   │   ├── PauseScreen.java
│   │       │   │   ├── Screen.java
│   │       │   │   ├── ScreenManager.java
│   │       │   │   ├── SettingScreen.java
│   │       │   │   ├── TutorialScreen.java
│   │       │   │   └── WinnerScreen.java
│   │       │   ├── UI
│   │       │   │   ├── Clickable.java
│   │       │   │   ├── HudElements.java
│   │       │   │   ├── UIButton.java
│   │       │   │   ├── UIElement.java
│   │       │   │   ├── UILabel.java
│   │       │   │   └── UIManager.java
│   │       │   └── GameView.java
│   │       ├── Constants.java
│   │       └── Main.java
│   │ 
│   └── resources
│       ├── bg
│       ├── fonts
│       ├── map
│       ├── sounds
│       └── sprites
└── test
    └── java
        └── CollisionManagerTest.java