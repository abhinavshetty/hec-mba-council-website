export class UserRoles {
    public static CLUB_ACCESS = 'CLUB_USER';
    public static ADMIN_ACCESS = 'COUNCIL_APPROVER';
    public static STUDENT_ACCESS = 'STUDENT';

    public static USER_ROLES = [
        {name: UserRoles.CLUB_ACCESS, value: UserRoles.CLUB_ACCESS}, 
        {name: UserRoles.ADMIN_ACCESS, value: UserRoles.ADMIN_ACCESS}, 
        {name: UserRoles.STUDENT_ACCESS, value: UserRoles.STUDENT_ACCESS}];
}