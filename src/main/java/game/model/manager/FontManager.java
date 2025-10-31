package game.model.manager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static final Map<String, Font> fonts = new HashMap<>();

    private FontManager() {}

    public static void loadAll() {
        registerFont("Tektur Bold", "/fonts/Tektur-Bold.ttf");
        registerFont("Tektur Regular", "/fonts/Tektur-Regular.ttf");
        registerFont("Tektur Black",  "/fonts/Tektur-Black.ttf");
        registerFont("Tektur SemiBold",  "/fonts/Tektur-SemiBold.ttf");
    }

    private static void registerFont(String fontName, String path) {
        try (InputStream is = FontManager.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Font file not found at: " + path);
                return;
            }

            //tạo font từ file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            fonts.put(fontName, customFont);
            System.out.println("Successfully registered font: " + fontName);

        } catch (IOException | FontFormatException e) {
            System.err.println("Failed to load or register font from path: " + path);
            e.printStackTrace();
        }
    }

// lấy font đã đăng kí
    public static Font getFont(String name, float size) {
        Font baseFont = fonts.get(name);
        if (baseFont != null) {
            // deriveFont cho phép tạo một phiên bản mới của font với kích thước khác
            return baseFont.deriveFont(size);
        } else {
            // Fallback an toàn: nếu không tìm thấy font, dùng font Arial mặc định
            System.err.println("Font '" + name + "' not found. Falling back to Arial.");
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }
}
