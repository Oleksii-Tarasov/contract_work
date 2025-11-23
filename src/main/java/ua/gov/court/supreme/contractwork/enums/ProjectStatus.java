package ua.gov.court.supreme.contractwork.enums;

public enum ProjectStatus {
    NEW(0, "-- не розпочато --"),
    IN_PROGRESS(1, "В роботі"),
    COMPLETED(2, "Завершена"),
    CANCELLED(3, "Не відбулася");

    private final int dbValue;
    private final String displayName;

    ProjectStatus(int dbValue, String displayName) {
        this.dbValue = dbValue;
        this.displayName = displayName;
    }

    public int getDbValue() {
        return dbValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCssClass() {
        return switch (this) {
            case NEW -> "status-new";
            case IN_PROGRESS -> "status-in-progress";
            case COMPLETED -> "status-completed";
            case CANCELLED -> "status-cancelled";
        };
    }

    public static ProjectStatus fromInt(int value) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.dbValue == value) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
