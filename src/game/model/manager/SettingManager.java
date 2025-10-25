package game.model.manager;

public class SettingManager {
    public enum ControlType {
        KEYBOARD,
        MOUSE
    }
    private static ControlType currentControlType = ControlType.KEYBOARD;
    public static ControlType getControlType() {
        return currentControlType;
    }
    public static void setControlType(ControlType controlType) {
        currentControlType = controlType;
    }
}
