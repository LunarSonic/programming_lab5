package LunarSonic.objects;

public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;

    /**
     * @return строка со всеми элементами Enum'а
     */
    public static String organizationNameList() {
        StringBuilder organizationNames = new StringBuilder();
        for (OrganizationType type: OrganizationType.values()) {
            organizationNames.append(type.name()).append(", ");
        }
        return organizationNames.substring(0, organizationNames.length() - 2);
    }
}
