package org.rojojun.levelupserver.adapter.out.dto.enums;

public enum UserLevel {
    NOVICE,
    ARMATURE,
    SEMI_PRO,
    PRO;

    public static UserLevel calculate(int score) {
        if (80 <= score) {
            return PRO;
        } else if (70 <= score) {
            return SEMI_PRO;
        } else if (50 <= score) {
            return ARMATURE;
        } else {
            return NOVICE;
        }
    }
}
