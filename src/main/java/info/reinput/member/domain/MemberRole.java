package info.reinput.member.domain;

public enum MemberRole {
    SUPER, ADMIN, MANAGER, USER;

    public String getRole() {
        return "ROLE_" + this.name();
    }
}
