package org.otus;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ClientDto {
    private Long id;
    private String name;

    private String address;

    private String phones;
}
