# 🎮 Arkanoid - Project Game (Lập Trình Hướng Đối Tượng) 

Game được xây dựng và phát triển bằng ngôn ngữ **java**, nhằm mục đích cho sinh viên áp dụng các nguyên tắc cơ bản của OOP
như đóng gói, kết thừa, đa hình,...Giúp phát triển kỹ năng phân tích, giải quyết vấn đề, rèn luyện kĩ năng làm việc nhóm và 
quản lý dự án phần mềm.

---
## 🔎 Thông Tin Nhóm
...

---

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
│   │
│   ├── view/                      # Hiển thị
│   │   ├── GamePanel.java         # Vẽ các đối tượng game
│   │   └── GameView.java          # Cửa sổ chính của game
│   │
│   ├── Constants.java             # Lưu các hằng số của game
│   └── Main.java                  # File main để chạy game
│
│
└── resources/