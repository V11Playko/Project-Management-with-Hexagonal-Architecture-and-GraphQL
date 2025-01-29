package com.playko.projectManagement.shared.constants;

public class RolesId {

    private RolesId() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long MANAGER_ROLE_ID = 2L;
    public static final Long CONTRIBUTOR_ROLE_ID = 3L;
    public static final Long OBSERVER_ROLE_ID = 4L;
    public static final Long USER_ROLE_ID = 5L;
}
