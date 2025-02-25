public User to(PasswordEncoder passwordEncoder) {
    return User.builder()
            .userName(this.userName)
            .email(this.email)
            .password(passwordEncoder.encode(this.password)) // Ensure password is encoded
            .role(Role.ROLE_USER) // Assign default role
            .build();
}