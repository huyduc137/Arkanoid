## 📂 Cấu trúc thư mục

```bash
src/
├── game/
│   ├── controller/                # điều khiển
│   │   └── GameController.java    # Xử lý input từ người chơi
│   │
│   │
│   ├── model/                     # Logic game
│   │   ├── Ball.java              # Lớp quả bóng
│   │   ├── Brick.java             # Lớp viên gạch
│   │   ├── GameModel.java         # Quản lý trạng thái và logic game
│   │   ├── GameObject.java        # Lớp cơ sở
│   │   ├── MovalbleObject.java    # Lớp cơ sở cho đối tượng di chuyển
│   │   └── Paddle.java            # Lớp thanh đỡ
│   │
    │
│   ├── view/                      # Hệ thống ghi nhớ hành động
│   │   ├── GamePanel.java         # Vẽ các đối tượng game
│   │   └── GameView.java          # Cửa sổ chính của game
│   │
│   ├── Constants.java             # Lưu các hằng số của game
│   └── Main.java                  # File main để chạy game
│
│
└── resources/