package com.library.security.filter;

/*@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
   @Autowired
   SecurityController securityController;

    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager= authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            log.info("username:", username);
            log.info("password:", password);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e) {
            System.out.println("false");
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        MyUserDetails userIn = (MyUserDetails) authentication.getPrincipal();


       // String password = request.getParameter("password");

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());


        long currentTimeMillis = System.currentTimeMillis();

        String access_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 2 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", userIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(userIn.getUsername())
                .withExpiresAt(new Date(currentTimeMillis + 5 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", userIn.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);

        Map<String, String> tokens =  new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }
}*/




