@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;
    private String message;
}
