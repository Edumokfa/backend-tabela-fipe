package br.com.integracaoFipe.integracaoFipe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLogin {

    private String user;
    private String token;

}
