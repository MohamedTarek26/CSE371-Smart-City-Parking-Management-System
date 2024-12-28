class UserDTO {
    constructor(userId, userName, userEmail, userPhone, roleId, licensePlate) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.roleId = roleId;
        this.licensePlate = licensePlate;
    }
}

export default UserDTO;