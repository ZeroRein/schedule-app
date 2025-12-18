import java.util.Objects;

/**
 * Chronos Logic: 衝突判定エンジン (Java版)
 * タイムゾーンに依存しない「0時からの経過分」ベースの計算を行います。
 */
public class ConflictChecker {

    private final int bufferMinutes;

    public ConflictChecker(int bufferMinutes) {
        this.bufferMinutes = bufferMinutes;
    }

    /**
     * 2つの予定が衝突しているか判定します。
     * 判定式: !(終了A + Buffer <= 開始B || 終了B + Buffer <= 開始A)
     * * @param sA タスクA 開始分
     * @param eA タスクA 終了分
     * @param sB タスクB 開始分
     * @param eB タスクB 終了分
     * @return 衝突している場合は true
     */
    public boolean hasConflict(int sA, int eA, int sB, int eB) {
        // Aの後にBが来るケース、またはBの後にAが来るケースのどちらでもない（＝重なっている）
        return !(eA + bufferMinutes <= sB || eB + bufferMinutes <= sA);
    }

    /**
     * HH:mm 形式の文字列を 0時からの経過分に変換します。
     */
    public static int toMinutes(String timeStr) {
        Objects.requireNonNull(timeStr);
        String[] parts = timeStr.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    // 簡易ユニットテスト・ランナー
    public static void main(String[] args) {
        ConflictChecker checker = new ConflictChecker(15);
        int successCount = 0;
        int failCount = 0;

        // テストケース1: 完全に離れている (OK)
        if (!checker.hasConflict(540, 600, 630, 690)) successCount++; else failCount++;

        // テストケース2: Buffer(15分)以内の接近 (NG)
        // A: 9:00-10:00 (540-600), B: 10:10-11:00 (610-660)
        if (checker.hasConflict(540, 600, 610, 660)) successCount++; else failCount++;

        // テストケース3: 完全に重なっている (NG)
        if (checker.hasConflict(540, 600, 550, 590)) successCount++; else failCount++;

        // テストケース4: 前後の入れ替え (NG)
        if (checker.hasConflict(610, 660, 540, 600)) successCount++; else failCount++;

        System.out.println("Java Unit Test Results:");
        System.out.println("Success: " + successCount);
        System.out.println("Failed: " + failCount);

        if (failCount > 0) System.exit(1);
    }
}