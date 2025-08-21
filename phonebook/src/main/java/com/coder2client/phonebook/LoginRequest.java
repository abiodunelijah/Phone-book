@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
