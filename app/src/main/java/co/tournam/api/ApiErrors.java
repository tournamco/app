package co.tournam.api;

import java.util.HashMap;
import java.util.Map;

public enum ApiErrors {

    NOT_WHEN_LOGGED_IN(0),
    NOT_LOGGED_IN(1),
    ALREADY_USED_EMAIL(2),
    ALREADY_USED_USERNAME(3),
    INCORRECT_EMAIL_PASSWORD(4),
    MISSING(5),
    INCORRECT_OPTION_VALUES(6),
    INCORRECT_TOKEN(7),
    FULL_TEAM(8),
    FULL_TOURNAMENT(9),
    ALREADY_IN_TOURNAMENT(10),
    ALREADY_IN_TOURNAMENT_LEADER(11),
    INCORRECT_FILE_TYPE(12),
    PROOF_ALREADY_SET(13),
    INCORRECT_SCORES(14),
    ALREADY_USED_NAME(15),
    ALREADY_FINISHED(16),
    STAGE_NOT_FOUND(17),
    ROUND_NOT_FOUND(18),
    UNAUTHORIZED(19),
    NOT_FOUND(20),
    INTERNAL_SERVER_ERROR(21);

    private static final Map<Integer, ApiErrors> BY_INDEX = new HashMap<>();

    static {
        for (ApiErrors e : values()) {
            BY_INDEX.put(e.index, e);
        }
    }

    private final int index;

    private ApiErrors(int index) {
        this.index = index;
    }

    public static ApiErrors valueOfIndex(int index) {
        return BY_INDEX.get(index);
    }
}
