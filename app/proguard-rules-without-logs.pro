# Удаление вызовов методов логирования Android
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}

# Удаление вызовов print и println
-assumenosideeffects class java.io.PrintStream {
    public void print(...);
    public void println(...);
}
